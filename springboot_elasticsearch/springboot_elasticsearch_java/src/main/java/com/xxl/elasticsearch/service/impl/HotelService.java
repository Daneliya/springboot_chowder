package com.xxl.elasticsearch.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.FunctionScoreQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.RangeQuery;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.TotalHits;
import co.elastic.clients.json.JsonData;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxl.elasticsearch.dto.HotelDoc;
import com.xxl.elasticsearch.mapper.HotelMapper;
import com.xxl.elasticsearch.pojo.Hotel;
import com.xxl.elasticsearch.pojo.PageResult;
import com.xxl.elasticsearch.pojo.RequestParams;
import com.xxl.elasticsearch.service.IHotelService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 酒店服务实现层
 *
 * @author xxl
 * @date 2026/2/24 11:34
 */
@Slf4j
@Service
public class HotelService extends ServiceImpl<HotelMapper, Hotel> implements IHotelService {

    @Autowired
    private ElasticsearchClient elasticsearchClient;

    @Override
    public PageResult search(RequestParams params) {
        try {
            // 1.准备请求参数
            int page = params.getPage() != null ? params.getPage() : 1;
            int size = params.getSize() != null ? params.getSize() : 20;

            // 2.构建查询
            SearchResponse<HotelDoc> response = elasticsearchClient.search(s -> {
                        // 基础查询配置
                        s.index("hotel")
                                .query(buildBasicQuery(params))
                                .from((page - 1) * size)
                                .size(size);
                        // 仅当location不为空时，才添加地理距离排序
                        String location = params.getLocation();
                        if (StringUtils.isNotBlank(location)) {

                            // 解析经纬度（建议添加格式校验，避免数组越界/格式错误）
                            String[] latLonArr = location.split(",");
                            if (latLonArr.length != 2) {
                                throw new IllegalArgumentException("位置参数格式错误，应为：纬度,经度");
                            }
                            double lat = Double.parseDouble(latLonArr[0].trim());
                            double lon = Double.parseDouble(latLonArr[1].trim());
                            // 添加地理距离排序
                            s.sort(sort -> sort.geoDistance(g -> g
                                    .field("location")
                                    .distanceType(co.elastic.clients.elasticsearch._types.GeoDistanceType.Arc)
                                    .location(l -> l.latlon(ll -> ll
                                            .lat(lat)
                                            .lon(lon)))
                                    .order(SortOrder.Asc)
                                    .unit(co.elastic.clients.elasticsearch._types.DistanceUnit.Kilometers)
                            ));
                        }
                        return s;
                    },
                    HotelDoc.class
            );

            // 3.解析响应
            return handleResponse(response);
        } catch (IOException e) {
            throw new RuntimeException("搜索数据失败", e);
        }
    }

