package MongoTests;

import controller.TestController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;

@SpringBootTest
public class TestControllerTest {
    @Autowired
    private TestController testController;

    @Test
    public void testMongoConnection() {

        ResponseEntity<String> response = testController.testConnection();

        Assert.isTrue(response.getStatusCode().is2xxSuccessful(), "A conexão com o MongoDB falhou!");
        Assert.isTrue(
                response.getBody().contains("Conexão com o MongoDB bem-sucedida"),
                "A mensagem de sucesso não foi retornada corretamente!"
        );
    }
}
