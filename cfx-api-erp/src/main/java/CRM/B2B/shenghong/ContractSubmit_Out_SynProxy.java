package CRM.B2B.shenghong;

public class ContractSubmit_Out_SynProxy implements CRM.B2B.shenghong.ContractSubmit_Out_Syn {
  private String _endpoint = null;
  private CRM.B2B.shenghong.ContractSubmit_Out_Syn contractSubmit_Out_Syn = null;
  
  public ContractSubmit_Out_SynProxy() {
    _initContractSubmit_Out_SynProxy();
  }
  
  public ContractSubmit_Out_SynProxy(String endpoint) {
    _endpoint = endpoint;
    _initContractSubmit_Out_SynProxy();
  }
  
  private void _initContractSubmit_Out_SynProxy() {
    try {
      contractSubmit_Out_Syn = (new CRM.B2B.shenghong.ContractSubmit_Out_SynServiceLocator()).getHTTPS_Port();
      if (contractSubmit_Out_Syn != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)contractSubmit_Out_Syn)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)contractSubmit_Out_Syn)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (contractSubmit_Out_Syn != null)
      ((javax.xml.rpc.Stub)contractSubmit_Out_Syn)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public CRM.B2B.shenghong.ContractSubmit_Out_Syn getContractSubmit_Out_Syn() {
    if (contractSubmit_Out_Syn == null)
      _initContractSubmit_Out_SynProxy();
    return contractSubmit_Out_Syn;
  }
  
  public org.tempuri.SubmitContractResponse contractSubmit_Out_Syn(org.tempuri.SubmitContract parameters) throws java.rmi.RemoteException{
    if (contractSubmit_Out_Syn == null)
      _initContractSubmit_Out_SynProxy();
    return contractSubmit_Out_Syn.contractSubmit_Out_Syn(parameters);
  }
  
  
}