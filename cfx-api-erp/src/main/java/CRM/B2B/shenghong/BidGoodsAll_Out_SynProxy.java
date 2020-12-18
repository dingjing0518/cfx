package CRM.B2B.shenghong;

public class BidGoodsAll_Out_SynProxy implements CRM.B2B.shenghong.BidGoodsAll_Out_Syn {
  private String _endpoint = null;
  private CRM.B2B.shenghong.BidGoodsAll_Out_Syn bidGoodsAll_Out_Syn = null;
  
  public BidGoodsAll_Out_SynProxy() {
    _initBidGoodsAll_Out_SynProxy();
  }
  
  public BidGoodsAll_Out_SynProxy(String endpoint) {
    _endpoint = endpoint;
    _initBidGoodsAll_Out_SynProxy();
  }
  
  private void _initBidGoodsAll_Out_SynProxy() {
    try {
      bidGoodsAll_Out_Syn = (new CRM.B2B.shenghong.BidGoodsAll_Out_SynServiceLocator()).getHTTPS_Port();
      if (bidGoodsAll_Out_Syn != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)bidGoodsAll_Out_Syn)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)bidGoodsAll_Out_Syn)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (bidGoodsAll_Out_Syn != null)
      ((javax.xml.rpc.Stub)bidGoodsAll_Out_Syn)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public CRM.B2B.shenghong.BidGoodsAll_Out_Syn getBidGoodsAll_Out_Syn() {
    if (bidGoodsAll_Out_Syn == null)
      _initBidGoodsAll_Out_SynProxy();
    return bidGoodsAll_Out_Syn;
  }
  
  public org.tempuri.BidGoodsAllResponse bidGoodsAll_Out_Syn(org.tempuri.BidGoodsAll parameters) throws java.rmi.RemoteException{
    if (bidGoodsAll_Out_Syn == null)
      _initBidGoodsAll_Out_SynProxy();
    return bidGoodsAll_Out_Syn.bidGoodsAll_Out_Syn(parameters);
  }
  
  
}