    @Override
    public Map<String, List<String>> getFilters(RequestParams params) {
        try {
            // 1.准备聚合查询
            SearchResponse<HotelDoc> response = elasticsearchClient.search(s -> s
                            .index("hotel")
                            .query(buildBasicQuery(params))
                            .size(0)
                            .aggregations("brandAgg", a -> a
                                    .terms(t -> t.field("brand").size(100)))
                            .aggregations("cityAgg", a -> a
                                    .terms(t -> t.field("city").size(100)))
                            .aggregations("starAgg", a -> a
                                    .terms(t -> t.field("starName").size(100))),
                    HotelDoc.class
            );

            // 2.解析聚合结果
            Map<String, List<String>> filters = new HashMap<>(3);
            filters.put("brand", getAggregationValues(response, "brandAgg"));
            filters.put("city", getAggregationValues(response, "cityAgg"));
            filters.put("starName", getAggregationValues(response, "starAgg"));

            return filters;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<String> getSuggestion(String key) {
        try {
            // 1.准备建议查询
            SearchResponse<HotelDoc> response = elasticsearchClient.search(s -> s
                            .index("hotel")
                            .suggest(sug -> sug
                                    .suggesters("hotelSuggest", sugg -> sugg
                                            .prefix(key)
                                            .completion(c -> c
                                                    .field("suggestion")
                                                    .size(10)
                                                    .skipDuplicates(true)
                                            )
                                    )),
                    HotelDoc.class
            );

            // 2.解析建议结果
            List<String> suggestions = new ArrayList<>();
            if (response.suggest() != null && response.suggest().get("hotelSuggest") != null) {
                response.suggest().get("hotelSuggest").forEach(suggestion ->
                        suggestion.completion().options().forEach(option ->
                                suggestions.add(option.text())));
            }

            return suggestions;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(Long hotelId) {
        try {
            // 删除文档
            elasticsearchClient.delete(d -> d
                    .index("hotel")
                    .id(hotelId.toString()));
        } catch (IOException e) {
            throw new RuntimeException("删除酒店数据失败", e);
        }
    }

    @Override
    public void saveById(Long hotelId) {
        try {
            // 查询酒店数据，应该基于Feign远程调用hotel-admin，根据id查询酒店数据（现在直接去数据库查）
            Hotel hotel = getById(hotelId);
            // 转换
            HotelDoc hotelDoc = new HotelDoc(hotel);

            // 保存文档
            elasticsearchClient.index(i -> i
                    .index("hotel")
                    .id(hotelId.toString())
                    .document(hotelDoc));
        } catch (IOException e) {
            throw new RuntimeException("新增酒店数据失败", e);
        }
    }

    /**
     * 解析聚合结果
     *
     * @param response 结果集
     * @param aggName  字段名
     * @return 返回结果
     */
    private List<String> getAggregationValues(SearchResponse<HotelDoc> response, String aggName) {
        List<String> values = new ArrayList<>();
        if (response.aggregations() != null && response.aggregations().get(aggName) != null) {
            response.aggregations().get(aggName).sterms().buckets().array().forEach(bucket ->
                    values.add(bucket.key().stringValue()));
        }
        return values;
    }

    /**
     * 基础查询配置
     *
     * @param params 参数
     * @return 返回结果
     */
    private Query buildBasicQuery(RequestParams params) {
        // 1.准备Boolean查询
        BoolQuery.Builder boolQueryBuilder = new BoolQuery.Builder();

        // 1.1.关键字搜索
        String key = params.getKey();
        if (StringUtils.isNotBlank(key)) {
            boolQueryBuilder.must(m -> m
                    .match(mt -> mt
                            .field("all")
                            .query(key)));
        } else {
            boolQueryBuilder.must(m -> m.matchAll(ma -> ma));
        }

        // 1.2.品牌过滤
        String brand = params.getBrand();
        if (StringUtils.isNotBlank(brand)) {
            boolQueryBuilder.filter(f -> f
                    .term(t -> t
                            .field("brand")
                            .value(brand)));
        }

        // 1.3.城市过滤
        String city = params.getCity();
        if (StringUtils.isNotBlank(city)) {
            boolQueryBuilder.filter(f -> f
                    .term(t -> t
                            .field("city")
                            .value(city)));
        }

        // 1.4.星级过滤
        String starName = params.getStarName();
        if (StringUtils.isNotBlank(starName)) {
            boolQueryBuilder.filter(f -> f
                    .term(t -> t
                            .field("starName")
                            .value(starName)));
        }

        // 1.5.价格范围过滤
        Integer minPrice = params.getMinPrice();
        Integer maxPrice = params.getMaxPrice();
        if (minPrice != null || maxPrice != null) {
            RangeQuery.Builder rangeQueryBuilder = new RangeQuery.Builder()
                    .field("price");

            if (minPrice != null) {
                rangeQueryBuilder.gte(JsonData.of(minPrice));
            }
            if (maxPrice != null && maxPrice > 0) {
                rangeQueryBuilder.lte(JsonData.of(maxPrice));
            }

            boolQueryBuilder.filter(f -> f.range(rangeQueryBuilder.build()));
        }

        // 2.添加算分函数
        FunctionScoreQuery.Builder functionScoreBuilder = new FunctionScoreQuery.Builder()
                .query(boolQueryBuilder.build()._toQuery())
                .functions(func -> func
                        .filter(f -> f
                                .term(t -> t
                                        .field("isAD")
                                        .value(true)))
                        .weight(10.0));

        return new Query.Builder()
                .functionScore(functionScoreBuilder.build())
                .build();
    }

    /**
     * 解析响应
     *
     * @param response 结果集
     * @return 返回结果
     */
    private PageResult handleResponse(SearchResponse<HotelDoc> response) {
        // 1.获取总条数
        TotalHits totalHits = response.hits().total();
        long total = totalHits != null ? totalHits.value() : 0;

        // 2.获取文档数组
        List<HotelDoc> hotels = new ArrayList<>();
        for (Hit<HotelDoc> hit : response.hits().hits()) {
            HotelDoc hotelDoc = hit.source();
            if (hotelDoc != null) {
                //处理排序距离信息
                if (hit.sort() != null && !hit.sort().isEmpty()) {
                    hotelDoc.setDistance(hit.sort().get(0));
                }
                hotels.add(hotelDoc);
            }
        }

        return new PageResult(total, hotels);
    }
}