package com.in28minutes.commonservicemodel.service;

import java.io.Serializable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.in28minutes.commonservicemodel.entity.BaseEntity;
import com.in28minutes.commonservicemodel.filter.BaseFilter;
import com.in28minutes.commonservicemodel.specification.BaseSpec;
import com.in28minutes.commonservicemodel.vo.BaseVO;

public interface GenericService<V extends BaseVO, F extends BaseFilter, S extends BaseSpec, T extends BaseEntity, ID extends Serializable> {

	V addUpdate(V vo);

	Page<V> getList(Pageable page, F f);

	boolean delete(ID id) ;

	S filter();
	
	T getTById(ID id);
	
	T getTById(ID id, Boolean isActive);
	
	V get(ID id, Boolean isActive);

}