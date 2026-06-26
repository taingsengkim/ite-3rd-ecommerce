package co.istad.sengkim.ite3rdecommerce.features.order;
import co.istad.sengkim.ite3rdecommerce.features.order.dto.CreateOrderRequest;
import co.istad.sengkim.ite3rdecommerce.features.order.dto.OrderResponse;
import co.istad.sengkim.ite3rdecommerce.features.order.orderline.OrderLine;
import co.istad.sengkim.ite3rdecommerce.features.product.Product;
import co.istad.sengkim.ite3rdecommerce.features.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;
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
        order.setCustomerId("ISTAD");
        order.setOrderLines(orderLines);
        order.setIsDeleted(false);
        order.setOrderedAt(LocalDateTime.now());
        order.setStatus(false);
         Order savedOrder = orderRepository.save(order);
         return orderMapper.mapOrderToOrderResponse(savedOrder);
    }
}
