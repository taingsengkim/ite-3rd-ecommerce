package co.istad.sengkim.ite3rdecommerce.features.product;

import co.istad.sengkim.ite3rdecommerce.features.category.Category;
import co.istad.sengkim.ite3rdecommerce.features.category.CategoryRepository;
import co.istad.sengkim.ite3rdecommerce.features.product.dto.CreateProductRequest;
import co.istad.sengkim.ite3rdecommerce.features.product.dto.ProductResponse;
import co.istad.sengkim.ite3rdecommerce.utils.GenerateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;
    @Override
    public ProductResponse createProductRequest(CreateProductRequest createProductRequest) {
        if(productRepository.existsByName(createProductRequest.name())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "A product name has already been used");
        }
        Category category = categoryRepository.findById(createProductRequest.categoryId())
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Category not found!"));
        Product product = productMapper.mapCreateProductRequestToProduct(createProductRequest);
        product.setCategory(category);
        product.setCode(GenerateUtils.generateProductCode()); //ITE-3RD-1234
        product.setSlug(GenerateUtils.generateSlug(createProductRequest.name()));
        product.setIsAvailable(true);
        product.setIsDeleted(false);
        product = productRepository.save(product);
        return productMapper.mapProductToProductResponse(product);
    }

    @Override
    public Page<ProductResponse> findAll(int pageNumber, int pageSize) {
        Sort sortById  = Sort.by(Sort.Direction.DESC,"id");
        PageRequest  pageRequest = PageRequest.of(pageNumber,pageSize, sortById);
        Page<Product> products = productRepository.findAll(pageRequest);
        return products.map(productMapper::mapProductToProductResponse);
    }


}
