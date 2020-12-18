/**
 * LogisticsRuleResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.tempuri;

public class LogisticsRuleResponse  implements java.io.Serializable {
    private java.lang.String logisticsRuleResult;

    public LogisticsRuleResponse() {
    }

    public LogisticsRuleResponse(
           java.lang.String logisticsRuleResult) {
           this.logisticsRuleResult = logisticsRuleResult;
    }


    /**
     * Gets the logisticsRuleResult value for this LogisticsRuleResponse.
     * 
     * @return logisticsRuleResult
     */
    public java.lang.String getLogisticsRuleResult() {
        return logisticsRuleResult;
    }


    /**
     * Sets the logisticsRuleResult value for this LogisticsRuleResponse.
     * 
     * @param logisticsRuleResult
     */
    public void setLogisticsRuleResult(java.lang.String logisticsRuleResult) {
        this.logisticsRuleResult = logisticsRuleResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof LogisticsRuleResponse)) return false;
        LogisticsRuleResponse other = (LogisticsRuleResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.logisticsRuleResult==null && other.getLogisticsRuleResult()==null) || 
             (this.logisticsRuleResult!=null &&
              this.logisticsRuleResult.equals(other.getLogisticsRuleResult())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getLogisticsRuleResult() != null) {
            _hashCode += getLogisticsRuleResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(LogisticsRuleResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">LogisticsRuleResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("logisticsRuleResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "LogisticsRuleResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
