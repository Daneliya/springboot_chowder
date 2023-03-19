package com.xxl.service;

import com.xxl.dao.master.MasterDao;
import com.xxl.dao.slaver.SlaverDao;
import com.xxl.vo.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description:
 * @Author: xxl
 * @Date: 2023/02/13 11:38
 * @Version: 1.0
 */
@Service
public class UserService {

    @Resource
    private MasterDao masterDao;

    @Resource
    private SlaverDao slaverDao;

    public List<User> getMaster() {
        return masterDao.findAll();
    }

    public List<User> getSlaver() {
        return slaverDao.findAll();
    }

}
