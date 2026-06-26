package co.istad.sengkim.ite3rdecommerce.features.order;

import co.istad.sengkim.ite3rdecommerce.features.order.dto.CreateOrderRequest;
import co.istad.sengkim.ite3rdecommerce.features.order.dto.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse createNew(@RequestBody CreateOrderRequest createOrderRequest){
        return orderService.createNew(createOrderRequest);
    }
}
