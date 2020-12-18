package cn.cf.service.operation.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.PageModel;
import cn.cf.common.Constants;
import cn.cf.common.QueryModel;
import cn.cf.dao.SysVersionManagementExtDao;
import cn.cf.dto.SysVersionManagementDto;
import cn.cf.dto.SysVersionManagementExtDto;
import cn.cf.model.SysVersionManagement;
import cn.cf.service.operation.VersionService;
import cn.cf.util.KeyUtils;

@Service
public class VersionServiceImpl implements VersionService {

	@Autowired
	private SysVersionManagementExtDao sysVersionManagementDao;

	@Override
	public PageModel<SysVersionManagementExtDto> searchVersionList(QueryModel<SysVersionManagementExtDto> qm) {
		PageModel<SysVersionManagementExtDto> pm = new PageModel<SysVersionManagementExtDto>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", qm.getStart());
		map.put("limit", qm.getLimit());
		map.put("orderName", qm.getFirstOrderName());
		map.put("orderType", qm.getFirstOrderType());
		map.put("terminal", qm.getEntity().getTerminal());
		map.put("version", qm.getEntity().getVersion());
		map.put("downloadUrl", qm.getEntity().getDownloadUrl());
		map.put("insertTimeStart", qm.getEntity().getInsertTimeStart());
		map.put("insertTimeEnd", qm.getEntity().getInsertTimeEnd());
		map.put("isDelete", Constants.ONE);
		int totalCount = sysVersionManagementDao.searchGridExtCount(map);
		List<SysVersionManagementExtDto> list = sysVersionManagementDao.searchGridExt(map);
		pm.setTotalCount(totalCount);
		pm.setDataList(list);
		return pm;
	}

	@Override
	public int updateVersion(SysVersionManagementDto dto) {
		Date data = new Date();
		int result = 0;
		// 同一模块下，版本号不能重复
		if (dto.getVersion() != null && !dto.getVersion().equals("")) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("version", dto.getVersion());
			map.put("terminal", dto.getTerminal());
			map.put("pk", dto.getPk());
			int count = sysVersionManagementDao.isExtVerionNum(map);
			if (count > 0) {
				result = 2;
			}
		}

		if (result == 0) {
			if (dto.getPk() == null || dto.getPk().equals("")) {
				SysVersionManagement model = new SysVersionManagement();
				model.setPk(KeyUtils.getUUID());
				model.setTerminal(dto.getTerminal());
				model.setVersion(dto.getVersion());
				model.setDownloadUrl(dto.getDownloadUrl());
				model.setInsertTime(data);
				model.setPublisher(dto.getPublisher());
				model.setRemark(dto.getRemark());
				model.setIsDelete(Constants.ONE);
				model.setIsVisable(Constants.ONE);
				model.setUpdateTime(data);
				model.setVersionName(dto.getVersionName());
				result = sysVersionManagementDao.insert(model);
			} else {
				dto.setUpdateTime(data);
				result = sysVersionManagementDao.updateExt(dto);
			}
		}

		return result;
	}

}
