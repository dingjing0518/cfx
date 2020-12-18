/**
 * 
 */
package cn.cf.entity;

import javax.persistence.Id;

/**
 * @author bin
 * 
 */
public class OrderRecord {
	@Id
	private String id;
	private String orderNumber;
	private String childOrderNumber;
	private String content;
	private String insertTime;
	private Integer type;//1 上传凭证  其他
	private String imgUrl;//图片地址
	private Integer status;//订单状态(1待付款2确认付款3待发货4已发货5确认收货6已完成)
	private String mobile;
	private String block;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getChildOrderNumber() {
		return childOrderNumber;
	}

	public void setChildOrderNumber(String childOrderNumber) {
		this.childOrderNumber = childOrderNumber;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getBlock() {
		return block;
	}

	public void setBlock(String block) {
		this.block = block;
	}
	
}
