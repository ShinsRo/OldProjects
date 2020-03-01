
package com.thomsonreuters.wokmws.v3.woksearch;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import com.thomsonreuters.wokmws.v3.woksearchlite.AuthenticationException;
import com.thomsonreuters.wokmws.v3.woksearchlite.ESTIWSException;
import com.thomsonreuters.wokmws.v3.woksearchlite.InternalServerException;
import com.thomsonreuters.wokmws.v3.woksearchlite.InvalidInputException;
import com.thomsonreuters.wokmws.v3.woksearchlite.QueryException;
import com.thomsonreuters.wokmws.v3.woksearchlite.SessionException;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.thomsonreuters.wokmws.v3.woksearch package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _QueryException_QNAME = new QName("http://woksearch.v3.wokmws.thomsonreuters.com", "QueryException");
    private final static QName _ESTIWSException_QNAME = new QName("http://woksearch.v3.wokmws.thomsonreuters.com", "ESTIWSException");
    private final static QName _SessionException_QNAME = new QName("http://woksearch.v3.wokmws.thomsonreuters.com", "SessionException");
    private final static QName _InternalServerException_QNAME = new QName("http://woksearch.v3.wokmws.thomsonreuters.com", "InternalServerException");
    private final static QName _AuthenticationException_QNAME = new QName("http://woksearch.v3.wokmws.thomsonreuters.com", "AuthenticationException");
    private final static QName _InvalidInputException_QNAME = new QName("http://woksearch.v3.wokmws.thomsonreuters.com", "InvalidInputException");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.thomsonreuters.wokmws.v3.woksearch
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://woksearch.v3.wokmws.thomsonreuters.com", name = "QueryException")
    public JAXBElement<QueryException> createQueryException(QueryException value) {
        return new JAXBElement<QueryException>(_QueryException_QNAME, QueryException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ESTIWSException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://woksearch.v3.wokmws.thomsonreuters.com", name = "ESTIWSException")
    public JAXBElement<ESTIWSException> createESTIWSException(ESTIWSException value) {
        return new JAXBElement<ESTIWSException>(_ESTIWSException_QNAME, ESTIWSException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SessionException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://woksearch.v3.wokmws.thomsonreuters.com", name = "SessionException")
    public JAXBElement<SessionException> createSessionException(SessionException value) {
        return new JAXBElement<SessionException>(_SessionException_QNAME, SessionException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InternalServerException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://woksearch.v3.wokmws.thomsonreuters.com", name = "InternalServerException")
    public JAXBElement<InternalServerException> createInternalServerException(InternalServerException value) {
        return new JAXBElement<InternalServerException>(_InternalServerException_QNAME, InternalServerException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AuthenticationException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://woksearch.v3.wokmws.thomsonreuters.com", name = "AuthenticationException")
    public JAXBElement<AuthenticationException> createAuthenticationException(AuthenticationException value) {
        return new JAXBElement<AuthenticationException>(_AuthenticationException_QNAME, AuthenticationException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InvalidInputException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://woksearch.v3.wokmws.thomsonreuters.com", name = "InvalidInputException")
    public JAXBElement<InvalidInputException> createInvalidInputException(InvalidInputException value) {
        return new JAXBElement<InvalidInputException>(_InvalidInputException_QNAME, InvalidInputException.class, null, value);
    }

}
