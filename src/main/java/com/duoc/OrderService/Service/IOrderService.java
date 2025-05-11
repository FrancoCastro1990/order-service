package com.duoc.OrderService.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.duoc.OrderService.Model.Order;
import com.duoc.OrderService.Model.OrderItem;

@Service
public interface IOrderService {
    public Order createOrder(Order order);
    public Order getOrderById(Long id);
    public List<Order> getOrdersByClientId(Long clientId);
    public List<Order> getAllOrders();
    public Order addItemToOrder(Long orderId, OrderItem item);
    public Order updateOrder(Long orderId, Order order);

    public Order updateOrderStatusOrder(Long orderId, String status);
}
