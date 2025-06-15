package interview.category.service;

import interview.category.dto.OrderDTO;
import interview.category.entity.Order;
import interview.category.entity.OrderStatus;
import interview.category.entity.Product;
import interview.category.exception.ProductNotFoundException;
import interview.category.mapper.OrderMapper;
import interview.category.repository.OrderRepository;
import interview.category.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService implements IOrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;

    @Override
    @Transactional
    public OrderDTO createOrder(OrderDTO orderDTO) {
        Order order = orderMapper.toEntity(orderDTO);
        order.setStatus(OrderStatus.PENDING);

        // Validate and set product details for each order item
        order.getOrderItems().forEach(orderItem -> {
            Product product = productRepository.findById(orderItem.getProduct().getId())
                    .orElseThrow(() -> new ProductNotFoundException(
                            "Product not found with id: " + orderItem.getProduct().getId()));

            orderItem.setProduct(product);
            orderItem.setPrice(product.getPrice());
        });

        // Calculate total amount
        order.calculateTotalAmount();

        Order savedOrder = orderRepository.save(order);
        return orderMapper.toDTO(savedOrder);
    }

    @Override
    public Page<OrderDTO> getAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable)
                .map(orderMapper::toDTO);
    }

    @Override
    public OrderDTO getOrderById(Long id) {
        Order order = orderRepository.findByIdWithDetails(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
        return orderMapper.toDTO(order);
    }
}