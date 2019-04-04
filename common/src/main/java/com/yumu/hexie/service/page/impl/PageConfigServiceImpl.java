package com.yumu.hexie.service.page.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.yumu.hexie.model.user.User;
import com.yumu.hexie.model.view.Banner;
import com.yumu.hexie.model.view.BannerRepository;
import com.yumu.hexie.model.view.PageConfigView;
import com.yumu.hexie.model.view.PageConfigViewRepository;
import com.yumu.hexie.service.page.PageConfigService;

@Service("pageConfigService")
public class PageConfigServiceImpl implements PageConfigService {

    @Inject
    private PageConfigViewRepository pageConfigViewRepository;
	@Inject
	private BannerRepository bannerRepository;

	@Override
	public List<Banner> queryBannerType(User user, int bannerType) {
		if(user.getProvinceId() != 0){
			return bannerRepository.queryByBannerTypeAndUser(user.getProvinceId(), user.getCityId(), user.getCountyId(), user.getXiaoquId(), bannerType);
		}else{
			return bannerRepository.queryByBannerTypeAndUser(19, user.getCityId(), user.getCountyId(), user.getXiaoquId(), bannerType);		
		}

	}

    @Override
    public String findByTempKey(String key) {
        PageConfigView v = pageConfigViewRepository.findByTempKey(key);
        if(v != null) {
            return v.getPageConfig();
        }
        return "";
    }

}
