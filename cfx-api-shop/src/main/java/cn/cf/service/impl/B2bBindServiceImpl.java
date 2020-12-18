package cn.cf.service.impl;


import cn.cf.common.RestCode;
import cn.cf.dao.B2bBindDaoEx;
import cn.cf.dao.B2bBindGoodsDaoEx;
import cn.cf.dto.B2bBindDto;
import cn.cf.dto.B2bBindDtoEx;
import cn.cf.dto.B2bBindGoodsDto;
import cn.cf.dto.PageModelBind;
import cn.cf.model.B2bBind;
import cn.cf.service.B2bBindService;
import cn.cf.util.ArithUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class B2bBindServiceImpl implements B2bBindService {
	
    @Autowired
    private B2bBindDaoEx b2bBindDaoEx;
    
    @Autowired
    private B2bBindGoodsDaoEx b2bBindGoodsDaoEx;
     

    //拼团活动的分页列表
    @Override
    public PageModelBind<B2bBindDtoEx> searchBindList(Map<String, Object> map) {
    	PageModelBind<B2bBindDtoEx> pm = new PageModelBind<B2bBindDtoEx>();
        if (null == map) {
            map = new HashMap<String, Object>();
        }
        map.put("isDelete", 1);
        map.put("orderName", "bindProductID");
        map.put("orderType", "DESC");
        List<B2bBindDtoEx> b2bBindDtoList = b2bBindDaoEx.searchGridEx(map);
        //计算销售进度
        for (int i = 0; i < b2bBindDtoList.size(); i++) {
        	B2bBindDtoEx bindDtoEx=b2bBindDtoList.get(i);
        	List<B2bBindGoodsDto> bindGoodsList=b2bBindGoodsDaoEx.getByBindPk(bindDtoEx.getPk());
        	if (null!=bindGoodsList && bindGoodsList.size()>0){
        		int leftCount=0;
				int totalCount=0;
				for (int j = 0; j < bindGoodsList.size(); j++) {
					leftCount=leftCount+bindGoodsList.get(j).getBoxes();
					totalCount=totalCount+bindGoodsList.get(j).getTotalBoxes();
				}
				if (totalCount!=0) {
					bindDtoEx.setProgress(ArithUtil.div((totalCount-leftCount)*100, totalCount,2));
				}
			}
		}
        int counts = b2bBindDaoEx.searchGridCount(map);
        pm.setDataList(b2bBindDtoList);
        pm.setTotalCount(counts);
        if (null != map.get("start")) {
            pm.setStartIndex(Integer.parseInt(map.get("start").toString()));
            pm.setPageSize(Integer.parseInt(map.get("limit").toString()));
        }
        //上架状态，0：全部，1：上架，2：下架
        map.put("isUpDown", 0);//全部数量
        pm.setAllNum(b2bBindDaoEx.searchGridCount(map));
        map.put("isUpDown", 1);//上架数量
        pm.setUpNum(b2bBindDaoEx.searchGridCount(map));
        map.put("isUpDown", 2);//下架数量
        pm.setDownNum(b2bBindDaoEx.searchGridCount(map));
        return pm;
    }

    @Override
    public B2bBindDto getBind(String pk) {
        return b2bBindDaoEx.getBind(pk);
    }

    /**
     * 添加拼团活动
     */
	@Override
	public boolean addBind(B2bBind model) {
		if (b2bBindDaoEx.insert(model)>0) {
			return true;
		}else{
			return false;
		}
	}
	
	
	/**
	 * 编辑拼团活动
	 */
	@Override
	public boolean updateEx(B2bBind model) {
		if (b2bBindDaoEx.updateEx(model)>0) {
			return true;
		}else {
			return false;
		}
	}

	/**
	 * 删除拼团活动
	 */
	@Override
	public boolean delBind(String pk) {
		boolean result=true;
		if (b2bBindDaoEx.delBind(pk)>0) {
			result=true;
		}else{
			result=false;
		}
		return result;
	}

	/**
	 * 编辑拼团活动/上下架
	 */
	@Override
	public B2bBindDto editBind(B2bBindDto bindDto) {
		B2bBind model=new B2bBind();
		bindDto.setUpdateTime(new Date());
		model.UpdateDTO(bindDto);
		if ( b2bBindDaoEx.updateEx(model)>0) {
			return b2bBindDaoEx.getBind(bindDto.getPk());
		}else{
			return null;
		}
	}

	/**
	 * 上下架拼团活动
	 */
	@Override
	public RestCode upDownBind(String bindPk, int isUpDown) {
		RestCode resultCode=RestCode.CODE_0000;
		B2bBindDto bindDto= b2bBindDaoEx.getBind(bindPk);
		if (null==bindDto||null==bindDto.getPk()) {
			return RestCode.CODE_S999;
		}
		B2bBind model=new B2bBind();
		model.setPk(bindPk);
		model.setUpdateTime(new Date());
		model.setIsUpDown(isUpDown);
		if (b2bBindDaoEx.updateEx(model)>0) {
			resultCode=RestCode.CODE_0000;
		}else {
			resultCode=RestCode.CODE_S999;
		}
		return resultCode;
	}
	
	/**
	 * 拼团活动下拉列表
	 */
	@Override
	public List<B2bBindDto> getBindLists(Map<String, Object> map) {
		return b2bBindDaoEx.getBindLists(map);
	}

	
	
}
