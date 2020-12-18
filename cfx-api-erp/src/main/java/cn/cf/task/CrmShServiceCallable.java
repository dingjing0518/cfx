package cn.cf.task;

import java.net.URL;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tempuri.OrderSync;
import org.tempuri.OrderSyncResponse;

import CRM.B2B.shenghong.OrderSync_Out_SynBindingStub;
import CRM.B2B.shenghong.OrderSync_Out_SynServiceLocator;

public class CrmShServiceCallable implements Callable<Object> {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private String jsonData;

	public CrmShServiceCallable(String jsonData) {
		 this.jsonData = jsonData;
	}

	@Override
	public Object call() throws Exception {
		String data = null;
		try {
			OrderSync orderSyncW = new OrderSync(jsonData);
			URL realUrl = new URL(
					new OrderSync_Out_SynServiceLocator()
							.getHTTPS_PortAddress());
			org.apache.axis.client.Service service = new org.apache.axis.client.Service();
			OrderSync_Out_SynBindingStub orderSync_Out_SynBindingStub = new OrderSync_Out_SynBindingStub(
					realUrl, service);
			OrderSyncResponse rsp = orderSync_Out_SynBindingStub
					.orderSync_Out_Syn(orderSyncW);
			data = rsp.getOrderSyncResult();
		} catch (Exception e) {
			logger.error("syncOrderByShenghong", e);
			data="{\"status\":\"N\",\"message\":\""+e+"\"}";
		}
		return data;
	}

}
