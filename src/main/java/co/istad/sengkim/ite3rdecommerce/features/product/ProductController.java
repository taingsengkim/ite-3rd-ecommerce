package co.istad.sengkim.ite3rdecommerce.features.product;

import co.istad.sengkim.ite3rdecommerce.features.product.dto.CreateProductRequest;
import co.istad.sengkim.ite3rdecommerce.features.product.dto.ProductResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse createNew(@Valid @RequestBody CreateProductRequest createProductRequest){
        return productService.createProductRequest(createProductRequest);
    }

    @GetMapping
    public Page<ProductResponse> findAll(
            @RequestParam(defaultValue = "0",required = false) Integer pageNumber,
            @RequestParam(required = false,defaultValue = "25") Integer pageSize){
        return productService.findAll(pageNumber,pageSize);
    }
}
