package com.example.demo_test;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class DemoTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoTestApplication.class, args);
	}
	@Value("tsdctest")
	private String dbName;

	@Value("103.226.249.128")
	private String dbHost;

	@Value("57017")
	private String dbPort;

	@Value("tsdctest")
	private String dbUser;

	@Value("CLzQhK8QYs4N9Sst")
	private String dbPass;

	MongoClient mongoClient = null;
	@Bean
	MongoClient mongoClientInit() {
		if (mongoClient != null) {
			return mongoClient;
		}

		String host[] = dbHost.split(";");

		List<ServerAddress> seeds = new ArrayList<>();
		for (String aHost : host) {
			seeds.add(new ServerAddress(aHost, Integer.parseInt(dbPort)));
		}

		MongoCredential credential = MongoCredential.createCredential(dbUser, dbName, dbPass.toCharArray());
		MongoClientOptions mongoClientOptions = MongoClientOptions.builder().build();
		mongoClient = new MongoClient(seeds, credential, mongoClientOptions);
		return mongoClient;
	}
	@Bean("poolXepLop")
	public ThreadPoolTaskExecutor poolXepLop() {
		ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
		pool.setCorePoolSize(10);
		pool.setMaxPoolSize(30);
		//pool.setQueueCapacity(100);
		pool.setThreadNamePrefix("poolXepLop-");
		pool.setWaitForTasksToCompleteOnShutdown(true);
		return pool;
	}

}
