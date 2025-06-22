package br.com.dantas.integrationtests.controllers.withxml;

import br.com.dantas.config.TestConfigs;
import br.com.dantas.integrationtests.dto.BookDTO;
import br.com.dantas.integrationtests.dto.PersonDTO;
import br.com.dantas.integrationtests.dto.wrappers.json.WrapperBookDTO;
import br.com.dantas.integrationtests.dto.wrappers.xmlandyaml.PagedModelBook;
import br.com.dantas.integrationtests.dto.wrappers.xmlandyaml.PagedModelPerson;
import br.com.dantas.integrationtests.testcontainers.AbstractIntegrationTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import java.math.BigDecimal;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookControllerXmlTest extends AbstractIntegrationTest {

    private static RequestSpecification specification;
    private static XmlMapper objectMapper;

    private static BookDTO book;

    @BeforeAll
    static void setUp() {
        objectMapper = new XmlMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        book = new BookDTO();
    }

    @Test
    @Order(1)
    void createTest() throws JsonProcessingException {
        mockBook();

        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_DANTAS)
                .setBasePath("/api/book/v1")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content = given(specification)
                .contentType(MediaType.APPLICATION_XML_VALUE)
                .accept(MediaType.APPLICATION_XML_VALUE)
                .body(book)
                .when()
                .post()
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_XML_VALUE)
                .extract()
                .body()
                .asString();

        BookDTO createdBook = objectMapper.readValue(content, BookDTO.class);
        book = createdBook;

        assertNotNull(createdBook.getId());
        assertTrue(createdBook.getId() > 0);

        assertEquals("Robert C. Martin", createdBook.getAuthor());
        assertEquals("2009-01-10", createdBook.getLaunchDate());
        assertEquals(0, createdBook.getPrice().compareTo(BigDecimal.valueOf(77.00)));
        assertEquals("Clean Code", createdBook.getTitle());
    }

    @Test
    @Order(2)
    void updateTest() throws JsonProcessingException {
        book.setTitle("JavaScript");

        var content = given(specification)
                .contentType(MediaType.APPLICATION_XML_VALUE)
                .accept(MediaType.APPLICATION_XML_VALUE)
                .body(book)
                .when()
                .put()
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_XML_VALUE)
                .extract()
                .body()
                .asString();

        BookDTO createdBook = objectMapper.readValue(content, BookDTO.class);
        book = createdBook;

        assertNotNull(createdBook.getId());
        assertTrue(createdBook.getId() > 0);

        assertEquals("Robert C. Martin", createdBook.getAuthor());
        assertEquals("2009-01-10", createdBook.getLaunchDate().substring(0, 10));
        assertEquals(0, createdBook.getPrice().compareTo(BigDecimal.valueOf(77.00)));
        assertEquals("JavaScript", createdBook.getTitle());
    }

    @Test
    @Order(3)
    void findByIdTest() throws JsonProcessingException {
        var content = given(specification)
                .contentType(MediaType.APPLICATION_XML_VALUE)
                .accept(MediaType.APPLICATION_XML_VALUE)
                .pathParam("id", book.getId())
                .when()
                .get("{id}")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_XML_VALUE)
                .extract()
                .body()
                .asString();

        BookDTO createdBook = objectMapper.readValue(content, BookDTO.class);
        book = createdBook;

        assertNotNull(createdBook.getId());
        assertTrue(createdBook.getId() > 0);

        assertEquals("Robert C. Martin", createdBook.getAuthor());
        assertEquals("2009-01-10", createdBook.getLaunchDate().substring(0, 10));
        assertEquals(0, createdBook.getPrice().compareTo(BigDecimal.valueOf(77.00)));
        assertEquals("JavaScript", createdBook.getTitle());
    }

    @Test
    @Order(4)
    void deleteTest() throws JsonProcessingException {
        given(specification)
                .pathParam("id", book.getId())
            .when()
                .delete("{id}")
            .then()
                .statusCode(204);
    }

    @Test
    @Order(5)
    void findAllTest() throws JsonProcessingException {
        var content = given(specification)
                    .accept(MediaType.APPLICATION_XML_VALUE)
                    .queryParams("page", 0, "size", 10, "direction", "asc")
                .when()
                    .get()
                .then()
                    .statusCode(200)
                    .contentType(MediaType.APPLICATION_XML_VALUE)
                .extract()
                    .body()
                        .asString();

        PagedModelBook pagedModel = objectMapper.readValue(content, PagedModelBook.class);
        List<BookDTO> books = pagedModel.getContent();

        BookDTO bookOne = books.getFirst();

        assertNotNull(bookOne.getId());
        assertTrue(bookOne.getId() > 0);

        assertEquals("Viktor Mayer-Schonberger e Kenneth Kukier", bookOne.getAuthor());
        assertEquals("2017-11-07", bookOne.getLaunchDate().substring(0, 10));
        assertEquals(0, bookOne.getPrice().compareTo(BigDecimal.valueOf(54.00)));
        assertEquals("Big Data: como extrair volume, variedade, velocidade e valor da avalanche de informação cotidiana", bookOne.getTitle());

        BookDTO bookFour = books.get(4);

        assertNotNull(bookFour.getId());
        assertTrue(bookFour.getId() > 0);

        assertEquals("Eric Evans", bookFour.getAuthor());
        assertEquals("2017-11-07", bookFour.getLaunchDate().substring(0, 10));
        assertEquals(0, bookFour.getPrice().compareTo(BigDecimal.valueOf(92.00)));
        assertEquals("Domain Driven Design", bookFour.getTitle());
    }

    private void mockBook() {
        book.setAuthor("Robert C. Martin");
        book.setLaunchDate("2009-01-10");
        book.setPrice(BigDecimal.valueOf(77.0));
        book.setTitle("Clean Code");
    }
}