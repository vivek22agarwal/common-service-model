package com.in28minutes.commonservicemodel.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public class BaseEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2843063617472649660L;
	
	@Id
	@GeneratedValue(generator = "custom-uuid")
	@GenericGenerator(name = "custom-uuid", strategy = "com.in28minutes.commonservicemodel.util.CustomUUIDGenerator")
	private String id;
	private Date createdOn;
	private Date modifiedOn;
	private String createdBy;
	private String modifiedBy;
	private boolean isActive;

}
