package cn.cf.dto;

public class SxMaterialExtDto extends SxMaterialDto{

    private java.lang.String insertTimeStart;
    private java.lang.String insertTimeEnd;
    private String parentName;
    private String parentPk;
    private String isBlendName;
    public java.lang.String getInsertTimeStart() {
        return insertTimeStart;
    }
    public void setInsertTimeStart(java.lang.String insertTimeStart) {
        this.insertTimeStart = insertTimeStart;
    }
    public java.lang.String getInsertTimeEnd() {
        return insertTimeEnd;
    }
    public void setInsertTimeEnd(java.lang.String insertTimeEnd) {
        this.insertTimeEnd = insertTimeEnd;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    @Override
    public String getParentPk() {
        return parentPk;
    }

    @Override
    public void setParentPk(String parentPk) {
        this.parentPk = parentPk;
    }

    public String getIsBlendName() {
        return isBlendName;
    }

    public void setIsBlendName(String isBlendName) {
        this.isBlendName = isBlendName;
    }
}
