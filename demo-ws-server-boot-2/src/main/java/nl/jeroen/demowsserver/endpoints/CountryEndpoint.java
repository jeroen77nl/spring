package nl.jeroen.demowsserver.endpoints;

import nl.jeroen.demowsserver.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import io.spring.guides.gs_producing_web_service.GetCountryRequest;
import io.spring.guides.gs_producing_web_service.GetCountryResponse;

import javax.swing.*;

@Endpoint
// @Endpoint registers the class with Spring WS as a potential candidate for processing incoming SOAP messages.
public class CountryEndpoint {
    private static final String NAMESPACE_URI = "http://spring.io/guides/gs-producing-web-service";

    private CountryRepository countryRepository;

    @Autowired
    public CountryEndpoint(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    // @PayloadRoot is used by Spring WS to pick the handler method,
    // based on the message’s namespace and localPart
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCountryRequest")
    // @ResponsePayload makes Spring WS map the returned value to the response payload
    @ResponsePayload
    // @RequestPayload makes Spring WS map the incoming message to the request parameter
    public GetCountryResponse getCountry(@RequestPayload GetCountryRequest request) {
        GetCountryResponse response = new GetCountryResponse();
        response.setCountry(countryRepository.findCountry(request.getName()));

        return response;
    }
}