package cn.cf.service.operation.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.cf.dao.SysLivebroadCategoryExDao;
import cn.cf.dto.SysLivebroadCategoryDto;
import cn.cf.model.SysLivebroadCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.PageModel;
import cn.cf.common.QueryModel;
import cn.cf.dao.SysMarketLivebroadExtDao;
import cn.cf.dto.B2bMarketLiveBroadExtDto;
import cn.cf.dto.SysMarketLivebroadDto;
import cn.cf.model.SysMarketLivebroad;
import cn.cf.service.operation.B2bMarketLiveBroadService;
import cn.cf.util.KeyUtils;

@Service
public class B2bMarketLiveBroadServiceImpl implements B2bMarketLiveBroadService {
	@Autowired
	private SysMarketLivebroadExtDao sysMarketLivebroadExtDao;

	@Autowired
	private SysLivebroadCategoryExDao sysLivebroadCategoryExDao;
	
	@Override
	public PageModel<B2bMarketLiveBroadExtDto> searchMarketLiveBroaddata(QueryModel<B2bMarketLiveBroadExtDto> qm) {
		PageModel<B2bMarketLiveBroadExtDto> pm = new PageModel<B2bMarketLiveBroadExtDto>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", qm.getStart());
		map.put("limit", qm.getLimit());
		map.put("orderName", qm.getFirstOrderName());
		map.put("orderType", qm.getFirstOrderType());
		map.put("isDelete", 1);
		map.put("livebroadcategoryPk", qm.getEntity().getLivebroadcategoryPk());
		map.put("strStartTime", qm.getEntity().getStrStartTime());
		map.put("strEndTime", qm.getEntity().getStrEndTime());
		int totalCount = sysMarketLivebroadExtDao.searchGridExCount(map);
		List<B2bMarketLiveBroadExtDto> list = sysMarketLivebroadExtDao.searchGridEx(map);
		pm.setTotalCount(totalCount);
		pm.setDataList(list);
		return pm;
	}

	@Override
	public int updateMarketLiveBroad(B2bMarketLiveBroadExtDto b2bMarketLiveBroadExtDto) {
		
		
		String pk = null;
		int result = 0;
/*		//验证是否重复添加
		if(b2bLiveBraodCastCategoryExDto.getName()!=null&&!"".equals(b2bLiveBraodCastCategoryExDto.getName())){
			int count =  sysLivebroadCategoryExDao.valiDateCounts(b2bLiveBraodCastCategoryExDto);
			if(count>0){
				return -1;
			}
		}*/
		if (b2bMarketLiveBroadExtDto.getPk() != null && !"".equals(b2bMarketLiveBroadExtDto.getPk())) {
			pk = b2bMarketLiveBroadExtDto.getPk();
			SysMarketLivebroadDto sysMarketLivebroadDto=sysMarketLivebroadExtDao.getByPk(pk);
			if(null!=b2bMarketLiveBroadExtDto.getIsDelete()){
				sysMarketLivebroadDto.setIsDelete(b2bMarketLiveBroadExtDto.getIsDelete());
			}
			
			sysMarketLivebroadDto.setKeyword(b2bMarketLiveBroadExtDto.getKeyword());
			sysMarketLivebroadDto.setContent(b2bMarketLiveBroadExtDto.getContent());
			sysMarketLivebroadDto.setLivebroadcategoryName(b2bMarketLiveBroadExtDto.getLivebroadcategoryName());
			sysMarketLivebroadDto.setLivebroadcategoryPk(b2bMarketLiveBroadExtDto.getLivebroadcategoryPk());
			sysMarketLivebroadDto.setUpdateTime(new Date());
			
			
			result = sysMarketLivebroadExtDao.updateEx(sysMarketLivebroadDto);
		} else {
			pk = KeyUtils.getUUID();

            SysMarketLivebroad sysMarketLivebroad=new SysMarketLivebroad();
            sysMarketLivebroad.setPk(pk);
            sysMarketLivebroad.setInsertTime(new Date());
            sysMarketLivebroad.setUpdateTime(new Date());
            sysMarketLivebroad.setIsDelete(1);
            sysMarketLivebroad.setContent(b2bMarketLiveBroadExtDto.getContent());
            sysMarketLivebroad.setKeyword(b2bMarketLiveBroadExtDto.getKeyword());
            sysMarketLivebroad.setLivebroadcategoryPk(b2bMarketLiveBroadExtDto.getLivebroadcategoryPk());
            sysMarketLivebroad.setLivebroadcategoryName(b2bMarketLiveBroadExtDto.getLivebroadcategoryName());
            result = sysMarketLivebroadExtDao.insert(sysMarketLivebroad);
		}
		return result;
	}

	@Override
	public SysMarketLivebroadDto getMarketLiveBroadByPk(String pk) {
		return sysMarketLivebroadExtDao.getByPk(pk);
	}

	@Override
	public SysLivebroadCategoryDto getLiveCategoryByPk(String pk) {

		return sysLivebroadCategoryExDao.getByPk(pk);
	}
}
