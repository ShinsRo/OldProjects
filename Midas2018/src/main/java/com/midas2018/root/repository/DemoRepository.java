package com.midas2018.root.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.stereotype.Repository;

import com.midas2018.root.model.User;

@Repository
public class DemoRepository {
    @Autowired
    private DemoUserMapper demoUserMapper;

    public User getUserById(long id) {
        return demoUserMapper.selectUser(id);
    }
}
