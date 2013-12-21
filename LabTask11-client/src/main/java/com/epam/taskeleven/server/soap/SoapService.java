
package com.epam.taskeleven.server.soap;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebService(name = "soapService", targetNamespace = "http://www.epam.com/taskeleven/server/soap")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({
    ObjectFactory.class
})
public interface SoapService {


    /**
     * 
     * @param serviceRequest
     * @return
     *     returns com.epam.taskeleven.server.soap.ServiceResponse
     */
    @WebMethod(operationName = "Service")
    @WebResult(name = "ServiceResponse", targetNamespace = "http://www.epam.com/taskeleven/server/soap", partName = "ServiceResponse")
    public ServiceResponse service(
        @WebParam(name = "ServiceRequest", targetNamespace = "http://www.epam.com/taskeleven/server/soap", partName = "ServiceRequest")
        ServiceRequest serviceRequest);

}
