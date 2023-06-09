package nl.jeroen.demowsserver;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

// Spring WS uses a different servlet type for handling SOAP messages: MessageDispatcherServlet.
// It is important to inject and set ApplicationContext to MessageDispatcherServlet.
// Without that, Spring WS will not automatically detect Spring beans.
//
// DefaultMethodEndpointAdapter configures the annotation-driven Spring WS programming model. This makes it possible to use the various annotations, such as @Endpoint (mentioned earlier).

@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {

    // You need to specify bean names for MessageDispatcherServlet and DefaultWsdl11Definition.
    // Bean names determine the URL under which the web service and the generated WSDL file are available.
    // In this case, the WSDL will be available under http://<host>:<port>/ws/countries.wsdl

    // Naming this bean messageDispatcherServlet does not replace Spring Boot’s default DispatcherServlet bean.
    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(
            ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean<>(servlet, "/ws/*");
    }

    // DefaultWsdl11Definition exposes a standard WSDL 1.1 by using XsdSchema
    @Bean(name = "countries")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema countriesSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("CountriesPort");
        wsdl11Definition.setLocationUri("/ws");
        wsdl11Definition.setTargetNamespace("http://spring.io/guides/gs-producing-web-service");
        wsdl11Definition.setSchema(countriesSchema);
        return wsdl11Definition;
    }

    @Bean
    public XsdSchema countriesSchema() {
        return new SimpleXsdSchema(new ClassPathResource("countries.xsd"));
    }
}