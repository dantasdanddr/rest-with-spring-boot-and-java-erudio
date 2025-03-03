package br.com.dantas.unittests.mapper;

import br.com.dantas.data.dto.BookDTO;
import br.com.dantas.model.Book;
import br.com.dantas.mocks.MockBook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static br.com.dantas.mapper.ObjectMapper.parseListObjects;
import static br.com.dantas.mapper.ObjectMapper.parseObject;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BookMapperTests {
    MockBook inputObject;

    @BeforeEach
    public void setUp() {
        inputObject = new MockBook();
    }

    @Test
    public void parseEntityToDTOTest() {
        BookDTO output = parseObject(inputObject.mockEntity(), BookDTO.class);
        assertEquals(Long.valueOf(0L), output.getId());
        assertEquals("Author Test0", output.getAuthor());
        assertNotNull(output.getLaunchDate());
        assertEquals(BigDecimal.ZERO, output.getPrice());
        assertEquals("Title Test0", output.getTitle());
    }

    @Test
    public void parseEntityListToDTOListTest() {
        List<BookDTO> outputList = parseListObjects(inputObject.mockEntityList(), BookDTO.class);
        BookDTO outputZero = outputList.getFirst();

        assertEquals(Long.valueOf(0L), outputZero.getId());
        assertEquals("Author Test0", outputZero.getAuthor());
        assertNotNull(outputZero.getLaunchDate());
        assertEquals(BigDecimal.ZERO, outputZero.getPrice());
        assertEquals("Title Test0", outputZero.getTitle());

        BookDTO outputSeven = outputList.get(7);

        assertEquals(Long.valueOf(7L), outputSeven.getId());
        assertEquals("Author Test7", outputSeven.getAuthor());
        assertNotNull(outputSeven.getLaunchDate());
        assertEquals(BigDecimal.valueOf(70), outputSeven.getPrice());
        assertEquals("Title Test7", outputSeven.getTitle());

        BookDTO outputTwelve = outputList.get(12);

        assertEquals(Long.valueOf(12L), outputTwelve.getId());
        assertEquals("Author Test12", outputTwelve.getAuthor());
        assertNotNull(outputTwelve.getLaunchDate());
        assertEquals(BigDecimal.valueOf(120), outputTwelve.getPrice());
        assertEquals("Title Test12", outputTwelve.getTitle());
    }

    @Test
    public void parseDTOToEntityTest() {
        Book output = parseObject(inputObject.mockDTO(), Book.class);
        assertEquals(Long.valueOf(0L), output.getId());
        assertEquals("Author Test0", output.getAuthor());
        assertNotNull(output.getLaunchDate());
        assertEquals(BigDecimal.ZERO, output.getPrice());
        assertEquals("Title Test0", output.getTitle());
    }

    @Test
    public void parserDTOListToEntityListTest() {
        List<Book> outputList = parseListObjects(inputObject.mockDTOList(), Book.class);
        Book outputZero = outputList.getFirst();

        assertEquals(Long.valueOf(0L), outputZero.getId());
        assertEquals("Author Test0", outputZero.getAuthor());
        assertNotNull(outputZero.getLaunchDate());
        assertEquals(BigDecimal.ZERO, outputZero.getPrice());
        assertEquals("Title Test0", outputZero.getTitle());

        Book outputSeven = outputList.get(7);

        assertEquals(Long.valueOf(7L), outputSeven.getId());
        assertEquals("Author Test7", outputSeven.getAuthor());
        assertNotNull(outputZero.getLaunchDate());
        assertEquals(BigDecimal.valueOf(70), outputSeven.getPrice());
        assertEquals("Title Test7", outputSeven.getTitle());

        Book outputTwelve = outputList.get(12);

        assertEquals(Long.valueOf(12L), outputTwelve.getId());
        assertEquals("Author Test12", outputTwelve.getAuthor());
        assertNotNull(outputZero.getLaunchDate());
        assertEquals(BigDecimal.valueOf(120), outputTwelve.getPrice());
        assertEquals("Title Test12", outputTwelve.getTitle());
    }
}