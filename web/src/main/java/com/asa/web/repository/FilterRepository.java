package com.asa.web.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.asa.web.model.Filter;

@Repository
public interface FilterRepository extends JpaRepository<Filter, UUID> {

}
