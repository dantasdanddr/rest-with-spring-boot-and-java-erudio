package br.com.dantas.repositories;

import br.com.dantas.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {}
