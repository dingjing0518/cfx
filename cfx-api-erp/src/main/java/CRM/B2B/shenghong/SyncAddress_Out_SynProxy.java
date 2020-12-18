package CRM.B2B.shenghong;

public class SyncAddress_Out_SynProxy implements CRM.B2B.shenghong.SyncAddress_Out_Syn {
  private String _endpoint = null;
  private CRM.B2B.shenghong.SyncAddress_Out_Syn syncAddress_Out_Syn = null;
  
  public SyncAddress_Out_SynProxy() {
    _initSyncAddress_Out_SynProxy();
  }
  
  public SyncAddress_Out_SynProxy(String endpoint) {
    _endpoint = endpoint;
    _initSyncAddress_Out_SynProxy();
  }
  
  private void _initSyncAddress_Out_SynProxy() {
    try {
      syncAddress_Out_Syn = (new CRM.B2B.shenghong.SyncAddress_Out_SynServiceLocator()).getHTTPS_Port();
      if (syncAddress_Out_Syn != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)syncAddress_Out_Syn)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)syncAddress_Out_Syn)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (syncAddress_Out_Syn != null)
      ((javax.xml.rpc.Stub)syncAddress_Out_Syn)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public CRM.B2B.shenghong.SyncAddress_Out_Syn getSyncAddress_Out_Syn() {
    if (syncAddress_Out_Syn == null)
      _initSyncAddress_Out_SynProxy();
    return syncAddress_Out_Syn;
  }
  
  public org.tempuri.SyncAddressResponse syncAddress_Out_Syn(org.tempuri.SyncAddress parameters) throws java.rmi.RemoteException{
    if (syncAddress_Out_Syn == null)
      _initSyncAddress_Out_SynProxy();
    return syncAddress_Out_Syn.syncAddress_Out_Syn(parameters);
  }
  
  
}