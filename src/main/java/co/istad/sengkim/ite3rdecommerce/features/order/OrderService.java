package co.istad.sengkim.ite3rdecommerce.features.order;

import co.istad.sengkim.ite3rdecommerce.features.order.dto.CreateOrderRequest;
import co.istad.sengkim.ite3rdecommerce.features.order.dto.OrderResponse;
import co.istad.sengkim.ite3rdecommerce.features.order.dto.PaymentStatusRequest;
import co.istad.sengkim.ite3rdecommerce.features.order.dto.SoftDeleteOrderRequest;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface OrderService {
    OrderResponse createNew(CreateOrderRequest createOrderRequest);

    Page<OrderResponse> findAll(Integer pageNumber, Integer pageSize);

    OrderResponse findByUuid(UUID uuid);

    void softDelete(UUID uuid, SoftDeleteOrderRequest softDeleteOrderRequest);

    void hardDelete(UUID uuid);

    void changePaymentStatus(UUID uuid, PaymentStatusRequest paymentStatusRequest);
}
