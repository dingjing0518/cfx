package cn.cf.common.creditpay.guangda;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.security.KeyStore;
import java.security.cert.CertificateException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


import cn.cf.property.PropertyConfig;

public class PostUtils {
	
	
    public static String post( String requestUrl,String requestMethod,String outputStr){
        StringBuffer buffer=null;
        try{
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }

                        public void checkClientTrusted(
                                final java.security.cert.X509Certificate[] arg0, final String arg1)
                                throws CertificateException {
                            // do nothing and blindly accept the certificate
                        }

                        public void checkServerTrusted(
                                final java.security.cert.X509Certificate[] arg0, final String arg1)
                                throws CertificateException {
                            // do nothing and blindly accept the server
                        }

                    }
            };

            //创建SSLContext 
      	    SSLContext sslContext= SSLContext.getInstance("SSL");
            KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
            KeyStore pks = KeyStore.getInstance("JKS");
//            pks.load(new FileInputStream(PropertyConfig.getProperty("ssl_path")+"client.jks"), "123456".toCharArray());
            pks.load(new FileInputStream(PropertyConfig.getProperty("gd_ssl_path")), PropertyConfig.getProperty("gd_ssl_password").toCharArray());
            kmf.init(pks,PropertyConfig.getProperty("gd_ssl_password").toCharArray());

            //初始化
            sslContext.init(kmf.getKeyManagers(), trustAllCerts, null);;

            SSLSocketFactory ssf=sslContext.getSocketFactory();
            URL url=new URL(requestUrl);
            HttpsURLConnection conn=(HttpsURLConnection)url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod(requestMethod);
            conn.setRequestProperty("Content-Type","application/json;charset=gbk");
            // 修改ip 不宜用，
            conn.setHostnameVerifier(new HostnameVerifier() {
				@Override
				public boolean verify(String arg0, SSLSession arg1) {
					// TODO Auto-generated method stub
					return true;
				}
            });
            conn.setConnectTimeout(10*1000);
            conn.setReadTimeout(60*1000);
            //设置当前实例使用的SSLSoctetFactory
            conn.setSSLSocketFactory(ssf);
            conn.connect();
            //往服务器端写内容
            if(null!=outputStr){
                OutputStream os=conn.getOutputStream();
                os.write(outputStr.getBytes("gbk"));
                os.close();
            }

            //读取服务器端返回的内容
            InputStream is=conn.getInputStream();
            InputStreamReader isr=new InputStreamReader(is,"gbk");
            BufferedReader br=new BufferedReader(isr);
            buffer=new StringBuffer();
            String line=null;
            while((line=br.readLine())!=null){
                buffer.append(line);
            }
        }catch(Exception e){
        	System.out.println("errorguangda:"+e);
        }
        return buffer.toString();
    }
}
