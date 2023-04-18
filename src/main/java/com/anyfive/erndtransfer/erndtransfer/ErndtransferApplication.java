package com.anyfive.erndtransfer.erndtransfer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = { "com.anyfive.erndtransfer", "" })
public class ErndtransferApplication {

	public static void main(String[] args) {
		SpringApplication.run(ErndtransferApplication.class, args);
	}

}
