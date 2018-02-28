package com.madongfang.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.madongfang.entity.Model;

public interface ModelRepository extends JpaRepository<Model, Integer> {

}
