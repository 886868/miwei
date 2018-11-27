package com.comm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.comm.dao.RuleDao;
import com.comm.model.Rule;
import com.comm.service.RuleService;

@Service
@Transactional
public class RuleServiceImpl implements RuleService {
    
    @Autowired
    private RuleDao ruleDao;

    @Override
    public List<Rule> getRule() {
        return ruleDao.listAll();
    }

}
