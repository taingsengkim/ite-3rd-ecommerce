package co.istad.sengkim.ite3rdecommerce.features.order;

import co.istad.sengkim.ite3rdecommerce.features.order.dto.CreateOrderRequest;
import co.istad.sengkim.ite3rdecommerce.features.order.dto.OrderResponse;
import co.istad.sengkim.ite3rdecommerce.features.order.dto.PaymentStatusRequest;
import co.istad.sengkim.ite3rdecommerce.features.order.dto.SoftDeleteOrderRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.boot.model.naming.ImplicitCollectionTableNameSource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse createNew(@Valid @RequestBody CreateOrderRequest createOrderRequest){
        return orderService.createNew(createOrderRequest);
    }

    @GetMapping
    public Page<OrderResponse> findAll(@RequestParam(defaultValue = "0" , required = false) Integer pageNumber, @RequestParam(defaultValue = "25" , required = false)  Integer pageSize){
        return orderService.findAll(pageNumber,pageSize);
    }

    @GetMapping("/{uuid}")
    public OrderResponse findByUuid(@PathVariable UUID uuid){
        return orderService.findByUuid(uuid);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{uuid}/soft-delete")
    public void softDelete(@PathVariable UUID uuid,@Valid  @RequestBody SoftDeleteOrderRequest softDeleteOrderRequest){
        orderService.softDelete(uuid,softDeleteOrderRequest);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{uuid}")
    public void hardDelete(@PathVariable UUID uuid){
        orderService.hardDelete(uuid);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{uuid}/status")
    public void setPaymentStatus(@PathVariable UUID uuid,@Valid @RequestBody PaymentStatusRequest paymentStatusRequest){
        orderService.changePaymentStatus(uuid,paymentStatusRequest);
    }



}

