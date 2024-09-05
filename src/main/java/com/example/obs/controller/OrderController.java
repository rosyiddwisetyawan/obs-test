package com.example.obs.controller;

import com.example.obs.model.ApiResponse;
import com.example.obs.model.GetRequest;
import com.example.obs.model.Item;
import com.example.obs.model.Order;
import com.example.obs.service.OrderService;
import jakarta.validation.Valid;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService service;

    @PostMapping(value = "/add")
    private ResponseEntity<?> save(@Valid @RequestBody Order request){

        Order order = service.save(request);
        if (order != null) {
            ApiResponse<Order> response = new ApiResponse<>("Add data success", 200, order);
            return ResponseEntity.ok(response);
        } else {
            ApiResponse<Order> response = new ApiResponse<>("Add data failed", 400, null);
            return ResponseEntity.status(400).body(response);
        }
    }

    @PostMapping(value = "/delete/{orderNo}")
    private ResponseEntity<?> delete(@PathVariable String orderNo){

        Boolean status = service.delete(orderNo);
        if (status) {
            ApiResponse<String> response = new ApiResponse<>("Delete data success", 200, orderNo);
            return ResponseEntity.ok(response);
        } else {
            ApiResponse<String> response = new ApiResponse<>("Delete data failed", 400, null);
            return ResponseEntity.status(400).body(response);
        }
    }

    @PostMapping(value = "/update")
    private ResponseEntity<?> update(@Valid @RequestBody Order request){

        Map<String, Object> map = new HashMap<String, Object>();
        Order order = service.update(request);
        if (order!=null) {
            ApiResponse<Order> response = new ApiResponse<>("Update data success", 200, order);
            return ResponseEntity.ok(response);
        } else {
            ApiResponse<Order> response = new ApiResponse<>("Update data failed", 400, null);
            return ResponseEntity.status(400).body(response);
        }
    }

    @GetMapping(value = "/get/{id}")
    private ResponseEntity<?> getById(@PathVariable String id){
        Order order = service.getById(id);
        if (order != null) {
            ApiResponse<Order> response = new ApiResponse<>("Get data success", 200, order);
            return ResponseEntity.ok(response);
        } else {
            ApiResponse<Order> response = new ApiResponse<>("Get data failed", 400, null);
            return ResponseEntity.status(400).body(response);
        }
    }

    @GetMapping(value = "/get")
    public ResponseEntity<ApiResponse<List<Order>>> getAllOrder(@RequestBody(required = false) GetRequest request) {
        List<Order> list = service.getAll(request);

        ApiResponse<List<Order>> response = new ApiResponse<>("Get all order successful", 200, list);
        return ResponseEntity.ok(response);
    }
}
