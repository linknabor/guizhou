package com.yumu.hexie.service.shequ.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.yumu.hexie.common.util.StringUtil;
import com.yumu.hexie.integration.wuye.WuyeUtil;
import com.yumu.hexie.integration.wuye.resp.BaseResult;
import com.yumu.hexie.integration.wuye.vo.HexieConfig;
import com.yumu.hexie.model.ModelConstant;
import com.yumu.hexie.model.user.User;
import com.yumu.hexie.service.shequ.ParamService;

@Service
public class ParamServiceImpl implements ParamService {
	
	private final static Logger logger = LoggerFactory.getLogger(ParamServiceImpl.class);
	public final static String PARAM_NAMES = "ONLINE_REPAIR,ONLINE_SUGGESTION,ONLINE_MESSAGE";
	public final static String CACHED_KEY = "param";
	
	@Autowired
	private RedisTemplate<String, Map<String, String>> redisTemplate;

	/**
	 * 缓存物业公司参数到redis中，如果失败重新请求，总共请求3次
	 */
	@Override
	public void cacheParam(String infoId, String type) {

		boolean isSuccess = false;
		int count = 0;
		while(!isSuccess && count <3) {	//请求三次
			try {
				BaseResult<HexieConfig> baseResult = WuyeUtil.queryServiceCfg(infoId, type, PARAM_NAMES);
				HexieConfig hexieConfig = baseResult.getData();
				if (hexieConfig == null) {
					logger.error("未查询到参数：" + PARAM_NAMES);
					break;
				}
				Map<String, String> paramMap = hexieConfig.getParamMap();
				Map<String, Map<String, String>> cachedMap = new HashMap<>();
				cachedMap.put(infoId, paramMap);
				redisTemplate.opsForHash().putAll(CACHED_KEY, cachedMap);
				isSuccess = true;
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				count++;
			}
			
		}
		
	}

	/**
	 * 根据用户的cspId获取该用户的公司参数，如果缓存中没有参数，则发起请求获取参数
	 * @param user 用户信息，其中绑了房子的用户才有cspId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, String> getParamByUser(User user) {

		String cspId = user.getCspId();
		if (StringUtil.isEmpty(cspId) || "0".equals(cspId)) {
			return new HashMap<String, String>();
		}
		Map<String, String> paramMap = (Map<String, String>) redisTemplate.opsForHash().get(CACHED_KEY, cspId);
		if (paramMap == null || paramMap.entrySet().isEmpty()) {
			cacheParam(cspId, ModelConstant.PARA_TYPE_CSP);
			paramMap = (Map<String, String>) redisTemplate.opsForHash().get(CACHED_KEY, cspId);
		}
		return paramMap;
	}
	

}
