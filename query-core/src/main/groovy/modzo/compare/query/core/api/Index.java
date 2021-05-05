package modzo.compare.query.core.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class Index {

    @GetMapping
    public ResponseEntity<String> index() {
        return ResponseEntity.ok("");
    }

}
