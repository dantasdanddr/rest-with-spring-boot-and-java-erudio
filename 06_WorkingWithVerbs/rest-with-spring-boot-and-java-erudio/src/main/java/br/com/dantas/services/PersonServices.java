package br.com.dantas.services;

import br.com.dantas.model.Person;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@Service
public class PersonServices {

    private final AtomicLong counter = new AtomicLong();
    private final Logger logger = Logger.getLogger(PersonServices.class.getName());

    public Person findById(String id) {
        logger.info("Finding one Person!");

        var person = new Person();
        person.setId(counter.incrementAndGet());
        person.setFirstName("Daniel");
        person.setLastName("Rodrigues");
        person.setAddress("Suzano - SP");
        person.setGender("male");
        return person;
    }

    public List<Person> findAll() {
        logger.info("Finding people!");

        var persons = new ArrayList<Person>();
        for (int i = 0; i < 8; i++) {
            Person person = mockPerson(i);
            persons.add(person);
        }
        return persons;
    }

    public Person create(Person person) {
        logger.info("Creating one Person!");

        return person;
    }

    public Person update(Person person) {
        logger.info("Updating one Person!");

        return person;
    }

    public void delete(String id) {
        logger.info("Deleting person from id " + id + "!");
    }

    private Person mockPerson(int i) {
        var person = new Person();
        person.setId((long) i);
        person.setFirstName("First Name - " + i);
        person.setLastName("Last Name - " + i);
        person.setAddress("Address - " + i);
        person.setGender((i % 2 == 0) ? "male" : "Female");
        return person;
    }
}
