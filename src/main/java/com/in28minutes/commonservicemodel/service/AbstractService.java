package com.in28minutes.commonservicemodel.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.in28minutes.commonservicemodel.entity.BaseEntity;
import com.in28minutes.commonservicemodel.filter.BaseFilter;
import com.in28minutes.commonservicemodel.repository.CustomRepo;
import com.in28minutes.commonservicemodel.specification.BaseSpec;
import com.in28minutes.commonservicemodel.vo.BaseVO;

public abstract class AbstractService<V extends BaseVO, F extends BaseFilter, S extends BaseSpec, T extends BaseEntity, ID extends Serializable> implements GenericService<V, F, S, T, ID> {
	
	@Autowired
	private CustomRepo<T, ID> repo;
	
	@Override
	public V addUpdate(V vo) {
		
		T t = this.convertToT(vo);
		V v = this.convertToV(repo.save(t));
		return v;
		
	}
	
	@Override
	public T getTById(ID id) {
		Optional<T> t = repo.findById(id);
		if(t.isPresent()) {
			return t.get();
		}
		return null;
	}

	@Override
	public T getTById(ID id, Boolean isActive) {
		Optional<T> t = repo.findByIdAndIsActive(id, isActive);
		if(t.isPresent()) {
			return t.get();
		}
		return null;
	}

	@Override
	public V get(ID id, Boolean isActive) {
		T t = this.getTById(id, isActive);
		V v = this.convertToV(t);
		return v;
	}

	@Override
	public Page<V> getList(Pageable page, F f){
		
		Pageable pageableWithoutSort = null;
		if(this.checkIfFilterAndSortManually(page,f)) {
			pageableWithoutSort = PageRequest.of(0, Integer.MAX_VALUE);
		}else {
			pageableWithoutSort = PageRequest.of(page.getPageNumber(), page.getPageSize());
		}
		S s = this.filter();
		Page<T> tPage = repo.findAll(s.filter(f,page.getSort()),pageableWithoutSort);
		if(tPage!=null && !tPage.getContent().isEmpty()) {
			Page<V> vPage = this.getPageVO(tPage,f);
			return vPage;
		}
		return null;
		
	}
	
	@Override
	public boolean delete(ID id) {
		Optional<T> t= repo.findById(id);
		if(t.isPresent()) {
			repo.delete(t.get());
			return true;
		}
		return false;
	}

	private Page<V> getPageVO(Page<T> tPage, F f) {
		List<V> vList = new ArrayList<>();
		for(T t : tPage.getContent()) {
			V v = this.convertToV(t);
			vList.add(v);
		}
		Page<V> page = new PageImpl<>(vList,tPage.getPageable(),tPage.getTotalElements());
		return page;
	}
	
	

	public boolean checkIfFilterAndSortManually(Pageable page, F f) {
		S s = this.filter();
		return s.sort(page.getSort())!=null || s.filter(f) != null;
	}

	@Override
	public S filter() {
		return null;
	}

	private V convertToV(final T input) {
		V output = null;
		if(input!=null) {
			output = this.applyV(input);
		}
		return output;
	}


	private T convertToT(final V input) {
		T output = null;
		if(input!=null) {
			output = this.apply(input);
		}
		return output;
	}

	public T apply(V input) {
		return null;
	}

	public V applyV(T t) {
		return null;
	}

}
