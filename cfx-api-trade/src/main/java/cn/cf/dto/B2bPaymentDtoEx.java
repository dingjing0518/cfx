package cn.cf.dto;

import java.util.List;

import cn.cf.entity.B2bBillCusgoodsDtoMa;
import cn.cf.entity.B2bCreditGoodsDtoMa;

public class B2bPaymentDtoEx extends B2bPaymentDto{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<B2bCreditGoodsDtoMa> creditGoods;
	
	private List<B2bBillCusgoodsDtoMa> billGoods;

	public List<B2bCreditGoodsDtoMa> getCreditGoods() {
		return creditGoods;
	}

	public void setCreditGoods(List<B2bCreditGoodsDtoMa> creditGoods) {
		this.creditGoods = creditGoods;
	}

	public List<B2bBillCusgoodsDtoMa> getBillGoods() {
		return billGoods;
	}

	public void setBillGoods(List<B2bBillCusgoodsDtoMa> billGoods) {
		this.billGoods = billGoods;
	}

	

	

	
	
	
}
