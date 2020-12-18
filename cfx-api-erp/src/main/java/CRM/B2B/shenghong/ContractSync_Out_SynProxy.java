package CRM.B2B.shenghong;

public class ContractSync_Out_SynProxy implements CRM.B2B.shenghong.ContractSync_Out_Syn {
  private String _endpoint = null;
  private CRM.B2B.shenghong.ContractSync_Out_Syn contractSync_Out_Syn = null;
  
  public ContractSync_Out_SynProxy() {
    _initContractSync_Out_SynProxy();
  }
  
  public ContractSync_Out_SynProxy(String endpoint) {
    _endpoint = endpoint;
    _initContractSync_Out_SynProxy();
  }
  
  private void _initContractSync_Out_SynProxy() {
    try {
      contractSync_Out_Syn = (new CRM.B2B.shenghong.ContractSync_Out_SynServiceLocator()).getHTTPS_Port();
      if (contractSync_Out_Syn != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)contractSync_Out_Syn)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)contractSync_Out_Syn)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (contractSync_Out_Syn != null)
      ((javax.xml.rpc.Stub)contractSync_Out_Syn)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public CRM.B2B.shenghong.ContractSync_Out_Syn getContractSync_Out_Syn() {
    if (contractSync_Out_Syn == null)
      _initContractSync_Out_SynProxy();
    return contractSync_Out_Syn;
  }
  
  public org.tempuri.ContractSyncResponse contractSync_Out_Syn(org.tempuri.ContractSync parameters) throws java.rmi.RemoteException{
    if (contractSync_Out_Syn == null)
      _initContractSync_Out_SynProxy();
    return contractSync_Out_Syn.contractSync_Out_Syn(parameters);
  }
  
  
}