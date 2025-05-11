package com.duoc.OrderService.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.duoc.OrderService.Client.ProductClient;
import com.duoc.OrderService.Model.Order;
import com.duoc.OrderService.Model.OrderItem;
import com.duoc.OrderService.Model.Product;
import com.duoc.OrderService.Repository.OrderRepository;

@Service
public class OrderService implements IOrderService {


    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductClient productClient;

    @Override
    public Order createOrder(Order order) {
        // for (OrderItem item : order.getItems()) {
        //     Product product = productClient.getProductById(item.getProductId());
        //     if (product == null || product.getStock() < item.getQuantity()) {
        //         throw new IllegalArgumentException("Invalid product ID or insufficient stock");
        //     }
        // }
        return orderRepository.save(order);
    }

    @Override
    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid order ID"));
    }

    @Override
    public List<Order> getOrdersByClientId(Long clientId) {
        return orderRepository.findByClientId(clientId);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order addItemToOrder(Long orderId, OrderItem item) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new IllegalArgumentException("Invalid order ID"));

        Product product = productClient.getProductById(item.getProductId());
        if (product == null || product.getStock() < item.getQuantity()) {
            throw new IllegalArgumentException("Invalid product ID or insufficient stock");
        }

        // Descontar stock
        product.setStock(product.getStock() - item.getQuantity());
        productClient.updateProduct(product.getId(), product);

        // Establecer precio y vínculo con la orden
        item.setPrice(product.getPrice() * item.getQuantity());
        item.setOrder(order); // 🔥 ESTO ES CRUCIAL

        // Agregar el item a la orden
        order.getItems().add(item);
        

        // Actualizar el total de la orden con el precio del nuevo item
        double currentTotal = order.getTotal() != null ? order.getTotal() : 0;
        order.setTotal(currentTotal + item.getPrice());
        order.setStatus("In Process"); // Cambiar el estado a "In Process"


        return orderRepository.save(order);
    }

    @Override
    public Order updateOrder(Long orderId, Order order) {
        Order existingOrder = orderRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException("Invalid order ID"));
        existingOrder.setClientId(order.getClientId());
        existingOrder.setOrderDate(order.getOrderDate());
        existingOrder.setTotal(order.getTotal());
        existingOrder.setStatus(order.getStatus());
        existingOrder.setItems(order.getItems());
        return orderRepository.save(existingOrder);
    }

    @Override
    public Order updateOrderStatusOrder(Long orderId, String status) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException("Invalid order ID"));
        order.setStatus(status);
        return orderRepository.save(order);
    }

}
