package br.com.dantas.repositories;

import br.com.dantas.integrationtests.testcontainers.AbstractIntegrationTest;
import br.com.dantas.model.Person;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PersonRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    PersonRepository repository;
    private static Person person;

    @BeforeAll
    static void setUp() {
        person = new Person();
    }

    @Test
    @Order(1)
    void findPeopleByName() {
        Pageable pageable = PageRequest.of(
                0,
                12,
                Sort.by(Sort.Direction.ASC, "firstName"));

        person = repository.findPeopleByName("iko", pageable).getContent().getFirst();

        assertNotNull(person);
        assertNotNull(person.getId());
        assertEquals("Nikola", person.getFirstName());
        assertEquals("Tesla", person.getLastName());
        assertEquals("Smiljan - Croatia", person.getAddress());
        assertEquals("Male", person.getGender());
        assertTrue(person.getEnabled());
    }

    @Test
    @Order(2)
    void disablePerson() {
        var id = person.getId();
        repository.disablePerson(id);

        person = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Person not found"));;

        assertNotNull(person);
        assertNotNull(person.getId());
        assertEquals("Nikola", person.getFirstName());
        assertEquals("Tesla", person.getLastName());
        assertEquals("Smiljan - Croatia", person.getAddress());
        assertEquals("Male", person.getGender());
        assertFalse(person.getEnabled());
    }
}