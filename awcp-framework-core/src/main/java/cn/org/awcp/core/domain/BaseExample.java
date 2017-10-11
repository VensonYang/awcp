package cn.org.awcp.core.domain;

import java.util.ArrayList;
import java.util.List;

public class BaseExample {
    protected String orderByClause;
    protected boolean distinct;
    protected int start;
    protected int limit;
    protected List<Criteria> oredCriteria;
    
    public BaseExample() {
        oredCriteria = new ArrayList<Criteria>();
    }
    
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }
    
    public String getOrderByClause() {
        return orderByClause;
    }
    
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }
    
    public boolean isDistinct() {
        return distinct;
    }
    
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }
    
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }
    
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }
    
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }
    
    public void setOredCriteria(List<Criteria> oredCriteria) {
		this.oredCriteria = oredCriteria;
	}
    
	protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }
	
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }
    
	public int getStart() {
		return start;
	}
	
	public void setStart(int start) {
		this.start = start;
	}
	
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}

}
