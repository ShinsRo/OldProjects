
package com.thomsonreuters.wokmws.v3.woksearchlite;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RawFaultInformation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RawFaultInformation">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="rawFaultstring" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="rawMessage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="rawReason" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="rawCause" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="rawRemedy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="messageData" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RawFaultInformation", propOrder = {
    "rawFaultstring",
    "rawMessage",
    "rawReason",
    "rawCause",
    "rawRemedy",
    "messageData"
})
public class RawFaultInformation {

    protected String rawFaultstring;
    protected String rawMessage;
    protected String rawReason;
    protected String rawCause;
    protected String rawRemedy;
    @XmlElement(nillable = true)
    protected List<String> messageData;

    /**
     * Gets the value of the rawFaultstring property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRawFaultstring() {
        return rawFaultstring;
    }

    /**
     * Sets the value of the rawFaultstring property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRawFaultstring(String value) {
        this.rawFaultstring = value;
    }

    /**
     * Gets the value of the rawMessage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRawMessage() {
        return rawMessage;
    }

    /**
     * Sets the value of the rawMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRawMessage(String value) {
        this.rawMessage = value;
    }

    /**
     * Gets the value of the rawReason property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRawReason() {
        return rawReason;
    }

    /**
     * Sets the value of the rawReason property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRawReason(String value) {
        this.rawReason = value;
    }

    /**
     * Gets the value of the rawCause property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRawCause() {
        return rawCause;
    }

    /**
     * Sets the value of the rawCause property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRawCause(String value) {
        this.rawCause = value;
    }

    /**
     * Gets the value of the rawRemedy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRawRemedy() {
        return rawRemedy;
    }

    /**
     * Sets the value of the rawRemedy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRawRemedy(String value) {
        this.rawRemedy = value;
    }

    /**
     * Gets the value of the messageData property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the messageData property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMessageData().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getMessageData() {
        if (messageData == null) {
            messageData = new ArrayList<String>();
        }
        return this.messageData;
    }

}
