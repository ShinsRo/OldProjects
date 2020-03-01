
package com.thomsonreuters.wokmws.v3.woksearchlite;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SupportingWebServiceException complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SupportingWebServiceException">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="remoteNamespace" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="remoteOperation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="remoteCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="remoteReason" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="handshakeCauseId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="handshakeCause" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SupportingWebServiceException", propOrder = {
    "remoteNamespace",
    "remoteOperation",
    "remoteCode",
    "remoteReason",
    "handshakeCauseId",
    "handshakeCause"
})
public class SupportingWebServiceException {

    protected String remoteNamespace;
    protected String remoteOperation;
    protected String remoteCode;
    protected String remoteReason;
    protected String handshakeCauseId;
    protected String handshakeCause;

    /**
     * Gets the value of the remoteNamespace property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRemoteNamespace() {
        return remoteNamespace;
    }

    /**
     * Sets the value of the remoteNamespace property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRemoteNamespace(String value) {
        this.remoteNamespace = value;
    }

    /**
     * Gets the value of the remoteOperation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRemoteOperation() {
        return remoteOperation;
    }

    /**
     * Sets the value of the remoteOperation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRemoteOperation(String value) {
        this.remoteOperation = value;
    }

    /**
     * Gets the value of the remoteCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRemoteCode() {
        return remoteCode;
    }

    /**
     * Sets the value of the remoteCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRemoteCode(String value) {
        this.remoteCode = value;
    }

    /**
     * Gets the value of the remoteReason property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRemoteReason() {
        return remoteReason;
    }

    /**
     * Sets the value of the remoteReason property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRemoteReason(String value) {
        this.remoteReason = value;
    }

    /**
     * Gets the value of the handshakeCauseId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHandshakeCauseId() {
        return handshakeCauseId;
    }

    /**
     * Sets the value of the handshakeCauseId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHandshakeCauseId(String value) {
        this.handshakeCauseId = value;
    }

    /**
     * Gets the value of the handshakeCause property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHandshakeCause() {
        return handshakeCause;
    }

    /**
     * Sets the value of the handshakeCause property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHandshakeCause(String value) {
        this.handshakeCause = value;
    }

}
