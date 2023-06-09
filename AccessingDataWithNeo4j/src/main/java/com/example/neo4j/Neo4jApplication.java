package com.example.neo4j;

import com.example.neo4j.entity.Person;
import com.example.neo4j.repository.PersonRepository;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Neo4jApplication {

    private final static Logger log = LoggerFactory.getLogger(Neo4jApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(Neo4jApplication.class, args);
    }

    @Bean
    CommandLineRunner run(PersonRepository personRepository) {
        return args -> {

            personRepository.deleteAll();

            Person greg = new Person("Greg");
            Person roy = new Person("Roy");
            Person craig = new Person("Craig");

            List<Person> team = Arrays.asList(greg, roy, craig);

            log.info("Before linking up with Neo4j...");

            team.stream().forEach(person -> log.info("\t" + person.toString()));

            personRepository.save(greg);
            personRepository.save(roy);
            personRepository.save(craig);

            greg = personRepository.findByName(greg.getName());
            greg.worksWith(roy);
            greg.worksWith(craig);

            personRepository.save(greg);

            roy = personRepository.findByName(roy.getName());
            roy.worksWith(craig);
            // We already know that roy works with greg
            personRepository.save(roy);

            // We already know craig works with roy and greg

            log.info("Lookup each person by name...");

            team.stream().forEach(person -> log.info("\t" + personRepository.findByName(person.getName()).toString()));

            List<Person> teammates = personRepository.findByTeammatesName(greg.getName());

            log.info("The following have Greg as a teammate...");

            teammates.stream().forEach(person -> log.info("\t" + person.getName()));

        };
    }
}
