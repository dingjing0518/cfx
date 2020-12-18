package cn.cf.dto;

public class ForB2BLgPriceDto extends BaseDTO implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
    private String lgLinePk;//线路pk
    private Double originalTotalFreight;//对外总价
    private Double basisLineTotalPrice;//对内总价
	public String getLgLinePk() {
		return lgLinePk;
	}
	public void setLgLinePk(String lgLinePk) {
		this.lgLinePk = lgLinePk;
	}
	public Double getOriginalTotalFreight() {
		return originalTotalFreight;
	}
	public void setOriginalTotalFreight(Double originalTotalFreight) {
		this.originalTotalFreight = originalTotalFreight;
	}
	public Double getBasisLineTotalPrice() {
		return basisLineTotalPrice;
	}
	public void setBasisLineTotalPrice(Double basisLineTotalPrice) {
		this.basisLineTotalPrice = basisLineTotalPrice;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
   
    
    
}
