package cn.cf.common.creditpay.jianshe;

import com.ccb.alibaba.fastjson.JSON;
import com.ccb.alibaba.fastjson.annotation.JSONField;
import com.ccb.sdk.aes.utils.JsonUtils;
import com.ccb.sdk.bean.AbstractBussinessBean;
import com.ccb.sdk.bean.CommonRequest;
import com.ccb.sdk.bean.CommonResponse;
import com.ccb.sdk.bean.SDKRequestHead;

public class IFSPOrderResultBean
  extends AbstractBussinessBean
{
//  private static final String serviceID = "IFSPOrderResult";
//  private static final String productType = "LoanSvc";
  private Request req;
  private Response resp;
  
  public IFSPOrderResultBean()
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
    return "LoanSvc/" + productID + "/" + "IFSPOrderResult";
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
    private String Chnl_ID;
    
    @JSONField(name="Order_Code")
    public String getOrder_Code()
    {
      return this.Order_Code;
    }
    
    public void setOrder_Code(String order_Code)
    {
      this.Order_Code = order_Code;
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
    private String Ori_Txn_Ind;
    private String Txn_Rvrs_Stcd;
    private String loanaccno;
    private String Txn_RtCd;
    private String Txn_Ret_Inf;
    
    @JSONField(name="Ori_Txn_Ind")
    public String getOri_Txn_Ind()
    {
      return this.Ori_Txn_Ind;
    }
    
    public void setOri_Txn_Ind(String ori_Txn_Ind)
    {
      this.Ori_Txn_Ind = ori_Txn_Ind;
    }
    
    @JSONField(name="loanaccno")
    public String getLoanaccno()
    {
      return this.loanaccno;
    }
    
    public void setLoanaccno(String loanaccno)
    {
      this.loanaccno = loanaccno;
    }
    
    @JSONField(name="Txn_Rvrs_Stcd")
    public String getTxn_Rvrs_Stcd()
    {
      return this.Txn_Rvrs_Stcd;
    }
    
    public void setTxn_Rvrs_Stcd(String txn_Rvrs_Stcd)
    {
      this.Txn_Rvrs_Stcd = txn_Rvrs_Stcd;
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
  }
}
