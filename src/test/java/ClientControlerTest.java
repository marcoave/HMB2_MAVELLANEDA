import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.*;
import java.io.InputStreamReader;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration
@AutoConfigureMockMvc

public class ClientControlerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testGetClient() throws Exception{
        ResponseEntity<String> responseEntity=restTemplate.getForEntity("/api/clients",String.class);
        assertThat(responseEntity.getStatusCode().is2xxSuccessful()).isTrue();
    }

}
