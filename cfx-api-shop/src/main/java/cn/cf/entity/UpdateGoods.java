/**
 * 
 */
package cn.cf.entity;


/**
 * @author bin
 * 
 */
public class UpdateGoods {
	private String pk;
	private Integer boxes;
	private Double weight;

	public UpdateGoods(String pk, Integer boxes, Double weight) {
		this.pk = pk;
		this.boxes = boxes;
		this.weight = weight;
	}

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	public Integer getBoxes() {
		return boxes;
	}

	public void setBoxes(Integer boxes) {
		this.boxes = boxes;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}



}
