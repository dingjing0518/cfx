package cn.cf.dto;

import java.util.List;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class B2bContractExtDto extends B2bContractDto{

	private String insertTimeStart;
	private String insertTimeEnd;
	private String payTimeStart;
	private String payTimeEnd;

	private String purchaserTel;
	private String signingCompany;
	private String signingAcc;
	private String signingTel;
	private List<String> payVoucherUrl;

	private String purchaserTelCol;
	private String signingCompanyCol;
	private String signingAccCol;
	private String signingTelCol;
	private String storeNameCol;
	private String purchaserNameCol;
	private String toAddressCol;

	private String modelType;

	private String toAddress;

	private String purchaserName;

	private String telephone;
	private String contractStatusName;
	private String closeReason;
	public String getInsertTimeStart() {
		return insertTimeStart;
	}

	public void setInsertTimeStart(String insertTimeStart) {
		this.insertTimeStart = insertTimeStart;
	}

	public String getInsertTimeEnd() {
		return insertTimeEnd;
	}

	public void setInsertTimeEnd(String insertTimeEnd) {
		this.insertTimeEnd = insertTimeEnd;
	}

	public String getPayTimeStart() {
		return payTimeStart;
	}

	public void setPayTimeStart(String payTimeStart) {
		this.payTimeStart = payTimeStart;
	}

	public String getPayTimeEnd() {
		return payTimeEnd;
	}

	public void setPayTimeEnd(String payTimeEnd) {
		this.payTimeEnd = payTimeEnd;
	}

	public String getPurchaserTel() {
		return purchaserTel;
	}

	public void setPurchaserTel(String purchaserTel) {
		this.purchaserTel = purchaserTel;
	}

	public String getSigningCompany() {
		return signingCompany;
	}

	public void setSigningCompany(String signingCompany) {
		this.signingCompany = signingCompany;
	}

	public String getSigningAcc() {
		return signingAcc;
	}

	public void setSigningAcc(String signingAcc) {
		this.signingAcc = signingAcc;
	}

	public String getSigningTel() {
		return signingTel;
	}

	public void setSigningTel(String signingTel) {
		this.signingTel = signingTel;
	}

	public List<String> getPayVoucherUrl() {
		return payVoucherUrl;
	}

	public void setPayVoucherUrl(List<String> payVoucherUrl) {
		this.payVoucherUrl = payVoucherUrl;
	}

	public String getPurchaserTelCol() {
		return purchaserTelCol;
	}

	public void setPurchaserTelCol(String purchaserTelCol) {
		this.purchaserTelCol = purchaserTelCol;
	}

	public String getSigningCompanyCol() {
		return signingCompanyCol;
	}

	public void setSigningCompanyCol(String signingCompanyCol) {
		this.signingCompanyCol = signingCompanyCol;
	}

	public String getSigningAccCol() {
		return signingAccCol;
	}

	public void setSigningAccCol(String signingAccCol) {
		this.signingAccCol = signingAccCol;
	}

	public String getSigningTelCol() {
		return signingTelCol;
	}

	public void setSigningTelCol(String signingTelCol) {
		this.signingTelCol = signingTelCol;
	}

	public String getStoreNameCol() {
		return storeNameCol;
	}

	public void setStoreNameCol(String storeNameCol) {
		this.storeNameCol = storeNameCol;
	}

	public String getPurchaserNameCol() {
		return purchaserNameCol;
	}

	public void setPurchaserNameCol(String purchaserNameCol) {
		this.purchaserNameCol = purchaserNameCol;
	}

	public String getToAddressCol() {
		return toAddressCol;
	}

	public void setToAddressCol(String toAddressCol) {
		this.toAddressCol = toAddressCol;
	}

	public String getModelType() {
		return modelType;
	}

	public void setModelType(String modelType) {
		this.modelType = modelType;
	}

	public String getToAddress() {
		return toAddress;
	}

	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}

	public String getPurchaserName() {
		return purchaserName;
	}

	public void setPurchaserName(String purchaserName) {
		this.purchaserName = purchaserName;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getContractStatusName() {
		return contractStatusName;
	}

	public void setContractStatusName(String contractStatusName) {
		this.contractStatusName = contractStatusName;
	}

	public String getCloseReason() {
		return closeReason;
	}

	public void setCloseReason(String closeReason) {
		this.closeReason = closeReason;
	}
}