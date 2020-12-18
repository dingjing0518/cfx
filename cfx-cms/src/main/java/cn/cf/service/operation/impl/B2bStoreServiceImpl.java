package cn.cf.service.operation.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.PageModel;
import cn.cf.common.ColAuthConstants;
import cn.cf.common.Constants;
import cn.cf.common.QueryModel;
import cn.cf.common.utils.CommonUtil;
import cn.cf.dao.B2bStoreAlbumDao;
import cn.cf.dao.B2bStoreExtDao;
import cn.cf.dto.B2bStoreAlbumDto;
import cn.cf.dto.B2bStoreExtDto;
import cn.cf.entity.B2bStoreAlbumEntry;
import cn.cf.model.B2bStoreAlbum;
import cn.cf.service.operation.B2bStoreService;
import cn.cf.util.KeyUtils;

@Service
public class B2bStoreServiceImpl implements B2bStoreService{

	@Autowired
	private B2bStoreExtDao b2bStoreExtDao;
	@Autowired
	private B2bStoreAlbumDao b2bStoreAlbumDao;
	
	@Override
	public PageModel<B2bStoreExtDto> searchStoreList(QueryModel<B2bStoreExtDto> qm) {
		
		
		Map<String, Object> map = new HashMap<String, Object>();
		PageModel<B2bStoreExtDto> pm = new PageModel<B2bStoreExtDto>();
		map.put("start", qm.getStart());
		map.put("limit", qm.getLimit());
		map.put("orderName", qm.getFirstOrderName());
		map.put("orderType", qm.getFirstOrderType());
		map.put("startsTime", qm.getEntity().getStartStime());
		map.put("starteTime", qm.getEntity().getStartEtime());
		map.put("endsTime", qm.getEntity().getEndStime());
		map.put("endeTime", qm.getEntity().getEndEtime());
		map.put("name", qm.getEntity().getName());
		map.put("isOpen", qm.getEntity().getIsOpen());
		if(qm.getEntity().getStartTimeBegin()!=null && !"".equals(qm.getEntity().getStartTimeBegin())){
			map.put("startTimeBegin", qm.getEntity().getStartTimeBegin()+":00");
		}
		if(qm.getEntity().getStarteTimeEnd() != null && !"".equals(qm.getEntity().getStarteTimeEnd())){
			map.put("starteTimeEnd", qm.getEntity().getStarteTimeEnd()+":00");
		}
		if (!CommonUtil.isExistAuthName(qm.getEntity().getAccountPk(), ColAuthConstants.MARKET_STORE_COL_STORENAME)) {
		    map.put("colStoreName",ColAuthConstants.MARKET_STORE_COL_STORENAME );
        }
		if (! CommonUtil.isExistAuthName(qm.getEntity().getAccountPk(), ColAuthConstants.MARKET_STORE_COL_CONTACTS)) {
		    map.put("colContacts", ColAuthConstants.MARKET_STORE_COL_CONTACTS);
        }
		if (! CommonUtil.isExistAuthName(qm.getEntity().getAccountPk(), ColAuthConstants.MARKET_STORE_COL_CONTACTSTEL)) {
            map.put("colContactsTel", ColAuthConstants.MARKET_STORE_COL_CONTACTSTEL);
        }
		if (! CommonUtil.isExistAuthName(qm.getEntity().getAccountPk(), ColAuthConstants.MARKET_STORE_COL_STOREQQ)) {
            map.put("colStoreQQ", ColAuthConstants.MARKET_STORE_COL_STOREQQ);
        }
		if (! CommonUtil.isExistAuthName(qm.getEntity().getAccountPk(), ColAuthConstants.MARKET_STORE_COL_STORELOGO)) {
            map.put("colStoreLogo", ColAuthConstants.MARKET_STORE_COL_STORELOGO);
        }
          
		
		List<B2bStoreExtDto> list = b2bStoreExtDao.searchGridExt(map);
		int counts = b2bStoreExtDao.searchGridExtCount(map);
		pm.setTotalCount(counts);
		pm.setDataList(list);
		return pm;
	}
	
	@Override
	public List<B2bStoreExtDto> searchStoreList(Map<String, Object> map) {
		
		return b2bStoreExtDao.getMap(map);
	}
	
	@Override
	public int updateStore(B2bStoreExtDto dto) {
		return b2bStoreExtDao.updateStore(dto);
	}

