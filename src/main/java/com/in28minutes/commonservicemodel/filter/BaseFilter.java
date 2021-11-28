package com.in28minutes.commonservicemodel.filter;

import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public class BaseFilter {
	
	private String id;
	private String createdOn;
	private String modifiedOn;
	private String createdBy;
	private String modifiedBy;

}
