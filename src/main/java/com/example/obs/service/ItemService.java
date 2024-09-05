package com.example.obs.service;

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
import java.util.Optional;

@Service
@Transactional
public class ItemService {

    @Autowired
    private ItemRepository repository;

    public Item save(Item request){
        return repository.save(request);
    }

    public Boolean delete(String id){
        Boolean status = repository.existsById(Long.parseLong(id));
        if(status) {
            repository.deleteById(Long.parseLong(id));
            return true;
        }
        return null;
    }

    public Item update(Item request){
        Boolean status = repository.existsById(request.getId());
        if(status) {
            return repository.save(request);
        }
        return null;
    }

    public Item getById(String id){
        Optional<Item> item = repository.findById(Long.parseLong(id));
        if(item.isPresent()) {
            Item it = item.get();
            return it;
        }
        return null;
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
