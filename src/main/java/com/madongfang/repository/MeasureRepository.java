package com.madongfang.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.madongfang.entity.Measure;

public interface MeasureRepository extends JpaRepository<Measure, Integer> {

	public List<Measure> findByTimeBetween(Date beginTime, Date endTime);
	
	public List<Measure> findByCirculationSheetIdOrderByReportTypeAsc(Integer circulationId);
	
	public Measure findFirstByOrderByIdDesc();
}
