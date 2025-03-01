package br.com.dantas.repositories;

import br.com.dantas.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {}
