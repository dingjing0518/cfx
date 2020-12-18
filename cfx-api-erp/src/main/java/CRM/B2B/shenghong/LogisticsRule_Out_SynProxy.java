package CRM.B2B.shenghong;

public class LogisticsRule_Out_SynProxy implements CRM.B2B.shenghong.LogisticsRule_Out_Syn {
  private String _endpoint = null;
  private CRM.B2B.shenghong.LogisticsRule_Out_Syn logisticsRule_Out_Syn = null;
  
  public LogisticsRule_Out_SynProxy() {
    _initLogisticsRule_Out_SynProxy();
  }
  
  public LogisticsRule_Out_SynProxy(String endpoint) {
    _endpoint = endpoint;
    _initLogisticsRule_Out_SynProxy();
  }
  
  private void _initLogisticsRule_Out_SynProxy() {
    try {
      logisticsRule_Out_Syn = (new CRM.B2B.shenghong.LogisticsRule_Out_SynServiceLocator()).getHTTPS_Port();
      if (logisticsRule_Out_Syn != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)logisticsRule_Out_Syn)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)logisticsRule_Out_Syn)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (logisticsRule_Out_Syn != null)
      ((javax.xml.rpc.Stub)logisticsRule_Out_Syn)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public CRM.B2B.shenghong.LogisticsRule_Out_Syn getLogisticsRule_Out_Syn() {
    if (logisticsRule_Out_Syn == null)
      _initLogisticsRule_Out_SynProxy();
    return logisticsRule_Out_Syn;
  }
  
  public org.tempuri.LogisticsRuleResponse logisticsRule_Out_Syn(org.tempuri.LogisticsRule parameters) throws java.rmi.RemoteException{
    if (logisticsRule_Out_Syn == null)
      _initLogisticsRule_Out_SynProxy();
    return logisticsRule_Out_Syn.logisticsRule_Out_Syn(parameters);
  }
  
  
}