package cn.cf.common;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


public class KeyWordUtil {
	private Map<String, Object> dictionaryMap;

	public KeyWordUtil(Set<String> wordSet) {
		this.dictionaryMap = handleToMap(wordSet);
	}

	public Map<String, Object> getDictionaryMap() {
		return dictionaryMap;
	}

	public void setDictionaryMap(Map<String, Object> dictionaryMap) {
		this.dictionaryMap = dictionaryMap;
	}

	@SuppressWarnings("unchecked")
	private Map<String, Object> handleToMap(Set<String> wordSet) {
		if (wordSet == null) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>(wordSet.size());
		Map<String, Object> curMap = null;
		Iterator<String> ite = wordSet.iterator();
		while (ite.hasNext()) {
			String word = ite.next();
			curMap = map;
			int len = word.length();
			for (int i = 0; i < len; i++) {
				String key = String.valueOf(word.charAt(i));
				Map<String, Object> wordMap = (Map<String, Object>) curMap
						.get(key);
				if (wordMap == null) {
					wordMap = new HashMap<String, Object>();
					wordMap.put("isEnd", "0");
					curMap.put(key, wordMap);
					curMap = wordMap;
				} else {
					curMap = wordMap;
				}
				if (i == len - 1) {
					curMap.put("isEnd", "1");
				}
			}
		}
		return map;
	}

	/**
	 * 
	 * @param text
	 *            关键字
	 * @param beginIndex
	 *            字符串看是位置
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int checkWord(String text, int beginIndex) {
		if (dictionaryMap == null) {
			throw new RuntimeException("字典不能为空！");
		}
		boolean isEnd = false;
		int wordLength = 0;
		Map<String, Object> curMap = dictionaryMap;
		int len = text.length();
		for (int i = beginIndex; i < len; i++) {
			String key = String.valueOf(text.charAt(i));
			curMap = (Map<String, Object>) curMap.get(key);
			if (curMap == null) {
				break;
			} else {
				wordLength++;
				if ("1".equals(curMap.get("isEnd"))) {
					isEnd = true;
				}
			}
		}
		if (!isEnd) {
			wordLength = 0;
		}
		return wordLength;
	}

	public Set<String> getWords(String text) {
		Set<String> wordSet = new HashSet<String>();
		int len = text.length();
		for (int i = 0; i < len; i++) {
			int wordLength = checkWord(text, i);
			if (wordLength > 0) {
				String word = text.substring(i, i + wordLength);
				wordSet.add(word);
				i = i + wordLength - 1;
			}
		}
		return wordSet;
	}

	public static String updateChildNumberByJsonStr(String jsonStr) {
		// 将Json字符串转为java对象
		JSONObject obj = JSONObject.parseObject(jsonStr);
		// 获取Object中的orderNumber
		String orderNumber = "";
		if (obj.containsKey("orderNumber")) {
			if (null != obj.getString("orderNumber")&& !"".equals(obj.getString("orderNumber"))) {
				orderNumber = obj.getString("orderNumber");
			}
		}
		// 获取items
		if (obj.containsKey("items")) {
			JSONArray transitListArray = obj.getJSONArray("items");
			if (transitListArray.size() > 0) {
				for (int i = 0; i < transitListArray.size(); i++) {
					JSONObject jo = transitListArray.getJSONObject(i);
					if (jo.containsKey("childOrderNumber")) {
						if (null != jo.getString("childOrderNumber")&& !"".equals(jo.getString("childOrderNumber"))
								&& jo.getString("childOrderNumber").trim().length() < 4) {
							if (jo.getString("childOrderNumber").trim().length() == 2) {
								jo.put("childOrderNumber", orderNumber+"0"+ jo.getString("childOrderNumber").trim());
							} else {
								jo.put("childOrderNumber", orderNumber+jo.getString("childOrderNumber").trim());
							}
						}
					}
				}
			}

		}
		return obj.toString();
	}

	public static void main(String[] args) {
		Set<String> wordSet = new HashSet<String>();
		wordSet.add("a");
		KeyWordUtil keyWordUtil = new KeyWordUtil(wordSet);
		String text = "江苏省";
		int beginIndex = (text.length()) - 1;
		int wordLength = keyWordUtil.checkWord(text, beginIndex);
		System.out.println(wordLength);
		System.out.println(keyWordUtil.getWords(text));
//		Map<String, Class> map = new HashMap<String, Class>();
//		map.put("items", cn.cf.entity.SubContractSync.class);
//		String jsonStr="{\"covenant\":\" 1,产品标准按企业定等标准执行\\r\\n 2,未尽事项按照执行 \",\"requireAccount\":\"采购商测试\",\"organizationCode\":\"91460600MA5RC30P9Y\",\"contractAmount\":\"15205.7\",\"townName\":\"\",\"contractCount\":\"0.93\",\"cityName\":\"\",\"endTime\":\"2019-02-24\",\"startTime\":\"2019-02-22\",\"contractNo\":\"C201902221452234599799\",\"carrier\":\"\",\"contractSource\":\"B2B创建\",\"plantName\":\"江苏国望高科假捻工厂\",\"contractRate\":\"100\",\"priceType\":\"现金\",\"salesmanNumber\":\"879238\",\"supplementary\":\" 1,贷款于XXXX年XXX日之前到账。\\r\\n 2,货物于XXXX年XX月XX日之前发完\\r\\n 3,合同款到生效 \",\"salesman\":\"陈超\",\"saleDepartment\":\"营销部\",\"days\":2,\"areaName\":\"\",\"items\":[{\"warehouseNumber\":\"666\",\"weight\":\"0.03244\",\"plantName\":\"江苏国望高科假捻工厂\",\"logisticsStepInfoPk\":\"\",\"basicPrice\":\"21210.0\",\"boxes\":1,\"detailNumber\":\"10\",\"logisticsPk\":\"\",\"packageNumber\":\"DTY正常小纸箱包装\",\"contractPrice\":\"21210.0\",\"discount\":\"0.0\",\"level\":\"AA\",\"warehouseType\":\"超存产品\",\"packageFee\":\"0.0\",\"loadFee\":\"10.0\",\"batchNumber\":\"H635\",\"adminFee\":\"10.0\",\"freight\":\"0.0\",\"carrier\":\"\",\"totalPrice\":\"0.0\",\"distinctNumber\":\"M000\"},{\"warehouseNumber\":\"666\",\"weight\":\"0.9\",\"plantName\":\"江苏国望高科假捻工厂\",\"logisticsStepInfoPk\":\"\",\"basicPrice\":\"16110.0\",\"boxes\":1,\"detailNumber\":\"20\",\"logisticsPk\":\"\",\"packageNumber\":\"DTY正常小纸箱包装\",\"contractPrice\":\"16110.0\",\"discount\":\"0.0\",\"level\":\"AA\",\"warehouseType\":\"超存产品\",\"packageFee\":\"0.0\",\"loadFee\":\"10.0\",\"batchNumber\":\"K001\",\"adminFee\":\"10.0\",\"freight\":\"0.0\",\"carrier\":\"\",\"totalPrice\":\"0.0\",\"distinctNumber\":\"M00W\"}],\"saleCompany\":\"江苏盛虹科贸有限公司\",\"logisticsModelPk\":\"3\",\"logisticsModel\":\"客户自提\",\"telephone\":\"18899599112\",\"toAddress\":\"\"}";
//		ContractSync contractSync = JsonUtils.toJSONMapBean(jsonStr, ContractSync.class, map);
//		System.out.println(contractSync);
	}

}
