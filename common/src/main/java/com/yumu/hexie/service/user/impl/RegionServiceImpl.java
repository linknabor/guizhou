package com.yumu.hexie.service.user.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yumu.hexie.model.distribution.region.Region;
import com.yumu.hexie.model.distribution.region.RegionRepository;
import com.yumu.hexie.service.user.RegionService;

@Service("regionService")
public class RegionServiceImpl implements RegionService{
	
	@Autowired
	RegionRepository regionRepository;

	@Override
	public List<Region> findByNameAndParentId(String name,Long parentId) {
		return regionRepository.findAllByParentIdAndName(parentId, name);
	}

	@Override
	public Region saveRegion(Region r) {
		r.setLatitude(0.0);
		r.setLongitude(0.0);
		return regionRepository.save(r);
	}

	@Override
	public List<Region> getRegionAll() {
		return regionRepository.findAll();
	}

	@Override
	public Region getRegionInfoById(long id) {
		return regionRepository.findOne(id);
	}

	@Override
	public List<Region> findAllBySectId(String sectId) {
		return regionRepository.findAllBySectId(sectId);
	}


}
