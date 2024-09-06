package com.example.obs.service;

import com.example.obs.exception.ResourceNotFoundException;
import com.example.obs.model.GetRequest;
import com.example.obs.model.Inventory;
import com.example.obs.model.Order;
import com.example.obs.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private InventoryService inventoryService;

    public Order save(Order request){
        Inventory data = inventoryService.save(new Inventory(null,request.getItemId(), request.getQty(),"T|order"));
        if(data!=null){
            return orderRepository.save(request);
        }
        return null;
    }

    public Boolean delete(String id){
        Boolean status = orderRepository.existsById(Long.parseLong(id));
        if(status) {
            orderRepository.deleteById(Long.parseLong(id));
            return true;
        }
        throw new ResourceNotFoundException("Order not found id: "+id);
    }

    public Order update(Order request){
        Boolean status = orderRepository.existsById(request.getId());
        if(status) {
            return orderRepository.save(request);
        }
        throw new ResourceNotFoundException("Order not found id: "+request.getId());
    }

    public Order getById(String id){

        return orderRepository.findById(Long.parseLong(id))
                .orElseThrow(() -> new ResourceNotFoundException("Order not found for id: " + id));
    }

    public List<Order> getAll(GetRequest request) {

        if(request==null){
            return orderRepository.findAll();
        }

        Pageable paging = PageRequest.of(request.getPageNo(), request.getPageSize(), Sort.by("id"));

        Page<Order> pagedResult = orderRepository.findAll(paging);

        if(pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<Order>();
        }
    }

}
