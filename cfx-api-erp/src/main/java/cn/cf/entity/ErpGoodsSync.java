package cn.cf.entity;

import java.util.List;

import cn.cf.dto.B2bGoodsDtoEx;

public class ErpGoodsSync {

	private List<B2bGoodsDtoEx> insertGoodsList;
	
	private List<B2bGoodsDtoEx> updateGoodsList;

	public List<B2bGoodsDtoEx> getInsertGoodsList() {
		return insertGoodsList;
	}

	public void setInsertGoodsList(List<B2bGoodsDtoEx> insertGoodsList) {
		this.insertGoodsList = insertGoodsList;
	}

	public List<B2bGoodsDtoEx> getUpdateGoodsList() {
		return updateGoodsList;
	}

	public void setUpdateGoodsList(List<B2bGoodsDtoEx> updateGoodsList) {
		this.updateGoodsList = updateGoodsList;
	}
	
	
}
