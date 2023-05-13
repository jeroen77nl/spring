### General info
Producing a SOAP web service.
https://spring.io/guides/gs/producing-web-service/

Spring boot 3.0.6

### Build an executable JAR
You can build a single executable JAR file that contains all the necessary dependencies,
classes, and resources and run that.
```shell
./mvnw clean package
```
Building an executable jar makes it easy to ship, version, and deploy the service
as an application throughout the development lifecycle, across different environments,
and so forth.

### Run the application as a excutable JAR
```shell
java -jar target/demo-ws-server-0.0.1-SNAPSHOT.jar
```

### Run the application from the command line
```shell
./mvnw spring-boot:run
```

### Opvragen wsdl in browser
http://localhost:8080/ws/countries.wsdl

This configuration also uses the WSDL location servlet transformation:
servlet.setTransformWsdlLocations(true).
If you visit http://localhost:8080/ws/countries.wsdl, the soap:address will have the proper address.
If you instead visit the WSDL from the public facing IP address assigned to your machine, you will see that address instead.
```xml
<wsdl:service name="CountriesPortService">
    <wsdl:port binding="tns:CountriesPortSoap11" name="CountriesPortSoap11">
        <soap:address location="http://localhost:8080/ws"/>
    </wsdl:port>
</wsdl:service>
```

### Testen
Testbestand request.xml in root folder van project.
```shell
curl --header "content-type: text/xml" -d @request.xml http://localhost:8080/ws
```

#### Inline xml
```shell
# Use inline XML data
curl <<-EOF -fsSL -H "content-type: text/xml" -d @- http://localhost:8080/ws \
  > target/response.xml && xmllint --format target/response.xml

<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                                  xmlns:gs="http://spring.io/guides/gs-producing-web-service">
   <soapenv:Header/>
   <soapenv:Body>
      <gs:getCountryRequest>
         <gs:name>Spain</gs:name>
      </gs:getCountryRequest>
   </soapenv:Body>
</soapenv:Envelope>

EOF
```

#### Reponse xml opmaken (tidy)
```shell
url -fsSL --header "content-type: text/xml" -d @request.xml http://localhost:8080/ws > output.xml
xmllint --format output.xml
```
