package CRM.B2B.shenghong;

public class BindGoodsAll_Out_SynProxy implements CRM.B2B.shenghong.BindGoodsAll_Out_Syn {
  private String _endpoint = null;
  private CRM.B2B.shenghong.BindGoodsAll_Out_Syn bindGoodsAll_Out_Syn = null;
  
  public BindGoodsAll_Out_SynProxy() {
    _initBindGoodsAll_Out_SynProxy();
  }
  
  public BindGoodsAll_Out_SynProxy(String endpoint) {
    _endpoint = endpoint;
    _initBindGoodsAll_Out_SynProxy();
  }
  
  private void _initBindGoodsAll_Out_SynProxy() {
    try {
      bindGoodsAll_Out_Syn = (new CRM.B2B.shenghong.BindGoodsAll_Out_SynServiceLocator()).getHTTPS_Port();
      if (bindGoodsAll_Out_Syn != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)bindGoodsAll_Out_Syn)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)bindGoodsAll_Out_Syn)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (bindGoodsAll_Out_Syn != null)
      ((javax.xml.rpc.Stub)bindGoodsAll_Out_Syn)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public CRM.B2B.shenghong.BindGoodsAll_Out_Syn getBindGoodsAll_Out_Syn() {
    if (bindGoodsAll_Out_Syn == null)
      _initBindGoodsAll_Out_SynProxy();
    return bindGoodsAll_Out_Syn;
  }
  
  public org.tempuri.BindGoodsAllResponse bindGoodsAll_Out_Syn(org.tempuri.BindGoodsAll parameters) throws java.rmi.RemoteException{
    if (bindGoodsAll_Out_Syn == null)
      _initBindGoodsAll_Out_SynProxy();
    return bindGoodsAll_Out_Syn.bindGoodsAll_Out_Syn(parameters);
  }
  
  
}