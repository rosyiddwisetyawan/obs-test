package com.example.obs.controller;

import com.example.obs.model.ApiResponse;
import com.example.obs.model.GetRequest;
import com.example.obs.model.Item;
import com.example.obs.service.ItemService;
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
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemService service;

    @PostMapping(value = "/add")
    private ResponseEntity<?> save(@Valid @RequestBody Item itemRequest){

        Item item = service.save(itemRequest);
        if (item != null) {
            ApiResponse<Item> response = new ApiResponse<>("Add data success", 200, item);
            return ResponseEntity.ok(response);
        } else {
            ApiResponse<Item> response = new ApiResponse<>("Add data failed", 400, null);
            return ResponseEntity.status(400).body(response);
        }

    }

    @PostMapping(value = "/delete/{id}")
    private ResponseEntity<?> delete(@PathVariable String id){

        Boolean status = service.delete(id);
        if (status) {
            ApiResponse<String> response = new ApiResponse<>("Delete data success", 200, id);
            return ResponseEntity.ok(response);
        } else {
            ApiResponse<String> response = new ApiResponse<>("Delete data failed", 400, null);
            return ResponseEntity.status(400).body(response);
        }
    }

    @PostMapping(value = "/update")
    private ResponseEntity<?> update(@Valid @RequestBody Item itemRequest){

        Item item = service.update(itemRequest);
        if (item!=null) {
            ApiResponse<Item> response = new ApiResponse<>("Update data success", 200, item);
            return ResponseEntity.ok(response);
        } else {
            ApiResponse<Item> response = new ApiResponse<>("Update data failed", 400, null);
            return ResponseEntity.status(400).body(response);
        }
    }

    @GetMapping(value = "/get/{id}")
    private ResponseEntity<?> getById(@PathVariable String id){
        Item item = service.getById(id);
        if (item != null) {
            ApiResponse<Item> response = new ApiResponse<>("Get data success", 200, item);
            return ResponseEntity.ok(response);
        } else {
            ApiResponse<Item> response = new ApiResponse<>("Get data failed", 400, null);
            return ResponseEntity.status(400).body(response);
        }
    }

    @GetMapping(value = "/get")
    public ResponseEntity<ApiResponse<List<Item>>> getAllItem(@RequestBody(required = false) GetRequest request) {
        List<Item> list = service.getAll(request);

        ApiResponse<List<Item>> response = new ApiResponse<>("Get all item successful", 200, list);
        return ResponseEntity.ok(response);
    }

}
