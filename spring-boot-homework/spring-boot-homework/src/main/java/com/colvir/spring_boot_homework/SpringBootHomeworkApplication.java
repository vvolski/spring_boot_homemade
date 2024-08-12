package com.colvir.spring_boot_homework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(exclude = HibernateJpaAutoConfiguration.class)
@EnableTransactionManagement
public class SpringBootHomeworkApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootHomeworkApplication.class, args);
	}

}
