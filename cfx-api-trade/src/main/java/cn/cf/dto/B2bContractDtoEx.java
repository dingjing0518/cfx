package cn.cf.dto;

import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;

import cn.cf.constant.Block;
import cn.cf.entity.AddressInfo;
import cn.cf.entity.B2bPayVoucher;
import cn.cf.entity.Cgoods;
import cn.cf.entity.Corder;
import cn.cf.entity.OrderRecord;
import cn.cf.entity.PurchaserInfo;
import cn.cf.entity.SupplierInfo;
import cn.cf.util.DateUtil;

public class B2bContractDtoEx extends B2bContractDto{
	
	private List<B2bContractGoodsDtoEx> contractGoods;
	
	private List<B2bPayVoucher> payVoucherList;
	
	private List<OrderRecord> orderRecordList;
	
	private String purchaserMobile;
	
	private Integer logisticsModelType;
	
	private SysCompanyBankcardDto bankDto;
	
	private String memberMobile;
	
	private Double actualAmount;
	
	private String spContactsTel;
	
	private String signingCompany;
	private Integer loanNumberStatus;
	private Integer onlinePayStatus;
	private Integer billPayStatus;
	private String billGoodsType;
	private Integer showPft;
	private String bank;
	
	public B2bContractDtoEx() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public B2bContractDtoEx(Corder o,String contractNo,Double totalAmount,Double totalWeight,B2bCompanyDto p,B2bCompanyDto purchaser,B2bMemberDto member,B2bAddressDto address,B2bStoreDto store,Cgoods cdtoEx) {
		this.setContractNo(contractNo);
		this.setContractStatus(1);
		this.setContractSource(o.getContractSource());
		this.setSaleDepartment(o.getSaleDepartment());
		this.setStartTime(DateUtil.parseDateFormatYmd(o.getStartTime()));
		this.setEndTime(DateUtil.parseDateFormatYmd(o.getEndTime()));
		this.setDays(o.getDays());
		this.setCovenant(o.getCovenant());
		this.setSupplementary(o.getSupplementary());
		this.setPriceType(o.getPriceType());
		this.setMember(member.getMobile());
		if(null != cdtoEx.getMemberDto()){
			this.setEmployeePk(cdtoEx.getMemberDto().getPk());
			this.setSalesman(cdtoEx.getMemberDto().getEmployeeName());
			this.setSalesmanNumber( cdtoEx.getMemberDto().getEmployeeNumber());
		}
		//this.setSupplierPk(p.getPk());
		this.setPurchaserPk(purchaser.getPk());
		this.setContractType(1);
		this.setPurchaserType(o.getPurchaserType());
		this.setContractRate(o.getContractRate());
		this.setContractCount(totalWeight);
		this.setOrderAmount(totalAmount);
		this.setTotalAmount(totalAmount);
		if(null != o.getLmdto()){
			this.setLogisticsModel(o.getLmdto().getName());
			this.setLogisticsModelPk(o.getLmdto().getPk());
			this.setLogisticsModelType(o.getLmdto().getType());
		}
		if(null != address){
			AddressInfo adto = new AddressInfo(address.getPk(), address.getProvinceName(), 
					address.getCityName(), address.getAreaName(), address.getTownName(),
					address.getAddress(), address.getContacts(), address.getContactsTel(), address.getSigningCompany());
			this.setAddressInfo(JSON.toJSONString(adto));
		}
		this.setIsDelete(1);
		this.setInsertTime(new Date());
		this.setUpdateTime(new Date());
		this.setStorePk(store.getPk());
		this.setStoreName(store.getName());
		this.setCarrier(cdtoEx.getLogisticsCarrierName());
		this.setCarrierPk(cdtoEx.getLogisticsCarrierPk());
		this.setSource(o.getSource());
		this.setPlantPk(o.getPlantPk());
		this.setPlantName(o.getPlantName());
		//采购商信息
				PurchaserInfo purchaserInfo = new PurchaserInfo(purchaser.getPk(), 
						purchaser.getName(), purchaser.getContacts(),
						purchaser.getContactsTel(), purchaser.getOrganizationCode(),
						o.getInvoicePk(), o.getInvoice().getName(), o.getInvoice().getOrganizationCode(), 
						o.getInvoice().getContactsTel(), o.getInvoice().getBankAccount(), 
						o.getInvoice().getBankName(),o.getInvoice().getRegAddress());
				this.setPurchaserInfo(JSON.toJSONString(purchaserInfo));
				this.setPurchaserPk(purchaser.getPk());
				//供应商信息
				SupplierInfo supplier = new SupplierInfo(p.getPk(), p.getName(),p.getOrganizationCode(),p.getContacts(),
						p.getContactsTel(), null, null,null);
				this.setSupplierPk(p.getPk());
				this.setSupplierInfo(JSON.toJSONString(supplier));
				this.setBlock(contractNo.contains(Block.CF.getContractType())?Block.CF.getValue():Block.SX.getValue());
	}

 
	public List<B2bContractGoodsDtoEx> getContractGoods() {
		return contractGoods;
	}


