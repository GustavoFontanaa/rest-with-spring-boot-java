package br.com.springboot.rest_with_spring_boot_java.repository;

import br.com.springboot.rest_with_spring_boot_java.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
