
package com.thomsonreuters.wokmws.v3.woksearchlite;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for search complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="search">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="queryParameters" type="{http://woksearchlite.v3.wokmws.thomsonreuters.com}queryParameters"/>
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
@XmlType(name = "search", propOrder = {
    "queryParameters",
    "retrieveParameters"
})
public class Search {

    @XmlElement(required = true)
    protected QueryParameters queryParameters;
    @XmlElement(required = true)
    protected RetrieveParameters retrieveParameters;

    /**
     * Gets the value of the queryParameters property.
     * 
     * @return
     *     possible object is
     *     {@link QueryParameters }
     *     
     */
    public QueryParameters getQueryParameters() {
        return queryParameters;
    }

    /**
     * Sets the value of the queryParameters property.
     * 
     * @param value
     *     allowed object is
     *     {@link QueryParameters }
     *     
     */
    public void setQueryParameters(QueryParameters value) {
        this.queryParameters = value;
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
