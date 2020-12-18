/**
 * GoodsVisableResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.tempuri;

public class GoodsVisableResponse  implements java.io.Serializable {
    private java.lang.String goodsVisableResult;

    public GoodsVisableResponse() {
    }

    public GoodsVisableResponse(
           java.lang.String goodsVisableResult) {
           this.goodsVisableResult = goodsVisableResult;
    }


    /**
     * Gets the goodsVisableResult value for this GoodsVisableResponse.
     * 
     * @return goodsVisableResult
     */
    public java.lang.String getGoodsVisableResult() {
        return goodsVisableResult;
    }


    /**
     * Sets the goodsVisableResult value for this GoodsVisableResponse.
     * 
     * @param goodsVisableResult
     */
    public void setGoodsVisableResult(java.lang.String goodsVisableResult) {
        this.goodsVisableResult = goodsVisableResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GoodsVisableResponse)) return false;
        GoodsVisableResponse other = (GoodsVisableResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.goodsVisableResult==null && other.getGoodsVisableResult()==null) || 
             (this.goodsVisableResult!=null &&
              this.goodsVisableResult.equals(other.getGoodsVisableResult())));
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
        if (getGoodsVisableResult() != null) {
            _hashCode += getGoodsVisableResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GoodsVisableResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">GoodsVisableResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("goodsVisableResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "GoodsVisableResult"));
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
