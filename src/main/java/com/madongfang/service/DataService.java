package com.madongfang.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.madongfang.api.DataApi;
import com.madongfang.api.ReturnApi;
import com.madongfang.entity.Data;
import com.madongfang.repository.DataRepository;

@Service
public class DataService {

	public List<DataApi> getDatas(int measureId) {
		List<DataApi> measureDatas = new LinkedList<>();
		List<Data> datas;
		if (measureId == 0)
		{
			datas = dataRepository.findByMeasureIdIsNull();
		}
		else
		{
			datas = dataRepository.findByMeasureId(measureId);
		}
		
		for (Data data : datas) {
			measureDatas.add(convertToDataApi(data));
		}
		
		return measureDatas;
	}
	
	public ReturnApi addDatas(List<DataApi> datas) {
		List<Data> saveDatas = new LinkedList<>();
		for (DataApi dataApi : datas) {
			Data data = new Data();
			data.setBaseValue(dataApi.getBaseValue());
			data.setBaseValue0(dataApi.getBaseValue0());
			data.setDecimalDigit(dataApi.getDecimalDigit());
			data.setRow(dataApi.getRow());
			data.setValue1(dataApi.getValue1());
			data.setValue2(dataApi.getValue2());
			data.setValue3(dataApi.getValue3());
			saveDatas.add(data);
		}
		dataRepository.save(saveDatas);
		return new ReturnApi(0, "OK");
	}
	
	@Autowired
	private DataRepository dataRepository;
	
	private DataApi convertToDataApi(Data data)
	{
		DataApi dataApi = new DataApi();
		dataApi.setBaseValue(data.getBaseValue());
		dataApi.setBaseValue0(data.getBaseValue0());
		dataApi.setDecimalDigit(data.getDecimalDigit());
		dataApi.setId(data.getId());
		dataApi.setRow(data.getRow());
		dataApi.setValue1(data.getValue1());
		dataApi.setValue2(data.getValue2());
		dataApi.setValue3(data.getValue3());
		
		return dataApi;
	}
}
