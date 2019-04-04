package com.yumu.hexie.service.common;

import com.yumu.hexie.model.market.ServiceOrder;
import com.yumu.hexie.model.promotion.share.ShareAccessRecord;
import com.yumu.hexie.model.user.User;

public interface ShareService {
	//记录访问信息
	public void access(User user,ShareAccessRecord record);
	//下单后确认是否存在分享结果，并保存
	public void record(ServiceOrder order);
}
