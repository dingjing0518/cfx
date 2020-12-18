package cn.cf.service.operation.impl;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.cf.common.ExportDoJsonParams;
import cn.cf.dao.*;
import cn.cf.entity.CustomerDataTypeParams;
import cn.cf.json.JsonUtils;
import cn.cf.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import cn.cf.PageModel;
import cn.cf.common.ColAuthConstants;
import cn.cf.common.Constants;
import cn.cf.common.QueryModel;
import cn.cf.common.utils.CommonUtil;
import cn.cf.dto.B2bAddressDto;
import cn.cf.dto.B2bCompanyDto;
import cn.cf.dto.B2bInvitationRecordDto;
import cn.cf.dto.B2bInvitationRecordExtDto;
import cn.cf.dto.B2bLotteryActivityExDto;
import cn.cf.dto.B2bLotteryAwardExDto;
import cn.cf.dto.B2bLotteryMaterialDto;
import cn.cf.dto.B2bLotteryRecordDto;
import cn.cf.dto.B2bLotteryRecordExDto;
import cn.cf.entity.LotteryActivityRule;
import cn.cf.service.operation.ActivityService;
import cn.cf.util.KeyUtils;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

@Service
public class ActivityServiceImpl implements ActivityService {


	@Autowired
	private B2bLotteryActivityExDao b2bLotteryActivityExDao;

	@Autowired
	private B2bLotteryMaterialExDao b2bLotteryMaterialExDao;

	@Autowired
	private B2bCompanyExtDao b2bCompanyExDao;

	@Autowired
	private B2bLotteryAwardExDao b2bLotteryAwardExDao;

	@Autowired
	private B2bInvitationRecordExtDao b2bInvitationRecordExtDao;

	@Autowired
	private B2bCompanyExtDao b2bCompanyExtDao;

	@Autowired
	private B2bAddressDao b2bAddressDao;

	@Autowired
	private B2bLotteryRecordExDao b2bLotteryRecordExDao;

	@Autowired
	private B2bAddressExDao b2bAddressExDao;

	@Autowired
	private B2bLotteryMaterialDao b2bLotteryMaterialDao;

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired
	private B2bMemberDao b2bMemberDao;

	@Autowired
	private SysExcelStoreExtDao sysExcelStoreExtDao;


	// =============================================抽奖活动=================================================

	@Override
	public int delAuctionActivityRule(String pk) {
		return 0;
	}

	// 抽奖活动页面搜索，返回pageModel
	@Override
	public PageModel<B2bLotteryActivityExDto> searchLotteryList(QueryModel<B2bLotteryActivityExDto> qm) {
		PageModel<B2bLotteryActivityExDto> pm = new PageModel<B2bLotteryActivityExDto>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", qm.getStart());
		map.put("limit", qm.getLimit());
		map.put("orderName", qm.getFirstOrderName());
		map.put("orderType", qm.getFirstOrderType());
		map.put("startTime", qm.getEntity().getStartTimeStr());
		map.put("endTime", qm.getEntity().getEndTimeStr());
		map.put("isVisable", qm.getEntity().getIsVisable());
		int totalCount = b2bLotteryActivityExDao.searchGridCountEx(map);
		List<B2bLotteryActivityExDto> list = b2bLotteryActivityExDao.searchGridEx(map);
		pm.setTotalCount(totalCount);
		pm.setDataList(list);
		return pm;
	}


	// 新增或编辑活动
	@Override
	public int updateLottery(B2bLotteryActivityExDto dto) {
		int result = 0;
		B2bLotteryActivityExDto activityExDto = b2bLotteryActivityExDao.getByActivityType(dto.getActivityType());
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			if (dto.getStartTimeStr() != null && !"".equals(dto.getStartTimeStr())) {
				Date startTime = format.parse(dto.getStartTimeStr());
				dto.setStartTime(startTime);
			}
			if (dto.getEndTimeStr() != null && !"".equals(dto.getEndTimeStr())) {
				Date endTime = format.parse(dto.getEndTimeStr());
				dto.setEndTime(endTime);
			}
			if (dto.getOnlineTimeStr() != null && !"".equals(dto.getOnlineTimeStr())) {
				Date onlineTime = format.parse(dto.getOnlineTimeStr());
				dto.setOnlineTime(onlineTime);
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return 0;
		}
		if (dto.getPk() != null && !"".equals(dto.getPk())) {
			// 判断编辑时是否存在相同活动类型
			if (activityExDto != null && !dto.getPk().equals(activityExDto.getPk())) {
				return 2;
			}
			dto.setUpdateTime(new Date());
			result = b2bLotteryActivityExDao.updateByDto(dto);
		} else {
			if (activityExDto != null) {// 存在相同类型的活动,不能新增
				return 2;
			}
			//删除之前过期的活动
//			b2bLotteryActivityExDao.deleteByType(dto.getActivityType());
			dto.setPk(KeyUtils.getUUID());
			dto.setInsertTime(new Date());
			dto.setIsDelete(1);
			dto.setIsVisable(1);
			B2bLotteryActivity activity = new B2bLotteryActivity(dto);
			result = b2bLotteryActivityExDao.insert(activity);
		}
		return result;
	}

