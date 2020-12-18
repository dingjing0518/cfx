package cn.cf.dto;

public class MarketingPersonnelExtDto extends MarketingPersonnelDto {
    
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String regionName;
    private String area;
    public String getRegionName() {
        return regionName;
    }
    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }
    public String getArea() {
        return area;
    }
    public void setArea(String area) {
        this.area = area;
    }
    public static long getSerialversionuid() {
        return serialVersionUID;
    }
   
  
    

}
