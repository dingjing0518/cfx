package cn.cf.service;

import cn.cf.entry.MangoInterfaceInfo;
import cn.cf.entry.MangoOperationInfo;

public interface MangoService {

	void insertMangoOperationInfo(MangoOperationInfo mangoOperationInfo);
	
	String insertMangoInterfaceInfo(MangoInterfaceInfo mangoInterfaceInfo);
}
