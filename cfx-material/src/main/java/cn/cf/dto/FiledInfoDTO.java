package cn.cf.dto;

import java.io.Serializable;
import java.lang.reflect.Type;

@SuppressWarnings("serial")
public class FiledInfoDTO implements Serializable {
	private Type type;
	private String name;
	private Object value;

	public FiledInfoDTO() {

	}

	public FiledInfoDTO(Type type, String name, Object value) {
		this();
		this.setType(type);
		this.setName(name);
		this.setValue(value);
	}

	public Type getType() {
		return this.type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object getValue() {
		return this.value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

//	@Override
//	public String toString() {
//		return "FiledInfo [getType()=" + this.getType() + ", getName()="
//				+ this.getName() + ", getValue()=" + this.getValue() + "]";
//	}

}
