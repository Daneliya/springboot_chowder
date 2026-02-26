package com.xxl.elasticsearch.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxl.elasticsearch.mapper.HotelMapper;
import com.xxl.elasticsearch.pojo.Hotel;
import com.xxl.elasticsearch.service.IHotelService;
import org.springframework.stereotype.Service;

/**
 * @author xxl
 * @date 2026/2/24 11:34
 */
@Service
public class HotelService extends ServiceImpl<HotelMapper, Hotel> implements IHotelService {
}

