package br.com.natanaelvich.todolist;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.natanaelvich.todolist.user.UserModel;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserEndpointE2ETest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String getRootUrl() {
        return String.format("http://localhost:%s/users/", port);
    }

    @Test
    public void deveCadastrarUmUsuario() {
        var user = new UserModel();
        user.setUsername("natanaelvich");
        user.setName("Natanael Vich");
        user.setPassword("123456");

        ResponseEntity<UserModel> response = restTemplate.postForEntity(getRootUrl(), user, UserModel.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("natanaelvich", response.getBody().getUsername());
    }

    @Test
    public void naoDeveCadastrarUmUsuarioComMesmoUsername() {
        var user = new UserModel();
        user.setUsername("natanaelvich");
        user.setName("Natanael Vich");
        user.setPassword("123456");

        restTemplate.postForEntity(getRootUrl(), user, UserModel.class);

        ResponseEntity<String> response = restTemplate.postForEntity(getRootUrl(), user, String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Usuário já existe", response.getBody());
    }

}
