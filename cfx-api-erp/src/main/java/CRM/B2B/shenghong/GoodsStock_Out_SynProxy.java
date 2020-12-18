package CRM.B2B.shenghong;

public class GoodsStock_Out_SynProxy implements CRM.B2B.shenghong.GoodsStock_Out_Syn {
  private String _endpoint = null;
  private CRM.B2B.shenghong.GoodsStock_Out_Syn goodsStock_Out_Syn = null;
  
  public GoodsStock_Out_SynProxy() {
    _initGoodsStock_Out_SynProxy();
  }
  
  public GoodsStock_Out_SynProxy(String endpoint) {
    _endpoint = endpoint;
    _initGoodsStock_Out_SynProxy();
  }
  
  private void _initGoodsStock_Out_SynProxy() {
    try {
      goodsStock_Out_Syn = (new CRM.B2B.shenghong.GoodsStock_Out_SynServiceLocator()).getHTTPS_Port();
      if (goodsStock_Out_Syn != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)goodsStock_Out_Syn)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)goodsStock_Out_Syn)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (goodsStock_Out_Syn != null)
      ((javax.xml.rpc.Stub)goodsStock_Out_Syn)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public CRM.B2B.shenghong.GoodsStock_Out_Syn getGoodsStock_Out_Syn() {
    if (goodsStock_Out_Syn == null)
      _initGoodsStock_Out_SynProxy();
    return goodsStock_Out_Syn;
  }
  
  public org.tempuri.GoodsStockResponse goodsStock_Out_Syn(org.tempuri.GoodsStock parameters) throws java.rmi.RemoteException{
    if (goodsStock_Out_Syn == null)
      _initGoodsStock_Out_SynProxy();
    return goodsStock_Out_Syn.goodsStock_Out_Syn(parameters);
  }
  
  
}