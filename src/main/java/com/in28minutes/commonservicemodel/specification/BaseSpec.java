package com.in28minutes.commonservicemodel.specification;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.util.StreamUtils;

import com.in28minutes.commonservicemodel.entity.BaseEntity;
import com.in28minutes.commonservicemodel.filter.BaseFilter;
import com.in28minutes.commonservicemodel.vo.BaseVO;

public abstract class BaseSpec <T extends BaseEntity, F extends BaseFilter, V extends BaseVO>{
	
	public abstract Specification<T> filter(F f, Sort sort);
	
	public Predicate<V> filter(F f){
		List<Predicate<V>> andPredicates = new ArrayList<Predicate<V>>();
		Predicate<V> predicate = null;
		if(f.getCreatedBy() != null && !f.getCreatedBy().trim().isEmpty()) {
			andPredicates.add(v->v.getCreatedBy()!=null && v.getCreatedBy().toLowerCase().contains(f.getCreatedBy().toLowerCase()));
		}
		if(f.getModifiedBy() != null && !f.getModifiedBy().trim().isEmpty()) {
			andPredicates.add(v->v.getModifiedBy()!=null && v.getModifiedBy().toLowerCase().contains(f.getCreatedBy().toLowerCase()));
		}
		
		for(Predicate _predicate : andPredicates) {
			if(predicate == null)
				predicate = _predicate;
			else
				predicate = _predicate.and(predicate);
		}
		return predicate;
	}
	
	public Comparator<V> sort(Sort sort){
		if(sort != null && !sort.isUnsorted()) {
			List<Sort.Order> orders = StreamUtils.createStreamFromIterator(sort.iterator()).collect(Collectors.toList());
			for(Sort.Order order : orders) {
				switch(order.getProperty()) {
				case "createdBy":
					if(order.isAscending())
						return (v1,v2)-> v1.getCreatedBy().compareTo(v2.getCreatedBy());
					else
						return (v1,v2)-> v2.getCreatedBy().compareTo(v2.getCreatedBy());
				case "modifiedBy":
					if(order.isAscending())
						return (v1,v2)-> v1.getCreatedBy().compareTo(v2.getCreatedBy());
					else
						return (v1,v2)-> v2.getCreatedBy().compareTo(v2.getCreatedBy());
				default :
					return null;
				}
			}
		}
		return null;
	}
	protected static Order orderBy(Root root, CriteriaBuilder builder, Boolean asc, String propertyName) {
		if(asc != null && asc)
			return builder.asc(root.get(propertyName));
		else
			return builder.desc(root.get(propertyName));
	}

}
