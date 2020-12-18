package cn.cf.entity;

import java.util.Date;

public class EconomicsBuildProperty {
	  	private String pk;
	  	//房地产抵押金额
	    private Integer minMortgage ;
	    private Integer maxMortgage ;
	    //房地产抵押金额/授信总金额
	    private Integer minTotalMortgage;
	    private Integer maxTotalMortgage;
	    //得分
	    private Integer score;
	    //状态
	    private Integer open;
	    private Date insertTime;
	    
	    private String startTime;
	    private String endTime;
	    private String mortgageName;
	    private String totalMortgageName;
	    private String insertStartTime;
	    private String insertEndTime;
	    
	    
		public String getInsertStartTime() {
			return insertStartTime;
		}
		public void setInsertStartTime(String insertStartTime) {
			this.insertStartTime = insertStartTime;
		}
		public String getInsertEndTime() {
			return insertEndTime;
		}
		public void setInsertEndTime(String insertEndTime) {
			this.insertEndTime = insertEndTime;
		}
		public String getPk() {
			return pk;
		}
		public void setPk(String pk) {
			this.pk = pk;
		}
		public Integer getMinMortgage() {
			return minMortgage;
		}
		public void setMinMortgage(Integer minMortgage) {
			this.minMortgage = minMortgage;
		}
		public Integer getMaxMortgage() {
			return maxMortgage;
		}
		public void setMaxMortgage(Integer maxMortgage) {
			this.maxMortgage = maxMortgage;
		}
		public Integer getMinTotalMortgage() {
			return minTotalMortgage;
		}
		public void setMinTotalMortgage(Integer minTotalMortgage) {
			this.minTotalMortgage = minTotalMortgage;
		}
		public Integer getMaxTotalMortgage() {
			return maxTotalMortgage;
		}
		public void setMaxTotalMortgage(Integer maxTotalMortgage) {
			this.maxTotalMortgage = maxTotalMortgage;
		}
		public Integer getScore() {
			return score;
		}
		public void setScore(Integer score) {
			this.score = score;
		}
		public Integer getOpen() {
			return open;
		}
		public void setOpen(Integer open) {
			this.open = open;
		}
		public Date getInsertTime() {
			return insertTime;
		}
		public void setInsertTime(Date insertTime) {
			this.insertTime = insertTime;
		}
		public String getStartTime() {
			return startTime;
		}
		public void setStartTime(String startTime) {
			this.startTime = startTime;
		}
		public String getEndTime() {
			return endTime;
		}
		public void setEndTime(String endTime) {
			this.endTime = endTime;
		}
		public String getMortgageName() {
			return mortgageName;
		}
		public void setMortgageName(String mortgageName) {
			this.mortgageName = mortgageName;
		}
		public String getTotalMortgageName() {
			return totalMortgageName;
		}
		public void setTotalMortgageName(String totalMortgageName) {
			this.totalMortgageName = totalMortgageName;
		}
	    

}
