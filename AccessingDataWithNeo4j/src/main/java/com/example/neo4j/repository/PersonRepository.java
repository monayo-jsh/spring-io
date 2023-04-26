package com.example.neo4j.repository;

import com.example.neo4j.entity.Person;
import java.util.List;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface PersonRepository extends Neo4jRepository<Person, Long> {
    Person findByName(String name);
    List<Person> findByTeammatesName(String name);
}
