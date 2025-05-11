package com.duoc.OrderService.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.duoc.OrderService.Model.Order;
import com.duoc.OrderService.Model.OrderItem;
import com.duoc.OrderService.Service.IOrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private IOrderService orderService;

    // Endpoint to create a new order
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        return new ResponseEntity<>(orderService.createOrder(order), HttpStatus.CREATED);
    }

    // Endpoint to get an order by ID
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        try {
            Order order = orderService.getOrderById(id);
            return new ResponseEntity<>(order, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint to get all orders
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        return new ResponseEntity<>(orderService.getAllOrders(), HttpStatus.OK);
    }

    // Endpoint to get orders by client ID
    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<Order>> getOrdersByClientId(@PathVariable Long clientId) {
        List<Order> orders = orderService.getOrdersByClientId(clientId);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    // Endpoint to add an item to an order
    @PostMapping("/{orderId}/items")
    public ResponseEntity<Order> addItemToOrder(@PathVariable Long orderId, @RequestBody OrderItem item) {
        try {
            Order updatedOrder = orderService.addItemToOrder(orderId, item);
            return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint to update an order
    @PutMapping("/{orderId}/{status}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long orderId, @PathVariable String status) {
        try {
            if (!status.equals("Shipped") && !status.equals("Completed") && !status.equals("Error")) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            Order updatedOrder = orderService.updateOrderStatusOrder(orderId, status);
            return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
