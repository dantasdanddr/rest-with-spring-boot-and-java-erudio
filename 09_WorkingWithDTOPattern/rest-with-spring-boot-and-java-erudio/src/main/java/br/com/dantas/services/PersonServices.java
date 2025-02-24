package br.com.dantas.services;

import br.com.dantas.data.dto.PersonDTO;
import br.com.dantas.exception.ResourceNotFoundException;
import br.com.dantas.model.Person;
import br.com.dantas.repositories.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import static br.com.dantas.mapper.ObjectMapper.parseListObjects;
import static br.com.dantas.mapper.ObjectMapper.parseObject;

@Service
public class PersonServices {

    private final AtomicLong counter = new AtomicLong();
    private final Logger logger = LoggerFactory.getLogger(PersonServices.class.getName());

    @Autowired
    PersonRepository repository;

    public PersonDTO findById(Long id) {
        logger.info("Finding one Person!");

        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Record with id '" + id + "' not found!"));
        return parseObject(entity, PersonDTO.class);
    }

    public List<PersonDTO> findAll() {
        logger.info("Finding people!");

        return parseListObjects(repository.findAll(), PersonDTO.class);
    }

    public PersonDTO create(PersonDTO person) {
        logger.info("Creating one Person!");

        var entity = parseObject(person, Person.class);
        return parseObject(repository.save(entity), PersonDTO.class);
    }

    public PersonDTO update(PersonDTO person) {
        logger.info("Updating one Person!");

        Person entity = repository.findById(person.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Record with id '" + person.getId() + "' not found!"));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        return parseObject(repository.save(entity), PersonDTO.class);
    }

    public void delete(Long id) {
        logger.info("Deleting person with id {}!", id);

        Person entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Record with id '" + id + "' not found!"));

        repository.delete(entity);
    }
}
