
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;


public class JsonFileReadingTest {
  ClassLoader classLoader = JsonFileReadingTest.class.getClassLoader();


  @Test
  @DisplayName("Reading Json file using Jackson library")
  void jsonJackson() throws Exception {
    ObjectMapper objectMapper = new ObjectMapper();
    InputStream inputStream = classLoader.getResourceAsStream("json-example.json");
    JsonNode jsonNode = objectMapper.readTree(new InputStreamReader(inputStream, UTF_8));

    assertThat(jsonNode.withArray("PersonData").findValue("id").asText()).isEqualTo("1");
    assertThat(jsonNode.withArray("PersonData").findValue("first_name").asText()).isEqualTo("Jeanette");
    assertThat(jsonNode.withArray("PersonData").findValue("last_name").asText()).isEqualTo("Penddreth");
    assertThat(jsonNode.withArray("PersonData").findValue("email").asText()).isEqualTo("jpenddreth0@census.gov");
    assertThat(jsonNode.withArray("PersonData").findValue("gender").asText()).isEqualTo("Female");
    assertThat(jsonNode.withArray("PersonData").findValue("ip_address").asText()).isEqualTo("26.58.193.2");

  }
}


