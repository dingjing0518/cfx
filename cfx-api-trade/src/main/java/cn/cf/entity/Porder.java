/**
 * 
 */
package cn.cf.entity;

import java.util.ArrayList;
import java.util.List;

import cn.cf.dto.B2bAddressDto;
import cn.cf.dto.B2bBindOrderDto;
import cn.cf.dto.B2bCompanyDto;
import cn.cf.dto.B2bCompanyDtoEx;
import cn.cf.dto.B2bMemberDto;
import cn.cf.dto.B2bReserveOrderDto;
import cn.cf.dto.LogisticsModelDto;
import cn.cf.util.EncodeUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * @author bin
 * 
 */
public class Porder {
	private String logisticsModelPk;// 物流方式
	private String logisticsModelName;// 物流方式
	private String meno;// 备注
	private String addressPk;// 地址
	private List<Pgoods> carts;// 商品集合
	private String purchaserName;//代采采购商
	private String purchaserPk;//代采采购商
	private int source;//订单来源
	private int purchaseType;//订单类型1自采 2代采 3平台采
	private List<String> cartList;//商品集合
	private String offerPk;//竞拍出价单pk
	private Integer orderType;//订单类型
	private LogisticsModelDto lmdto;//物流信息
	private B2bAddressDto adto;//地址
	private B2bCompanyDto company;//采购商
	private B2bCompanyDto supplier;//供应商
	private B2bMemberDto member;//当前登录人
	private String bindPk;//捆绑Pk
	private Integer isBatches;
	private String invoicePk;//发票Pk
	private B2bCompanyDtoEx invoice;//发票
	private String deliverDetails;//送货要求
	private String otherNumber;//其他号码
	private String appointmentTime;//预约时间

	public Porder(B2bReserveOrderDto reserve,LogisticsModelDto lmdto,AddressInfo addressInfo,B2bCompanyDtoEx purchaser,B2bMemberDto member,B2bCompanyDtoEx invoice) {
       this.setLogisticsModelPk(reserve.getLogisticsModelPk());
       this.setLogisticsModelName(reserve.getLogisticsModelName());
       this.setMeno(reserve.getMeno());
       this.setPurchaseType(reserve.getPurchaseType());
       this.setSource(reserve.getSource());
       if(null!=lmdto){
    	   this.setLmdto(lmdto);
       }
       if(null!=addressInfo){
    	   B2bAddressDto adto=new B2bAddressDto();
    	   adto.setPk(addressInfo.getAddressPk());
    	   adto.setProvinceName(addressInfo.getProvinceName());
    	   adto.setCityName(addressInfo.getCityName());
    	   adto.setAreaName(addressInfo.getAreaName());
    	   adto.setTownName(addressInfo.getTownName());
    	   adto.setAddress(addressInfo.getAddress());
    	   adto.setContacts(addressInfo.getContacts());
    	   adto.setContactsTel(addressInfo.getContactsTel());
    	   adto.setSigningCompany(addressInfo.getSigningCompany());
    	   this.setAdto(adto);
    	   this.setAddressPk(addressInfo.getAddressPk());
       }
       this.setOrderType(5);
       if(null!=purchaser){
    	   this.setCompany(purchaser);
       }
       if(null!=member){
    	   this.setMember(member);
       }
       this.setOtherNumber(reserve.getOrderNumber());
       this.setIsBatches(reserve.getIsBatches());
       if(null!=invoice){
    	   this.setInvoice(invoice);
       }
       this.setDeliverDetails(reserve.getDeliverDetails());
       
	}
		

	public Porder(String value,B2bCompanyDto c,B2bMemberDto m) {
		this.company=c;
		this.member=m;
		JSONObject js = JSON.parseObject(value);
		logisticsModelPk = js.containsKey("logisticsModelPk") ? js
				.getString("logisticsModelPk") : "";
		logisticsModelName = EncodeUtil.URLDecode(js.containsKey("logisticsModelName") ? js
				.getString("logisticsModelName") : "");
		meno = js.containsKey("meno") ? js.getString("meno") : "";
		addressPk = js.containsKey("addressPk") ? js.getString("addressPk")
				: "";
		purchaserPk = js.containsKey("purchaserPk") ? js.getString("purchaserPk")
				: "";
		purchaserName = EncodeUtil.URLDecode(js.containsKey("purchaserName") ? js
				.getString("purchaserName") : "");
		bindPk=js.containsKey("bindPk")?js.getString("bindPk"):"";
		invoicePk = js.containsKey("invoicePk") ? js.getString("invoicePk")
				: "";
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
		offerPk = js.containsKey("offerPk") ? js.getString("offerPk"): "";
		isBatches = js.containsKey("isBatches") ? js.getInteger("isBatches"): 0;
		deliverDetails = js.containsKey("deliverDetails") ? js.getString("deliverDetails") : "";
		appointmentTime = js.containsKey("appointmentTime") ? js.getString("appointmentTime") : "";
	}



