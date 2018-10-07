package com.rs.app;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.dsl.IntegrationFlowBuilder;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.dsl.SourcePollingChannelAdapterSpec;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.FileWritingMessageHandler;

import com.rs.app.service.TransformService;

/**
 * 
 * @author ramesh
 */
@SpringBootApplication
public class SpringbootSpringIntegrationApplication {

	@Autowired
	private TransformService transformService;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootSpringIntegrationApplication.class, args);
	}

	@Bean
	public IntegrationFlowBuilder integrationFlow() {
		return IntegrationFlows.from(fileReadingMessageSource(),
						(SourcePollingChannelAdapterSpec spec) -> spec.poller(Pollers.fixedDelay(500)))
				.transform(this.transformService, "trasnformFile").handle(fileWritingMessageHandler());
	}

	@Bean
	public FileReadingMessageSource fileReadingMessageSource() {
		FileReadingMessageSource readingMessageSource = new FileReadingMessageSource();
		readingMessageSource.setDirectory(new File(new File(System.getProperty("java.io.tmpdir")), "source"));
		return readingMessageSource;
	}

	@Bean
	public FileWritingMessageHandler fileWritingMessageHandler() {
		return new FileWritingMessageHandler(
				new File(new File(System.getProperty("java.io.tmpdir")), "transformations"));
	}
}
