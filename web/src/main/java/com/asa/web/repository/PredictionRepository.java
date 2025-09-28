package com.asa.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.asa.web.model.Prediction;

@Repository
public interface PredictionRepository extends JpaRepository<Prediction, Long> {

}