	public void setContractGoods(List<B2bContractGoodsDtoEx> contractGoods) {
		this.contractGoods = contractGoods;
	}


	public List<B2bPayVoucher> getPayVoucherList() {
		return payVoucherList;
	}

	public void setPayVoucherList(List<B2bPayVoucher> payVoucherList) {
		this.payVoucherList = payVoucherList;
	}

	public List<OrderRecord> getOrderRecordList() {
		return orderRecordList;
	}

	public void setOrderRecordList(List<OrderRecord> orderRecordList) {
		this.orderRecordList = orderRecordList;
	}

	public String getPurchaserMobile() {
		return purchaserMobile;
	}

	public void setPurchaserMobile(String purchaserMobile) {
		this.purchaserMobile = purchaserMobile;
	}

	public Integer getLogisticsModelType() {
		return logisticsModelType;
	}

	public void setLogisticsModelType(Integer logisticsModelType) {
		this.logisticsModelType = logisticsModelType;
	}

	public SysCompanyBankcardDto getBankDto() {
		return bankDto;
	}

	public void setBankDto(SysCompanyBankcardDto bankDto) {
		this.bankDto = bankDto;
	}

	public String getMemberMobile() {
		return memberMobile;
	}

	public void setMemberMobile(String memberMobile) {
		this.memberMobile = memberMobile;
	}

	public Double getActualAmount() {
		return actualAmount;
	}

	public void setActualAmount(Double actualAmount) {
		this.actualAmount = actualAmount;
	}

	public String getSpContactsTel() {
		return spContactsTel;
	}

	public void setSpContactsTel(String spContactsTel) {
		this.spContactsTel = spContactsTel;
	}

	public String getSigningCompany() {
		return signingCompany;
	}

	public void setSigningCompany(String signingCompany) {
		this.signingCompany = signingCompany;
	}


	public Integer getLoanNumberStatus() {
		return loanNumberStatus;
	}


	public void setLoanNumberStatus(Integer loanNumberStatus) {
		this.loanNumberStatus = loanNumberStatus;
	}


	public Integer getOnlinePayStatus() {
		return onlinePayStatus;
	}


	public void setOnlinePayStatus(Integer onlinePayStatus) {
		this.onlinePayStatus = onlinePayStatus;
	}


	public Integer getBillPayStatus() {
		return billPayStatus;
	}


	public void setBillPayStatus(Integer billPayStatus) {
		this.billPayStatus = billPayStatus;
	}


	public String getBillGoodsType() {
		return billGoodsType;
	}


	public void setBillGoodsType(String billGoodsType) {
		this.billGoodsType = billGoodsType;
	}


	public Integer getShowPft() {
		return showPft;
	}


	public void setShowPft(Integer showPft) {
		this.showPft = showPft;
	}


	public String getBank() {
		return bank;
	}


	public void setBank(String bank) {
		this.bank = bank;
	}
	
	

	
	
	
}
