/**
 * 
 */
package cn.cf.entity;

import java.util.ArrayList;
import java.util.List;

import cn.cf.model.B2bBindOrder;
import cn.cf.model.B2bOrder;
import cn.cf.model.B2bOrderGoods;
import cn.cf.model.B2bReserveOrder;

/**
 * @author bin
 * 
 */
public class BatchOrder {
	private List<B2bOrderGoods> oglist = new ArrayList<B2bOrderGoods>();
	private List<B2bOrder> olist = new ArrayList<B2bOrder>();
	private List<B2bBindOrder> bolist = new ArrayList<B2bBindOrder>();
	private List<B2bReserveOrder> rolist = new ArrayList<B2bReserveOrder>();
	public List<B2bReserveOrder> getRolist() {
		return rolist;
	}

	public void setRolist(List<B2bReserveOrder> rolist) {
		this.rolist = rolist;
	}
 

	public List<B2bBindOrder> getBolist() {
		return bolist;
	}

	public void setBolist(List<B2bBindOrder> bolist) {
		this.bolist = bolist;
	}

	public List<B2bOrderGoods> getOglist() {
		return oglist;
	}

	public void setOglist(List<B2bOrderGoods> oglist) {
		this.oglist = oglist;
	}

	public List<B2bOrder> getOlist() {
		return olist;
	}

	public void setOlist(List<B2bOrder> olist) {
		this.olist = olist;
	}

}
