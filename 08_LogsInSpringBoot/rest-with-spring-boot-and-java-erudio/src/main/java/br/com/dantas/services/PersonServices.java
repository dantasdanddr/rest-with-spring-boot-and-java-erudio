package br.com.dantas.services;

import br.com.dantas.exception.ResourceNotFoundException;
import br.com.dantas.model.Person;
import br.com.dantas.repositories.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class PersonServices {

    private final AtomicLong counter = new AtomicLong();
    private final Logger logger = LoggerFactory.getLogger(PersonServices.class.getName());

    @Autowired
    PersonRepository repository;

    public Person findById(Long id) {
        logger.info("Finding one Person!");

        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Record with id '" + id + "' not found!"));
    }

    public List<Person> findAll() {
        logger.info("Finding people!");

        return repository.findAll();
    }

    public Person create(Person person) {
        logger.info("Creating one Person!");

        return repository.save(person);
    }

    public Person update(Person person) {
        logger.info("Updating one Person!");

        Person entity = repository.findById(person.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Record with id '" + person.getId() + "' not found!"));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        return repository.save(entity);
    }

    public void delete(Long id) {
        logger.info("Deleting person with id {}!", id);

        Person entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Record with id '" + id + "' not found!"));

        repository.delete(entity);
    }
}
