package com.in28minutes.commonservicemodel.restcontroller;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.in28minutes.commonservicemodel.entity.BaseEntity;
import com.in28minutes.commonservicemodel.filter.BaseFilter;
import com.in28minutes.commonservicemodel.service.GenericService;
import com.in28minutes.commonservicemodel.specification.BaseSpec;
import com.in28minutes.commonservicemodel.vo.BaseVO;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import springfox.documentation.annotations.ApiIgnore;

public abstract class RESTController <V extends BaseVO, F extends BaseFilter, S extends BaseSpec, T extends BaseEntity, ID extends Serializable>{
	
	@Autowired
	GenericService<V,F,S,T,ID> service;
	
	public static final String CONTROLLERURL = "";
	public static final String ADDUPDATECONTROLLERURL = "/8000";
	public static final String LISTCONTROLLERURL = "/8200";
	public static final String GETBYIDCONTROLLERURL = "/8400";
	public static final String DELETEBYIDCONTROLLERURL = "/8600";
	
	@PostMapping(ADDUPDATECONTROLLERURL)
	public ResponseEntity<V> addUpdate(@RequestBody V v){
		return new ResponseEntity<V>(service.addUpdate(v), HttpStatus.OK);
	}
	
	@GetMapping(GETBYIDCONTROLLERURL + "{/id}")
	public ResponseEntity<V> get(@PathVariable ID id, @RequestParam(required = false) Boolean isActive){
		return new ResponseEntity<V>(service.get(id,isActive), HttpStatus.OK);
	}
	
	@GetMapping(DELETEBYIDCONTROLLERURL + "{/id}")
	public ResponseEntity<V> delete(@PathVariable ID id){
		service.delete(id);
		return new ResponseEntity<V>(HttpStatus.OK);
	}
	
	@PostMapping(LISTCONTROLLERURL)
	@ApiImplicitParams({@ApiImplicitParam(name = "page", dataType = "integer", paramType = "query", value = "Results page you want to retrieve(0..N)"),
			@ApiImplicitParam(name = "size", dataType = "integer", paramType = "query", value = "Number of records per page."),
			@ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query", value = "Sorting criteria in the format: property(,asc|desc). "
					+ "Default sort order is ascending. " + "Multiple sort criteria is supported.")})
	public Page<V> getList(@ApiIgnore Pageable pageRequest, @RequestBody(required = false) F f){
		return service.getList(pageRequest,f);
	}

}
