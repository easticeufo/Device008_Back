package com.madongfang.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.madongfang.entity.Data;

public interface DataRepository extends JpaRepository<Data, Integer> {
	public List<Data> findByMeasureId(Integer measureId);
	
	public List<Data> findByMeasureIdIsNull();
	
	public List<Data> findByIdIn(Collection<Integer> dataIds);
}
