/**
 * SubmitContractResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.tempuri;

public class SubmitContractResponse  implements java.io.Serializable {
    private java.lang.String submitContractResult;

    public SubmitContractResponse() {
    }

    public SubmitContractResponse(
           java.lang.String submitContractResult) {
           this.submitContractResult = submitContractResult;
    }


    /**
     * Gets the submitContractResult value for this SubmitContractResponse.
     * 
     * @return submitContractResult
     */
    public java.lang.String getSubmitContractResult() {
        return submitContractResult;
    }


    /**
     * Sets the submitContractResult value for this SubmitContractResponse.
     * 
     * @param submitContractResult
     */
    public void setSubmitContractResult(java.lang.String submitContractResult) {
        this.submitContractResult = submitContractResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SubmitContractResponse)) return false;
        SubmitContractResponse other = (SubmitContractResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.submitContractResult==null && other.getSubmitContractResult()==null) || 
             (this.submitContractResult!=null &&
              this.submitContractResult.equals(other.getSubmitContractResult())));
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
        if (getSubmitContractResult() != null) {
            _hashCode += getSubmitContractResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SubmitContractResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">SubmitContractResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("submitContractResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "SubmitContractResult"));
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
