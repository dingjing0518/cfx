/**
 * 
 */
package cn.cf.entity;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.cf.dto.B2bAddressDto;
import cn.cf.dto.B2bCompanyDtoEx;
import cn.cf.dto.B2bMemberDto;
import cn.cf.dto.LogisticsModelDto;
import cn.cf.util.EncodeUtil;

/**
 * @author bin
 * 
 */
public class Porder {
	private String logisticsModelPk;// 物流方式
	private String logisticsModelName;// 物流方式
	private String meno;// 备注
	private String addressPk;// 地址
	private String invoicePk;// 发票
	private List<Pgoods> carts;// 商品集合
	private String purchaserName;//采购商
	private String purchaserPk;//采购商
	private int source;//订单来源
	private int purchaseType;//订单类型1自采 2代采 3平台采
	private List<String> cartList;
	private String offerPk;//竞拍出价单pk
	private Integer orderType;//订单类型
	private LogisticsModelDto lmdto;
	private B2bAddressDto adto;
	private B2bCompanyDtoEx company;
	private B2bMemberDto member;
	private String bindPk;
	
	public Porder() {
		// TODO Auto-generated constructor stub
	}
	
	public Porder(String value,B2bCompanyDtoEx c,B2bMemberDto m) {
		this.company=c;
		this.member=m;
		JSONObject js = JSONObject.parseObject(value);
		logisticsModelPk = js.containsKey("logisticsModelPk") ? js
				.getString("logisticsModelPk") : "";
		logisticsModelName = EncodeUtil.URLDecode(js.containsKey("logisticsModelName") ? js
				.getString("logisticsModelName") : "");
		meno = js.containsKey("meno") ? js.getString("meno") : "";
		addressPk = js.containsKey("addressPk") ? js.getString("addressPk")
				: "";
		invoicePk = js.containsKey("invoicePk") ? js.getString("invoicePk")
				: "";
		purchaserPk = js.containsKey("purchaserPk") ? js.getString("purchaserPk")
				: "";
		purchaserName = EncodeUtil.URLDecode(js.containsKey("purchaserName") ? js
				.getString("purchaserName") : "");
		bindPk=js.containsKey("bindPk")?js.getString("bindPk"):"";
		JSONArray gs = js.containsKey("carts") ? js.getJSONArray("carts")
				: null;
		if (null != gs && gs.size() > 0) {
			carts = new ArrayList<Pgoods>();
			cartList = new ArrayList<String>();
			Pgoods pg = null;
			for (int i = 0; i < gs.size(); i++) {
				pg=new Pgoods(gs.getJSONObject(i));
				carts.add(pg);
				if(null!=pg.getCartPk()&&!"".equals(pg.getCartPk())){
					cartList.add(pg.getCartPk());
				}
			
			}
		}
		offerPk = js.containsKey("offerPk") ? js.getString("offerPk")
				: "";
		
	}

	public String getBindPk() {
		return bindPk;
	}

	public void setBindPk(String bindPk) {
		this.bindPk = bindPk;
	}

	public List<String> getCartList() {
		return cartList;
	}

	public void setCartList(List<String> cartList) {
		this.cartList = cartList;
	}
	
	public List<Pgoods> getCarts() {
		return carts;
	}

	public void setCarts(List<Pgoods> carts) {
		this.carts = carts;
	}

	public String getLogisticsModelPk() {
		return logisticsModelPk;
	}

	public String getLogisticsModelName() {
		return logisticsModelName;
	}


	public String getOfferPk() {
		return offerPk;
	}

	public void setOfferPk(String offerPk) {
		this.offerPk = offerPk;
	}

	public int getSource() {
		return source;
	}

	public String getMeno() {
		return meno;
	}

	public String getAddressPk() {
		return addressPk;
	}

	public String getInvoicePk() {
		return invoicePk;
	}

	public String getPurchaserName() {
		return purchaserName;
	}

 
	public String getPurchaserPk() {
		return purchaserPk;
	}

	public void setSource(int source) {
		this.source = source;
	}

	public int getPurchaseType() {
		return purchaseType;
	}

	public void setPurchaseType(int purchaseType) {
		this.purchaseType = purchaseType;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public LogisticsModelDto getLmdto() {
		return lmdto;
	}

	public void setLmdto(LogisticsModelDto lmdto) {
		this.lmdto = lmdto;
	}

	public B2bAddressDto getAdto() {
		return adto;
	}

	public void setAdto(B2bAddressDto adto) {
		this.adto = adto;
	}

	public B2bCompanyDtoEx getCompany() {
		return company;
	}

	public void setCompany(B2bCompanyDtoEx company) {
		this.company = company;
	}

	public B2bMemberDto getMember() {
		return member;
	}

	public void setMember(B2bMemberDto member) {
		this.member = member;
	}
	
	
		

	 
}
