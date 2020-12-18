package cn.cf.common.creditpay.gongshang;

import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import cn.cf.dto.B2bCompanyDto;
import cn.cf.dto.B2bLoanNumberDto;
import cn.cf.dto.SysCompanyBankcardDto;
import cn.cf.entity.B2bOrderDtoMa;
import cn.cf.entity.B2bRepaymentRecord;
import cn.cf.entry.OrderGoodsDtoEx;
import cn.cf.property.PropertyConfig;
import cn.cf.util.DateUtil;
import cn.cf.util.KeyUtils;

public class XmlUtils {
	
	private String getPub(String name){
		String xml = "<pub>"+
				"<TransCode>"+name+"</TransCode>"+
				"<MerId>"+PropertyConfig.getProperty("gs_merid")+"</MerId>"+
				"<TranDate>"+DateUtil.formatYearMonthDay(new Date())+"</TranDate>"+
				"<TranTime>"+DateUtil.getTimes()+"</TranTime>"+
				"<TransNo>"+KeyUtils.getFlowNumber()+"</TransNo>"+
			"</pub>";
		return xml;
	}
	/**
	 * 查询客户授信额度
	 * @param company
	 * @return
	 */
	public String  usersxQuery(B2bCompanyDto company){
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version='1.0' encoding = 'UTF-8'?>");
		sb.append("<GYJ>");
		sb.append(getPub("USERSXQUERY"));
		sb.append("<in>");
		sb.append("<CompanyId></CompanyId>");
		sb.append("<CustomerId>"+company.getOrganizationCode()+"</CustomerId>");
		sb.append("<LoanType>SJD</LoanType>");
		sb.append("</in>");
		sb.append("</GYJ>");
		return sb.toString();
	}
	/**
	 * 查询放款记录
	 * @param loanNumber
	 * @return
	 */
	public String loanList(B2bLoanNumberDto loanNumber){
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version='1.0' encoding = 'UTF-8'?>");
		sb.append("<GYJ>");
		sb.append(getPub("LOANLIST"));
		sb.append("<in>");
		sb.append("<CompanyId></CompanyId>");
		sb.append("<CustomerId>"+loanNumber.getOrganizationCode()+"</CustomerId>");
		sb.append("<BillNo></BillNo>");
		sb.append("<OrderNo>"+loanNumber.getOrderNumber()+"</OrderNo>");
		sb.append("<QueryStartTime></QueryStartTime>");
		sb.append("<QueryEndTime></QueryEndTime>");
		sb.append("<Page>1</Page>");
		sb.append("<PageCount>10</PageCount>");
		sb.append("</in>");
		sb.append("</GYJ>");
		return sb.toString();
	}
	/**
	 * 
	 * @param loanNumber
	 * @return
	 */
	public String resultQuery(B2bLoanNumberDto loanNumber){
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version='1.0' encoding = 'UTF-8'?>");
		sb.append("<GYJ>");
		sb.append(getPub("RESULTQUERY"));
		sb.append("<in>");
		sb.append("<CompanyId></CompanyId>");
		sb.append("<CustomerId>"+loanNumber.getOrganizationCode()+"</CustomerId>");
		sb.append("<BillType>1</BillType>");
		sb.append("<OrderNo>"+loanNumber.getOrderNumber()+"</OrderNo>");
		sb.append("<QueryStartTime>"+DateUtil.formatDateAndTime(loanNumber.getLoanStartTime())+"</QueryStartTime>");
		sb.append("<QueryEndTime>"+DateUtil.formatDateAndTime(new Date())+"</QueryEndTime>");
		sb.append("<Page>1</Page>");
		sb.append("<PageCount>20</PageCount>");
		sb.append("</in>");
		sb.append("</GYJ>");
		return sb.toString();
	}
	/**
	 * 订单上传/取消
	 * @param order
	 * @param goodsList
	 * @param company
	 * @param card
	 * @return
	 */
	public String orderUpload(B2bOrderDtoMa order,List<OrderGoodsDtoEx> goodsList,B2bCompanyDto company,
			SysCompanyBankcardDto card,Integer orderType,Date endDate){
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version='1.0' encoding = 'UTF-8'?>");
		sb.append("<GYJ>");
		sb.append(getPub("ORDERUPLOAD"));
		sb.append("<in>");
		sb.append("<rd>");
		sb.append("<OrderNo>"+order.getOrderNumber()+"</OrderNo>");
		sb.append("<CompanyId></CompanyId>");
		sb.append("<CompanyName></CompanyName>");
		sb.append("<CustomerId>"+company.getOrganizationCode()+"</CustomerId>");
		sb.append("<CustomerName>"+order.getPurchaser().getPurchaserName()+"</CustomerName>");
		sb.append("<OrderAmount>"+order.getActualAmount()+"</OrderAmount>");
		sb.append("<OrderStatus>1</OrderStatus>");
		sb.append("<OrderTime>"+DateUtil.formatDateAndTime(order.getInsertTime())+"</OrderTime>");
		sb.append("<OrderType>销售订单</OrderType>");
		sb.append("<OperateType>"+orderType+"</OperateType>");
		
		sb.append("<LoanFlag>1</LoanFlag>");//融资是否指定单据标志 0-不指定 1-指定
		sb.append("<LoanType>SJD</LoanType>");//融资类型
		sb.append("<LoanAmount>"+order.getActualAmount()+"</LoanAmount>");//融资金额 
		sb.append("<ExpiredDate>"+DateUtil.formatYearMonthDay(endDate)+"</ExpiredDate>");//融资到期日期yyyyMMdd 
		if(null != card){
			sb.append("<PayInfo>");
			sb.append("<PayAccNo>"+card.getBankAccount()+"</PayAccNo>");
			sb.append("<PayAccName>"+order.getSupplier().getSupplierName()+"</PayAccName>");
			sb.append("<PayAmount>"+order.getActualAmount()+"</PayAmount>");
			sb.append("<Memo></Memo>");
			sb.append("</PayInfo>");
			//----如有多个收款账户
			//<PayInfo>...</PayInfo><PayInfo>...</PayInfo>
		}
		if(null !=goodsList && goodsList.size()>0){
			for (OrderGoodsDtoEx dto : goodsList) {
				sb.append("<OrderProduct>");
				sb.append("<ProductId></ProductId>");
				sb.append("<OrderEntry></OrderEntry>");
				if(null != dto.getProductName() && !"".equals(dto.getProductName())){
					sb.append("<ProductName>"+dto.getProductName()+"</ProductName>");
					sb.append("<ProductUnit>吨</ProductUnit>");
				}else{
					sb.append("<ProductName>"+dto.getRawMaterialName()+"</ProductName>");
					sb.append("<ProductUnit>千克</ProductUnit>");
				}
				sb.append("<ProductType>"+dto.getGoodsType()+"</ProductType>");
				sb.append("<ProductSize>"+dto.getSpecifications()+"</ProductSize>");
				sb.append("<ProductAddress></ProductAddress>");
				sb.append("<ProductBatch>"+dto.getBatchNumber()+"</ProductBatch>");
				sb.append("<ProductCount>"+dto.getAfterWeight()+"</ProductCount>");
				sb.append("<ProductPrice>"+dto.getOriginalPrice()+"</ProductPrice>");
				sb.append("<RealPrice>"+dto.getPresentPrice()+"</RealPrice>");
				sb.append("<ProductNote></ProductNote>");
				sb.append("</OrderProduct>");
			}
		}
		sb.append("</rd>");
		sb.append("</in>");
		sb.append("</GYJ>");
		return sb.toString();
	}
	
