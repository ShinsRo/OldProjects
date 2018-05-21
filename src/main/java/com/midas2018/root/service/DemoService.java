package com.midas2018.root.service;

import com.midas2018.root.model.DemoUserVO;
import com.midas2018.root.repository.DemoUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DemoService {

    @Autowired
    private DemoUserMapper demoUserMapper;

    public DemoUserVO findUserById(String id) {
        return demoUserMapper.selectUserById(id);
    }
}
