package com.yumu.hexie.service.shequ.impl;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import javax.inject.Inject;
import javax.xml.bind.ValidationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yumu.hexie.common.util.TransactionUtil;
import com.yumu.hexie.integration.wuye.WuyeUtil;
import com.yumu.hexie.integration.wuye.resp.BaseResult;
import com.yumu.hexie.integration.wuye.resp.BillListVO;
import com.yumu.hexie.integration.wuye.resp.CellListVO;
import com.yumu.hexie.integration.wuye.resp.HouseListVO;
import com.yumu.hexie.integration.wuye.resp.PayWaterListVO;
import com.yumu.hexie.integration.wuye.vo.HexieHouse;
import com.yumu.hexie.integration.wuye.vo.HexieUser;
import com.yumu.hexie.integration.wuye.vo.PayResult;
import com.yumu.hexie.integration.wuye.vo.PaymentInfo;
import com.yumu.hexie.integration.wuye.vo.WechatPayInfo;
import com.yumu.hexie.model.market.ServiceOrder;
import com.yumu.hexie.model.user.User;
import com.yumu.hexie.model.user.UserRepository;
import com.yumu.hexie.service.exception.BizValidateException;
import com.yumu.hexie.service.shequ.WuyeService;

@Service("wuyeService")
public class WuyeServiceImpl<T> implements WuyeService {
	
	private static final Logger logger = LoggerFactory.getLogger(WuyeServiceImpl.class);

	@Inject
	private UserRepository userRepository;
	
	@Inject
	private TransactionUtil<T> transactionUtil;
	
