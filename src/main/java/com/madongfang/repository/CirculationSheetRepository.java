package com.madongfang.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.madongfang.entity.CirculationSheet;

public interface CirculationSheetRepository extends JpaRepository<CirculationSheet, Integer> {

	public List<CirculationSheet> findByUserId(int userId);
}
