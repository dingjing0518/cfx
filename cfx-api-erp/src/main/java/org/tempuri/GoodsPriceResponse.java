/**
 * GoodsPriceResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.tempuri;

public class GoodsPriceResponse  implements java.io.Serializable {
    private java.lang.String goodsPriceResult;

    public GoodsPriceResponse() {
    }

    public GoodsPriceResponse(
           java.lang.String goodsPriceResult) {
           this.goodsPriceResult = goodsPriceResult;
    }


    /**
     * Gets the goodsPriceResult value for this GoodsPriceResponse.
     * 
     * @return goodsPriceResult
     */
    public java.lang.String getGoodsPriceResult() {
        return goodsPriceResult;
    }


    /**
     * Sets the goodsPriceResult value for this GoodsPriceResponse.
     * 
     * @param goodsPriceResult
     */
    public void setGoodsPriceResult(java.lang.String goodsPriceResult) {
        this.goodsPriceResult = goodsPriceResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GoodsPriceResponse)) return false;
        GoodsPriceResponse other = (GoodsPriceResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.goodsPriceResult==null && other.getGoodsPriceResult()==null) || 
             (this.goodsPriceResult!=null &&
              this.goodsPriceResult.equals(other.getGoodsPriceResult())));
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
        if (getGoodsPriceResult() != null) {
            _hashCode += getGoodsPriceResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GoodsPriceResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">GoodsPriceResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("goodsPriceResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "GoodsPriceResult"));
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
