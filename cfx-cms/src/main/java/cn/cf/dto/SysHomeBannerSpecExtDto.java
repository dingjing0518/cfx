package cn.cf.dto;


public class SysHomeBannerSpecExtDto  extends  SysHomeBannerSpecDto{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -7479690042039645221L;
	private java.lang.String insertTimeStart;
    private java.lang.String insertTimeEnd;

    private java.lang.String productName;
    private java.lang.Integer updateStatus;

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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(Integer updateStatus) {
        this.updateStatus = updateStatus;
    }
}
