package com.comm.dao.impl;

import org.springframework.stereotype.Repository;

import com.comm.dao.ACommonDao;
import com.comm.dao.RuleDao;
import com.comm.model.Rule;

@Repository
public class RuleDaoImpl extends ACommonDao<Rule> implements RuleDao {

    protected RuleDaoImpl(Class<Rule> entityClass) {
        super(entityClass);
    }
    
    public RuleDaoImpl() {
        super(Rule.class);
    }

}
