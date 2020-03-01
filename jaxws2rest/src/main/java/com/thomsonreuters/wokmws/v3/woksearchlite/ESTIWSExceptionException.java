
package com.thomsonreuters.wokmws.v3.woksearchlite;

import javax.xml.ws.WebFault;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebFault(name = "ESTIWSException", targetNamespace = "http://woksearch.v3.wokmws.thomsonreuters.com")
public class ESTIWSExceptionException
    extends Exception
{

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private ESTIWSException faultInfo;

    /**
     * 
     * @param faultInfo
     * @param message
     */
    public ESTIWSExceptionException(String message, ESTIWSException faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param faultInfo
     * @param cause
     * @param message
     */
    public ESTIWSExceptionException(String message, ESTIWSException faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: com.thomsonreuters.wokmws.v3.woksearchlite.ESTIWSException
     */
    public ESTIWSException getFaultInfo() {
        return faultInfo;
    }

}
