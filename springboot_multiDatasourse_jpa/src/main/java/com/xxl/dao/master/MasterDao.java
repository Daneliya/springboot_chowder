package com.xxl.dao.master;

import com.xxl.vo.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Description:
 * @Author: xxl
 * @Date: 2023/02/13 11:36
 * @Version: 1.0
 */
//@Repository
public interface MasterDao extends JpaRepository<User, Integer> {
}
