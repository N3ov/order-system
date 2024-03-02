package cc.demo.order.controller.product;

import cc.demo.order.controller.product.dto.ProductReqDto;
import cc.demo.order.infra.response.ResponseDto;
import cc.demo.order.service.product.ProductService;
import cc.demo.order.vo.ProductVo;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public ResponseDto<ProductVo> getProduct(@RequestParam @NotBlank int pid) {
        return ResponseDto.success(productService.getProduct(pid));
    }

    @GetMapping("/all")
    public ResponseDto<List<ProductVo>> getAllProduct() {
        return ResponseDto.success(productService.getAllProduct());
    }


}
