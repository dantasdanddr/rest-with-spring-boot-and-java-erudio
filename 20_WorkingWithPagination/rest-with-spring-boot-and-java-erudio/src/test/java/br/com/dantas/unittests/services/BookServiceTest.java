package br.com.dantas.unittests.services;

import br.com.dantas.data.dto.BookDTO;
import br.com.dantas.exception.RequiredObjectIsNullException;
import br.com.dantas.unittests.mocks.MockBook;
import br.com.dantas.model.Book;
import br.com.dantas.repositories.BookRepository;
import br.com.dantas.services.BookService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    MockBook input;

    @Mock
    BookRepository repository;

    @InjectMocks
    private BookService service;

    private AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        input = new MockBook();
        openMocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void findById() {
        Book book = input.mockEntity(1);
        book.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(book));

        var result = service.findById(1L);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());

        //checking hateoas
        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/api/book/v1/1")
                        && Objects.equals(link.getType(), "GET"))
        );
        //</api/book/v1?page=1&size=12&direction=asc>;rel="findAll";type="GET"
        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("/api/book/v1?page=1&size=12&direction=asc")
                        && Objects.equals(link.getType(), "GET"))
        );
        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/api/book/v1")
                        && Objects.equals(link.getType(), "POST"))
        );
        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/api/book/v1")
                        && Objects.equals(link.getType(), "PUT"))
        );
        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/book/v1/1")
                        && Objects.equals(link.getType(), "DELETE"))
        );

        //checking result
        assertEquals("Author Test1", result.getAuthor());
        assertNotNull(result.getLaunchDate());
        assertEquals(BigDecimal.TEN, result.getPrice());
        assertEquals("Title Test1", result.getTitle());
    }

    @Test
    void create() {
        Book persisted = input.mockEntity(1);
        persisted.setId(1L);

        BookDTO dto = input.mockDTO(1);

        when(repository.save(any(Book.class))).thenReturn(persisted);

        var result = service.create(dto);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());

        System.out.println("Result: " + result.getLinks());

        //checking hateoas
        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/api/book/v1/1")
                        && Objects.equals(link.getType(), "GET"))
        );
        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("/api/book/v1?page=1&size=12&direction=asc")
                        && Objects.equals(link.getType(), "GET"))
        );
        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/api/book/v1")
                        && Objects.equals(link.getType(), "POST"))
        );
        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/api/book/v1")
                        && Objects.equals(link.getType(), "PUT"))
        );
        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/book/v1/1")
                        && Objects.equals(link.getType(), "DELETE"))
        );

        //checking result
        assertEquals("Author Test1", result.getAuthor());
        assertNotNull(result.getLaunchDate());
        assertEquals(BigDecimal.TEN, result.getPrice());
        assertEquals("Title Test1", result.getTitle());
    }

    @Test
    void testCreateWithNullBook() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class,
                () -> service.create(null)
        );

        String expectedMessage = "It is not allowed to persist a null object!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void update() {
        Book book = input.mockEntity(1);
        Book persisted = book;
        persisted.setId(1L);

        BookDTO dto = input.mockDTO(1);

        when(repository.findById(1L)).thenReturn(Optional.of(book));
        when(repository.save(book)).thenReturn(persisted);

        var result = service.update(dto);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());

        //checking hateoas
        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/api/book/v1/1")
                        && Objects.equals(link.getType(), "GET"))
        );
        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("/api/book/v1?page=1&size=12&direction=asc")
                        && Objects.equals(link.getType(), "GET"))
        );
        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/api/book/v1")
                        && Objects.equals(link.getType(), "POST"))
        );
        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/api/book/v1")
                        && Objects.equals(link.getType(), "PUT"))
        );
        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/book/v1/1")
                        && Objects.equals(link.getType(), "DELETE"))
        );

        //checking result
        assertEquals("Author Test1", result.getAuthor());
        assertNotNull(result.getLaunchDate());
        assertEquals(BigDecimal.TEN, result.getPrice());
        assertEquals("Title Test1", result.getTitle());
    }

    @Test
    void testUpdateWithNullBook() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class,
                () -> service.update(null)
        );

        String expectedMessage = "It is not allowed to persist a null object!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void delete() {
        Book book = input.mockEntity(1);
        book.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(book));

        service.delete(1L);

        //checking interactions
        verify(repository, times(1)).findById(anyLong());
        verify(repository, times(1)).delete(any(Book.class));
        verifyNoMoreInteractions(repository);
    }

    @Test
    @Disabled("REASON: Still Under Development")
    void findAll() {
        List<Book> list = input.mockEntityList();
        when(repository.findAll()).thenReturn(list);

        List<BookDTO> books = new ArrayList<>();//service.findAll();

        assertNotNull(books);
        assertEquals(14, books.size());

        var bookOne = books.get(1);

        assertNotNull(bookOne);
        assertNotNull(bookOne.getId());
        assertNotNull(bookOne.getLinks());

        assertTrue(bookOne.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/api/book/v1/1")
                        && Objects.equals(link.getType(), "GET"))
        );
        assertTrue(bookOne.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("/api/book/v1")
                        && Objects.equals(link.getType(), "GET"))
        );
        assertTrue(bookOne.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/api/book/v1")
                        && Objects.equals(link.getType(), "POST"))
        );
        assertTrue(bookOne.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/api/book/v1")
                        && Objects.equals(link.getType(), "PUT"))
        );
        assertTrue(bookOne.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/book/v1/1")
                        && Objects.equals(link.getType(), "DELETE"))
        );

        assertEquals("Author Test1", bookOne.getAuthor());
        assertNotNull(bookOne.getLaunchDate());
        assertEquals(BigDecimal.TEN, bookOne.getPrice());
        assertEquals("Title Test1", bookOne.getTitle());

        var bookFour = books.get(4);

        assertNotNull(bookFour);
        assertNotNull(bookFour.getId());
        assertNotNull(bookFour.getLinks());

        assertTrue(bookFour.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/api/book/v1/4")
                        && Objects.equals(link.getType(), "GET"))
        );
        assertTrue(bookFour.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("/api/book/v1")
                        && Objects.equals(link.getType(), "GET"))
        );
        assertTrue(bookFour.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/api/book/v1")
                        && Objects.equals(link.getType(), "POST"))
        );
        assertTrue(bookFour.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/api/book/v1")
                        && Objects.equals(link.getType(), "PUT"))
        );
        assertTrue(bookFour.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/book/v1/4")
                        && Objects.equals(link.getType(), "DELETE"))
        );

        assertEquals("Author Test4", bookFour.getAuthor());
        assertNotNull(bookFour.getLaunchDate());
        assertEquals(BigDecimal.valueOf(40), bookFour.getPrice());
        assertEquals("Title Test4", bookFour.getTitle());

        var bookSeven = books.get(7);

        assertNotNull(bookSeven);
        assertNotNull(bookSeven.getId());
        assertNotNull(bookSeven.getLinks());

        assertTrue(bookSeven.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/api/book/v1/7")
                        && Objects.equals(link.getType(), "GET"))
        );
        assertTrue(bookSeven.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("/api/book/v1")
                        && Objects.equals(link.getType(), "GET"))
        );
        assertTrue(bookSeven.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/api/book/v1")
                        && Objects.equals(link.getType(), "POST"))
        );
        assertTrue(bookSeven.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/api/book/v1")
                        && Objects.equals(link.getType(), "PUT"))
        );
        assertTrue(bookSeven.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/book/v1/7")
                        && Objects.equals(link.getType(), "DELETE"))
        );

        assertEquals("Author Test7", bookSeven.getAuthor());
        assertNotNull(bookSeven.getLaunchDate());
        assertEquals(BigDecimal.valueOf(70), bookSeven.getPrice());
        assertEquals("Title Test7", bookSeven.getTitle());
    }
}