package co.istad.sengkim.ite3rdecommerce.features.order;

import co.istad.sengkim.ite3rdecommerce.features.order.dto.CreateOrderRequest;
import co.istad.sengkim.ite3rdecommerce.features.order.dto.OrderResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    Order mapCreateOrderRequestToOrder(CreateOrderRequest createOrderRequest);
    OrderResponse mapOrderToOrderResponse(Order order);
}
