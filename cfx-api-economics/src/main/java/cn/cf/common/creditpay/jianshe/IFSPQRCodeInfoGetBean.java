package cn.cf.common.creditpay.jianshe;

import com.ccb.alibaba.fastjson.JSON;
import com.ccb.alibaba.fastjson.annotation.JSONField;
import com.ccb.sdk.aes.utils.JsonUtils;
import com.ccb.sdk.bean.AbstractBussinessBean;
import com.ccb.sdk.bean.CommonRequest;
import com.ccb.sdk.bean.CommonResponse;
import com.ccb.sdk.bean.SDKRequestHead;

public class IFSPQRCodeInfoGetBean
  extends AbstractBussinessBean
{
//  private static final String serviceID = "IFSPQRCodeInfoGet";
//  private static final String productType = "LoanSvc";
  private Request req;
  private Response resp;
  
  public IFSPQRCodeInfoGetBean()
  {
    this.req = new Request();
    this.resp = new Response();
  }
  
  public CommonRequest getReq()
  {
    return this.req;
  }
  
  public SDKRequestHead getRequestHead()
  {
    return this.req.requestHead;
  }
  
  public CommonResponse getResp()
  {
    return this.resp;
  }
  
  public String getUrl(String productID)
  {
    return "LoanSvc/" + productID + "/" + "IFSPQRCodeInfoGet";
  }
  
  public void setResp(CommonResponse resp)
  {
    this.resp = ((Response)resp);
  }
  
  public String ReqToJsonString()
    throws Exception
  {
    return JsonUtils.objToJSON(getReq());
  }
  
  public void parseReponseJson(String json)
    throws Exception
  {
    CommonResponse resp = (CommonResponse)JSON.parseObject(json, getResp().getClass());
    setResp(resp);
  }
  
  public static class Request
    extends CommonRequest
  {
    private String Order_Code;
    private String Usr_ID;
    private String Aply_Psn_Ph_No;
    private String Aply_Psn_Crdt_TpCd;
    private String Aply_Psn_Crdt_No;
    private String Aply_Psn_Nm;
    private String Loan_AccNo;
    private String Order_Fail_Date;
    private String Order_Status;
    private String Order_Date;
    private String Expdtr_Amt;
    private String Aply_Ent_Nm;
    private String Crg_TLmt_ID;
    private String Aplyentbnk_Nm;
    private String Rcvpymtps_Accnm;
    private String Rppdbnk_Accno;
    private String Accno_Bkcgycd;
    private String Rppdbnk_Nm;
    private String Rppdbnk_Py_Bnkcd;
    private String Bsn_Jrnl_No;
    private String Idpy_Cst_Id;
    private String Chnl_ID;
    private String Unn_Soc_Cr_Cd;
    
    @JSONField(name="Order_Code")
    public String getOrder_Code()
    {
      return this.Order_Code;
    }
    
    public void setOrder_Code(String order_Code)
    {
      this.Order_Code = order_Code;
    }
    
    @JSONField(name="Usr_ID")
    public String getUsr_ID()
    {
      return this.Usr_ID;
    }
    
    public void setUsr_ID(String usr_ID)
    {
      this.Usr_ID = usr_ID;
    }
    
    @JSONField(name="Aply_Psn_Ph_No")
    public String getAply_Psn_Ph_No()
    {
      return this.Aply_Psn_Ph_No;
    }
    
    public void setAply_Psn_Ph_No(String aply_Psn_Ph_No)
    {
      this.Aply_Psn_Ph_No = aply_Psn_Ph_No;
    }
    
    @JSONField(name="Aply_Psn_Crdt_TpCd")
    public String getAply_Psn_Crdt_TpCd()
    {
      return this.Aply_Psn_Crdt_TpCd;
    }
    
    public void setAply_Psn_Crdt_TpCd(String aply_Psn_Crdt_TpCd)
    {
      this.Aply_Psn_Crdt_TpCd = aply_Psn_Crdt_TpCd;
    }
    
    @JSONField(name="Aply_Psn_Crdt_No")
    public String getAply_Psn_Crdt_No()
    {
      return this.Aply_Psn_Crdt_No;
    }
    
    public void setAply_Psn_Crdt_No(String aply_Psn_Crdt_No)
    {
      this.Aply_Psn_Crdt_No = aply_Psn_Crdt_No;
    }
    
    @JSONField(name="Aply_Psn_Nm")
    public String getAply_Psn_Nm()
    {
      return this.Aply_Psn_Nm;
    }
    
    public void setAply_Psn_Nm(String aply_Psn_Nm)
    {
      this.Aply_Psn_Nm = aply_Psn_Nm;
    }
    
    @JSONField(name="Loan_AccNo")
    public String getLoan_AccNo()
    {
      return this.Loan_AccNo;
    }
    
    public void setLoan_AccNo(String loan_AccNo)
    {
      this.Loan_AccNo = loan_AccNo;
    }
    
    @JSONField(name="Order_Fail_Date")
    public String getOrder_Fail_Date()
    {
      return this.Order_Fail_Date;
    }
    
    public void setOrder_Fail_Date(String order_Fail_Date)
    {
      this.Order_Fail_Date = order_Fail_Date;
    }
    
    @JSONField(name="Order_Status")
    public String getOrder_Status()
    {
      return this.Order_Status;
    }
    
    public void setOrder_Status(String order_Status)
    {
      this.Order_Status = order_Status;
    }
    
    @JSONField(name="Order_Date")
    public String getOrder_Date()
    {
      return this.Order_Date;
    }
    
    public void setOrder_Date(String order_Date)
    {
      this.Order_Date = order_Date;
    }
    
    @JSONField(name="Expdtr_Amt")
    public String getExpdtr_Amt()
    {
      return this.Expdtr_Amt;
    }
    
    public void setExpdtr_Amt(String expdtr_Amt)
    {
      this.Expdtr_Amt = expdtr_Amt;
    }
    
    @JSONField(name="Aply_Ent_Nm")
    public String getAply_Ent_Nm()
    {
      return this.Aply_Ent_Nm;
    }
    
    public void setAply_Ent_Nm(String aply_Ent_Nm)
    {
      this.Aply_Ent_Nm = aply_Ent_Nm;
    }
    
    @JSONField(name="Crg_TLmt_ID")
    public String getCrg_TLmt_ID()
    {
      return this.Crg_TLmt_ID;
    }
    
    public void setCrg_TLmt_ID(String crg_TLmt_ID)
    {
      this.Crg_TLmt_ID = crg_TLmt_ID;
    }
    
    @JSONField(name="Aplyentbnk_Nm")
    public String getAplyentbnk_Nm()
    {
      return this.Aplyentbnk_Nm;
    }
    
    public void setAplyentbnk_Nm(String aplyentbnk_Nm)
    {
      this.Aplyentbnk_Nm = aplyentbnk_Nm;
    }
    
    @JSONField(name="Rcvpymtps_Accnm")
    public String getRcvpymtps_Accnm()
    {
      return this.Rcvpymtps_Accnm;
    }
    
    public void setRcvpymtps_Accnm(String rcvpymtps_Accnm)
    {
      this.Rcvpymtps_Accnm = rcvpymtps_Accnm;
    }
    
    @JSONField(name="Rppdbnk_Accno")
    public String getRppdbnk_Accno()
    {
      return this.Rppdbnk_Accno;
    }
    
    public void setRppdbnk_Accno(String rppdbnk_Accno)
    {
      this.Rppdbnk_Accno = rppdbnk_Accno;
    }
    
    @JSONField(name="Accno_Bkcgycd")
    public String getAccno_Bkcgycd()
    {
      return this.Accno_Bkcgycd;
    }
    
    public void setAccno_Bkcgycd(String accno_Bkcgycd)
    {
      this.Accno_Bkcgycd = accno_Bkcgycd;
    }
    
    @JSONField(name="Rppdbnk_Nm")
    public String getRppdbnk_Nm()
    {
      return this.Rppdbnk_Nm;
    }
    
    public void setRppdbnk_Nm(String rppdbnk_Nm)
    {
      this.Rppdbnk_Nm = rppdbnk_Nm;
    }
    
    @JSONField(name="Rppdbnk_Py_Bnkcd")
    public String getRppdbnk_Py_Bnkcd()
    {
      return this.Rppdbnk_Py_Bnkcd;
    }
    
    public void setRppdbnk_Py_Bnkcd(String rppdbnk_Py_Bnkcd)
    {
      this.Rppdbnk_Py_Bnkcd = rppdbnk_Py_Bnkcd;
    }
    
    @JSONField(name="Bsn_Jrnl_No")
    public String getBsn_Jrnl_No()
    {
      return this.Bsn_Jrnl_No;
    }
    
    public void setBsn_Jrnl_No(String bsn_Jrnl_No)
    {
      this.Bsn_Jrnl_No = bsn_Jrnl_No;
    }
    
    @JSONField(name="Idpy_Cst_Id")
    public String getIdpy_Cst_Id()
    {
      return this.Idpy_Cst_Id;
    }
    
    public void setIdpy_Cst_Id(String idpy_Cst_Id)
    {
      this.Idpy_Cst_Id = idpy_Cst_Id;
    }
    
    @JSONField(name="Chnl_ID")
    public String getChnl_ID()
    {
      return this.Chnl_ID;
    }
    
    public void setChnl_ID(String chnl_ID)
    {
      this.Chnl_ID = chnl_ID;
    }
    
    @JSONField(name="Unn_Soc_Cr_Cd")
    public String getUnn_Soc_Cr_Cd()
    {
      return this.Unn_Soc_Cr_Cd;
    }
    
    public void setUnn_Soc_Cr_Cd(String unn_Soc_Cr_Cd)
    {
      this.Unn_Soc_Cr_Cd = unn_Soc_Cr_Cd;
    }
    
    private SDKRequestHead requestHead = new SDKRequestHead();
    private String Tpl_Vno;
    private String Chnl_TpCd;
    private String Txn_Chnl_ID;
    private String TermTp;
    private String Tmnl_ID;
    private String Trgt_Eqmt_IP_Adr;
    private String Tsk_Cord;
    private String Cr_Rsdnc_Adr;
    private String Urbn_Cd;
    private String MsgRp_Tp_No;
    private String MsgIDNo;
    private String MsgRp_Snd_Tm;
    private String Idt_Verf_Ind;
    private String Pgg_Seq_TpCd;
    private String EcPg_Max_Rcrd_Num;
    private String Txn_Cd;
    
    @JSONField(name="Tpl_Vno")
    public String getTpl_Vno()
    {
      return this.Tpl_Vno;
    }
    
    public void setTpl_Vno(String tpl_Vno)
    {
      this.Tpl_Vno = tpl_Vno;
    }
    
    @JSONField(name="Chnl_TpCd")
    public String getChnl_TpCd()
    {
      return this.Chnl_TpCd;
    }
    
    public void setChnl_TpCd(String chnl_TpCd)
    {
      this.Chnl_TpCd = chnl_TpCd;
    }
    
    @JSONField(name="Txn_Chnl_ID")
    public String getTxn_Chnl_ID()
    {
      return this.Txn_Chnl_ID;
    }
    
    public void setTxn_Chnl_ID(String txn_Chnl_ID)
    {
      this.Txn_Chnl_ID = txn_Chnl_ID;
    }
    
    @JSONField(name="TermTp")
    public String getTermTp()
    {
      return this.TermTp;
    }
    
    public void setTermTp(String termTp)
    {
      this.TermTp = termTp;
    }
    
    @JSONField(name="Tmnl_ID")
    public String getTmnl_ID()
    {
      return this.Tmnl_ID;
    }
    
    public void setTmnl_ID(String tmnl_ID)
    {
      this.Tmnl_ID = tmnl_ID;
    }
    
    @JSONField(name="Trgt_Eqmt_IP_Adr")
    public String getTrgt_Eqmt_IP_Adr()
    {
      return this.Trgt_Eqmt_IP_Adr;
    }
    
    public void setTrgt_Eqmt_IP_Adr(String trgt_Eqmt_IP_Adr)
    {
      this.Trgt_Eqmt_IP_Adr = trgt_Eqmt_IP_Adr;
    }
    
    @JSONField(name="Tsk_Cord")
    public String getTsk_Cord()
    {
      return this.Tsk_Cord;
    }
    
    public void setTsk_Cord(String tsk_Cord)
    {
      this.Tsk_Cord = tsk_Cord;
    }
    
    @JSONField(name="Cr_Rsdnc_Adr")
    public String getCr_Rsdnc_Adr()
    {
      return this.Cr_Rsdnc_Adr;
    }
    
    public void setCr_Rsdnc_Adr(String cr_Rsdnc_Adr)
    {
      this.Cr_Rsdnc_Adr = cr_Rsdnc_Adr;
    }
    
    @JSONField(name="Urbn_Cd")
    public String getUrbn_Cd()
    {
      return this.Urbn_Cd;
    }
    
    public void setUrbn_Cd(String urbn_Cd)
    {
      this.Urbn_Cd = urbn_Cd;
    }
    
    @JSONField(name="MsgRp_Tp_No")
    public String getMsgRp_Tp_No()
    {
      return this.MsgRp_Tp_No;
    }
    
    public void setMsgRp_Tp_No(String msgRp_Tp_No)
    {
      this.MsgRp_Tp_No = msgRp_Tp_No;
    }
    
    @JSONField(name="MsgIDNo")
    public String getMsgIDNo()
    {
      return this.MsgIDNo;
    }
    
    public void setMsgIDNo(String msgIDNo)
    {
      this.MsgIDNo = msgIDNo;
    }
    
    @JSONField(name="MsgRp_Snd_Tm")
    public String getMsgRp_Snd_Tm()
    {
      return this.MsgRp_Snd_Tm;
    }
    
    public void setMsgRp_Snd_Tm(String msgRp_Snd_Tm)
    {
      this.MsgRp_Snd_Tm = msgRp_Snd_Tm;
    }
    
    @JSONField(name="Idt_Verf_Ind")
    public String getIdt_Verf_Ind()
    {
      return this.Idt_Verf_Ind;
    }
    
    public void setIdt_Verf_Ind(String idt_Verf_Ind)
    {
      this.Idt_Verf_Ind = idt_Verf_Ind;
    }
    
    @JSONField(name="Pgg_Seq_TpCd")
    public String getPgg_Seq_TpCd()
    {
      return this.Pgg_Seq_TpCd;
    }
    
    public void setPgg_Seq_TpCd(String pgg_Seq_TpCd)
    {
      this.Pgg_Seq_TpCd = pgg_Seq_TpCd;
    }
    
    @JSONField(name="EcPg_Max_Rcrd_Num")
    public String getEcPg_Max_Rcrd_Num()
    {
      return this.EcPg_Max_Rcrd_Num;
    }
    
    public void setEcPg_Max_Rcrd_Num(String ecPg_Max_Rcrd_Num)
    {
      this.EcPg_Max_Rcrd_Num = ecPg_Max_Rcrd_Num;
    }
    
    @JSONField(name="Txn_Cd")
    public String getTxn_Cd()
    {
      return this.Txn_Cd;
    }
    
    public void setTxn_Cd(String txn_Cd)
    {
      this.Txn_Cd = txn_Cd;
    }
  }
  
  public static class Response
    extends CommonResponse
  {
    private String Qr_Code;
    private String Seq_No;
    private String Txn_RtCd;
    private String Txn_Ret_Inf;
    private String url;
    
    @JSONField(name="Qr_Code")
    public String getQr_Code()
    {
      return this.Qr_Code;
    }
    
    public void setQr_Code(String qr_Code)
    {
      this.Qr_Code = qr_Code;
    }
    
    @JSONField(name="Seq_No")
    public String getSeq_No()
    {
      return this.Seq_No;
    }
    
    public void setSeq_No(String seq_No)
    {
      this.Seq_No = seq_No;
    }
    
    @JSONField(name="Txn_RtCd")
    public String getTxn_RtCd()
    {
      return this.Txn_RtCd;
    }
    
    public void setTxn_RtCd(String txn_RtCd)
    {
      this.Txn_RtCd = txn_RtCd;
    }
    
    @JSONField(name="Txn_Ret_Inf")
    public String getTxn_Ret_Inf()
    {
      return this.Txn_Ret_Inf;
    }
    
    public void setTxn_Ret_Inf(String txn_Ret_Inf)
    {
      this.Txn_Ret_Inf = txn_Ret_Inf;
    }
    @JSONField(name="Url")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
    
  }
}
