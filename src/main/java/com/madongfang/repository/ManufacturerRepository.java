package com.madongfang.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.madongfang.entity.Manufacturer;

public interface ManufacturerRepository extends JpaRepository<Manufacturer, Integer> {

}
