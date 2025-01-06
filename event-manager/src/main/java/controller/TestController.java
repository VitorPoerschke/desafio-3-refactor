package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @Autowired
    private MongoTemplate mongoTemplate;

    @GetMapping
    public ResponseEntity<String> testConnection() {
        if (mongoTemplate.getDb() != null) {
            return ResponseEntity.ok("Conexão com o MongoDB bem-sucedida!");
        }
        return ResponseEntity.status(500).body("Erro ao conectar!");
    }
}