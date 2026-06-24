package co.istad.sengkim.ite3rdecommerce.features.product;

import co.istad.sengkim.ite3rdecommerce.features.product.dto.CreateProductRequest;
import co.istad.sengkim.ite3rdecommerce.features.product.dto.ProductResponse;
import org.springframework.data.domain.Page;

public interface ProductService {
        /**
         * Create
         * @param createProductRequest is requesting data for creating product
         * @return {@link ProductResponse}
         * @author Taing Sengkim
         */
        ProductResponse createProductRequest(CreateProductRequest createProductRequest);

        /**
         * Find products by pagination
         * @param pageNumber
         * @param pageSize
         * @return
         */
        Page<ProductResponse> findAll(int pageNumber, int pageSize);

}
