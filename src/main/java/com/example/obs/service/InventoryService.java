package com.example.obs.service;

import com.example.obs.model.GetRequest;
import com.example.obs.model.Inventory;
import com.example.obs.repository.InventoryRepository;
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
public class InventoryService {

    @Autowired
    private InventoryRepository repository;

    public Inventory save(Inventory request){
        String[] arrType = request.getType().split("\\|");
        Inventory inv = repository.findByItemId(request.getItemId());
        if(inv==null&&arrType.length==1){
            if(arrType[0].equals("T")){
                return repository.save(request);
            }else {
                return null;
            }
        }

        if(arrType[0].equals("T")){
            inv.setQty(inv.getQty()+request.getQty());
        } else if (arrType[0].equals("W")) {
            if(request.getQty()> inv.getQty()){
                return null;
            }
            inv.setQty(inv.getQty()-request.getQty());
        }

        return repository.save(inv);
    }

    public Boolean delete(String id){
        Boolean status = repository.existsById(Long.parseLong(id));
        if(status) {
            repository.deleteById(Long.parseLong(id));
            return true;
        }
        return null;
    }

    public Inventory update(Inventory request){
        Boolean status = repository.existsById(request.getId());
        if(status) {
            return repository.save(request);
        }
        return null;
    }

    public Inventory getById(String id){
        Optional<Inventory> inventory = repository.findById(Long.parseLong(id));
        if(inventory.isPresent()) {
            Inventory inv = inventory.get();
            return inv;
        }
        return null;
    }

    public List<Inventory> getAll(GetRequest request) {
        if(request==null){
            return repository.findAll();
        }
        Pageable paging = PageRequest.of(request.getPageNo(), request.getPageSize(), Sort.by("id"));

        Page<Inventory> pagedResult = repository.findAll(paging);

        if(pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<Inventory>();
        }
    }
}
