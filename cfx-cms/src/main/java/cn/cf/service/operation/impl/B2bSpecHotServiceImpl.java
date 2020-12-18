package cn.cf.service.operation.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bSpecHotDtoEx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.PageModel;
import cn.cf.common.Constants;
import cn.cf.common.QueryModel;
import cn.cf.dao.B2bSpecHotDaoEx;
import cn.cf.dto.B2bSpecHotDto;
import cn.cf.model.B2bSpecHot;
import cn.cf.service.operation.B2bSpecHotService;
import cn.cf.util.KeyUtils;

@Service
public class B2bSpecHotServiceImpl implements B2bSpecHotService {

	@Autowired
	private B2bSpecHotDaoEx b2bSpecHotDao;

	@Override
	public PageModel<B2bSpecHotDto> searchList(QueryModel<B2bSpecHotDtoEx> query) {
		PageModel<B2bSpecHotDto> pm = new PageModel<B2bSpecHotDto>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", query.getStart());
		map.put("limit", query.getLimit());
		map.put("insertTimeBegin", query.getEntity().getInsertTimeBegin());
		map.put("insertTimeEnd", query.getEntity().getInsertTimeEnd());
		map.put("firstLevelPk", query.getEntity().getFirstLevelPk());
		map.put("secondLevelPk", query.getEntity().getSecondLevelPk());

		map.put("thirdLevelPk", query.getEntity().getThirdLevelPk());
		map.put("fourthLevelPk", query.getEntity().getFourthLevelPk());

		map.put("block", query.getEntity().getBlock());
		map.put("status", query.getEntity().getStatus());
		map.put("orderName", query.getFirstOrderName());
		map.put("orderType", query.getFirstOrderType());
		map.put("isDelete", 1);
		List<B2bSpecHotDto> list = b2bSpecHotDao.searchSpecHotList(map);
		int count = b2bSpecHotDao.searchSpecHotCount(map);
		pm.setDataList(list);
		pm.setTotalCount(count);
		return pm;
	}

	@Override
	public String update(B2bSpecHot specHot) {
		int result = 0;
		String rest = "";
		Map<String, Object> map = new HashMap<>();
		map.put("firstLevelPk", specHot.getFirstLevelPk());
		map.put("secondLevelPk", specHot.getSecondLevelPk());
		if (Constants.BLOCK_SX.equals(specHot.getBlock())) {
			map.put("thirdLevelPk", specHot.getThirdLevelPk());
			map.put("fourthLevelPk", specHot.getFourthLevelPk());
		} else {
			map.put("fourthLevelName", specHot.getFourthLevelName());
		}
		map.put("pk", specHot.getPk());

		int count = b2bSpecHotDao.isExitSpecHot(map);

		if (null == specHot.getPk() || "".equals(specHot.getPk())) {
			specHot.setPk(KeyUtils.getUUID());
			specHot.setInsertTime(new Date());
			specHot.setIsDelete(1);
			if (count>0) {
				rest = Constants.ISEXTIST_HOT_SPEC_NAME;
			} else {
				result = b2bSpecHotDao.insert(specHot);
				if (result == 1) {
					rest = Constants.RESULT_SUCCESS_MSG;
				} else {
					rest = Constants.RESULT_FAIL_MSG;
				}
			}
		} else {
			specHot.setUpdateTime(new Date());
			if (count>0) {
				rest = Constants.ISEXTIST_HOT_SPEC_NAME;
			} else {
				result = b2bSpecHotDao.updateExt(specHot);
				if (result == 1) {
					rest = Constants.RESULT_SUCCESS_MSG;
				} else {
					rest = Constants.RESULT_FAIL_MSG;
				}
			}
		}

		return rest;
	}

	@Override
	public String delete(String pk) {
		int result = 0;
		String msg = "";
		if (null != pk || !"".equals(pk)) {
			result = b2bSpecHotDao.delete(pk);
		}
		if (result == 1) {
			msg = Constants.RESULT_SUCCESS_MSG;
		} else {
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;
	}

	@Override
	public String updateSpecHotStatus(B2bSpecHot specHot) {
		String rest = "";
		int result = b2bSpecHotDao.updateExt(specHot);
		if (result == 1) {
			rest = Constants.RESULT_SUCCESS_MSG;
		} else {
			rest = Constants.RESULT_FAIL_MSG;
		}

		return rest;
	}

}
