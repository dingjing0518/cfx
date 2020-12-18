/**
 * BindGoodsAllResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.tempuri;

public class BindGoodsAllResponse  implements java.io.Serializable {
    private java.lang.String bindGoodsAllResult;

    public BindGoodsAllResponse() {
    }

    public BindGoodsAllResponse(
           java.lang.String bindGoodsAllResult) {
           this.bindGoodsAllResult = bindGoodsAllResult;
    }


    /**
     * Gets the bindGoodsAllResult value for this BindGoodsAllResponse.
     * 
     * @return bindGoodsAllResult
     */
    public java.lang.String getBindGoodsAllResult() {
        return bindGoodsAllResult;
    }


    /**
     * Sets the bindGoodsAllResult value for this BindGoodsAllResponse.
     * 
     * @param bindGoodsAllResult
     */
    public void setBindGoodsAllResult(java.lang.String bindGoodsAllResult) {
        this.bindGoodsAllResult = bindGoodsAllResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof BindGoodsAllResponse)) return false;
        BindGoodsAllResponse other = (BindGoodsAllResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.bindGoodsAllResult==null && other.getBindGoodsAllResult()==null) || 
             (this.bindGoodsAllResult!=null &&
              this.bindGoodsAllResult.equals(other.getBindGoodsAllResult())));
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
        if (getBindGoodsAllResult() != null) {
            _hashCode += getBindGoodsAllResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(BindGoodsAllResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">BindGoodsAllResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bindGoodsAllResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "BindGoodsAllResult"));
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
