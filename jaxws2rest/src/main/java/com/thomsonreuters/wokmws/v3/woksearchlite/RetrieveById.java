
package com.thomsonreuters.wokmws.v3.woksearchlite;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for retrieveById complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="retrieveById">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="databaseId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="uid" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/>
 *         &lt;element name="queryLanguage" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="retrieveParameters" type="{http://woksearchlite.v3.wokmws.thomsonreuters.com}retrieveParameters"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "retrieveById", propOrder = {
    "databaseId",
    "uid",
    "queryLanguage",
    "retrieveParameters"
})
public class RetrieveById {

    @XmlElement(required = true)
    protected String databaseId;
    @XmlElement(required = true)
    protected List<String> uid;
    @XmlElement(required = true)
    protected String queryLanguage;
    @XmlElement(required = true)
    protected RetrieveParameters retrieveParameters;

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
     * Gets the value of the uid property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the uid property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUid().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getUid() {
        if (uid == null) {
            uid = new ArrayList<String>();
        }
        return this.uid;
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

    /**
     * Gets the value of the retrieveParameters property.
     * 
     * @return
     *     possible object is
     *     {@link RetrieveParameters }
     *     
     */
    public RetrieveParameters getRetrieveParameters() {
        return retrieveParameters;
    }

    /**
     * Sets the value of the retrieveParameters property.
     * 
     * @param value
     *     allowed object is
     *     {@link RetrieveParameters }
     *     
     */
    public void setRetrieveParameters(RetrieveParameters value) {
        this.retrieveParameters = value;
    }

}
