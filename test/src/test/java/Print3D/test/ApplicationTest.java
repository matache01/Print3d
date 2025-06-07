package Print3D.test;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import Print3D.test.controller.UsusarioController;

// @SpringBootTest
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class TestApplication {
	@Autowired
	private UsusarioController ususarioController;

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void contextLoads(){
		System.out.println("Testing the context loading ...");
		// System.out.println("Server running on port: " + port);
	}

	@Test
	void contextLoads2() throws Exception{
		System.out.println("Testing the context loading. and the controller ...");
		assertThat(ususarioController).isNotNull();
	}

	@Test
	void getUsuariosContainsBrackets() throws Exception{
		assertThat(this.restTemplate.getForObject("http://localhost" + port + "/api/v1/usuarios",
		responseType_String.class)).toString().contains("[");
	}

	// @Test
    // void shouldReturnDefaultMessage() throws Exception {
    // this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
    // .andExpect(content().string(containsString("Hello, World")));
    // }

}
