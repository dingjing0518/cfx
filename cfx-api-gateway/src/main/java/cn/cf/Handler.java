package cn.cf;

import javax.servlet.http.HttpServletRequest;

public abstract class Handler {
	
    protected Handler successor = null;  
   
    public Handler getSuccessor() {  
        return successor;  
    }  
   
    public void setSuccessor(Handler successor) {  
        this.successor = successor;  
    }  
   
    public abstract void handleRequest(HttpServletRequest request); 
    
   
}