	@Override
	public PageModel<B2bStoreAlbumEntry> searchStoreAlbumList(QueryModel<B2bStoreAlbumDto> qm) {
		PageModel<B2bStoreAlbumEntry> pm = new PageModel<>();
		Map<String,Object> map = new HashMap<String,Object>();
		
		List<B2bStoreAlbumEntry> entryList = new ArrayList<>();
		int start = qm.getStart();
		int limit = qm.getLimit();
		map.put("orderName", qm.getFirstOrderName());
		map.put("orderType", qm.getFirstOrderType());
		map.put("start", start);
		map.put("limit", limit/3);
		map.put("storePk", qm.getEntity().getStorePk());
		List<B2bStoreAlbumDto> list1 = b2bStoreAlbumDao.searchGrid(map);
		int count = b2bStoreAlbumDao.searchGridCount(map);
		setAlbumEntry(list1, entryList);
		
		map.put("start", start+(limit/3));
		map.put("limit", (limit/3)*2);
		List<B2bStoreAlbumDto> list2 = b2bStoreAlbumDao.searchGrid(map);
		setAlbumEntry(list2, entryList);
		
		map.put("start", start+((limit/3)*2));
		map.put("limit", limit);
		List<B2bStoreAlbumDto> list3 = b2bStoreAlbumDao.searchGrid(map);
		setAlbumEntry(list3, entryList);
		pm.setDataList(entryList);
		pm.setTotalCount(count);
		return pm;
	}

	private void setAlbumEntry(List<B2bStoreAlbumDto> list,List<B2bStoreAlbumEntry> entryList) {
		
		if(list != null && list.size() > 0){
			B2bStoreAlbumEntry entry = new B2bStoreAlbumEntry();
			for (int i = 0; i < list.size(); i++) {
				
				if(list.get(i) != null && i == 0){
					B2bStoreAlbumDto dto = list.get(i);
					String[] obj = setAlbum(dto);
					entry.setImgOne(obj);
				}
				if(list.get(i) != null && i == 1){
					B2bStoreAlbumDto dto = list.get(i);
					String[] obj = setAlbum(dto);
					entry.setImgTwo(obj);
				}
				if(list.get(i) != null && i == 2){
					B2bStoreAlbumDto dto = list.get(i);
					String[] obj = setAlbum(dto);
					entry.setImgThree(obj);
				}
				if(list.get(i) != null && i == 3){
					B2bStoreAlbumDto dto = list.get(i);
					String[] obj = setAlbum(dto);
					entry.setImgFour(obj);
				}
			}
			entryList.add(entry);
		}
	}

	private String[] setAlbum(B2bStoreAlbumDto dto) {
		String[] obj = new String[4];
		obj[0] = dto.getPk();
		obj[1] = dto.getStorePk();
		obj[2] = dto.getUrl();
		obj[3] = dto.getInsertTime() == null?"":new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dto.getInsertTime());
		return obj;
	}

	@Override
	public int updateStoreAlbum(B2bStoreAlbumDto dto) {
		int result = 0;
		if(dto.getPk() != null){
			result = b2bStoreAlbumDao.delete(dto.getPk());
		}else{
			B2bStoreAlbum album = new B2bStoreAlbum();
			album.setPk(KeyUtils.getUUID());
			album.setUrl(dto.getUrl());
			album.setStorePk(dto.getStorePk());
			result = b2bStoreAlbumDao.insert(album);
		}
		return result;
	}

	@Override
	public PageModel<B2bStoreExtDto> searchStoreBindList(QueryModel<B2bStoreExtDto> qm) {
		Map<String, Object> map = new HashMap<String, Object>();
		PageModel<B2bStoreExtDto> pm = new PageModel<B2bStoreExtDto>();
		
		map.put("orderName", qm.getFirstOrderName());
		map.put("orderType", qm.getFirstOrderType());
		map.put("name", qm.getEntity().getName());
		if (qm.getEntity().getName()==null||qm.getEntity().getName().equals("")) {
			map.put("start",0);
			map.put("limit", 0);
		}else{
			map.put("start", qm.getStart());
			map.put("limit", qm.getLimit());
		}
		List<B2bStoreExtDto> list = b2bStoreExtDao.searchGridExt(map);
		int counts = b2bStoreExtDao.searchGridExtCount(map);
		pm.setTotalCount(counts);
		pm.setDataList(list);
		return pm;
	}
	
}
