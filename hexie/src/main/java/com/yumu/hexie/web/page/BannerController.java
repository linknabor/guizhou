package com.yumu.hexie.web.page;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yumu.hexie.common.Constants;
import com.yumu.hexie.model.distribution.OnSaleAreaItem;
import com.yumu.hexie.model.user.User;
import com.yumu.hexie.model.view.Banner;
import com.yumu.hexie.service.common.DistributionService;
import com.yumu.hexie.service.page.PageConfigService;
import com.yumu.hexie.web.BaseController;
import com.yumu.hexie.web.BaseResult;
import com.yumu.hexie.web.page.resp.BannerProductVO;
import com.yumu.hexie.web.page.resp.BannersVO;

@Controller(value = "bannerController")
public class BannerController extends BaseController{
	private static final Logger Log = LoggerFactory.getLogger(BannerController.class);

    @Inject
    private PageConfigService pageConfigService;
    @Inject
    private DistributionService distributionService;

	@RequestMapping(value = "/banner/{bannerType}", method = RequestMethod.GET)
	@ResponseBody
	public BaseResult<List<Banner>> getBannerByBannerType(@ModelAttribute(Constants.USER)User user,@PathVariable int bannerType) throws Exception {
		return BaseResult.successResult(pageConfigService.queryBannerType(user, bannerType));
    }
	

	@RequestMapping(value = "/banners", method = RequestMethod.GET)
	@ResponseBody
	public BaseResult<BannersVO> getBanners(@ModelAttribute(Constants.USER)User user) throws Exception {
		BannersVO vo = new BannersVO();
		vo.setTitles(pageConfigService.queryBannerType(user, 1));
		vo.setActivities(pageConfigService.queryBannerType(user, 4));
		vo.setProduct(pageConfigService.queryBannerType(user, 7));
		
		List<BannerProductVO> products = new ArrayList<>();
		
		List<Banner> banners = pageConfigService.queryBannerType(user, 5);
		List<OnSaleAreaItem> salePlans = distributionService.queryOnsales(user, 100, 0);
		for(Banner ban : banners) {
			BannerProductVO b = new BannerProductVO(ban);
			products.add(b);
			for(OnSaleAreaItem item : salePlans) {
				if(item.getProductType() == ban.getOnSaleType()) {
					b.getIcons().add(item);
				}
			}
		}
		vo.setBrands(products);
		return new BaseResult<BannersVO>().success(vo);
    }
}
