package CRM.B2B.shenghong;

public class GoodsPrice_Out_SynProxy implements CRM.B2B.shenghong.GoodsPrice_Out_Syn {
  private String _endpoint = null;
  private CRM.B2B.shenghong.GoodsPrice_Out_Syn goodsPrice_Out_Syn = null;
  
  public GoodsPrice_Out_SynProxy() {
    _initGoodsPrice_Out_SynProxy();
  }
  
  public GoodsPrice_Out_SynProxy(String endpoint) {
    _endpoint = endpoint;
    _initGoodsPrice_Out_SynProxy();
  }
  
  private void _initGoodsPrice_Out_SynProxy() {
    try {
      goodsPrice_Out_Syn = (new CRM.B2B.shenghong.GoodsPrice_Out_SynServiceLocator()).getHTTPS_Port();
      if (goodsPrice_Out_Syn != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)goodsPrice_Out_Syn)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)goodsPrice_Out_Syn)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (goodsPrice_Out_Syn != null)
      ((javax.xml.rpc.Stub)goodsPrice_Out_Syn)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public CRM.B2B.shenghong.GoodsPrice_Out_Syn getGoodsPrice_Out_Syn() {
    if (goodsPrice_Out_Syn == null)
      _initGoodsPrice_Out_SynProxy();
    return goodsPrice_Out_Syn;
  }
  
  public org.tempuri.GoodsPriceResponse goodsPrice_Out_Syn(org.tempuri.GoodsPrice parameters) throws java.rmi.RemoteException{
    if (goodsPrice_Out_Syn == null)
      _initGoodsPrice_Out_SynProxy();
    return goodsPrice_Out_Syn.goodsPrice_Out_Syn(parameters);
  }
  
  
}