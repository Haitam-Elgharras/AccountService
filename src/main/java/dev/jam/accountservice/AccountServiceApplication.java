package dev.jam.accountservice;

import dev.jam.accountservice.config.JwtConfig;
import dev.jam.accountservice.dao.entities.Industry;
import dev.jam.accountservice.dao.entities.Category;
import dev.jam.accountservice.dao.repositories.CategoryRepository;
import dev.jam.accountservice.dao.repositories.IndustryRepository;
import dev.jam.accountservice.service.CompanyServiceImpl;
import dev.jam.accountservice.service.UserServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@EnableConfigurationProperties(JwtConfig.class)
@EnableFeignClients
@CrossOrigin
public class AccountServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner start(UserServiceImpl userService, CompanyServiceImpl companyService,
                                   CategoryRepository categoryRepository, IndustryRepository industryRepository
    ) {
        return (args) -> {

//             populate some industries and categories of events
            categoryRepository.save(new Category("Technology"));
            categoryRepository.save(new Category("Business"));
            categoryRepository.save(new Category("Science"));
            categoryRepository.save(new Category("Health"));
            categoryRepository.save(new Category("Music"));
            categoryRepository.save(new Category("Food & Drink"));
            categoryRepository.save(new Category("Film & Media"));

            industryRepository.save(new Industry("Technology"));
            industryRepository.save(new Industry("Business"));
            industryRepository.save(new Industry("Science"));
            industryRepository.save(new Industry("Health"));
            industryRepository.save(new Industry("Music"));
            industryRepository.save(new Industry("Food & Drink"));
            industryRepository.save(new Industry("Film & Media"));



        };
    }
}