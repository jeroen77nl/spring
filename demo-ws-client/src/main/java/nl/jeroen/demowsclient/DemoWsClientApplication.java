package nl.jeroen.demowsclient;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import nl.jeroen.demowsclient.wsdl.GetCountryResponse;

@SpringBootApplication
public class DemoWsClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoWsClientApplication.class, args);
	}

	@Bean
	CommandLineRunner lookup(CountryClient quoteClient) {
		return args -> {
			String country = "Spain";

			if (args.length > 0) {
				country = args[0];
			}
			GetCountryResponse response = quoteClient.getCountry(country);
			System.err.println(response.getCountry().getCurrency());
		};
	}
	@Bean
	CommandLineRunner lookup2(CountryClient quoteClient) {
		return args -> {
			String country = "Poland";

			if (args.length > 0) {
				country = args[0];
			}
			GetCountryResponse response = quoteClient.getCountry(country);
			System.err.println(response.getCountry().getCurrency());
		};
	}
}