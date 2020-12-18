package cn.cf.service.enconmics.impl;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import cn.cf.common.utils.CommonUtil;
import cn.cf.dto.B2bEconomicsBankDto;
import cn.cf.entity.BankBalanceEntity;
import cn.cf.entity.EconProductbalanceReport;
import cn.cf.json.JsonUtils;
import cn.cf.service.enconmics.EconProductBalanceService;
import cn.cf.util.ArithUtil;

@Service
public class EconProductBalanceServiceImpl implements EconProductBalanceService {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public String getEveryDayProductBalance(List<B2bEconomicsBankDto> banklist, String start, String end) {
		String content = "";
		List<EconProductbalanceReport> list = getMonogoList(start, end);
		if (list != null && list.size() > 0) {
			// 求所有天数的数值和
			Map<String, BigDecimal> map = countSumAmount(list);
			// 求删选日期相差的天数
			int day = countDays(start, end);
			if (day > 0) {
				content = content + "<td>日均</td>";
				for (B2bEconomicsBankDto dto : banklist) {
					if (map.containsKey(dto.getPk() + "_bt")) {
						content = content + " <td>" + ArithUtil.divBigDecimal(map.get(dto.getPk() + "_bt"), day)
								.setScale(2, BigDecimal.ROUND_HALF_UP) + "</td>";
					} else {
						content = content + " <td>" + 0.00 + "</td>";
					}

					if (map.containsKey(dto.getPk() + "_d")) {
						content = content + " <td>" + ArithUtil.divBigDecimal(map.get(dto.getPk() + "_d"), day)
								.setScale(2, BigDecimal.ROUND_HALF_UP) + "</td>";
					} else {
						content = content + " <td>" + 0.00 + "</td>";
					}
				}
			}
		}
		if (content.equals("")) {
			content = content + "<td>日均</td>";
			for (B2bEconomicsBankDto dto : banklist) {
				content = content + " <td>" + 0.00 + "</td>";
				content = content + " <td>" + 0.00 + "</td>";
			}
		}

		return content;
	}

	/**
	 * 求删选日期相差的天数
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	private int countDays(String start, String end) {
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		int day = 0;
		try {
			day = CommonUtil.getInterval(df.parse(start), df.parse(end));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (end.equals(CommonUtil.today())) {
			return day;
		}else{
			return day+1;
		}
		
	}

	/**
	 * 求所有天数的数值和
	 * 
	 * @param list
	 * @return
	 */
	private Map<String, BigDecimal> countSumAmount(List<EconProductbalanceReport> list) {
		Map<String, BigDecimal> map = new HashMap<String, BigDecimal>();
		for (EconProductbalanceReport report : list) {
			if (report.getBankList() != null && report.getBankList().size() > 0) {
				for (BankBalanceEntity entity : report.getBankList()) {

					if (map.containsKey(entity.getBankPk() + "_bt")) {
						map.put(entity.getBankPk() + "_bt",
								ArithUtil.addBigDecimal(entity.getBtAmount(), map.get(entity.getBankPk() + "_bt")));
					} else {
						map.put(entity.getBankPk() + "_bt", entity.getBtAmount());
					}

					if (map.containsKey(entity.getBankPk() + "_d")) {
						map.put(entity.getBankPk() + "_d",
								ArithUtil.addBigDecimal(entity.getdAmount(), map.get(entity.getBankPk() + "_d")));
					} else {
						map.put(entity.getBankPk() + "_d", entity.getdAmount());
					}
				}

			}
			;
		}
		return map;
	}

	
	private List<EconProductbalanceReport> getMonogoList(String startTime, String endTime) {
		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("date").lte(endTime), Criteria.where("date").gte(startTime));
		Query query = new Query(criteria);
		List<EconProductbalanceReport> list = mongoTemplate.find(query.with(new Sort(new Order(Direction.ASC, "date"))),
				EconProductbalanceReport.class);
		return list;
	}

}
