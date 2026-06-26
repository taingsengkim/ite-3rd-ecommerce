package co.istad.sengkim.ite3rdecommerce.features.order;

import co.istad.sengkim.ite3rdecommerce.features.order.dto.CreateOrderRequest;
import co.istad.sengkim.ite3rdecommerce.features.order.dto.OrderResponse;

public interface OrderService {
    OrderResponse createNew(CreateOrderRequest createOrderRequest);
}
