package cn.cf.dto;

public class B2bEconomicsCustomerGoodsExtDto extends B2bEconomicsCustomerGoodsDto  implements Comparable<B2bEconomicsCustomerGoodsExtDto>{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public int compareTo(B2bEconomicsCustomerGoodsExtDto o) {
		return this.getBankPk().compareTo(o.getBankPk());
	}


}