	@Override
	public B2bLotteryActivityExDto getLotteryByActivityType(Integer type) {
		return b2bLotteryActivityExDao.getByActivityType(type);
	}

	@Override
	public List<B2bLotteryActivityExDto> getAllLotteryActivity(Integer activityType) {
		return b2bLotteryActivityExDao.getAllLotteryActivity(activityType);
	}



	@Override
	public List<B2bLotteryMaterialDto> getAllLotteryMaterailList() {
		return b2bLotteryMaterialExDao.getAllMaterial();
	}


	@Override
	public B2bLotteryActivityExDto getLotteryByPk(String pk) {
	
		return b2bLotteryActivityExDao.getByPkEx(pk);
	}

	@Override
	public List<B2bCompanyDto> getB2bCompayDto(Map<String, Object> map) {
		return b2bCompanyExDao.getCompanyDto(map);
	}

	@Override
	public int delLotteryRule(String pk) {
		return 0;
	}

	@Override
	public PageModel<B2bInvitationRecordExtDto> searchLotteryInvitationRecordList(
			QueryModel<B2bInvitationRecordExtDto> qm) {
		PageModel<B2bInvitationRecordExtDto> pm = new PageModel<B2bInvitationRecordExtDto>();
		Map<String, Object> map = invitationRecordParams(qm);
		int totalCount = b2bInvitationRecordExtDao.searchGridExtCount(map);
		List<B2bInvitationRecordExtDto> list = b2bInvitationRecordExtDao.searchGridExt(map);
		for (B2bInvitationRecordExtDto dto : list) {
			// 获取无地址的根据公司名称获取地址。
			if (dto.getInvitationStatus() != 3
					&& (null == dto.getProvince() || "".equals(dto
							.getProvince())) && null != dto.getMcompanyPk()
					&& !"".equals(dto.getMcompanyPk())) {
				List<B2bAddressDto> idtolsit = b2bAddressExDao
						.getAddressisDefaultByCompanyPk(dto.getMcompanyPk());
				if (null != idtolsit && idtolsit.size() > 0) {
					B2bAddressDto adto = idtolsit.get(0);
					dto.setProvince(adto.getProvince());
					dto.setProvinceName(adto.getProvinceName());
					dto.setCity(adto.getCity());
					dto.setCityName(adto.getCityName());
					dto.setArea(adto.getArea());
					dto.setAreaName(adto.getAreaName());
					dto.setAddressPk(adto.getPk());
					dto.setAddress(adto.getAddress());
					dto.setContacts(adto.getContacts());
					dto.setContactsTel(adto.getContactsTel());
				}

			}
		}
		pm.setTotalCount(totalCount);
		pm.setDataList(list);
		return pm;
	}

