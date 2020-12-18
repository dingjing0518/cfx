package cn.cf.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bMemberDto;

public class LogisticsErpList {
	List<Map<String, String>> list = new ArrayList<Map<String, String>>();

	public LogisticsErpList(Map<String, String> map, B2bMemberDto dto) {
		list = new ArrayList<Map<String, String>>();
		// 1业务员+省份+城市+区县+乡镇+厂区
		map.put("employeeNumber", dto.getEmployeeNumber()==null?"":dto.getEmployeeNumber());
		Map<String, String> map1 = new HashMap<String, String>();
		map1.putAll(map);
		list.add(map1);
		// 2业务员+省份+城市+区县+乡镇
		map1 = new HashMap<String, String>();
		map1.putAll(map);
		map1.put("plantPk", "");
		list.add(map1);
		// 3省份+城市+区县+乡镇+厂区
		map1 = new HashMap<String, String>();
		map1.putAll(map);
		map1.remove("employeeNumber");
		list.add(map1);
		// 4省份+城市+区县+乡镇
		map1 = new HashMap<String, String>();
		map1.putAll(map);
		map1.remove("employeeNumber");
		map1.put("plantPk", "");
		list.add(map1);
		// 5业务员+省份+城市+区县+厂区
		map1 = new HashMap<String, String>();
		map1.putAll(map);
		map1.put("town", "");
		list.add(map1);
		// 6业务员+省份+城市+区县
		map1 = new HashMap<String, String>();
		map1.putAll(map);
		map1.put("town", "");
		map1.put("plantPk", "");
		list.add(map1);
		// 7省份+城市+区县+厂区
		map1 = new HashMap<String, String>();
		map1.putAll(map);
		map1.put("town", "");
		map1.remove("employeeNumber");
		list.add(map1);
		// 8省份+城市+区县
		map1 = new HashMap<String, String>();
		map1.putAll(map);
		map1.put("town", "");
		map1.remove("employeeNumber");
		map1.put("plantPk", "");
		list.add(map1);
		// 9业务员+省份+城市+厂区
		map1 = new HashMap<String, String>();
		map1.putAll(map);
		map1.put("town", "");
		map1.put("area", "");
		list.add(map1);
		// 10业务员+省份+城市
		map1 = new HashMap<String, String>();
		map1.putAll(map);
		map1.put("town", "");
		map1.put("area", "");
		map1.put("plantPk", "");
		list.add(map1);
		// 11省份+城市+厂区
		map1 = new HashMap<String, String>();
		map1.putAll(map);
		map1.put("town", "");
		map1.put("area", "");
		map1.remove("employeeNumber");
		list.add(map1);
		// 12省份+城市
		map1 = new HashMap<String, String>();
		map1.putAll(map);
		map1.put("town", "");
		map1.put("area", "");
		map1.remove("employeeNumber");
		map1.put("plantPk", "");
		list.add(map1);
		// 13 业务员+省份+厂区
		map1 = new HashMap<String, String>();
		map1.putAll(map);
		map1.put("city", "");
		map1.put("town", "");
		map1.put("area", "");
		list.add(map1);
		// 14业务员+省份
		map1 = new HashMap<String, String>();
		map1.putAll(map);
		map1.put("city", "");
		map1.put("town", "");
		map1.put("area", "");
		map1.put("plantPk", "");
		list.add(map1);
		// 15省份+厂区
		map1 = new HashMap<String, String>();
		map1.putAll(map);
		map1.put("city", "");
		map1.put("town", "");
		map1.put("area", "");
		map1.remove("employeeNumber");
		list.add(map1);
		// 16省份
		map1 = new HashMap<String, String>();
		map1.putAll(map);
		map1.put("city", "");
		map1.put("town", "");
		map1.put("area", "");
		map1.remove("employeeNumber");
		map1.put("plantPk", "");
		list.add(map1);
	}

	public List<Map<String, String>> getList() {
		return list;
	}

	public void setList(List<Map<String, String>> list) {
		this.list = list;
	}

}
