package co.istad.sengkim.ite3rdecommerce.features.order;
import co.istad.sengkim.ite3rdecommerce.features.order.dto.CreateOrderRequest;
import co.istad.sengkim.ite3rdecommerce.features.order.dto.OrderResponse;
import co.istad.sengkim.ite3rdecommerce.features.order.dto.PaymentStatusRequest;
import co.istad.sengkim.ite3rdecommerce.features.order.dto.SoftDeleteOrderRequest;
import co.istad.sengkim.ite3rdecommerce.features.order.orderline.OrderLine;
import co.istad.sengkim.ite3rdecommerce.features.product.Product;
import co.istad.sengkim.ite3rdecommerce.features.product.ProductRepository;
import co.istad.sengkim.ite3rdecommerce.security.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import tools.jackson.databind.cfg.MapperBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;
    private final MapperBuilder mapperBuilder;

    @Override
    public OrderResponse createNew(CreateOrderRequest createOrderRequest) {
        List<OrderLine> orderLines = new ArrayList<>();
        final Order order = orderMapper.mapCreateOrderRequestToOrder(createOrderRequest);
        boolean isValidTrue = createOrderRequest.orderLines().stream()
                 .allMatch(orderLineDto -> {
                     Optional<Product> optionalProduct = productRepository.findByCode(orderLineDto.code());
                    if(optionalProduct.isPresent()){
                        OrderLine orderLine = new OrderLine();
                        orderLine.setProduct(optionalProduct.get());
                        orderLine.setQty(optionalProduct.get().getQty());
                        orderLine.setOrder(order);
                        orderLine.setUnitPrice(optionalProduct.get().getUnitPrice());
                         orderLines.add(orderLine);
                        return true;
                    }
                     return false;
                 });
         if(!isValidTrue){
             throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid order line.");
         }

        order.setCustomerId(AuthUtils.extractUserId());
        order.setOrderLines(orderLines);
        order.setIsDeleted(false);
        order.setOrderedAt(LocalDateTime.now());
        order.setStatus(false);
         Order savedOrder = orderRepository.save(order);
         return orderMapper.mapOrderToOrderResponse(savedOrder);
    }

    @Override
    public Page<OrderResponse> findAll(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber,pageSize, Sort.by(Sort.Direction.DESC,"orderedAt"));
        return orderRepository.findAll(pageable).map(orderMapper::mapOrderToOrderResponse);
    }

    @Override
    public OrderResponse findByUuid(UUID uuid) {
        return orderRepository.findById(uuid).map(orderMapper::mapOrderToOrderResponse).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Order has not been found."));
    }

    @Override
    public void softDelete(UUID uuid, SoftDeleteOrderRequest softDeleteOrderRequest) {
        Order order = orderRepository.findById(uuid).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Order has not been found."));
        order.setIsDeleted(softDeleteOrderRequest.isDeleted());
        orderRepository.save(order);
    }

    @Override
    public void hardDelete(UUID uuid) {
        orderRepository.delete(orderRepository.findById(uuid).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Order has not been found.")));
    }

    @Override
    public void changePaymentStatus(UUID uuid, PaymentStatusRequest paymentStatusRequest) {
        Order order = orderRepository.findById(uuid).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Order has not been found."));
        order.setStatus(paymentStatusRequest.status());
        orderRepository.save(order);
    }
}
