package com.mydeveloperplanet.mymcpserverplanet.server;

import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MyMcpServerPlanetApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyMcpServerPlanetApplication.class, args);
	}

	@Bean
	public ToolCallbackProvider mcpServices(ArtistService artistService, SongService songService) {
		return MethodToolCallbackProvider.builder()
				.toolObjects(artistService,	songService)
				.build();
	}

}
