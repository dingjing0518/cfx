package cn.cf.service;

import cn.cf.model.B2bAddress;

public interface B2bAddressService {

	B2bAddress getAddressByDetails(B2bAddress address);
	
	void insertOrUpdate(B2bAddress address);
	
}
