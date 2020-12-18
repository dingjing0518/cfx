package CRM.B2B.shenghong;

public class GoodsVisable_Out_SynProxy implements CRM.B2B.shenghong.GoodsVisable_Out_Syn {
  private String _endpoint = null;
  private CRM.B2B.shenghong.GoodsVisable_Out_Syn goodsVisable_Out_Syn = null;
  
  public GoodsVisable_Out_SynProxy() {
    _initGoodsVisable_Out_SynProxy();
  }
  
  public GoodsVisable_Out_SynProxy(String endpoint) {
    _endpoint = endpoint;
    _initGoodsVisable_Out_SynProxy();
  }
  
  private void _initGoodsVisable_Out_SynProxy() {
    try {
      goodsVisable_Out_Syn = (new CRM.B2B.shenghong.GoodsVisable_Out_SynServiceLocator()).getHTTPS_Port();
      if (goodsVisable_Out_Syn != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)goodsVisable_Out_Syn)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)goodsVisable_Out_Syn)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (goodsVisable_Out_Syn != null)
      ((javax.xml.rpc.Stub)goodsVisable_Out_Syn)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public CRM.B2B.shenghong.GoodsVisable_Out_Syn getGoodsVisable_Out_Syn() {
    if (goodsVisable_Out_Syn == null)
      _initGoodsVisable_Out_SynProxy();
    return goodsVisable_Out_Syn;
  }
  
  public org.tempuri.GoodsVisableResponse goodsVisable_Out_Syn(org.tempuri.GoodsVisable parameters) throws java.rmi.RemoteException{
    if (goodsVisable_Out_Syn == null)
      _initGoodsVisable_Out_SynProxy();
    return goodsVisable_Out_Syn.goodsVisable_Out_Syn(parameters);
  }
  
  
}