package cn.cf.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import cn.cf.dao.C2bMarrieddealDaoEx;
import cn.cf.dto.C2bMarrieddealDto;
import cn.cf.dto.C2bMarrieddealDtoEx;
import cn.cf.model.C2bMarrieddeal;
import cn.cf.service.C2bMarrieddealService;
import cn.cf.util.DateUtil;
import cn.cf.util.StringUtils;

@Service
public class C2bMarrieddealServiceImpl implements C2bMarrieddealService {

	private C2bMarrieddealDaoEx c2bMarrieddealDaoEx;
	
	@Override
	public int exist(C2bMarrieddealDto marrieddeal) {
		
		    C2bMarrieddeal model = new C2bMarrieddeal();
	        BeanUtils.copyProperties(marrieddeal, model);
	        return c2bMarrieddealDaoEx.exist(model);
	}

	@Override
	public void addMarrieddeal(C2bMarrieddealDto marrieddeal) {
		
		C2bMarrieddeal model = new C2bMarrieddeal();
        BeanUtils.copyProperties(marrieddeal, model);
        model.setStartTime(DateUtil.formatYearMonthDay(new Date()));
        model.setFlag(1);
        model.setStatus(C2bMarrieddeal.MARRIEDDEAL_BIDING);
        model.setAuditStatus(0);
        model.setPk("CG" + StringUtils.getTimesRandomCode(2));
        c2bMarrieddealDaoEx.insert(model);
	}

	@Override
	public void update(C2bMarrieddeal model) {
		c2bMarrieddealDaoEx.update(model);
		
	}

	@Override
	public C2bMarrieddealDto getByMarrieddealId(String marrieddealPk) {
		
		return c2bMarrieddealDaoEx.getByPk(marrieddealPk);
	}

	@Override
	public void insert(C2bMarrieddeal model) {
		
		 c2bMarrieddealDaoEx.insert(model);
	}

	@Override
	public List<C2bMarrieddealDtoEx> searchMyGrid(Map<String, Object> map) {
		
		return c2bMarrieddealDaoEx.searchMyGrid(map);
	}

	@Override
	public int searchMyGridCount(Map<String, Object> map) {
		
		return c2bMarrieddealDaoEx.searchMyGridCount(map);
	}

}
