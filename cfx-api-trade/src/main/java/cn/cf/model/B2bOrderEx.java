package cn.cf.model;

import java.util.Date;

import cn.cf.constant.Block;
import cn.cf.dto.B2bCompanyDto;
import cn.cf.dto.B2bContractDto;
import cn.cf.dto.B2bMemberDto;
import cn.cf.dto.B2bStoreDto;
import cn.cf.entity.AddressInfo;
import cn.cf.entity.Porder;
import cn.cf.entity.PurchaserInfo;
import cn.cf.entity.SupplierInfo;
import cn.cf.util.ArithUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class B2bOrderEx extends B2bOrder{

	
	private static final long serialVersionUID = 1L;
	
	public B2bOrderEx(Porder o,B2bCompanyDto s,
			B2bStoreDto store,String orderNumber,Integer orderStatus,Double amount,B2bMemberDto mdto) {
		this.setPurchaseType(o.getPurchaseType());
		this.setOrderNumber(orderNumber);
		this.setOrderAmount(ArithUtil.round(amount, 2));
		this.setActualAmount(this.getOrderAmount());
		//采购商信息
		PurchaserInfo purchaser = new PurchaserInfo(o.getCompany().getPk(), 
				o.getCompany().getName(),o.getCompany().getContacts(), 
				o.getCompany().getContactsTel(), o.getCompany().getOrganizationCode(),
				o.getInvoicePk(), o.getInvoice().getName(), o.getInvoice().getOrganizationCode(), 
				o.getInvoice().getContactsTel(), o.getInvoice().getBankAccount(), 
				o.getInvoice().getBankName(),o.getInvoice().getRegAddress());
		this.setPurchaserInfo(JSON.toJSONString(purchaser));
		this.setPurchaserPk(null == o.getCompany()?"":o.getCompany().getPk());
		//供应商信息
		SupplierInfo supplier = new SupplierInfo(s.getPk(), s.getName(),s.getOrganizationCode(),s.getContacts(),
				s.getContactsTel(), null, null,null);
		this.setSupplierPk(s.getPk());
		this.setSupplierInfo(JSON.toJSONString(supplier));
		//收货地址信息
		if(null != o.getAddressPk() && !"".equals(o.getAddressPk())){
			AddressInfo address = new AddressInfo(o.getAddressPk(), o.getAdto().getProvinceName(),
					o.getAdto().getCityName(), o.getAdto().getAreaName(), o.getAdto().getTownName(),
					 o.getAdto().getAddress(), o.getAdto().getContacts(),
					o.getAdto().getContactsTel(), o.getAdto().getSigningCompany());
			this.setAddressInfo(JSON.toJSONString(address));
		}
		this.setOrderStatus(null==orderStatus?1:orderStatus);//默认存待付款 
		this.setInsertTime(new Date());
		this.setMeno(o.getMeno());
		this.setMemberPk(o.getMember().getPk());
		this.setMemberName(o.getMember().getMobile());
		this.setStoreName(store.getName());
		this.setStorePk(store.getPk());
		this.setSource(o.getSource());
		this.setLogisticsModelPk(o.getLogisticsModelPk());
		this.setLogisticsModelName(o.getLogisticsModelName());
		this.setEmployeePk(mdto==null?"":mdto.getPk());
		this.setEmployeeName(mdto==null?"":mdto.getEmployeeName()==null?"":mdto.getEmployeeName());
		this.setEmployeeNumber(mdto==null?"":mdto.getEmployeeNumber()==null?"":mdto.getEmployeeNumber());
		this.setOrderClassify(o.getOrderType());
		this.setIsBatches(o.getIsBatches());
		this.setDeliverDetails(o.getDeliverDetails());
		this.setOtherNumber(o.getOtherNumber());
		this.setBlock(orderNumber.contains(Block.CF.getOrderType())?Block.CF.getValue():Block.SX.getValue());
	}
	
	

	public B2bOrderEx(B2bContractDto contract, String orderNumber,
			double orderAmount,B2bMemberDto member,String address) {
		this.setOrderNumber(orderNumber);
		this.setOrderClassify(2);//合同订单
		this.setOtherNumber(contract.getContractNo());
		this.setPurchaseType(contract.getPurchaserType());
		this.setOrderAmount(ArithUtil.round(orderAmount, 2));
		this.setActualAmount(this.getOrderAmount());
		this.setPurchaserPk(contract.getPurchaserPk());
		this.setSupplierPk(contract.getSupplierPk());
		this.setOrderStatus(0);//待审核
		this.setInsertTime(new Date());
		this.setMemberPk(member.getPk());
		this.setMemberName(member.getMobile());
		this.setStoreName(contract.getStoreName());
		this.setStorePk(contract.getStorePk());
		this.setSource(contract.getSource());
		this.setLogisticsModelPk(contract.getLogisticsModelPk());
		this.setLogisticsModelName(contract.getLogisticsModel());
			this.setEmployeePk(contract.getEmployeePk());
			this.setEmployeeName(contract.getSalesman());
			this.setEmployeeNumber(contract.getSalesmanNumber());
		this.setPaymentName(contract.getPaymentName());
		this.setPaymentTime(contract.getPaymentTime() );
		this.setPaymentType(contract.getPaymentType());
		this.setEconomicsGoodsName(contract.getEconomicsGoodsName());
		this.setEconomicsGoodsType(contract.getEconomicsGoodsType());
		this.setOtherNumber(contract.getContractNo());
		this.setPurchaserInfo(contract.getPurchaserInfo());
		this.setSupplierInfo(contract.getSupplierInfo());
		this.setAddressInfo(contract.getAddressInfo());
		if(null!=address&&!"".equals(address)&&null!=this.getAddressInfo()&&!"".equals(this.getAddressInfo())){
			JSONObject js=JSON.parseObject(this.getAddressInfo());
			js.put("address", address);
			this.setAddressInfo(js.toJSONString());
			
		}
		this.setBlock(contract.getBlock());
	}
}
