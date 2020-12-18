package cn.cf.model;


public class LgRegionsPk {
    private String provincePk ;
    private String cityPk;
    private String areaPk;
    private String townPk;
    public String getProvincePk() {
        return provincePk;
    }
    public void setProvincePk(String provincePk) {
        this.provincePk = provincePk;
    }
    public String getCityPk() {
        return cityPk;
    }
    public void setCityPk(String cityPk) {
        this.cityPk = cityPk;
    }
    public String getAreaPk() {
        return areaPk;
    }
    public void setAreaPk(String areaPk) {
        this.areaPk = areaPk;
    }
    public String getTownPk() {
        return townPk;
    }
    public void setTownPk(String townPk) {
        this.townPk = townPk;
    }
}
