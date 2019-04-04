package com.yumu.hexie.service.user.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.yumu.hexie.common.util.StringUtil;
import com.yumu.hexie.model.ModelConstant;
import com.yumu.hexie.model.user.PointRecord;
import com.yumu.hexie.model.user.PointRecordRepository;
import com.yumu.hexie.model.user.User;
import com.yumu.hexie.model.user.UserRepository;
import com.yumu.hexie.service.user.PointService;

@Service("pointService")
public class PointServiceImpl implements PointService {

	@Inject
	private PointRecordRepository pointRecordRepository;
	@Inject
	private UserRepository userRepository;
	@Override
	public void addLvdou(User user, int point, String key) {
		if(StringUtil.isNotEmpty(key)) {
			List<PointRecord> rs = pointRecordRepository.findAllByKeyStr(key);
			if(rs != null&&rs.size()>0) {
				return;
			}
		}
		PointRecord pr = new PointRecord();
		pr.setType(ModelConstant.POINT_TYPE_LVDOU);
		pr.setUserId(user.getId());
		pr.setPoint(point);
		pr.setKeyStr(key);
		pointRecordRepository.save(pr);
		user.setLvdou(user.getLvdou()+point);
		userRepository.save(user);
	}

	@Override
	public void addZhima(User user, int point, String key) {
		if(StringUtil.isNotEmpty(key)) {
			List<PointRecord> rs = pointRecordRepository.findAllByKeyStr(key);
			if(rs != null&&rs.size()>0) {
				return;
			}
		}
		PointRecord pr = new PointRecord();
		pr.setType(ModelConstant.POINT_TYPE_ZIMA);
		pr.setUserId(user.getId());
		pr.setPoint(point);
		pr.setKeyStr(key);
		pointRecordRepository.save(pr);
		user.setZhima(user.getZhima()+point);
		userRepository.save(user);
	}

}