	private Map<String, Object> invitationRecordParams(QueryModel<B2bInvitationRecordExtDto> qm) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", qm.getStart());
		map.put("limit", qm.getLimit());
		map.put("orderName", qm.getFirstOrderName());
		map.put("orderType", qm.getFirstOrderType());
		if (qm.getEntity().getIsInvitationStatus() != null) {
			map.put("invitationStatus", qm.getEntity().getIsInvitationStatus());
		} else {
			map.put("invitationStatus", qm.getEntity().getInvitationStatus());
		}
		map.put("mphone", qm.getEntity().getMphone());
		map.put("tphone", qm.getEntity().getTphone());
		map.put("mcompanyName", qm.getEntity().getMcompanyName());
		map.put("mname", qm.getEntity().getMname());
		return map;
	}
	

	@Override
	public List<B2bInvitationRecordExtDto> exportLotteryInvitationRecord(QueryModel<B2bInvitationRecordExtDto> qm) {
		Map<String, Object> map = invitationRecordParams(qm);
		map.put("start", null);
		map.put("limit", null);
		map.put("orderName", null);
		map.put("orderType", null);
		return b2bInvitationRecordExtDao.searchGridExt(map);
	}

	@Override
	public String importLotteryInvitationRecord(MultipartFile file) throws IOException, BiffException {
		int result = 0;
		String msg = "";
		InputStream is = file.getInputStream(); 
		if(!(file.getSize() > 0)){
			msg = Constants.IMPORT_FILE_ISEMPTY;
		}else{
			//得到excel
			 Workbook workbook = Workbook.getWorkbook(is); 
			//得到sheet  
	         Sheet sheet = workbook.getSheet(0); 
	         //得到列数  
	         int colsNum = sheet.getColumns(); 
	         List<Map<Integer, String>> list = getCell(workbook);
	         if(list != null && list.size() > 0){
	        	for (Map<Integer, String> map : list) {
	        		//获取一列数要修改的数据
	             	B2bInvitationRecordExtDto invitationRecordDto = new B2bInvitationRecordExtDto();
	                for(Map.Entry<Integer, String> entry : map.entrySet()){
	                	invitationRecordDto = updateInvitationRecord(entry,colsNum,invitationRecordDto);
	               } 
	                if(invitationRecordDto != null 
	                		&& (invitationRecordDto.getAwardStatus() != null
	                		|| invitationRecordDto.getGrantType() != null || invitationRecordDto.getNote() != null)){
	                	try {
	                		result = b2bInvitationRecordExtDao.updateInvitation(invitationRecordDto);
						} catch (Exception e) {
							e.printStackTrace();
							result = 0;
							break;
						}
	                }
				}
	         }
	         if(result > 0){
	        	 msg = "导入成功!";
	         }else{
	        	 msg = "导入失败!"; 
	         }
		}
		return msg;
	}
	//逐行修改抽奖记录状态
		private B2bInvitationRecordExtDto updateInvitationRecord(Map.Entry<Integer, String> entry,int colsNum,B2bInvitationRecordExtDto recordDto){
	     	 Integer n = entry.getKey();
	     	    if(n != null && n == 0){
	     	    	recordDto.setPk(entry.getValue());
	     	      }
	     	   
	     	   if(n != null && n == (colsNum-3)){
	   	    	 String remarks = entry.getValue();
	   	    	 if(remarks != null && !"".equals(remarks)){
		     	    	 recordDto.setNote(remarks);	
	   	    	 }
	   	       }
	     	  if(n != null && n == (colsNum-2)){
	     	    	String awardStatus = entry.getValue();
	     	    	if(awardStatus != null && !"".equals(awardStatus)){
	     	    		String[] strArr = awardStatus.split("\\.");
	     	    		recordDto.setAwardStatus(Integer.valueOf(strArr[0]));	
	     	    	}	
	     	       }  
	     	   if(n != null && n == (colsNum-1)){
	   	    	 String grantType = entry.getValue();
	   	    	 if(grantType != null && !"".equals(grantType)){
		     	    	 String[] strArr = grantType.split("\\.");
		     	    	 recordDto.setGrantType(Integer.valueOf(strArr[0]));	
	   	    	 }
	   	       }
	     	     	
			return recordDto;
		}
		
	//获取Excel中的所有数据
	private List<Map<Integer, String>> getCell(Workbook workbook){
			 
			 List<Map<Integer, String>> listCell = new ArrayList<Map<Integer, String>>();
				//得到sheet  
		        Sheet sheet = workbook.getSheet(0); 
		        //得到列数  
		        int colsNum = sheet.getColumns(); 
		        //得到行数  
		        int rowsNum = sheet.getRows();
		        Cell cell;
		        rowsNumbers:for (int i = 3; i < rowsNum; i++) {//读取excel内容，从第四行开始,所以 i从3开始 
		        	 Map<Integer, String> map = new HashMap<Integer, String>();
		             for (int j = 0; j < colsNum; j++) {
		            	 cell = sheet.getCell(j, i);
		            	 //如果循环到某一行第一列没有值，说明已是最后一行。
		            	 if(j == 0 && (cell.getContents() == null || "".equals(cell.getContents()))){
		            		 break rowsNumbers;
		            	 }
		                 map.put(j, cell.getContents());
		             }
		             listCell.add(map);
		         } 
			return listCell;
		}

	@Override
	public B2bInvitationRecordExtDto getInvitationRecordByPk(String pk, String tcompanyName) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", tcompanyName);
		map.put("isDelete", Constants.ONE);
		map.put("isVisable", Constants.ONE);
		List<B2bCompanyDto> list = b2bCompanyExtDao.searchList(map);
		B2bInvitationRecordExtDto extDto = b2bInvitationRecordExtDao.getByPkExt(pk);
		if (list != null && list.size() > 0) {
			extDto.setCompanyNameIsExist(true);
		} else {
			extDto.setCompanyNameIsExist(false);
		}
		return extDto;
	}

	@Override
	public int updateInvitationRecord(B2bInvitationRecordExtDto recordDto) {
		int result = 0;
		if (recordDto.getPk() != null && !"".equals(recordDto.getPk())) {
			result = b2bInvitationRecordExtDao.updateInvitation(recordDto);
			if (result > 0 && recordDto.getIsPut() != null && recordDto.getIsPut() == 1) {
				// 维护地区
				result = updateAddressOfInvitation(recordDto);
			}
		}
		return result;
	}

	private int updateAddressOfInvitation(B2bInvitationRecordDto dto) {
		int result = 0;

		if (dto.getMcompanyPk() != null && !"".equals(dto.getMcompanyPk())) {

			B2bAddress b2bAddress = new B2bAddress();
			b2bAddress.setPk(KeyUtils.getUUID());
			b2bAddress.setCompanyPk(dto.getMcompanyPk());
			b2bAddress.setInsertTime(new Date());
			b2bAddress.setAddress(dto.getAddress());
			b2bAddress.setProvince(dto.getProvince());
			b2bAddress.setProvinceName(dto.getProvinceName());
			b2bAddress.setCity(dto.getCity());
			b2bAddress.setCityName(dto.getCityName());
			b2bAddress.setArea(dto.getArea());
			b2bAddress.setAreaName(dto.getAreaName());
			b2bAddress.setIsDelete(1);

			Map<String, Object> companyMap = new HashMap<String, Object>();
			companyMap.put("companyPk", dto.getMcompanyPk());
			companyMap.put("isDelete", Constants.ONE);
			List<B2bAddressDto> addressDtoList = b2bAddressDao.searchList(companyMap);

			if (addressDtoList != null && addressDtoList.size() > 0) {// 公司有地址
				companyMap.put("address", dto.getAddress());
				companyMap.put("province", dto.getProvince());
				companyMap.put("city", dto.getCity());
				companyMap.put("area", dto.getArea());
				List<B2bAddressDto> list = b2bAddressDao.searchList(companyMap);// 查询是否存在此条地址
				if (list != null && list.size() > 0) {
					result = 1;
				} else {
					b2bAddress.setIsDefault(2);
					result = b2bAddressDao.insert(b2bAddress);
				}
			} else {
				// 若公司没有地址，则添加一条默认地址
				b2bAddress.setIsDefault(1);
				result = b2bAddressDao.insert(b2bAddress);
			}
		}
		return result;
	}

	@Override
	public List<B2bCompanyDto> getB2bCompayDto() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("isDelete", Constants.ONE);
		map.put("isVisable", Constants.ONE);
		return b2bCompanyExtDao.searchList(map);
	}

	// 奖品设置页面，pagemodel
	@Override
	public PageModel<B2bLotteryAwardExDto> searchLotteryAwardList(QueryModel<B2bLotteryAwardExDto> qm) {
		PageModel<B2bLotteryAwardExDto> pm = new PageModel<B2bLotteryAwardExDto>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", qm.getStart());
		map.put("limit", qm.getLimit());
		map.put("orderName", qm.getFirstOrderName());
		map.put("orderType", qm.getFirstOrderType());
		map.put("awardType", qm.getEntity().getAwardType());
		int totalCount = b2bLotteryAwardExDao.searchGridCountEx(map);
		List<B2bLotteryAwardExDto> list = b2bLotteryAwardExDao.searchGridEx(map);
		pm.setTotalCount(totalCount);
		pm.setDataList(list);
		return pm;
	}

	// 编辑，新增，删除 奖项
	@Override
	public int editLotteryAward(B2bLotteryAwardExDto dto) {
		int result = 0;

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			if (dto.getStartTimeStr() != null && !"".equals(dto.getStartTimeStr())) {
				Date startTime = null;
				try {
					startTime = format.parse(dto.getStartTimeStr());
				} catch (ParseException e) {
					e.printStackTrace();
				}
				dto.setStartTime(startTime);
			}
			if (dto.getEndTimeStr() != null && !"".equals(dto.getEndTimeStr())) {
				Date endTime = null;
				try {
					endTime = format.parse(dto.getEndTimeStr());
				} catch (ParseException e) {
					e.printStackTrace();
				}
				dto.setEndTime(endTime);
			}
		// 当启用禁用不修改此状态
		if (dto.getIsVisable() == null) {
			// 如果奖品类型是谢谢参与，奖品就是无效奖品
			if (dto.getAwardType() != null && dto.getAwardType() == Constants.TWO) {
				dto.setStatus(Constants.TWO);
			} else {
				dto.setStatus(Constants.ONE);
			}
		}
		if (dto.getPk() != null && !"".equals(dto.getPk())) {
			dto.setUpdateTime(new Date());
			B2bLotteryAward award = new B2bLotteryAward(dto);
			result = b2bLotteryAwardExDao.updateAwardObjEx(award);
		} else {
			dto.setPk(KeyUtils.getUUID());
			dto.setInsertTime(new Date());
			dto.setUpdateTime(new Date());
			dto.setIsDelete(Constants.ONE);
			dto.setIsVisable(Constants.ONE);
			B2bLotteryAward award = new B2bLotteryAward(dto);
			result = b2bLotteryAwardExDao.insert(award);
		}
		return result;
	}

	@Override
	public int updateLotteryAward(B2bLotteryAwardExDto dto) {

		return b2bLotteryAwardExDao.updateAwardStatus(dto);
	}

	// 根据pk查询奖项
	@Override
	public B2bLotteryAwardExDto getLotteryAwardByPk(String pk) {
		return b2bLotteryAwardExDao.getLotteryAwardByAwardPk(pk);
	}

	// 获奖名单pagemodel
	@Override
	public PageModel<B2bLotteryRecordExDto> lotteryAwardRosterList(QueryModel<B2bLotteryRecordExDto> qm, ManageAccount account) {
		PageModel<B2bLotteryRecordExDto> pm = new PageModel<B2bLotteryRecordExDto>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", qm.getStart());
		map.put("limit", qm.getLimit());
		map.put("orderName", qm.getFirstOrderName());
		map.put("orderType", qm.getFirstOrderType());
		// 获奖名单页签条件
		if (qm.getEntity().getIsHistory() == null) {
			map.put("status", qm.getEntity().getStatus());
			map.put("awardStatus", qm.getEntity().getIsAwardStatus());
			map.put("activityType", qm.getEntity().getActivityType());
		}
		map.put("insertTimeStart", qm.getEntity().getInsertTimeStart());
		map.put("insertTimeEnd", qm.getEntity().getInsertTimeEnd());
		map.put("mobile", qm.getEntity().getMobile());
		map.put("memberName", qm.getEntity().getMemberName());
		map.put("isHistory", qm.getEntity().getIsHistory());
		// 抽奖记录页签查询条件
		if (qm.getEntity().getIsHistory() != null) {
			map.put("status", qm.getEntity().getIsStatus());
			map.put("awardStatus", qm.getEntity().getAwardStatus());
			map.put("name", qm.getEntity().getAwardName());
			map.put("awardType", qm.getEntity().getAwardType());
		}
		int totalCount = b2bLotteryRecordExDao.searchLotteryAwardRosterCount(map);
		if (qm.getEntity().getColAuthAwardType() == Constants.ONE){
		setRosterColParams(account,map);
		}
		if (qm.getEntity().getColAuthAwardType() == Constants.TWO){
			setHistoryColParams(account,map);
		}
		List<B2bLotteryRecordExDto> list = b2bLotteryRecordExDao.searchLotteryAwardRosterList(map);
		pm.setTotalCount(totalCount);
		pm.setDataList(list);
		return pm;
	}

	private void setRosterColParams(ManageAccount account,Map<String,Object> map){
		if (account != null){
			if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.OPER_ACTIVITY_AWARD_COL_MEMBERNAME)){
				map.put("memberNameCol",ColAuthConstants.OPER_ACTIVITY_AWARD_COL_MEMBERNAME);
			}
			if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.OPER_ACTIVITY_AWARD_COL_MOBILE)){
				map.put("mobileCol",ColAuthConstants.OPER_ACTIVITY_AWARD_COL_MOBILE);
			}
			if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.OPER_ACTIVITY_AWARD_COL_COMPANYNAME)){
				map.put("companyNameCol",ColAuthConstants.OPER_ACTIVITY_AWARD_COL_COMPANYNAME);
			}
		}
	}

	private void setHistoryColParams(ManageAccount account,Map<String,Object> map){
		if (account != null){
			if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.OPER_ACTIVITY_HIS_COL_MEMBERNAME)){
				map.put("memberNameCol",ColAuthConstants.OPER_ACTIVITY_HIS_COL_MEMBERNAME);
			}
			if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.OPER_ACTIVITY_HIS_COL_MOBILE)){
				map.put("mobileCol",ColAuthConstants.OPER_ACTIVITY_HIS_COL_MOBILE);
			}
			if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.OPER_ACTIVITY_HIS_COMPANYNAME)){
				map.put("companyNameCol",ColAuthConstants.OPER_ACTIVITY_HIS_COMPANYNAME);
			}
		}
	}

	// 根据pk查询抽奖记录对象
	@Override
	public B2bLotteryRecordExDto getLotteryRecordByPk(String pk) {
		return b2bLotteryRecordExDao.getExDtoByPk(pk);
	}

	// 编辑抽奖记录
	@Override
	public int editLotteryRecord(B2bLotteryRecordExDto dto) {
		int result = 0;
		String addressPk = "";
		if (dto.getPk() != null && !"".equals(dto.getPk())) {
			B2bLotteryRecordDto recordExDto = b2bLotteryRecordExDao.getByPk(dto.getPk());
			addressPk =	recordExDto.getAddressPk();
			if (addressPk!=null &&!addressPk.equals("")) {
				Map<String, Object> map = new HashMap<>();
				map.put("pk", addressPk);
				map.put("isDelete", 1);
				B2bAddressDto addressDto = b2bAddressExDao.getExtByMap(map);
				if ( addressDto!=null) {
					B2bAddress b2bAddress = new B2bAddress();
					b2bAddress.setPk(addressPk);
					b2bAddress.setAddress(dto.getAddress());
					b2bAddress.setProvince(dto.getProvince());
					b2bAddress.setProvinceName(dto.getProvinceName());
					b2bAddress.setCity(dto.getCity());
					b2bAddress.setCityName(dto.getCityName());
					b2bAddress.setArea(dto.getArea());
					b2bAddress.setAreaName(dto.getAreaName());
					b2bAddress.setTown(dto.getTown());
					b2bAddress.setTownName(dto.getTownName());
					b2bAddress.setContacts(dto.getContacts());
					b2bAddress.setContactsTel(dto.getContactsTel());
					result = b2bAddressExDao.updateAddress(b2bAddress);
				}else{
					result = updateAddress(dto);
				}
			}else{
				result = updateAddress(dto);
			}
			if (result > 0) {
				result = b2bLotteryRecordExDao.updateByLotteryRecordDto(dto);
			}
		}
		return result;
	}

	private int updateAddress(B2bLotteryRecordDto dto) {
		int result = 0;
		String pk = "";
		if (dto.getCompanyPk() != null && !"".equals(dto.getCompanyPk())) {
			pk = KeyUtils.getUUID();
			B2bAddress b2bAddress = new B2bAddress();
			b2bAddress.setPk(pk);
			b2bAddress.setCompanyPk(dto.getCompanyPk());
			if (dto.getCompanyPk()!=null&& !dto.getCompanyPk().equals("")) {
				B2bCompanyDto companyDto = b2bCompanyExtDao.getByPk(dto.getCompanyPk());
				if (companyDto!=null) {
					b2bAddress.setSigningCompany(companyDto.getName());
				}
			}
			
			b2bAddress.setInsertTime(new Date());
			b2bAddress.setAddress(dto.getAddress());
			b2bAddress.setProvince(dto.getProvince());
			b2bAddress.setProvinceName(dto.getProvinceName());
			b2bAddress.setCity(dto.getCity());
			b2bAddress.setCityName(dto.getCityName());
			b2bAddress.setArea(dto.getArea());
			b2bAddress.setAreaName(dto.getAreaName());
			b2bAddress.setTown(dto.getTown());
			b2bAddress.setTownName(dto.getTownName());
			b2bAddress.setContacts(dto.getContacts());
			b2bAddress.setContactsTel(dto.getContactsTel());
			b2bAddress.setIsDelete(1);
			List<B2bAddressDto> addressDtoList = b2bAddressExDao.getByCompanyPkEx(dto.getCompanyPk());
			if (addressDtoList != null && addressDtoList.size() > 0) {// 公司有地址
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("companyPk", dto.getCompanyPk());
				map.put("province", dto.getProvince());
				map.put("city", dto.getCity());
				map.put("area", dto.getArea());
				map.put("town", dto.getTown()==null?"":dto.getTown());
				map.put("contactsTel", dto.getContactsTel());
				map.put("contacts", dto.getContacts());
				List<B2bAddressDto> list = b2bAddressExDao.getAddressByMap(map);// 查询是否存在此条地址
				if (list != null && list.size() > 0) {
					dto.setAddressPk(list.get(0).getPk());
					result = 1;
				} else {
//					List<B2bAddressDto> isDefaultList = b2bAddressExDao.getAddressisDefaultByCompanyPk(dto.getCompanyPk());// 查询是否有默认地址
//					if (isDefaultList != null && isDefaultList.size() > 0) {
						b2bAddress.setIsDefault(2);
//					} else {
//						b2bAddress.setIsDefault(1);
//					}
					result = b2bAddressExDao.insert(b2bAddress);
					dto.setAddressPk(pk);
				}
			} else {
				// 若公司没有地址，则添加一条地址
				b2bAddress.setIsDefault(2);
				result = b2bAddressExDao.insert(b2bAddress);
				dto.setAddressPk(pk);
			}
		}
		return result;
	}

	// 导出获奖记录 List
	@Override
	public List<B2bLotteryRecordExDto> searchExportLotteryAwardList(QueryModel<B2bLotteryRecordExDto> qm,ManageAccount account) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderName", qm.getFirstOrderName());
		map.put("orderType", qm.getFirstOrderType());
		// 获奖名单页签条件
		if (qm.getEntity().getIsHistory() == null) {
			map.put("status", qm.getEntity().getStatus());
			map.put("awardStatus", qm.getEntity().getIsAwardStatus());
			map.put("activityType", qm.getEntity().getActivityType());
		}
		map.put("insertTimeStart", qm.getEntity().getInsertTimeStart());
		map.put("insertTimeEnd", qm.getEntity().getInsertTimeEnd());
		map.put("mobile", qm.getEntity().getMobile());
		map.put("memberName", qm.getEntity().getMemberName());
		map.put("isHistory", qm.getEntity().getIsHistory());
		// 抽奖记录页签查询条件
		if (qm.getEntity().getIsHistory() != null) {
			map.put("status", qm.getEntity().getIsStatus());
			map.put("awardStatus", qm.getEntity().getAwardStatus());
			map.put("name", qm.getEntity().getName());
			map.put("awardType", qm.getEntity().getAwardType());
		}
		if (qm.getEntity().getColAuthAwardType() == Constants.ONE){
			setRosterColParams(account,map);
		}
		if (qm.getEntity().getColAuthAwardType() == Constants.TWO){
			setHistoryColParams(account,map);
		}
		List<B2bLotteryRecordExDto> list = b2bLotteryRecordExDao.searchLotteryAwardRosterList(map);
		return list;
	}

	@Override
	public int saveLotteryAwardToOss(CustomerDataTypeParams params, ManageAccount account) {

		Date time = new Date();
		String timeStr = new SimpleDateFormat("yyyy-MM-dd").format(time);
		Map<String, String> awardMap = null;
		try {
			awardMap = CommonUtil.checkExportTime(params.getInsertTimeStart(), params.getInsertTimeEnd(), timeStr);
			params.setInsertTimeStart(awardMap.get("startTime"));
			params.setInsertTimeEnd(awardMap.get("endTime"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		String json = JsonUtils.convertToString(params);
		SysExcelStore store = new SysExcelStore();
		store.setPk(KeyUtils.getUUID());
		store.setMethodName("exportAwardRoster_"+params.getUuid());
		store.setParams(json);
		store.setParamsName(ExportDoJsonParams.doActivityRunnableParams(params,time));
		store.setIsDeal(Constants.TWO);
		store.setInsertTime(time);
		if (CommonUtil.isNotEmpty(params.getIsHistory()) && "1".equals(params.getIsHistory())){
			store.setName("运营中心-会员活动-抽奖管理-抽奖记录");
		}else{
			store.setName("运营中心-会员活动-抽奖管理-获奖名单");
		}
		store.setType(Constants.ONE);
		store.setAccountPk(account.getPk());
		return sysExcelStoreExtDao.insert(store);
	}

	// 导入获奖名单
	@Override
	public int importUpdateLotteryRecord(B2bLotteryRecordExDto dto) throws Exception {
		int result = 0;
		if (dto.getPk() != null && !"".equals(dto.getPk())) {
			result = b2bLotteryRecordExDao.updateByLotteryRecordDto(dto);
		}
		return result;
	}

	@Override
	public PageModel<B2bLotteryMaterialDto> searchLotteryMaterialList(QueryModel<B2bLotteryMaterialDto> qm) {
		PageModel<B2bLotteryMaterialDto> pm = new PageModel<B2bLotteryMaterialDto>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", qm.getStart());
		map.put("limit", qm.getLimit());
		map.put("orderName", qm.getFirstOrderName());
		map.put("orderType", qm.getFirstOrderType());
		map.put("name", qm.getEntity().getName());
		map.put("isDelete", Constants.ONE);
		int totalCount = b2bLotteryMaterialDao.searchGridCount(map);
		List<B2bLotteryMaterialDto> list = b2bLotteryMaterialDao.searchGrid(map);
		pm.setTotalCount(totalCount);
		pm.setDataList(list);
		return pm;
	}

	@Override
	public int updateLotteryMaterial(B2bLotteryMaterialDto dto) {
		int result = 0;
		B2bLotteryMaterialDto materialDto = b2bLotteryMaterialDao.getByName(dto.getName());

		if(materialDto != null){
			result = -1;
		}else{
			if (dto.getPk() != null && !"".equals(dto.getPk())) {
				dto.setUpdateTime(new Date());
				result = b2bLotteryMaterialDao.updateMaterial(dto);

			} else {
				B2bLotteryMaterial material = new B2bLotteryMaterial();
				material.setPk(KeyUtils.getUUID());
				material.setInsertTime(new Date());
				material.setIsDelete(1);
				material.setIsVisable(1);
				material.setName(dto.getName());
				result = b2bLotteryMaterialDao.insert(material);
			}
		}


		return result;
	}

	@Override
	public LotteryActivityRule searchLotteryCouponUseRule(Integer type) {
		return mongoTemplate.findOne(new Query(Criteria.where("id").ne("").ne(null)// 非空判断
				.and("ruleType").is(type)), LotteryActivityRule.class);
	}

	@Override
	public String updateLotteryCouponUseRule(LotteryActivityRule rule) {
		LotteryActivityRule useRule = mongoTemplate.findOne(new Query(Criteria.where("id").ne("").ne(null)// 非空判断
				.and("ruleType").is(rule.getRuleType())), LotteryActivityRule.class);
		String msg = "";
		String nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		if (useRule != null) {
			useRule.setRuleDetail(rule.getRuleDetail());
			useRule.setUpdateTime(nowTime);
			msg = mongoSave(useRule);
		} else {
			LotteryActivityRule lotteryActivityRule = new LotteryActivityRule();
			lotteryActivityRule.setId(KeyUtils.getUUID());
			lotteryActivityRule.setInsertTime(nowTime);
			lotteryActivityRule.setRuleType(1);
			lotteryActivityRule.setRuleDetail(rule.getRuleDetail());
			msg = mongoSave(lotteryActivityRule);
		}
		return msg;
	}

	private String mongoSave(LotteryActivityRule rule) {
		String msg = "";
		try {
			mongoTemplate.save(rule);
			msg = Constants.RESULT_SUCCESS_MSG;
		} catch (Exception e) {
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;
	}
}
