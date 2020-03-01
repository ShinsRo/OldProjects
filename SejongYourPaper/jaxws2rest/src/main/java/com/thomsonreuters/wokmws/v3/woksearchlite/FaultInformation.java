
package com.thomsonreuters.wokmws.v3.woksearchlite;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FaultInformation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FaultInformation">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="code" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="message" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="reason" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="causeType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cause" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="supportingWebServiceException" type="{http://woksearchlite.v3.wokmws.thomsonreuters.com}SupportingWebServiceException" minOccurs="0"/>
 *         &lt;element name="remedy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FaultInformation", propOrder = {
    "code",
    "message",
    "reason",
    "causeType",
    "cause",
    "supportingWebServiceException",
    "remedy"
})
public class FaultInformation {

    @XmlElement(required = true)
    protected String code;
    protected String message;
    protected String reason;
    protected String causeType;
    protected String cause;
    protected SupportingWebServiceException supportingWebServiceException;
    protected String remedy;

    /**
     * Gets the value of the code property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the value of the code property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCode(String value) {
        this.code = value;
    }

    /**
     * Gets the value of the message property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the value of the message property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessage(String value) {
        this.message = value;
    }

    /**
     * Gets the value of the reason property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReason() {
        return reason;
    }

    /**
     * Sets the value of the reason property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReason(String value) {
        this.reason = value;
    }

    /**
     * Gets the value of the causeType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCauseType() {
        return causeType;
    }

    /**
     * Sets the value of the causeType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCauseType(String value) {
        this.causeType = value;
    }

    /**
     * Gets the value of the cause property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCause() {
        return cause;
    }

    /**
     * Sets the value of the cause property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCause(String value) {
        this.cause = value;
    }

    /**
     * Gets the value of the supportingWebServiceException property.
     * 
     * @return
     *     possible object is
     *     {@link SupportingWebServiceException }
     *     
     */
    public SupportingWebServiceException getSupportingWebServiceException() {
        return supportingWebServiceException;
    }

    /**
     * Sets the value of the supportingWebServiceException property.
     * 
     * @param value
     *     allowed object is
     *     {@link SupportingWebServiceException }
     *     
     */
    public void setSupportingWebServiceException(SupportingWebServiceException value) {
        this.supportingWebServiceException = value;
    }

    /**
     * Gets the value of the remedy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRemedy() {
        return remedy;
    }

    /**
     * Sets the value of the remedy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRemedy(String value) {
        this.remedy = value;
    }

}
