package br.com.erudio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DockerController {

	private final Logger logger = LoggerFactory.getLogger(DockerController.class.getName());
	
	@RequestMapping("/hello-docker")
	public HelloDocker greeting() {

		logger.info("Called the hello-docker endpoint!");
		
		//var hostName = System.getenv("COMPUTERNAME");
		var hostName = System.getenv("HOSTNAME");
		
		return new HelloDocker(
					"Hello Docker",
					hostName
				);
	}
}
