package br.com.springboot.rest_with_spring_boot_java.services;

import br.com.springboot.rest_with_spring_boot_java.exception.ResourceNotFoundException;
import br.com.springboot.rest_with_spring_boot_java.model.Person;
import br.com.springboot.rest_with_spring_boot_java.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class PersonServices {
    private Logger logger = Logger.getLogger(PersonServices.class.getName());

    @Autowired
    PersonRepository repository;

    public List<Person> findAll() {
        logger.info("Finding all People!");

        return repository.findAll();
    }

    public Person findById(Long id) {
        logger.info("Finding one Person!");

        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this Id"));
    }

    public Person create(Person person) {
        logger.info("Creating one Person!");

        return repository.save(person);
    }

    public Person update(Person person) {
        logger.info("Updating one Person!");

        Person entity = repository.findById(person.getId()).orElseThrow(() -> new ResourceNotFoundException("No records found for this Id"));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddressName(person.getAddressName());
        entity.setGender(person.getGender());

        return repository.save(person);
    }

    public void delete(Long id) {
        logger.info("Deleting one Person!");

        Person entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this Id"));

        repository.delete(entity);
    }
}
