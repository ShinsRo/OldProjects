
package com.thomsonreuters.wokmws.v3.woksearchlite;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for queryParameters complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="queryParameters">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="databaseId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="userQuery" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="editions" type="{http://woksearchlite.v3.wokmws.thomsonreuters.com}editionDesc" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="symbolicTimeSpan" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="timeSpan" type="{http://woksearchlite.v3.wokmws.thomsonreuters.com}timeSpan" minOccurs="0"/>
 *         &lt;element name="queryLanguage" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "queryParameters", propOrder = {
    "databaseId",
    "userQuery",
    "editions",
    "symbolicTimeSpan",
    "timeSpan",
    "queryLanguage"
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QueryParameters {

    @XmlElement(required = true)
    protected String databaseId;
    @XmlElement(required = true)
    protected String userQuery;
    @XmlElement(nillable = true)
    protected List<EditionDesc> editions;
    protected String symbolicTimeSpan;
    protected TimeSpan timeSpan;
    @XmlElement(required = true)
    protected String queryLanguage;

    /**
     * Gets the value of the databaseId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDatabaseId() {
        return databaseId;
    }

    /**
     * Sets the value of the databaseId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDatabaseId(String value) {
        this.databaseId = value;
    }

    /**
     * Gets the value of the userQuery property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserQuery() {
        return userQuery;
    }

    /**
     * Sets the value of the userQuery property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserQuery(String value) {
        this.userQuery = value;
    }

    /**
     * Gets the value of the editions property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the editions property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEditions().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EditionDesc }
     * 
     * 
     */
    public List<EditionDesc> getEditions() {
        if (editions == null) {
            editions = new ArrayList<EditionDesc>();
        }
        return this.editions;
    }

    /**
     * Gets the value of the symbolicTimeSpan property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSymbolicTimeSpan() {
        return symbolicTimeSpan;
    }

    /**
     * Sets the value of the symbolicTimeSpan property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSymbolicTimeSpan(String value) {
        this.symbolicTimeSpan = value;
    }

    /**
     * Gets the value of the timeSpan property.
     * 
     * @return
     *     possible object is
     *     {@link TimeSpan }
     *     
     */
    public TimeSpan getTimeSpan() {
        return timeSpan;
    }

    /**
     * Sets the value of the timeSpan property.
     * 
     * @param value
     *     allowed object is
     *     {@link TimeSpan }
     *     
     */
    public void setTimeSpan(TimeSpan value) {
        this.timeSpan = value;
    }

    /**
     * Gets the value of the queryLanguage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQueryLanguage() {
        return queryLanguage;
    }

    /**
     * Sets the value of the queryLanguage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQueryLanguage(String value) {
        this.queryLanguage = value;
    }

}
