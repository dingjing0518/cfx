package cn.cf.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import cn.cf.dto.B2bGoodsDtoEx;
import cn.cf.entity.Sessions;



public interface FileOperationService {

	void exportGoods(List<B2bGoodsDtoEx> list,HttpServletRequest request,HttpServletResponse response,Sessions sessions);
	
}
