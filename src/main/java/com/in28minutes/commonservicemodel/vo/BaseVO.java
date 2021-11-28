package com.in28minutes.commonservicemodel.vo;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1257641336287315818L;
	private String id;
	private String createdOn;
	private String modifiedOn;
	private String createdBy;
	private String modifiedBy;
	private boolean isActive;

}