	@Override
	public HouseListVO queryHouse(String userId) {
		return WuyeUtil.queryHouse(userId).getData();
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public HexieUser bindHouse(User user, String stmtId, HexieHouse house) {
		
		logger.error("userId : " + user.getId());
		logger.error("hosue is :" + house.toString());
		
		User currUser = userRepository.findOne(user.getId());
		
		logger.error("total_bind :" + currUser.getTotal_bind());
		
		if (currUser.getTotal_bind() <= 0) {//从未绑定过的做新增
			currUser.setTotal_bind(1);
			currUser.setSect_id(house.getSect_id());
			currUser.setSect_name(house.getSect_name());
			currUser.setCell_id(house.getMng_cell_id());
			currUser.setCell_addr(house.getCell_addr());
			
			user.setTotal_bind(1);
			user.setSect_id(house.getSect_id());
			user.setSect_name(house.getSect_name());
			user.setCell_id(house.getMng_cell_id());
			user.setCell_addr(house.getCell_addr());	//set到session
			
		}else {
			currUser.setTotal_bind((currUser.getTotal_bind()+1));
		}
		
		BaseResult<HexieUser> r= WuyeUtil.bindHouse(currUser.getWuyeId(), stmtId, house.getMng_cell_id());
		if ("04".equals(r.getResult())){
			throw new BizValidateException("当前用户已经认领该房屋!");
		}
		if ("05".equals(r.getResult())) {
			throw new BizValidateException("用户当前绑定房屋与已绑定房屋不属于同个小区，暂不支持此功能。");
		}
		if ("01".equals(r.getResult())) {
			throw new BizValidateException("账户不存在");
		}
		
		if (r.isSuccess()) {
			//添加电话到user表
			currUser.setOfficeTel(r.getData().getOffice_tel());	//保存到数据库
			user.setOfficeTel(r.getData().getOffice_tel());	//set到session
		}
		userRepository.save(currUser);
		
		return r.getData();
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public BaseResult<String> deleteHouse(User user, String userId, String houseId) {
		
		User currUser = userRepository.findOne(user.getId());
		long curr_bind = currUser.getTotal_bind() - 1;
		if (curr_bind <= 0) {
			currUser.setSect_id("0");
			currUser.setSect_name("");
			currUser.setCell_id("");
			currUser.setCell_addr("");
			currUser.setTotal_bind(curr_bind);
			
			user.setSect_id("0");	//set到session
			user.setSect_name("");
			user.setCell_id("");
			user.setCell_addr("");
			user.setTotal_bind(curr_bind);
			
		}else {
			currUser.setTotal_bind(curr_bind);
			
		}
		
		BaseResult<String> r = WuyeUtil.deleteHouse(userId, houseId);
		boolean isSuccess = r.isSuccess();
		
		if (isSuccess) {
			//添加电话到user表
			currUser.setOfficeTel(r.getData());
			user.setOfficeTel(r.getData());	//set到session
		}else {
			throw new BizValidateException("删除房屋失败。");
		}
		
		userRepository.save(currUser);	//保存的数据库
		return r;
	}

	@Override
	public HexieHouse getHouse(String userId, String stmtId, String house_id) {
		return WuyeUtil.getHouse(userId, stmtId, house_id).getData();
	}

	@Override
	public HexieUser userLogin(String openId) {
		return WuyeUtil.userLogin(openId).getData();
	}

	@Override
	public PayWaterListVO queryPaymentList(String userId, String startDate,
			String endDate) {
		return WuyeUtil.queryPaymentList(userId, startDate, endDate).getData();
	}

	@Override
	public PaymentInfo queryPaymentDetail(String userId, String waterId) {
		return WuyeUtil.queryPaymentDetail(userId, waterId).getData();
	}

	@Override
	public BillListVO queryBillList(String userId, String payStatus,
			String startDate, String endDate,String currentPage, String totalCount, String house_id) {
		return WuyeUtil.queryBillList(userId, payStatus, startDate, endDate, currentPage, totalCount, house_id).getData();
	}

	@Override
	public PaymentInfo getBillDetail(String userId, String stmtId,
			String anotherbillIds) {
		return WuyeUtil.getBillDetail(userId, stmtId, anotherbillIds).getData();
	}

	@Override
	public WechatPayInfo getPrePayInfo(String userId, String billId,
			String stmtId, String openId, String couponUnit, String couponNum, 
			String couponId,String mianBill,String mianAmt, String reduceAmt) throws ValidationException {
		return WuyeUtil.getPrePayInfo(userId, billId, stmtId, openId, couponUnit, couponNum, couponId,mianBill,mianAmt, reduceAmt)
				.getData();
	}

	public PaymentInfo queryPayMent(String userId, String waterId) {
		return WuyeUtil.queryPaymentDetail(userId, waterId).getData();
	}
	
	@Override
	public PayResult noticePayed(User user, String billId, String stmtId, String tradeWaterId, String packageId, String bind_switch) {
		PayResult pay = WuyeUtil.noticePayed(user.getWuyeId(), billId, stmtId, tradeWaterId, packageId).getData();
		//如果switch为1，则顺便绑定该房屋
		if("1".equals(bind_switch))
		{
			BaseResult<String> result = WuyeUtil.getPayWaterToCell(user.getWuyeId(), tradeWaterId);
			String ids = result.getResult();
			String[] idsSuff = ids.split(",");
			//因为考虑一次支持存在多套房子的情况
			for (int i = 0; i < idsSuff.length; i++) {
				try {
					HexieHouse house = getHouse(user.getWuyeId(), stmtId, idsSuff[i]);
					if(house!=null)
					{
						bindHouse(user, stmtId, house);
					}
				} catch(Exception e)
				{
					//不影响支付完整性，如果有问题则不向外面抛
					logger.error("bind house error:"+e);
				}
			}
		}
		return pay;
	}

	@Override
	public BillListVO quickPayInfo(String stmtId, String currPage, String totalCount) {
		return WuyeUtil.quickPayInfo(stmtId, currPage, totalCount).getData();
	}

	@Override
	public String queryCouponIsUsed(String userId) {

		BaseResult<String> r = WuyeUtil.couponUseQuery(userId);
		return r.getResult();
	}

	//根据名称模糊查询合协社区小区列表
	@Override
	public CellListVO getVagueSectByName(String sect_name) {
		return WuyeUtil.getVagueSectByName(sect_name).getData();
	}

	@Override
	public CellListVO querySectHeXieList(String sect_id, String build_id, String unit_id, String data_type) {
		return WuyeUtil.getMngHeXieList(sect_id, build_id, unit_id, data_type).getData();
	}

	@Override
	public void fixUserBindedHouses(String sign) {

		if ("dhzj3".equals(sign)) {
			
			long count = userRepository.count();
			Long loops = count/1000;
			if (count%1000!=0) {
				loops++;
			}
			//每页1000条循环user表
			for (int i = 0; i < loops.intValue(); i++) {
				
				Pageable pageable = new PageRequest(i*1000, 1000);
				Page<User> userList = userRepository.findAll(pageable);
				for (User user : userList) {
					BaseResult<HouseListVO> hio = WuyeUtil.queryHouse(String.valueOf(user.getWuyeId()));
					transactionUtil.transact(s->fixBindedHouse(user, hio.getData()));
				}
			}
			
		}
		
	}
	
	public static void main(String[] args) {
		
		Function<Integer, Integer> name = e -> e * 2;
        Function<Integer, Integer> square = e -> e * e;
        
        int value = name.andThen(square).apply(3);
        System.out.println("andThen value=" + value);
        int value2 = name.compose(square).apply(3);
        System.out.println("compose value2=" + value2);
        //返回一个执行了apply()方法之后只会返回输入参数的函数对象
        Object identity = Function.identity().apply("huohuo");
        System.out.println(identity);

        ServiceOrder serviceOrder = new ServiceOrder();
        Consumer<ServiceOrder> orderConsumer = serviceOrderC -> serviceOrderC.setPrice(100f);
        orderConsumer.accept(serviceOrder);
        System.out.println("price:"+serviceOrder.getPrice());
        
        
	}
	
	/**
	 * 修正已绑房屋用户正确的绑定房屋数和小区、房屋地址
	 * @param user
	 * @param vo
	 */
	public void fixBindedHouse(User user, HouseListVO vo) {
		
		List<HexieHouse> houList = vo.getHou_info();
		if (houList!=null && houList.size()>0) {
			user.setTotal_bind(houList.size());
			user.setSect_id(houList.get(0).getSect_id());
			user.setSect_name(houList.get(0).getSect_name());
			user.setCell_id(houList.get(0).getMng_cell_id());
			user.setCell_addr(houList.get(0).getCell_addr());
		}else {
			user.setTotal_bind(0);
			user.setSect_id("");
			user.setSect_name("");
			user.setCell_id("");
			user.setCell_addr("");
		}
		userRepository.save(user);
		
		
	}
	
	

}
