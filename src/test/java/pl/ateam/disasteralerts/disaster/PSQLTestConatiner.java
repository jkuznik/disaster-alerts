package pl.ateam.disasteralerts.disaster;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
public class PSQLTestConatiner {

    public static final String TESTCONTAINER_DB_NAME = "testdb";
    public static final String TESTCONTAINER_DB_USERNAME = "testUsername";
    public static final String TESTCONTAINER_DB_PASSWORD = "testPassword";

    @Container
    static PostgreSQLContainer postgres = new PostgreSQLContainer("postgres:16.3")
            .withDatabaseName(TESTCONTAINER_DB_NAME)
            .withUsername(TESTCONTAINER_DB_USERNAME)
            .withPassword(TESTCONTAINER_DB_PASSWORD);

    @DynamicPropertySource
    static void setTestContainerProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Test
    void testConnection() {
        //given
        Integer firstMappedPort = postgres.getFirstMappedPort();
        String databaseName = postgres.getDatabaseName();
        String username = postgres.getUsername();
        String password = postgres.getPassword();

        //when
        String jdbcUrl = postgres.getJdbcUrl();

        //then
        assertThat(jdbcUrl).contains("jdbc:postgresql://localhost:" + firstMappedPort + "/" + TESTCONTAINER_DB_NAME);
        assertThat(databaseName).isEqualTo(TESTCONTAINER_DB_NAME);
        assertThat(username).isEqualTo(TESTCONTAINER_DB_USERNAME);
        assertThat(password).isEqualTo(TESTCONTAINER_DB_PASSWORD);
    }
}
