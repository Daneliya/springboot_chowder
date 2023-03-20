package com.xxl.elasticsearch.constants;

/**
 * @Description:
 * @Author: xxl
 * @Date: 2023/03/18 9:54
 * @Version: 1.0
 */
public class LoginConstants {
    public static final String MAPPING_TEMPLATE =
            "{\n" +
                    "  \"mappings\": {\n" +
                    "    \"properties\": {\n" +
                    "      \"userId\": {\n" +
                    "        \"type\": \"long\",\n" +
                    "        \"index\": true\n" +
                    "      },\n" +
                    "      \"mobile\": {\n" +
                    "        \"type\": \"long\",\n" +
                    "        \"index\": true\n" +
                    "      },\n" +
                    "      \"tenantId\": {\n" +
                    "        \"type\": \"integer\",\n" +
                    "        \"index\": true\n" +
                    "      }\n" +
                    "    }\n" +
                    "  }\n" +
                    "}";
}
