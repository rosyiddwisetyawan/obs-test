package com.example.obs.service;

import com.example.obs.exception.ResourceNotFoundException;
import com.example.obs.model.GetRequest;
import com.example.obs.model.Item;
import com.example.obs.repository.ItemRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ItemService {

    @Autowired
    private ItemRepository repository;

    public Item save(Item request){

        Item item = repository.findByName(request.getName());
        if(item!=null){
            throw new IllegalArgumentException("Item already exist");
        }
        return repository.save(request);
    }

    public Boolean delete(String id){
        Boolean status = repository.existsById(Long.parseLong(id));
        if(status) {
            repository.deleteById(Long.parseLong(id));
            return true;
        }
        throw new ResourceNotFoundException("Item not found id: "+id);
    }

    public Item update(Item request){
        Boolean status = repository.existsById(request.getId());
        if(status) {
            return repository.save(request);
        }
        throw new ResourceNotFoundException("Item not found id: "+request.getId());
    }

    public Item getById(String id){
        return repository.findById(Long.parseLong(id))
                .orElseThrow(() -> new ResourceNotFoundException("item not found for id: " + id));
    }

    public List<Item> getAll(GetRequest request) {

        if(request==null){
            return repository.findAll();
        }

        Pageable paging = PageRequest.of(request.getPageNo(), request.getPageSize(), Sort.by("id"));

        Page<Item> pagedResult = repository.findAll(paging);

        if(pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<Item>();
        }
    }
}
