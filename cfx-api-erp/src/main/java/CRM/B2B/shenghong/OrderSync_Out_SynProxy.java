package CRM.B2B.shenghong;

public class OrderSync_Out_SynProxy implements CRM.B2B.shenghong.OrderSync_Out_Syn {
  private String _endpoint = null;
  private CRM.B2B.shenghong.OrderSync_Out_Syn orderSync_Out_Syn = null;
  
  public OrderSync_Out_SynProxy() {
    _initOrderSync_Out_SynProxy();
  }
  
  public OrderSync_Out_SynProxy(String endpoint) {
    _endpoint = endpoint;
    _initOrderSync_Out_SynProxy();
  }
  
  private void _initOrderSync_Out_SynProxy() {
    try {
      orderSync_Out_Syn = (new CRM.B2B.shenghong.OrderSync_Out_SynServiceLocator()).getHTTPS_Port();
      if (orderSync_Out_Syn != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)orderSync_Out_Syn)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)orderSync_Out_Syn)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (orderSync_Out_Syn != null)
      ((javax.xml.rpc.Stub)orderSync_Out_Syn)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public CRM.B2B.shenghong.OrderSync_Out_Syn getOrderSync_Out_Syn() {
    if (orderSync_Out_Syn == null)
      _initOrderSync_Out_SynProxy();
    return orderSync_Out_Syn;
  }
  
  public org.tempuri.OrderSyncResponse orderSync_Out_Syn(org.tempuri.OrderSync parameters) throws java.rmi.RemoteException{
    if (orderSync_Out_Syn == null)
      _initOrderSync_Out_SynProxy();
    return orderSync_Out_Syn.orderSync_Out_Syn(parameters);
  }
  
  
}