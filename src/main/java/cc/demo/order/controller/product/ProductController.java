package cc.demo.order.controller.product;

import cc.demo.order.controller.product.dto.ProductReqDto;
import cc.demo.order.infra.response.ResponseDto;
import cc.demo.order.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseDto<?> createProduct(@RequestBody ProductReqDto dto) {
        productService.create(dto);
        return ResponseDto.success();
    }
}
