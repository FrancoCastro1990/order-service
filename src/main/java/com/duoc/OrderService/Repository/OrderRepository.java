package com.duoc.OrderService.Repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.duoc.OrderService.Model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
   List<Order> findByClientId(Long clientId);
}
