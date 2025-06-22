package br.com.dantas;

import br.com.dantas.integrationtests.controllers.cors.withjson.PersonControllerCorsTest;
import br.com.dantas.integrationtests.controllers.withjson.BookControllerJsonTest;
import br.com.dantas.integrationtests.controllers.withjson.PersonControllerJsonTest;
import br.com.dantas.integrationtests.controllers.withxml.BookControllerXmlTest;
import br.com.dantas.integrationtests.controllers.withxml.PersonControllerXmlTest;
import br.com.dantas.integrationtests.controllers.withyaml.BookControllerYamlTest;
import br.com.dantas.integrationtests.controllers.withyaml.PersonControllerYamlTest;
import br.com.dantas.integrationtests.swagger.SwaggerIntegrationTest;
import br.com.dantas.repositories.PersonRepositoryTest;
import br.com.dantas.unittests.mapper.BookMapperTests;
import br.com.dantas.unittests.mapper.PersonMapperTests;
import br.com.dantas.unittests.services.BookServiceTest;
import br.com.dantas.unittests.services.PersonServiceTest;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
        PersonMapperTests.class,
        PersonServiceTest.class,
        BookMapperTests.class,
        BookServiceTest.class,
        SwaggerIntegrationTest.class,
        PersonControllerCorsTest.class,
        PersonControllerJsonTest.class,
        PersonControllerXmlTest.class,
        PersonControllerYamlTest.class,
        BookControllerJsonTest.class,
        BookControllerXmlTest.class,
        BookControllerYamlTest.class,
        PersonRepositoryTest.class
})
public class StartSuiteTests {
}
