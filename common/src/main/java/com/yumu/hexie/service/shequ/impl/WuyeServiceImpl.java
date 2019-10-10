package com.yumu.hexie.service.shequ.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
import com.yumu.hexie.integration.wuye.vo.InvoiceInfo;
import com.yumu.hexie.integration.wuye.vo.PayResult;
import com.yumu.hexie.integration.wuye.vo.PaymentInfo;
import com.yumu.hexie.integration.wuye.vo.WechatPayInfo;
import com.yumu.hexie.model.distribution.region.Region;
import com.yumu.hexie.model.distribution.region.RegionRepository;
import com.yumu.hexie.model.user.AddRegionSectIdWorker;
import com.yumu.hexie.model.user.AddUserSectIdWorker;
import com.yumu.hexie.model.user.Address;
import com.yumu.hexie.model.user.AddressRepository;
import com.yumu.hexie.model.user.AddressWorker;
import com.yumu.hexie.model.user.TempHouse;
import com.yumu.hexie.model.user.TempHouseRepository;
import com.yumu.hexie.model.user.TempHouseWorker;
import com.yumu.hexie.model.user.TempSect;
import com.yumu.hexie.model.user.TempSectRepository;
import com.yumu.hexie.model.user.TempUserRepository;
import com.yumu.hexie.model.user.User;
import com.yumu.hexie.model.user.UserRepository;
import com.yumu.hexie.service.exception.BizValidateException;
import com.yumu.hexie.service.shequ.WuyeService;
import com.yumu.hexie.service.user.AddressService;
import com.yumu.hexie.service.user.RegionService;
import com.yumu.hexie.service.user.UserService;

@Service("wuyeService")
public class WuyeServiceImpl implements WuyeService {
	private static final Logger log = LoggerFactory.getLogger(WuyeServiceImpl.class);
	
	private static Map<String,Long> map=null;
	
	@Autowired
	private TempSectRepository tempSectRepository;
	
	@Autowired
	private RegionRepository regionRepository;
	
	@Autowired
	private RegionService regionService;
	
	@Autowired
	private TempUserRepository  tempUserRepository;
	
	@Autowired
	private AddressService addressService;
	
	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private WuyeService wuyeService;
	
	@SuppressWarnings("rawtypes")
	@Autowired
	private TransactionUtil transactionUtil;
	
	@Override
	public HouseListVO queryHouse(String userId) {
		return WuyeUtil.queryHouse(userId).getData();
	}

	@PostConstruct
	public void init() {
		if(map==null){
			getNeedRegion();
		}
	}

	@Override
	public HexieUser bindHouse(String userId, String stmtId, String houseId) {
		BaseResult<HexieUser> r= WuyeUtil.bindHouse(userId, stmtId, houseId);
		if("04".equals(r.getResult())){
			throw new BizValidateException("当前用户已经认领该房屋!");
		}
		if ("05".equals(r.getResult())) {
			throw new BizValidateException("用户当前绑定房屋与已绑定房屋不属于同个小区，暂不支持此功能。");
		}
		if("01".equals(r.getResult())) {
			throw new BizValidateException("账户不存在！");
		}
		return r.getData();
	}

	@Override
	public BaseResult<String> deleteHouse(String userId, String houseId) {
		BaseResult<String> r = WuyeUtil.deleteHouse(userId, houseId);
		return r;
	}

	@Override
	public HexieHouse getHouse(String userId, String stmtId) {
		return WuyeUtil.getHouse(userId, stmtId).getData();
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
			String startDate, String endDate,String currentPage, String totalCount,String house_id,String sect_id) {
		return WuyeUtil.queryBillList(userId, payStatus, startDate, endDate, currentPage, totalCount,house_id,sect_id).getData();
	}

	@Override
	public PaymentInfo getBillDetail(String userId, String stmtId,
			String anotherbillIds) {
		return WuyeUtil.getBillDetail(userId, stmtId, anotherbillIds).getData();
	}

	@Override
	public WechatPayInfo getPrePayInfo(User user, String billId,
			String stmtId, String couponUnit, String couponNum, 
			String couponId,String mianBill,String mianAmt, String reduceAmt, 
			String invoice_title_type, String credit_code, String invoice_title) throws Exception {
		return WuyeUtil.getPrePayInfo(user, billId, stmtId, couponUnit, couponNum, couponId,mianBill,mianAmt, reduceAmt, 
				invoice_title_type, credit_code, invoice_title)
				.getData();
	}

