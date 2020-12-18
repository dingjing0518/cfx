package cn.cf.entity;

public class EconCustDatumRuleEntry {

	
	private String deviceType;
	
	private String useType;
	
	private Integer machineNumber;
	
	private Integer machineYear;
	
	private String score;
	
	
	

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getUseType() {
		return useType;
	}

	public void setUseType(String useType) {
		this.useType = useType;
	}

	public Integer getMachineNumber() {
		return machineNumber;
	}

	public void setMachineNumber(Integer machineNumber) {
		this.machineNumber = machineNumber;
	}

	public Integer getMachineYear() {
		return machineYear;
	}

	public void setMachineYear(Integer machineYear) {
		this.machineYear = machineYear;
	}
	
}
