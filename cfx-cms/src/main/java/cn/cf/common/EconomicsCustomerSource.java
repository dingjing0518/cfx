package cn.cf.common;

/**
 * 业务来源枚举类
 */
public enum EconomicsCustomerSource {
    SOURCE_1(1, "盛虹推荐"),
    SOURCE_2(2, "自主申请"),
    SOURCE_3(3, "新凤鸣推荐"),
    SOURCE_4(4, "营销推荐"),
    SOURCE_5(5, "其他供应商推荐"),
    SOURCE_6(6, "CMS后台申请");
    private Integer pk;
    private String sourceName;

    EconomicsCustomerSource(Integer pk, String sourceName) {
        this.pk = pk;
        this.sourceName = sourceName;
    }

    public Integer getPk() {
        return pk;
    }

    public void setPk(Integer pk) {
        this.pk = pk;
    }

    public String getSourceName() {
        return sourceName;
    }

    public static String getSourceName(Integer pk) {
        EconomicsCustomerSource[] values = values();
        for (EconomicsCustomerSource value : values) {
            if (value.getPk().equals(pk)) {
                return value.getSourceName();
            }
        }
        return null;
    }
    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }
}