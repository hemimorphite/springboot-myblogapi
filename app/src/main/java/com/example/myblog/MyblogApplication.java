package com.example.myblog;

import java.util.Arrays;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.hc.core5.net.URIBuilder;
import com.github.javafaker.Faker;
import org.apache.commons.text.StringEscapeUtils;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.json.JSONObject;
import org.json.JSONArray;

@SpringBootApplication
public class MyblogApplication {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public static void main(String[] args) {
		SpringApplication.run(MyblogApplication.class, args);
	}
	//mvnw -X spring-boot:run
	//var/lib/snapd/snap/bin
	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {
			String[] categories = {"environment", "science", "technology", "entertainment","business","food","health","sports"};
			
			/*for (String cat : categories) {
				HttpGet httpGet = new HttpGet("https://newsdata.io/api/1/news");
			
				try {
					URI uri = new URIBuilder(httpGet.getUri())
								.addParameter("apikey", "pub_28420515fa604950c2699d4efe2635d586103")
								.addParameter("q", "*")
								.addParameter("country", "us")
								.addParameter("category", cat)
								.build();
					httpGet.setUri(uri);
				} catch (URISyntaxException e) {
					e.printStackTrace();
				}
				
				try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
					try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
						// Get status code
						System.out.println(response.getVersion()); // HTTP/1.1
						System.out.println(response.getCode()); // 200
						System.out.println(response.getReasonPhrase()); // OK
						HttpEntity entity = response.getEntity();
						// Get response information
						String resultString = EntityUtils.toString(entity);
						
						JSONObject resultJson = new JSONObject(resultString);
						JSONArray results = (JSONArray) resultJson.get("results");
						
						for (int i = 0; i < results.length(); i++) {  
							JSONObject obj = results.getJSONObject(i);
							Faker faker = new Faker();
							
							jdbcTemplate.execute("insert into blogs (title, content, author, created_at, updated_at) "
							+ "values ('" + obj.get("title").toString().replaceAll("#", "").replaceAll("'", "").replaceAll("’", "") +
							"', '" + obj.get("content").toString().replaceAll("#", "").replaceAll("'", "").replaceAll("’", "") + "', '"
							+ faker.name().firstName().replaceAll("'", "") + " " + faker.name().lastName().replaceAll("'", "") + "', "
							+ "current_timestamp(), current_timestamp())");  
						}  
										
					}
				} catch (IOException | ParseException e) {
					e.printStackTrace();
				}
			}*/
			
		};
	}
}
