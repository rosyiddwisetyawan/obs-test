package com.example.obs.controller;

import com.example.obs.model.ApiResponse;
import com.example.obs.model.GetRequest;
import com.example.obs.model.Inventory;
import com.example.obs.model.Item;
import com.example.obs.model.Order;
import com.example.obs.service.InventoryService;
import com.example.obs.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("inventory")
public class InventoryController {

    @Autowired
    InventoryService service;

    @PostMapping(value = "/add")
    private ResponseEntity<?> save(@Valid @RequestBody Inventory request){

        Inventory inventory = service.save(request);
        if (inventory != null) {
            ApiResponse<Inventory> response = new ApiResponse<>("Add data success", 200, inventory);
            return ResponseEntity.ok(response);
        } else {
            ApiResponse<Inventory> response = new ApiResponse<>("Add data failed", 400, null);
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
    private ResponseEntity<?> update(@Valid @RequestBody Inventory request){

        Map<String, Object> map = new HashMap<String, Object>();
        Inventory inventory = service.update(request);
        if (inventory!=null) {
            ApiResponse<Inventory> response = new ApiResponse<>("Update data success", 200, inventory);
            return ResponseEntity.ok(response);
        } else {
            ApiResponse<Inventory> response = new ApiResponse<>("Update data failed", 400, null);
            return ResponseEntity.status(400).body(response);
        }
    }

    @GetMapping(value = "/get/{id}")
    private ResponseEntity<?> getById(@PathVariable String id){
        Inventory inventory = service.getById(id);
        if (inventory != null) {
            ApiResponse<Inventory> response = new ApiResponse<>("Get data success", 200, inventory);
            return ResponseEntity.ok(response);
        } else {
            ApiResponse<Inventory> response = new ApiResponse<>("Get data failed", 400, null);
            return ResponseEntity.status(400).body(response);
        }
    }

    @GetMapping(value = "/get")
    public ResponseEntity<ApiResponse<List<Inventory>>> getAllOrder(@RequestBody(required = false) GetRequest request) {
        List<Inventory> list = service.getAll(request);

        ApiResponse<List<Inventory>> response = new ApiResponse<>("Get all order successful", 200, list);
        return ResponseEntity.ok(response);
    }
}
