package com.xxl.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxl.security.entity.Menu;

import java.util.List;

/**
 * @author xxl
 * @date 2022/12/19 23:51
 */
public interface MenuMapper extends BaseMapper<Menu> {

    List<String> selectPermsByUserId(Long id);
}
