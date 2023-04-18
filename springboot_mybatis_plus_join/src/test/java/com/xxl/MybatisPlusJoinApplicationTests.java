package com.xxl;

import com.baomidou.mybatisplus.plugins.Page;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.xxl.entity.UserAddressDO;
import com.xxl.entity.UserDO;
import com.xxl.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class MybatisPlusJoinApplicationTests {

    @Resource
    private UserMapper userMapper;

    @Test
    void testJoin() {
        //和Mybatis plus一致，MPJLambdaWrapper的泛型必须是主表的泛型，并且要用主表的Mapper来调用
//        MPJLambdaWrapper<UserDO> wrapper = new MPJLambdaWrapper<UserDO>()
//                .selectAll(UserDO.class)//查询user表全部字段
//                .select(UserAddressDO::getTel)//查询user_address tel 字段
//                .selectAs(UserAddressDO::getAddress, UserDTO::getUserAddress)//别名 t.address AS userAddress
//                .select(AreaDO::getProvince, AreaDO::getCity)
//                .leftJoin(UserAddressDO.class, UserAddressDO::getUserId, UserDO::getId)
//                .leftJoin(AreaDO.class, AreaDO::getId, UserAddressDO::getAreaId)
//                .eq(UserDO::getId, 1)
//                .like(UserAddressDO::getTel, "1")
//                .gt(UserDO::getId, 5);
//
//        //连表查询 返回自定义ResultType
//        List<UserDTO> list = userMapper.selectJoinList(UserDTO.class, wrapper);
//
//        //分页查询 （需要启用 mybatis plus 分页插件）
//        Page<UserDTO> listPage = userMapper.selectJoinPage(new Page<>(2, 10), UserDTO.class, wrapper);
    }

}