	/**
	 * 代扣服务费提交
	 * @param loanNumber
	 * @return
	 */
	public String skblOrderPay(B2bRepaymentRecord record){
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version='1.0' encoding = 'UTF-8'?>");
		sb.append("<GYJ>");
		sb.append("<transtime>"+DateUtil.formatYearMonthDayHms(new Date())+"</transtime>");
		sb.append("<orderno>"+record.getId()+"</orderno>");
		sb.append("<sellerid></sellerid>");
		sb.append("<sellername>"+PropertyConfig.getProperty("gs_mername")+"</sellername>");
		sb.append("<customerid>"+record.getOrganizationCode()+"</customerid>");
		sb.append("<customername>"+record.getCompanyName()+"</customername>");
		sb.append("<orderamount>"+record.getServiceCharge()+"</orderamount>");
		sb.append("<noticeurl></noticeurl>");
		sb.append("<purpose></purpose>");
		sb.append("<remark1>"+record.getIouNumber()+"</remark1>");
		sb.append("<remark2></remark2>");
		sb.append("<remark3></remark3>");
		sb.append("<deducttype>1</deducttype>");
		sb.append("<projectid></projectid>");
		sb.append("<agentno></agentno>");
		sb.append("<mobile></mobile>");
		sb.append("</GYJ>");
		return sb.toString();
	}
	
