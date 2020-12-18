package cn.cf.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.cf.constant.Block;
import cn.cf.dto.B2bGoodsDto;

public class LogisticsErpStepInfoList {
	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	public LogisticsErpStepInfoList(B2bGoodsDto goods){
		B2bGoodsDtoMa bm = new B2bGoodsDtoMa();
		bm.UpdateMine(goods);
		Map<String, Object> map=new HashMap<String, Object>();
		// 品名+是否暂存+包装形式
		map.put("productPk", Block.CF.getValue().equals(bm.getBlock())?bm.getCfGoods().getProductPk():"");
		map.put("isStore", 2);
		map.put("packNumber", Block.CF.getValue().equals(bm.getBlock())?bm.getCfGoods().getPackNumber():"");
		list.add(map);
		
		// 品名 是否暂存
		    map=new HashMap<String, Object>();
		     map.put("productPk", Block.CF.getValue().equals(bm.getBlock())?bm.getCfGoods().getProductPk():"");
			map.put("isStore", 2);
     		map.put("packNumber","");
		list.add(map);
		//是否暂存+包装形式
		map=new HashMap<String, Object>();
		map.put("isStore", 2);
		map.put("productPk", "");
		map.put("packNumber", Block.CF.getValue().equals(bm.getBlock())?bm.getCfGoods().getPackNumber():"");
		list.add(map);
		//	是否暂存
		map=new HashMap<String, Object>();
		map.put("isStore", 2);
		map.put("productPk", "");
		map.put("packNumber", "");
		list.add(map);
	}
	public List<Map<String, Object>> getList() {
		return list;
	}
	public void setList(List<Map<String, Object>> list) {
		this.list = list;
	}
}
