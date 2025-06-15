package interview.category.mapper;

import interview.category.dto.OrderDTO;
import interview.category.dto.OrderItemDTO;
import interview.category.entity.Order;
import interview.category.entity.OrderItem;
import interview.category.entity.Product;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderMapper {

    @Mapping(target = "orderItems", ignore = true)
    Order toEntity(OrderDTO orderDTO);

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.name")
    OrderItemDTO toOrderItemDTO(OrderItem orderItem);

    @Mapping(target = "product.id", source = "productId")
    @Mapping(target = "order", ignore = true)
    OrderItem toOrderItem(OrderItemDTO orderItemDTO);

    @Mapping(target = "orderItems", source = "orderItems")
    OrderDTO toDTO(Order order);

    @AfterMapping
    default void setOrderItems(@MappingTarget Order order, OrderDTO orderDTO) {
        if (orderDTO.getOrderItems() != null) {
            orderDTO.getOrderItems().forEach(itemDTO -> {
                OrderItem orderItem = toOrderItem(itemDTO);
                orderItem.setOrder(order);
                order.getOrderItems().add(orderItem);
            });
        }
    }

    @AfterMapping
    default void updateProductName(@MappingTarget OrderItemDTO orderItemDTO, OrderItem orderItem) {
        Product product = orderItem.getProduct();
        if (product != null) {
            orderItemDTO.setProductName(product.getName());
        }
    }
}