package com.asa.web.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.asa.web.model.Filter;
import com.asa.web.repository.FilterRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FilterService {
    private final FilterRepository filterRepository;

    public List<Filter> getAllFilters() {
        return filterRepository.findAll(Sort.by("name").ascending());
    }

    public Filter getFilterById(UUID id) {
        return filterRepository.findById(id).orElse(null);
    }

    public Filter saveFilter(Filter filter) {
        return filterRepository.save(filter);
    }
}
