package cn.cf.entity;

import org.activiti.engine.impl.persistence.entity.CommentEntity;

public class CommentEntityEx extends  CommentEntity{
	
	private String state ;

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	
}
