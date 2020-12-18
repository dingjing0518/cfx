package cn.cf.service.operation.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.PageModel;
import cn.cf.common.ColAuthConstants;
import cn.cf.common.Constants;
import cn.cf.common.QueryModel;
import cn.cf.dao.B2bDimensionalityDao;
import cn.cf.dao.B2bDimensionalityExtrewardExtDao;
import cn.cf.dao.B2bDimensionalityPresentExDao;
import cn.cf.dao.B2bDimensionalityRelevancyExtDao;
import cn.cf.dao.B2bDimensionalityRewardExtDao;
import cn.cf.dao.B2bMemberGradeExtDao;
import cn.cf.dto.B2bDimensionalityDto;
import cn.cf.dto.B2bDimensionalityExtrewardDto;
import cn.cf.dto.B2bDimensionalityExtrewardExtDto;
import cn.cf.dto.B2bDimensionalityPresentExDto;
import cn.cf.dto.B2bDimensionalityRelevancyDto;
import cn.cf.dto.B2bDimensionalityRelevancyExtDto;
import cn.cf.dto.B2bDimensionalityRewardDto;
import cn.cf.dto.B2bDimensionalityRewardExtDto;
import cn.cf.dto.B2bMemberGradeDto;
import cn.cf.dto.B2bMemberGradeExtDto;
import cn.cf.model.B2bDimensionality;
import cn.cf.model.B2bDimensionalityExtreward;
import cn.cf.model.B2bDimensionalityRelevancy;
import cn.cf.model.B2bDimensionalityReward;
import cn.cf.model.B2bMemberGrade;
import cn.cf.model.ManageAccount;
import cn.cf.service.operation.MemberSysService;
import cn.cf.util.CommonUtil;
import cn.cf.util.KeyUtils;
import cn.cf.util.StringUtils;

@Service
public class MemberSysServiceImpl implements MemberSysService {

	@Autowired
	private B2bDimensionalityDao b2bDimensionalityDao;

	@Autowired
	private B2bDimensionalityRelevancyExtDao b2bDimensionalityRelevancyExtDao;
	
	@Autowired
	private B2bDimensionalityRewardExtDao b2bDimensionalityRewardExtDao;
	
	@Autowired
	private B2bDimensionalityExtrewardExtDao b2bDimensionalityExtrewardExtDao;
	
	@Autowired
	private B2bMemberGradeExtDao b2bMemberGradeExtDao;

	@Autowired
	private B2bDimensionalityPresentExDao b2bDimensionalityPresentExDao;

	@Override
	public PageModel<B2bDimensionalityDto> searchDimensionalityList(QueryModel<B2bDimensionalityDto> qm) {
		PageModel<B2bDimensionalityDto> pm = new PageModel<B2bDimensionalityDto>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", qm.getStart());
		map.put("limit", qm.getLimit());
		map.put("orderName", qm.getFirstOrderName());
		map.put("orderType", qm.getFirstOrderType());
		map.put("isVisable", qm.getEntity().getIsVisable());
		map.put("updateStartTime", qm.getEntity().getUpdateStartTime());
		map.put("updateEndTime", qm.getEntity().getUpdateEndTime());
		int totalCount = b2bDimensionalityDao.searchGridCount(map);
		List<B2bDimensionalityDto> list = b2bDimensionalityDao.searchGrid(map);
		pm.setTotalCount(totalCount);
		pm.setDataList(list);
		return pm;
	}
	
