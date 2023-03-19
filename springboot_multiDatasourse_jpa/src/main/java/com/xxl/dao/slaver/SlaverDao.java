package com.xxl.dao.slaver;

import com.xxl.vo.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Description:
 * @Author: xxl
 * @Date: 2023/02/13 11:37
 * @Version: 1.0
 */
public interface SlaverDao extends JpaRepository<User, Integer> {
}