	@Override
	public PayResult noticePayed(String userId, String billId, String stmtId, String tradeWaterId, String packageId) {
		return WuyeUtil.noticePayed(userId, billId, stmtId, tradeWaterId, packageId).getData();
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

	@Override
	public String updateInvoice(String mobile, String invoice_title, String invoice_title_type, String credit_code, String trade_water_id) {
		BaseResult<String> r = WuyeUtil.updateInvoice(mobile, invoice_title, invoice_title_type, credit_code, trade_water_id);
		return r.getResult();
	}

	@Override
	public InvoiceInfo getInvoiceByTradeId(String trade_water_id) {
		return WuyeUtil.getInvoiceInfo(trade_water_id).getData();
	}
	
	@Override
	public CellListVO querySectHeXieList(String sect_id, String build_id,
			String unit_id, String data_type) {
		try {
			return WuyeUtil.getMngHeXieList(sect_id, build_id, unit_id, data_type).getData();
		} catch (Exception e) {
			log.error("异常捕获信息:"+e);
			e.printStackTrace();
		}
		return null;
	}
	
	//根据名称模糊查询合协社区小区列表
	@Override
	public CellListVO getVagueSectByName(String sect_name) {
		try {
			BaseResult<CellListVO> s = WuyeUtil.getVagueSectByName(sect_name);
			log.error(s.getResult());
			return WuyeUtil.getVagueSectByName(sect_name).getData();
		} catch (Exception e) {
			log.error("异常捕获信息:"+e);
		}
		return null;
	}

	@Override
	public HexieUser bindHouseNoStmt(String userId, String houseId, String area) {
		BaseResult<HexieUser> r= WuyeUtil.bindHouseNoStmt(userId, houseId, area);
		if("04".equals(r.getResult())){
			throw new BizValidateException("当前用户已经认领该房屋!");
		}
		if ("05".equals(r.getResult())) {
			throw new BizValidateException("用户当前绑定房屋与已绑定房屋不属于同个小区，暂不支持此功能。");
		}
		if("01".equals(r.getResult())) {
			throw new BizValidateException("账户不存在！");
		}
		if("06".equals(r.getResult())) {
			throw new BizValidateException("建筑面积允许误差在±1平方米以内！");
		}
		return r.getData();
	}

	@Override
	public HexieUser getAddressByBill(String billId) {
		
		return WuyeUtil.getAddressByBill(billId).getData();
	}

	@Override
	public void addSectToRegion() {
		List<TempSect> list=tempSectRepository.findAll();
		for (TempSect tempSect : list) {
			List<Region> regionList=regionRepository.findAllByNameAndParentName(tempSect.getSectName(), tempSect.getRegionName());
			if(regionList.size()==0){
				Region region = regionRepository.findByNameAndRegionType(tempSect.getRegionName(), 3);
				if(region==null){
					log.error("外地小区，区名："+tempSect.getRegionName()+"小区名"+tempSect.getSectName());
					continue;
				}
				Region r = new Region();
				r.setCreateDate(System.currentTimeMillis());
				r.setName(tempSect.getSectName());
				r.setParentId(region.getId());
				r.setParentName(region.getName());
				r.setRegionType(4);
				r.setLatitude(0.0);
				r.setLongitude(0.0);
				r.setSectId(tempSect.getSectId());
				regionService.saveRegion(r);
			}
		}
		
	}

	@Override
	public void addDefaultAddressAndUser() throws InterruptedException {
		ExecutorService pool = Executors.newFixedThreadPool(10);
		List<TempSect> list=tempSectRepository.findAll();
		for (TempSect tempSect : list) {
			AddressWorker w=new AddressWorker(tempSect, userService, wuyeService, transactionUtil, tempUserRepository);
			pool.execute(w);
		}
		pool.shutdown();
		while(!pool.awaitTermination(30l, TimeUnit.SECONDS)){
		};
		
		
	}

	@Override
	public void setDefaultAddress(User user,HexieUser u) {

		boolean result = true;
		List<Address> list = addressService.getAddressByuserIdAndAddress(user.getId(), u.getCell_addr());
		for (Address address : list) {
			if (address.isMain()) {
				log.error("存在重复默认地址:"+address.getDetailAddress()+"---id:"+address.getId());
				result = false;
				break;
			}
		}
		if (result) {
			List<Address> addressList= addressService.getAddressByMain(user.getId(), true);
			for (Address address : addressList) {
				if (address != null) {
					address.setMain(false);
					addressRepository.save(address);
					log.error("默认地址设置为不是默认:"+address.getDetailAddress()+"---id:"+address.getId());
				}
			}
			
			List<Region> re=regionService.findAllBySectId(u.getSect_id());
			if(re.size()==0){
				log.error("未查询到小区！"+u.getSect_name());
				return;
			}
			Address add = new Address();
			if (list.size() > 0) {
				add = list.get(0);
			} else {
				
				add.setReceiveName(user.getNickname());
				add.setTel(user.getTel());
				add.setUserId(user.getId());
				add.setCreateDate(System.currentTimeMillis());
				add.setXiaoquId(re.get(0).getId());
				add.setXiaoquName(u.getSect_name());
				add.setDetailAddress(u.getCell_addr());
				add.setCity(u.getCity_name());
				add.setCityId(map.get(u.getCity_name()));
				add.setCounty(u.getRegion_name());
				add.setCountyId(map.get(u.getRegion_name()));
				add.setProvince(u.getProvince_name());
				add.setProvinceId(map.get(u.getProvince_name()));
				//add.setXiaoquAddress(u.getSect_addr());
				double latitude = 0;
				double longitude = 0;
				if (user.getLatitude() != null) {
					latitude = user.getLatitude();
				}

				if (user.getLongitude() != null) {
					longitude = user.getLongitude();
				}
				add.setLatitude(latitude);
				add.setLongitude(longitude);

			}
			add.setMain(true);
			addressRepository.save(add);
			user.setProvince(u.getProvince_name());
			user.setCity(u.getCity_name());
			user.setCounty(u.getRegion_name());
			user.setXiaoquId(re.get(0).getId());
			user.setXiaoquName(u.getSect_name());
			log.error("保存用户成功！！！");
		}
		user.setSectId(u.getSect_id());	
		user.setCspId(u.getCsp_id());
		userService.save(user);
		
	}

	@Override
	public void saveRegion(HexieUser u) {
		log.error("进入保存region！！！");
	//	List<Region> regionList=regionRepository.findAllByNameAndParentName(u.getSect_name(), u.getRegion_name());
		List<Region> regionList=regionRepository.findAllBySectId(u.getSect_id());
		if(regionList.size()==0){
			Region region = regionRepository.findByNameAndRegionType(u.getRegion_name(), 3);
			if(region!=null){
				Region r = new Region();
				r.setCreateDate(System.currentTimeMillis());
				r.setName(u.getSect_name());
				r.setParentId(region.getId());
				r.setParentName(region.getName());
				r.setRegionType(4);
				r.setLatitude(0.0);
				r.setLongitude(0.0);
				r.setSectId(u.getSect_id());
				r.setXiaoquAddress(u.getSect_addr());
				regionService.saveRegion(r);
			}
			log.error("保存region完成！！！");
		}
	}

	@Override
	@Transactional
	public void updateAddr() {
		List<Address>  addressList=addressRepository.getNeedAddress();
		getNeedRegion();
		for (Address address : addressList) {
			Long provinceId=map.get(address.getProvince());
			Long cityId=map.get(address.getCity());
			Long countyId=map.get(address.getCounty());
			
			if(provinceId ==null ){
				continue;
			}
			if(cityId ==null ){
				continue;
			}
			if(countyId ==null ){
				continue;
			}
			address.setProvinceId(provinceId);
			address.setCityId(cityId);
			address.setCountyId(countyId);
			addressRepository.save(address);
		}
		
	}
    
	public void getNeedRegion(){
		
		if(map==null){
			map=new HashMap<>();
			List<Region>  regionList=regionRepository.findNeedRegion();
			for (Region region : regionList) {
				map.put(region.getName(), region.getId());
			}
		}
	}

	@Override
	public void updateUserShareCode() {
		List<User> list=userService.getShareCodeIsNull();
		for (User user : list) {
			try {
				String  shareCode=DigestUtils.md5Hex("UID["+user.getId()+"]");
				user.setShareCode(shareCode);
				userService.save(user);
			} catch (Exception e) {
				log.error("user保存失败："+user.getId());
			}
		}
		
	}

	@Override
	public void updateRepeatUserShareCode() {
		List<String> repeatUserList=userService.getRepeatShareCodeUser();
		for (String string : repeatUserList) {
			List<User>  uList=userService.getUserByShareCode(string);
			for (User user2 : uList) {
				try {
					String  shareCode=DigestUtils.md5Hex("UID["+user2.getId()+"]");
					user2.setShareCode(shareCode);
					userService.save(user2);
				} catch (Exception e) {
					log.error("user保存失败："+user2.getId());
				}
			}
		}
		
	}
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private TempHouseRepository tempHouseRepository;

	@Override
	public void updateNonBindUser() throws InterruptedException {

		List<TempHouse> list = tempHouseRepository.findAll();
		ExecutorService service = Executors.newFixedThreadPool(10);
		//统计成功失败数
		AtomicInteger success = new AtomicInteger(0);
		AtomicInteger fail = new AtomicInteger(0);
		for (TempHouse tempHouse : list) {
			TempHouseWorker tempHouseWorker = new TempHouseWorker(tempHouse, wuyeService, 
					userRepository, transactionUtil,success,fail);
			service.execute(tempHouseWorker);
		}
		service.shutdown();
		while(!service.awaitTermination(30l, TimeUnit.SECONDS)){
		};
		log.error("成功更新" + success.get() + "户。");
		log.error("更新失败" + fail.get() + "户。");
	}

	
	@Override
	public void setHasHouseUserSectId() throws InterruptedException {
		int pageSize=10000;
		int pageNum=0;
		getUserList(pageNum,pageSize,null);		
		
	}
	
	
	public void getUserList(int pageNum,int pageSize,List<User> userList) throws InterruptedException{
		userList=userRepository.getUserList(pageNum,pageSize);
		excuteWorker(userList,pageNum);
		pageNum+=pageSize;
		if(userList.size()>0){
			getUserList(pageNum,pageSize,userList);
		}
	}
	public void excuteWorker(List<User> userList,int pageNum) throws InterruptedException{
		ExecutorService service = Executors.newFixedThreadPool(10);
		//统计成功失败数
		AtomicInteger success = new AtomicInteger(0);
		AtomicInteger fail = new AtomicInteger(0);
		log.error("开始更新" + "第"+pageNum+"页,共" +userList.size() + "户。");
		for (User user : userList) {
			AddUserSectIdWorker addUserSectIdWorker=new AddUserSectIdWorker(user,userRepository,wuyeService, transactionUtil,success,fail);
			service.execute(addUserSectIdWorker);
		}
		service.shutdown();
		while(!service.awaitTermination(30l, TimeUnit.SECONDS)){
			
		}
		log.error("成功更新" + "第"+pageNum+"页,共"+success.get() + "户。");
		log.error("更新失败" + "第"+pageNum+"页,共"+fail.get() + "户。");
	}

	@Override
	public void setUserSectid(User user, HexieUser u) {
		user.setSectId(u.getSect_id());	
		user.setCspId(u.getCsp_id());
		userRepository.save(user);
	}

	@Override
	public HexieUser queryPayUserAndBindHouse(String wuyeId) {
		
		return WuyeUtil.queryPayUserAndBindHouse(wuyeId).getData();
	}

	@Override
	public void addSectIdToRegion() throws InterruptedException {
		List<Region>  list=regionRepository.getRegionList();
		ExecutorService service = Executors.newFixedThreadPool(10);
		//统计成功失败数
		AtomicInteger success = new AtomicInteger(0);
		AtomicInteger fail = new AtomicInteger(0);
		log.error("开始更新" + list.size() + "小区。");
		for (Region region : list) {
			AddRegionSectIdWorker addRegionSectIdWorker=new AddRegionSectIdWorker(region,wuyeService, transactionUtil,success,fail);
			service.execute(addRegionSectIdWorker);
		}
		
		service.shutdown();
		while(!service.awaitTermination(30l, TimeUnit.SECONDS)){
			
		}
		
		log.error("成功更新" + success.get() + "小区。");
		log.error("更新失败" + fail.get() + "小区。");
	}

	@Override
	public void saveRegionSectId(Region region, String sectId) {
		region.setSectId(sectId);
		regionRepository.save(region);
	}

	@Override
	public String getSectIdByRegionName(String regionName) {
		return WuyeUtil.querySectIdByName(regionName).getData();
	}
	
}