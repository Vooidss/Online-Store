package com.onlinestore.backend.Controllers;

import ch.qos.logback.core.pattern.parser.OptionTokenizer;
import com.onlinestore.backend.Models.Short;
import com.onlinestore.backend.Services.ShortService;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/short/v1")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class ShortController {

    private final ShortService shortService;

    @GetMapping
    private Map<String, Object> getShorts() {
        return shortService.findAll();
    }

    @GetMapping("/{id}")
    private Map<String, Optional<Short>> getSneaker(
        @PathVariable("id") int id
    ) {
        return shortService.findById(id);
    }

    @PostMapping
    private Short setShort(@RequestBody Short shorts) {
        return shortService.setShort(shorts);
    }

    @DeleteMapping("/{id}")
    private Optional<Short> deleteShort(@PathVariable("id") int id) {
        return shortService.deleteShort(id);
    }
}
