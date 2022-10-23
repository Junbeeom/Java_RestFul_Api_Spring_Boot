package com.example.RestFulApi;

//import com.example.RestFulApi.config.JpaConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Slf4j
//@Import(JpaConfig.class)
@SpringBootApplication(scanBasePackages = "com.example.RestFulApi")
public class ChoJunBeomApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChoJunBeomApplication.class, args);
	}

}
