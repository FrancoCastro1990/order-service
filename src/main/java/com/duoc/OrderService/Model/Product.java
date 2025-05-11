package com.duoc.OrderService.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Product {
    private Long id;
    private String name;
    private double price;
    private String category;
    private int stock;
    private Long branchId;
}
