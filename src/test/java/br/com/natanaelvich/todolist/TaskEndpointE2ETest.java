package br.com.natanaelvich.todolist;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.natanaelvich.todolist.task.TaskModel;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TaskEndpointE2ETest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String getRootUrl() {
        return String.format("http://localhost:%s/tasks/", port);
    }

    @Test
    public void deveCadastrarUmaTarefa() throws Exception {
        var task = new TaskModel();
        task.setTitle("Tarefa 1");
        task.setDescription("Descrição da tarefa 1");

        ResponseEntity<TaskModel> response = restTemplate.postForEntity(getRootUrl(), task, TaskModel.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Tarefa 1", response.getBody().getTitle());
    }

    @Test
    public void naoDeveCadastrarUmaTarefaComMesmoTitulo() throws Exception {
        var task = new TaskModel();
        task.setTitle("Tarefa 1");
        task.setDescription("Descrição da tarefa 1");

        restTemplate.postForEntity(getRootUrl(), task, TaskModel.class);

        ResponseEntity<String> response = restTemplate.postForEntity(getRootUrl(), task, String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Tarefa já existe", response.getBody());
    }

}