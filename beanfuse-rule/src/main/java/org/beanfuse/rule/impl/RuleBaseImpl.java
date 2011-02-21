package org.beanfuse.rule.impl;

import java.util.List;

import org.beanfuse.collection.Order;
import org.beanfuse.persist.EntityService;
import org.beanfuse.query.EntityQuery;
import org.beanfuse.rule.RuleBase;
import org.beanfuse.rule.model.BusinessRule;

public class RuleBaseImpl implements RuleBase{
   
    private EntityService entityService;
    
    public List getRules() {
        EntityQuery query=new EntityQuery(BusinessRule.class,"rule");
        query.addOrder(Order.desc("rule.id"));
        return entityService.search(query);
    }

    public void setEntityService(EntityService entityService) {
        this.entityService = entityService;
    }
    
}