	@Override
	public List<B2bDimensionalityDto> getAllDimensionalityList() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("isDelete", Constants.ONE);
		map.put("isVisable", Constants.ONE);
		return b2bDimensionalityDao.searchGrid(map);
	}

	@Override
	public int updateDimensionality(B2bDimensionalityDto dto) {
		int result = 0;
		Map<String, Object> map = new HashMap<String, Object>();
		/*map.put("isVisable", Constants.ONE);*/
		map.put("isDelete", Constants.ONE);
		map.put("name", dto.getName());
		if (CommonUtil.isEmpty(dto.getPk())) {
			List<B2bDimensionalityDto> list = b2bDimensionalityDao.searchList(map);// 判断是否存在相同的
			if (list != null && list.size() > 0) {
				result = 2;
			} else {
				B2bDimensionality dm = new B2bDimensionality();
				dm.setPk(KeyUtils.getUUID());
				dm.setInsertTime(new Date());
				dm.setUpdateTime(new Date());
				dm.setIsDelete(Constants.ONE);
				dm.setIsVisable(Constants.ONE);
				dm.setName(dto.getName());
				dm.setType(dto.getType());
				result = b2bDimensionalityDao.insert(dm);
			}
		} else {
			map.put("pk", dto.getPk());
			List<B2bDimensionalityDto> isDtoList = b2bDimensionalityDao.isExistName(map);

			if (isDtoList != null && isDtoList.size() > 0) {
				result = 2;
			} else {
				result = b2bDimensionalityDao.updateDimensionality(dto);
				B2bDimensionalityDto dimensionalityDto = b2bDimensionalityDao.getByPk(dto.getPk());
				 Integer type = dimensionalityDto.getType();
				if(dimensionalityDto.getIsVisable()==2){
					int i = b2bDimensionalityRewardExtDao.batchDel(type);

				}
			}
		}
		return result;
	}

	@Override
	public B2bDimensionalityDto geteByDimensionalityPk(String pk) {
		// TODO Auto-generated method stub
		return b2bDimensionalityDao.getByPk(pk);
	}

	@Override
	public PageModel<B2bDimensionalityRelevancyExtDto> searchDimensionalityRelevancyList(
			QueryModel<B2bDimensionalityRelevancyExtDto> qm) {
		PageModel<B2bDimensionalityRelevancyExtDto> pm = new PageModel<B2bDimensionalityRelevancyExtDto>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", qm.getStart());
		map.put("limit", qm.getLimit());
		map.put("orderName", qm.getFirstOrderName());
		map.put("orderType", qm.getFirstOrderType());
		map.put("isVisable", qm.getEntity().getIsVisable());
		map.put("dimenCategory", qm.getEntity().getDimenCategory());
		map.put("updateStartTime", qm.getEntity().getUpdateStartTime());
		map.put("updateEndTime", qm.getEntity().getUpdateEndTime());
		int totalCount = b2bDimensionalityRelevancyExtDao.searchGridExtCount(map);
		List<B2bDimensionalityRelevancyExtDto> list = b2bDimensionalityRelevancyExtDao.searchGridExt(map);
		pm.setTotalCount(totalCount);
		pm.setDataList(list);
		return pm;
	}

	@Override
	public int updateDimensionalityRelevancy(B2bDimensionalityRelevancyExtDto dto) {
		int result = 0;
		Map<String, Object> map = new HashMap<String, Object>();
		/*map.put("isVisable", Constants.ONE);*/
		map.put("isDelete", Constants.ONE);
		map.put("dimenName", dto.getDimenName());
		map.put("dimenTypeName", dto.getDimenTypeName());
		if (CommonUtil.isEmpty(dto.getPk())) {
			List<B2bDimensionalityRelevancyDto> list = b2bDimensionalityRelevancyExtDao.searchList(map);// 判断是否存在相同的
			if (list != null && list.size() > 0) {
				result = 2;
			} else {
				B2bDimensionalityRelevancy dm = new B2bDimensionalityRelevancy();
				dm.setPk(KeyUtils.getUUID());
				dm.setInsertTime(new Date());
				dm.setUpdateTime(new Date());
				dm.setIsDelete(Constants.ONE);
				dm.setIsVisable(Constants.ONE);
				dm.setDimenCategory(dto.getDimenCategory());
				dm.setDimenName(dto.getDimenName());
				dm.setDimenType(dto.getDimenType());
				dm.setDimenTypeName(dto.getDimenTypeName());
				dm.setLinkUrl(dto.getLinkUrl());
				result = b2bDimensionalityRelevancyExtDao.insert(dm);
			}
		} else {
			map.put("pk", dto.getPk());
			List<B2bDimensionalityRelevancyExtDto> isDtoList = b2bDimensionalityRelevancyExtDao.isExistName(map);
			if (isDtoList != null && isDtoList.size() > 0) {
				result = 2;
			} else {
				result = b2bDimensionalityRelevancyExtDao.updateDimenRe(dto);
			}
		}
		return result;
	}

	
	@Override
	public List<B2bDimensionalityRelevancyDto> getDimenReByCategory(Integer type) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("isDelete", Constants.ONE);
		map.put("isVisable", Constants.ONE);
		map.put("dimenCategory", type);
		return b2bDimensionalityRelevancyExtDao.searchList(map);
	}

	@Override
	public PageModel<B2bDimensionalityRewardExtDto> searchDimensionalityRewardList(
			QueryModel<B2bDimensionalityRewardExtDto> qm) {
		PageModel<B2bDimensionalityRewardExtDto> pm = new PageModel<B2bDimensionalityRewardExtDto>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", qm.getStart());
		map.put("limit", qm.getLimit());
		map.put("orderName", qm.getFirstOrderName());
		map.put("orderType", qm.getFirstOrderType());
		map.put("isVisable", qm.getEntity().getIsVisable());
		map.put("isDelete", Constants.ONE);
		map.put("dimenCategory", qm.getEntity().getSelectDimen());
		map.put("dimenType", qm.getEntity().getSelectDimenCategory());
		map.put("updateStartTime", qm.getEntity().getUpdateStartTime());
		map.put("updateEndTime", qm.getEntity().getUpdateEndTime());
		map.put("periodType", qm.getEntity().getPeriodType());
		int totalCount = b2bDimensionalityRewardExtDao.searchGridExtCount(map);
		List<B2bDimensionalityRewardExtDto> list = b2bDimensionalityRewardExtDao.searchGridExt(map);
		pm.setTotalCount(totalCount);
		pm.setDataList(list);
		return pm;
	}

	@Override
	public int updateDimensionalityReward(B2bDimensionalityRewardExtDto dto) {
		int result = 0;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("isVisable", Constants.ONE);
		map.put("isDelete", Constants.ONE);
		map.put("dimenName", dto.getDimenName());
		map.put("dimenTypeName", dto.getDimenTypeName());
		if (CommonUtil.isEmpty(dto.getPk())) {
			List<B2bDimensionalityRewardDto> list = b2bDimensionalityRewardExtDao.searchList(map);// 判断是否存在相同的
			if (list != null && list.size() > 0) {
				result = 2;
			} else {
				result = insertDimenReward(dto);
			}
		} else {
			map.put("pk", dto.getPk());
			List<B2bDimensionalityRewardExtDto> isDtoList = b2bDimensionalityRewardExtDao.isExistName(map);
			if (isDtoList != null && isDtoList.size() > 0) {
				result = 2;
			} else {
				result = b2bDimensionalityRewardExtDao.updateDimenReward(dto);
			}
		}
		return result;
	}
	private int insertDimenReward(B2bDimensionalityRewardExtDto dto) {
		int result;
		B2bDimensionalityReward dm = new B2bDimensionalityReward();
		dm.setPk(KeyUtils.getUUID());
		dm.setInsertTime(new Date());
		dm.setUpdateTime(new Date());
		dm.setIsDelete(Constants.ONE);
		dm.setIsVisable(Constants.ONE);
		dm.setDimenCategory(dto.getDimenCategory());
		dm.setDimenName(dto.getDimenName());
		dm.setDimenType(dto.getDimenType());
		dm.setDimenTypeName(dto.getDimenTypeName());
		dm.setPeriodType(dto.getPeriodType());
		dm.setFibreShellNumber(dto.getFibreShellNumber());
		dm.setEmpiricalValue(dto.getEmpiricalValue());
		dm.setFibreShellRatio(dto.getFibreShellRatio());
		dm.setEmpiricalRatio(dto.getEmpiricalRatio());
		result = b2bDimensionalityRewardExtDao.insert(dm);
		return result;
	}

	@Override
	public PageModel<B2bDimensionalityPresentExDto> searchDimensionalityPresentList(
			QueryModel<B2bDimensionalityPresentExDto> qm,ManageAccount account) {
		PageModel<B2bDimensionalityPresentExDto> pm = new PageModel<B2bDimensionalityPresentExDto>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", qm.getStart());
		map.put("limit", qm.getLimit());
		map.put("orderName", qm.getFirstOrderName());
		map.put("orderType", qm.getFirstOrderType());
		map.put("isVisable", qm.getEntity().getIsVisable());
		map.put("dimenType", qm.getEntity().getDimenCategory());
		map.put("dimenCategory", qm.getEntity().getDimenType());
		
		map.put("periodType", qm.getEntity().getPeriodType());
		
		map.put("type", qm.getEntity().getType());
		map.put("companyName", qm.getEntity().getCompanyName());
		map.put("contactsTel", qm.getEntity().getContactsTel());

		map.put("orderNumber",qm.getEntity().getOrderNumber());
		
		map.put("updateStartTime", qm.getEntity().getUpdateStartTime());
		map.put("updateEndTime", qm.getEntity().getUpdateEndTime());
		int totalCount = b2bDimensionalityPresentExDao.searchGridCountExt(map);
		setColAwardParams(map,account);
		List<B2bDimensionalityPresentExDto> list = b2bDimensionalityPresentExDao.searchGridExt(map);
		pm.setTotalCount(totalCount);
		pm.setDataList(list);
		return pm;
	}
	
	@Override
	public PageModel<B2bDimensionalityPresentExDto> searchDimensionalityExtPresentList(
			QueryModel<B2bDimensionalityPresentExDto> qm,ManageAccount account) {
		PageModel<B2bDimensionalityPresentExDto> pm = new PageModel<B2bDimensionalityPresentExDto>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", qm.getStart());
		map.put("limit", qm.getLimit());
		map.put("orderName", qm.getFirstOrderName());
		map.put("orderType", qm.getFirstOrderType());
		map.put("isVisable", qm.getEntity().getIsVisable());
		
		
		map.put("dimenType", qm.getEntity().getDimenCategory());
		map.put("dimenCategory", qm.getEntity().getDimenType());
		
		map.put("periodType", qm.getEntity().getPeriodType());
		
		map.put("type", qm.getEntity().getType());
		map.put("companyName", qm.getEntity().getCompanyName());
		map.put("contactsTel", qm.getEntity().getContactsTel());
		
		map.put("updateStartTime", qm.getEntity().getUpdateStartTime());
		map.put("updateEndTime", qm.getEntity().getUpdateEndTime());
		
		map.put("orderNumber", qm.getEntity().getOrderNumber());
		
		int totalCount = b2bDimensionalityPresentExDao.searchGridCountExtWard(map);
		setColAwardExtParams(map,account);
		List<B2bDimensionalityPresentExDto> list = b2bDimensionalityPresentExDao.searchGridExtWard(map);
		pm.setTotalCount(totalCount);
		pm.setDataList(list);
		return pm;
	}

	/**
	 * 额外奖励权限判断
	 * @param map
	 * @param account
	 */
	private void setColAwardExtParams(Map<String,Object> map,ManageAccount account){
		if (account != null){
			if (!cn.cf.common.utils.CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.OPER_MEMBER_DIMEMSEXT_COL_COMPANYNAME)){
				map.put("companyNameCol",ColAuthConstants.OPER_MEMBER_DIMEMSEXT_COL_COMPANYNAME);
			}
			if (!cn.cf.common.utils.CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.OPER_MEMBER_DIMEMSEXT_COL_MOBILE)){
				map.put("contactTelCol",ColAuthConstants.OPER_MEMBER_DIMEMSEXT_COL_MOBILE);
			}
		}
	}
	/**
	 * 奖励权限判断
	 * @param map
	 * @param account
	 */
	private void setColAwardParams(Map<String,Object> map,ManageAccount account){
		if (account != null){
			if (!cn.cf.common.utils.CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.OPER_MEMBER_DIMEMS_COL_COMPANYNAME)){
				map.put("companyNameCol",ColAuthConstants.OPER_MEMBER_DIMEMS_COL_COMPANYNAME);
			}
			if (!cn.cf.common.utils.CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.OPER_MEMBER_DIMEMS_COL_MOBILE)){
				map.put("contactTelCol",ColAuthConstants.OPER_MEMBER_DIMEMS_COL_MOBILE);
			}
		}
	}

	@Override
	public PageModel<B2bDimensionalityExtrewardExtDto> searchDimenExtrewardList(
			QueryModel<B2bDimensionalityExtrewardExtDto> qm) {
		PageModel<B2bDimensionalityExtrewardExtDto> pm = new PageModel<B2bDimensionalityExtrewardExtDto>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", qm.getStart());
		map.put("limit", qm.getLimit());
		map.put("orderName", qm.getFirstOrderName());
		map.put("orderType", qm.getFirstOrderType());
		map.put("isVisable", qm.getEntity().getIsVisable());
		map.put("isDelete", Constants.ONE);
		map.put("dimenCategory", qm.getEntity().getSelectDimen());
		map.put("dimenType", qm.getEntity().getSelectDimenCategory());
		map.put("periodType", qm.getEntity().getPeriodType());
		map.put("updateStartTime", qm.getEntity().getUpdateStartTime());
		map.put("updateEndTime", qm.getEntity().getUpdateEndTime());
		int totalCount = b2bDimensionalityExtrewardExtDao.searchGridExtCount(map);
		List<B2bDimensionalityExtrewardExtDto> list = b2bDimensionalityExtrewardExtDao.searchGridExt(map);
		pm.setTotalCount(totalCount);
		pm.setDataList(list);
		return pm;
	}

	@Override
	public int updateDimenExtreward(B2bDimensionalityExtrewardExtDto dto) {
		int result = 0;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("isVisable", Constants.ONE);
		map.put("isDelete", Constants.ONE);
		map.put("dimenName", dto.getDimenName());
		map.put("dimenTypeName", dto.getDimenTypeName());
		if (CommonUtil.isEmpty(dto.getPk())) {
			List<B2bDimensionalityExtrewardDto> list = b2bDimensionalityExtrewardExtDao.searchList(map);// 判断是否存在相同的
			if (list != null && list.size() > 0) {
				result = 2;
			} else {
				result = insertDimenExtreward(dto);
			}
		} else {
			map.put("pk", dto.getPk());
			List<B2bDimensionalityExtrewardExtDto> isDtoList = b2bDimensionalityExtrewardExtDao.isExistName(map);
			if (isDtoList != null && isDtoList.size() > 0) {
				result = 2;
			} else {
				result = b2bDimensionalityExtrewardExtDao.updateDimenExtreward(dto);
			}
		}
		return result;
	}
	
	private int insertDimenExtreward(B2bDimensionalityExtrewardExtDto dto) {
		int result;
		B2bDimensionalityExtreward dm = new B2bDimensionalityExtreward();
		dm.setPk(KeyUtils.getUUID());
		dm.setInsertTime(new Date());
		dm.setIsDelete(Constants.ONE);
		dm.setIsVisable(Constants.ONE);
		dm.setUpdateTime(new Date());
		dm.setDimenCategory(dto.getDimenCategory());
		dm.setDimenName(dto.getDimenName());
		dm.setDimenType(dto.getDimenType());
		dm.setDimenTypeName(dto.getDimenTypeName());
		dm.setPeriodType(dto.getPeriodType());
		dm.setFibreShellNumber(dto.getFibreShellNumber());
		dm.setEmpiricalValue(dto.getEmpiricalValue());
		dm.setPeriodTime(dto.getPeriodTime());
		dm.setNumberTimes(dto.getNumberTimes());
		dm.setUtil(dto.getUtil());
		dm.setDetail(dto.getDetail());
		dm.setTimesDetail(dto.getTimesDetail());
		dm.setConditionType(dto.getConditionType());
		result = b2bDimensionalityExtrewardExtDao.insert(dm);
		return result;
	}

	@Override
	public PageModel<B2bMemberGradeExtDto> searchMemberGradeList(QueryModel<B2bMemberGradeExtDto> qm) {
		PageModel<B2bMemberGradeExtDto> pm = new PageModel<B2bMemberGradeExtDto>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", qm.getStart());
		map.put("limit", qm.getLimit());
		map.put("orderName", qm.getFirstOrderName());
		map.put("orderType", qm.getFirstOrderType());
		map.put("isVisable", qm.getEntity().getIsVisable());
		map.put("isDelete", Constants.ONE);
		map.put("updateStartTime", qm.getEntity().getUpdateStartTime());
		map.put("updateEndTime", qm.getEntity().getUpdateEndTime());
		map.put("gradeName", qm.getEntity().getGradeName());
		int totalCount = b2bMemberGradeExtDao.searchGridExtCount(map);
		List<B2bMemberGradeExtDto> list = b2bMemberGradeExtDao.searchGridExt(map);
		pm.setTotalCount(totalCount);
		pm.setDataList(list);
		return pm;
	}

	@Override
	public int updateMemberGrade(B2bMemberGradeExtDto dto) {
		int result = 0;
		Map<String, Object> map = new HashMap<String, Object>();
		//map.put("isVisable", Constants.ONE);
		map.put("isDelete", Constants.ONE);
		map.put("gradeNumber", dto.getGradeNumber());
		if (CommonUtil.isEmpty(dto.getPk())) {
			List<B2bMemberGradeDto> list = b2bMemberGradeExtDao.searchList(map);// 判断是否存在相同的
			if (list != null && list.size() > 0) {
				result = 2;
			} else {
				result = insertMemberGrade(dto);
			}
		} else {
			//map.put("pk", dto.getPk());
			List<B2bMemberGradeExtDto> isDtoList = b2bMemberGradeExtDao.isExistName(map);
			if(null!=isDtoList && isDtoList.size()==1){
				B2bMemberGradeExtDto b2bMemberGradeExtDto=isDtoList.get(0);
				if(!b2bMemberGradeExtDto.getPk().equals(dto.getPk())){
					return 2;
				}
			}
			if (isDtoList != null && isDtoList.size() > 1) {
				result = 2;
			} else {
				result = b2bMemberGradeExtDao.updateMemberGrade(dto);
			}
		}
		return result;
	}
	
	private int insertMemberGrade(B2bMemberGradeExtDto dto) {
		int result;
		B2bMemberGrade dm = new B2bMemberGrade();
		dm.setPk(KeyUtils.getUUID());
		dm.setInsertTime(new Date());
		dm.setUpdateTime(new Date());
		dm.setIsDelete(Constants.ONE);
		dm.setIsVisable(Constants.ONE);
		dm.setGradeName(dto.getGradeName());
		dm.setGradeNumber(dto.getGradeNumber());
		dm.setNumberStart(dto.getNumberStart());
		dm.setNumberEnd(dto.getNumberEnd());
		dm.setFbGradeRatio(dto.getFbGradeRatio());
		dm.setEmGradeRatio(dto.getEmGradeRatio());
		result = b2bMemberGradeExtDao.insert(dm);
		return result;
	}

	@Override
	public List<B2bDimensionalityPresentExDto> searchDimensionalityPresentExList(
			QueryModel<B2bDimensionalityPresentExDto> qm) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", qm.getStart());
		map.put("limit", qm.getLimit());
		map.put("orderName", qm.getFirstOrderName());
		map.put("orderType", qm.getFirstOrderType());
		map.put("type", qm.getEntity().getType());
		map.put("dimenCategory",qm.getEntity().getDimenCategory());
		map.put("dimenType",qm.getEntity().getDimenType());
		map.put("contactsTel", qm.getEntity().getContactsTel());
		map.put("companyName", qm.getEntity().getCompanyName());
		map.put("periodType", qm.getEntity().getPeriodType());
		map.put("isVisable", qm.getEntity().getIsVisable());
		map.put("updateStartTime", qm.getEntity().getUpdateStartTime());
		map.put("updateEndTime", qm.getEntity().getUpdateEndTime());
		return b2bDimensionalityPresentExDao.exportDimensionalityPresentList(map);
	}

	@Override
	public void delDimensionalityReward(B2bDimensionalityRewardExtDto dto) throws Exception{

		B2bDimensionalityRewardDto b2bDimensionalityRewardDto=b2bDimensionalityRewardExtDao.getByPk(dto.getPk());
		
		b2bDimensionalityRewardExtDao.logicDelete(dto.getPk());
		
		Map<String, Object> map=new HashMap<String, Object>();
		
		map.put("dimenCategory",b2bDimensionalityRewardDto.getDimenCategory());
		map.put("dimenType",b2bDimensionalityRewardDto.getDimenType());
		map.put("type", 1);
		
		b2bDimensionalityPresentExDao.deleteByDimen(map);
		
	}

	@Override
	public void delDimenExtreward(B2bDimensionalityExtrewardExtDto dto) throws Exception{
		
		B2bDimensionalityExtrewardDto b2bDimensionalityExtrewardDto=b2bDimensionalityExtrewardExtDao.getByPk(dto.getPk());
		b2bDimensionalityExtrewardExtDao.delete(dto.getPk());
		
		
        Map<String, Object> map=new HashMap<String, Object>();
		map.put("dimenCategory",b2bDimensionalityExtrewardDto.getDimenCategory());
		map.put("dimenType",b2bDimensionalityExtrewardDto.getDimenType());
		map.put("type", 2);
		b2bDimensionalityPresentExDao.deleteByDimen(map);
		
	}

	@Override
	public List<B2bDimensionalityPresentExDto> exportDimensionalityExtPresentList(String pks,ManageAccount account) {
		String[] pkStrings = StringUtils.splitStrs(pks);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pks", pkStrings);
		setColAwardExtParams(map,account);
		return  b2bDimensionalityPresentExDao.searchGridExtWard(map);
	}


//    @Override
//    public List<B2bDimensionalityPresentExDto> exportDimensionalityHistoryList(String[] pks,ManageAccount account) throws Exception {
//        Map<String, Object> map=new HashMap<String, Object>();
//        map.put("pks", pks);
//		setColAwardParams(map,account);
//        return  b2bDimensionalityPresentExDao.searchGridExt(map);
//
//    }
}
