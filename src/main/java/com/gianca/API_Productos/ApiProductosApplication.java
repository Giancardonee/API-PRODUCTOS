package com.gianca.API_Productos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@SpringBootApplication
public class ApiProductosApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiProductosApplication.class, args);
	}
	{
		System.out.println("Api Productos Application");
	}

}
