package interview.category.service;

import interview.category.dto.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IOrderService {
    OrderDTO createOrder(OrderDTO orderDTO);

    Page<OrderDTO> getAllOrders(Pageable pageable);

    OrderDTO getOrderById(Long id);
}