	/**
	 * 代扣结果查询
	 * @param loanNumber
	 * @return
	 */
	public String skblOrderQry(B2bRepaymentRecord record){
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version='1.0' encoding = 'UTF-8'?>");
		sb.append("<GYJ>");
		sb.append(getPub("SKBLORDERQRY"));
		sb.append("<transtime>"+DateUtil.formatYearMonthDayHms(new Date())+"</transtime>");
		sb.append("<orderno>"+record.getId()+"</orderno>");
		sb.append("<serialno>"+KeyUtils.getFlowNumber()+"</serialno>");
		sb.append("<sellerid></sellerid>");
		sb.append("</GYJ>");
		return sb.toString();
	}
	
	/**
	 * 查询客户授信额度
	 * @param company
	 * @return
	 */
	public String  loanPage(B2bLoanNumberDto b2bLoanNumberDto,String bankCard){
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version='1.0' encoding = 'UTF-8'?>");
		sb.append("<GYJ>");
		sb.append(getPub("LOANPAGE"));
		sb.append("<in>");
		sb.append("<CustomerId>"+b2bLoanNumberDto.getOrganizationCode()+"</CustomerId>"); 
		sb.append("<OrderFlag>1</OrderFlag>");//融资是否指定单据标志 0-不指定 1-指定
		sb.append("<OrderNos>"+b2bLoanNumberDto.getOrderNumber()+"</OrderNos>"); 
		sb.append("<LoanType>SJD</LoanType>");//贷款品种 WD-委贷 SJD-网上小额贷款 
//		sb.append("<PayAmount>"+b2bLoanNumberDto.getLoanAmount()+"</PayAmount>");//单据支付金额 
		sb.append("<LoanAmount>"+b2bLoanNumberDto.getLoanAmount()+"</LoanAmount>");//融资金额 
		sb.append("<ExpiredDate>"+DateUtil.formatYearMonthDay(b2bLoanNumberDto.getLoanEndTime())+"</ExpiredDate>");//融资到期日期yyyyMMdd 
		sb.append("<PayList>");
		sb.append("<PayInfo>");
		sb.append("<PayAccNo>"+bankCard+"</PayAccNo>");
		sb.append("<PayAccName>"+b2bLoanNumberDto.getSupplierName()+"</PayAccName>");
		sb.append("<PayAmount>"+b2bLoanNumberDto.getLoanAmount()+"</PayAmount>");
		sb.append("<Memo></Memo>");
		sb.append("</PayInfo>");
		sb.append("</PayList>");
		sb.append("<Rsv1></Rsv1>"); 
		sb.append("<Rsv2></Rsv2>"); 
		sb.append("<Rsv3></Rsv3>"); 
		sb.append("</in>");
		sb.append("</GYJ>");
		return sb.toString();
	}
	
	public  JSONObject getJsonByXml(String xml) throws JSONException{
		JSONObject xmlJSONObj = null;
		try {
			xmlJSONObj = XML.toJSONObject(xml);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		if(!xmlJSONObj.isNull("GYJ")){
			xmlJSONObj = xmlJSONObj.getJSONObject("GYJ").getJSONObject("out");
		}
       return xmlJSONObj;
	}
	
	public  JSONObject getJsonByXmlAnother(String xml) throws JSONException{
		JSONObject xmlJSONObj = null;
		try {
			xmlJSONObj = XML.toJSONObject(xml);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		if(!xmlJSONObj.isNull("GYJANS")){
			xmlJSONObj = xmlJSONObj.getJSONObject("GYJANS");
		}
       return xmlJSONObj;
	}
}
