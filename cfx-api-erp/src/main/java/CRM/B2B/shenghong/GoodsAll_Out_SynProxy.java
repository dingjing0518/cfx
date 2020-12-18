package CRM.B2B.shenghong;

public class GoodsAll_Out_SynProxy implements CRM.B2B.shenghong.GoodsAll_Out_Syn {
  private String _endpoint = null;
  private CRM.B2B.shenghong.GoodsAll_Out_Syn goodsAll_Out_Syn = null;
  
  public GoodsAll_Out_SynProxy() {
    _initGoodsAll_Out_SynProxy();
  }
  
  public GoodsAll_Out_SynProxy(String endpoint) {
    _endpoint = endpoint;
    _initGoodsAll_Out_SynProxy();
  }
  
  private void _initGoodsAll_Out_SynProxy() {
    try {
      goodsAll_Out_Syn = (new CRM.B2B.shenghong.GoodsAll_Out_SynServiceLocator()).getHTTPS_Port();
      if (goodsAll_Out_Syn != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)goodsAll_Out_Syn)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)goodsAll_Out_Syn)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (goodsAll_Out_Syn != null)
      ((javax.xml.rpc.Stub)goodsAll_Out_Syn)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public CRM.B2B.shenghong.GoodsAll_Out_Syn getGoodsAll_Out_Syn() {
    if (goodsAll_Out_Syn == null)
      _initGoodsAll_Out_SynProxy();
    return goodsAll_Out_Syn;
  }
  
  public org.tempuri.GoodsAllResponse goodsAll_Out_Syn(org.tempuri.GoodsAll parameters) throws java.rmi.RemoteException{
    if (goodsAll_Out_Syn == null)
      _initGoodsAll_Out_SynProxy();
    return goodsAll_Out_Syn.goodsAll_Out_Syn(parameters);
  }
  
  
}