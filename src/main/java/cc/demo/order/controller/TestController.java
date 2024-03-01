package cc.demo.order.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Payment Controller", description = "test Controller API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class TestController {

    @GetMapping
    public String test() {
        return "1244";
    }
}
