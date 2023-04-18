package com.example.jdbc;

import com.example.jdbc.domain.Customer;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class JdbcApplication implements CommandLineRunner {

    private final static Logger log = LoggerFactory.getLogger(JdbcApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(JdbcApplication.class, args);
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) throws Exception {
        log.info("Creating tables");

        jdbcTemplate.execute("DROP TABLE customers IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE customers ("
                                 + "id SERIAL, first_name VARCHAR2(255), last_name VARCHAR2(255)"
                                 + ")");

        List<Object[]> splitUpNames = Stream.of("John Woo", "Jeff Dean", "Josh Bloch", "Josh Long")
                                            .map(name -> name.split(" "))
                                            .collect(Collectors.toList());

        splitUpNames.forEach(name -> log.info(String.format("Inserting customer record for %s %s", name[0], name[1])));

        jdbcTemplate.batchUpdate("INSERT INTO customers(first_name, last_name) VALUES ( ?, ? )", splitUpNames);

        log.info("Querying for customer records where first_name = 'Josh':");

        jdbcTemplate.query("SELECT id, first_name, last_name FROM customers WHERE first_name = ?",
                           (rs, rowNum) -> new Customer(rs.getLong("id"), rs.getString("first_name"), rs.getString("last_name")),
                           new Object[]{ "Josh" })
                    .forEach(customer -> log.info(customer.toString()));
    }
}