	public String getAppointmentTime() {
		return appointmentTime;
	}


	public void setAppointmentTime(String appointmentTime) {
		this.appointmentTime = appointmentTime;
	}


	public void setAddressPk(String addressPk) {
		this.addressPk = addressPk;
	}


	public B2bCompanyDtoEx getInvoice() {
		return invoice;
	}

	public void setInvoice(B2bCompanyDtoEx invoice) {
		this.invoice = invoice;
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

	

	public B2bCompanyDto getCompany() {
		return company;
	}

	public void setCompany(B2bCompanyDto company) {
		this.company = company;
	}

	public B2bMemberDto getMember() {
		return member;
	}

	public void setMember(B2bMemberDto member) {
		this.member = member;
	}

	public Integer getIsBatches() {
		return isBatches;
	}

	public void setIsBatches(Integer isBatches) {
		this.isBatches = isBatches;
	}

	public String getInvoicePk() {
		return invoicePk;
	}

	public void setInvoicePk(String invoicePk) {
		this.invoicePk = invoicePk;
	}

	public String getDeliverDetails() {
		return deliverDetails;
	}

	public void setDeliverDetails(String deliverDetails) {
		this.deliverDetails = deliverDetails;
	}
	public void setLogisticsModelPk(String logisticsModelPk) {
		this.logisticsModelPk = logisticsModelPk;
	}
	public void setLogisticsModelName(String logisticsModelName) {
		this.logisticsModelName = logisticsModelName;
	}
	public void setPurchaserName(String purchaserName) {
		this.purchaserName = purchaserName;
	}
	public void setPurchaserPk(String purchaserPk) {
		this.purchaserPk = purchaserPk;
	}
	public void setMeno(String meno) {
		this.meno = meno;
	}
	public String getOtherNumber() {
		return otherNumber;
	}
	public void setOtherNumber(String otherNumber) {
		this.otherNumber = otherNumber;
	}
	public B2bCompanyDto getSupplier() {
		return supplier;
	}
	public void setSupplier(B2bCompanyDto supplier) {
		this.supplier = supplier;
	}
	
	public Porder(B2bBindOrderDto bindOrder, LogisticsModelDto lmdtos, AddressInfo addressInfo, PurchaserInfo purchaserInfo, B2bMemberDto member2) {
		   this.setLogisticsModelPk(bindOrder.getLogisticsModelPk());
	       this.setLogisticsModelName(bindOrder.getLogisticsModelName());
	       this.setMeno(bindOrder.getMeno());
	       this.setPurchaseType(bindOrder.getPurchaseType());
	       this.setSource(bindOrder.getSource());
	       if(null!=lmdtos){
	    	   this.setLmdto(lmdtos);
	       }
	       if(null!=addressInfo){
	    	   B2bAddressDto adto=new B2bAddressDto();
	    	   adto.setPk(addressInfo.getAddressPk());
	    	   adto.setProvinceName(addressInfo.getProvinceName());
	    	   adto.setCityName(addressInfo.getCityName());
	    	   adto.setAreaName(addressInfo.getAreaName());
	    	   adto.setTownName(addressInfo.getTownName());
	    	   adto.setAddress(addressInfo.getAddress());
	    	   adto.setContacts(addressInfo.getContacts());
	    	   adto.setContactsTel(addressInfo.getContactsTel());
	    	   adto.setSigningCompany(addressInfo.getSigningCompany());
	    	   this.setAdto(adto);
	    	   this.setAddressPk(addressInfo.getAddressPk());
	       }
	       this.setOrderType(4);
	       if(null!=purchaserInfo){
	    	   B2bCompanyDtoEx company=new B2bCompanyDtoEx();
	    	   company.setPk(purchaserInfo.getPurchaserPk());
	    	   company.setName(purchaserInfo.getPurchaserName());
	    	   company.setContacts(purchaserInfo.getContacts());
	    	   company.setContactsTel(purchaserInfo.getContactsTel());
	    	   company.setOrganizationCode(purchaserInfo.getOrganizationCode());
	    	   company.setContactsTel(purchaserInfo.getContactsTel());
	    	   company.setBankAccount(purchaserInfo.getInvoiceBankAccount());
	    	   company.setBankName(purchaserInfo.getInvoiceBankName());
	    	   company.setRegAddress(purchaserInfo.getInvoiceRegAddress());
	    	   this.setCompany(company);
	    	   this.setInvoice(company);
	       }
	       if(null!=member2){
	    	   this.setMember(member2);
	       }
	       this.setOtherNumber(bindOrder.getOrderNumber());
	       this.setIsBatches(0);
	}
	 
}
