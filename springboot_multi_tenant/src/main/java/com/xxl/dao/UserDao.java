package com.xxl.dao;

import com.xxl.vo.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xxl
 * @date 2022/5/5 23:26
 */
@Repository
public class UserDao extends BaseDao {

    public List<User> get() {
        //通过 baseDao 区分数据源（不再通过jdbc）
        String sql = "select * from t_user";
        return super.getJdbcTemplate().query(sql, (rs, rowNum) -> {
            User user = new User();
            user.setId(rs.getInt("dbid"));
            user.setUname(rs.getString("uname"));
            user.setPwd(rs.getString("pwd"));
            user.setAge(rs.getInt("age"));
            return user;
        });
    }

}
