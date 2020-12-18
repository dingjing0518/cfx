package cn.cf.service.platform.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.cf.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.dao.B2bFinanceRecordsDaoEx;
import cn.cf.dao.B2bLoanNumberDaoEx;
import cn.cf.dto.B2bLoanNumberDto;
import cn.cf.dto.B2bLoanNumberDtoEx;
import cn.cf.model.B2bLoanNumber;
import cn.cf.service.platform.B2bLoanNumberService;

@Service
public class B2bLoanNumberServiceImpl implements B2bLoanNumberService {
	
	@Autowired
	private B2bLoanNumberDaoEx b2bLoanNumberDao;
	
	@Autowired
	private B2bFinanceRecordsDaoEx financeRecordsDaoEx;
	
	@Override
	public B2bLoanNumberDto getB2bLoanNumberDto(String orderNumber) {
		return b2bLoanNumberDao.getByOrderNumber(orderNumber);
	}

	@Override
	public List<B2bLoanNumberDto> searchB2bLoanNumberDtoList() {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("loanStatus", 2);
		return b2bLoanNumberDao.searchList(map);
	}

	@Override
	public List<B2bLoanNumberDtoEx> searchB2bRepaymentDtoList(Map<String,String> map) {
		// TODO Auto-generated method stub
		return b2bLoanNumberDao.searchLoanNumberDtoByRepayment(map);
	}

//	@Override
//	public void update(B2bLoanNumber loanNumber) {
//		b2bLoanNumberDao.update(loanNumber);
//		
//	}

	@Override
	public void updateBackCancalOrder(String orderNumber) {
		//将交易记录设置成处理失败
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("orderNumber", orderNumber);
				map.put("status", 2);
				financeRecordsDaoEx.updateStatus(map);
				//将订单状态设置成为申请失败
				B2bLoanNumber loanNumber = new B2bLoanNumber();
				loanNumber.setOrderNumber(orderNumber);
				loanNumber.setLoanStatus(4);
				b2bLoanNumberDao.updateByOrderNumber(loanNumber);
	}

	@Override
	public Double searchSumLoan(String purchaserPk, int goodsType) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("purchaserPk", purchaserPk);
		map.put("economicsGoodsType", goodsType);
		return b2bLoanNumberDao.searchSumLoan(map);
	}

	@Override
	public PageModel<B2bLoanNumberDto> searchListByPage(Map<String, Object> map) {
		PageModel<B2bLoanNumberDto> pm = new PageModel<>();
		map.put("orderName", "loanStartTime");
		map.put("orderType", "desc");
		List<B2bLoanNumberDto> list =  b2bLoanNumberDao.searchGrid(map);
		int count =  b2bLoanNumberDao.searchGridCount(map);
		pm.setDataList(list);
		pm.setTotalCount(count);
		pm.setStartIndex(Integer.parseInt(map.get("start").toString()));
		pm.setPageSize(Integer.parseInt(map.get("limit").toString()));
		return pm;
	}

}
