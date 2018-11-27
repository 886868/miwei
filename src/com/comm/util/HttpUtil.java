package com.comm.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;


public class HttpUtil {

	/**
	 * instances of the log class
	 */
	private static Logger logger = Logger.getLogger(HttpUtil.class);

	/**
	 * 从输入的数据流中得到JSON字符串<br>
	 * 不能取到时返回Null
	 * 
	 * @param request Http请求对象
	 * 
	 * @return String JSON字符串
	 */
	public static String getStringFromRequest(HttpServletRequest request) {
		ServletInputStream input = null;
		ByteArrayOutputStream outStream = null;
		String inputstr = null;
		try {
			input = request.getInputStream();
			outStream = new ByteArrayOutputStream();
			int len = -1;
			byte[] buffer = new byte[1024];
			while ((len = input.read(buffer)) != -1) {
				outStream.write(buffer, 0, len);
			}
			inputstr = new String(outStream.toByteArray(), "utf-8");

		} catch (IOException ioexp) {
			logger.error("解析Http请求时异常", ioexp);
			return null;
		} finally {
			if (outStream != null) {
				try {
					outStream.close();
				} catch (IOException ioexp) {
					logger.error(ioexp.getMessage());
				}
			}
			if (input != null) {
				try {
					input.close();
				} catch (IOException ioexp) {
					logger.error(ioexp.getMessage());
				}
			}
		}

		// 获取调用者IP地址
		String callerIp = request.getRemoteAddr();
		if (logger.isDebugEnabled()) {
			logger.debug("Http POST Request CALLER IP: " + callerIp + "  PARAM: req=" + inputstr);
		}
		
		if (StringUtils.isBlank(inputstr)) {
			logger.error("无应答内容");
			return null;
		}
		return inputstr;
	}
	
	/**
	 * 从输入的数据流中得到byte数据<br>
	 * 不能取到时返回Null
	 * 
	 * @param request Http请求对象
	 * 
	 * @return byte[] 
	 */
	public static byte[] getStringFromRequestBytes(HttpServletRequest request) {
		ServletInputStream input = null;
		ByteArrayOutputStream outStream = null;
		byte[] inputstr = null;
		try {
			input = request.getInputStream();
			outStream = new ByteArrayOutputStream();
			int len = -1;
			byte[] buffer = new byte[1024];
			while ((len = input.read(buffer)) != -1) {
				outStream.write(buffer, 0, len);
			}
			inputstr = outStream.toByteArray();

		} catch (IOException ioexp) {
			logger.error("解析Http请求时异常", ioexp);
			return null;
		} finally {
			if (outStream != null) {
				try {
					outStream.close();
				} catch (IOException ioexp) {
					logger.error(ioexp.getMessage());
				}
			}
			if (input != null) {
				try {
					input.close();
				} catch (IOException ioexp) {
					logger.error(ioexp.getMessage());
				}
			}
		}

		// 获取调用者IP地址
		String callerIp = request.getRemoteAddr();
		if (logger.isDebugEnabled()) {
			logger.debug("Http POST Request CALLER IP: " + callerIp + "  PARAM: req=" + inputstr);
		}
		
		if (null == inputstr || inputstr.length <=0) {
			logger.error("无应答内容");
			return null;
		}
		return inputstr;
	}

	/**
	 * 从输入的数据流中得到指定的JSON对象<br>
	 * 不能取到时返回Null<br>
	 * 注意，此方法只能获取以HTTP POST方式提交的请求数据
	 * 
	 * @param request Http请求对象
	 *
	 * @return JSONObject JSON对象
	 */
	public static JSONObject getJsonObjFromRequest(HttpServletRequest request) {
		String inputstr = getStringFromRequest(request);
		if (inputstr == null) {
			return null;
		}
		try {
			return JSONObject.fromObject(inputstr);
		} catch (JSONException jxp) {
			logger.error(jxp.getMessage());
			return null;
		}
	}

	/**
	 * 从输入的数据流中得到指定的JSON数组<br>
	 * 不能取到时返回Null<br>
	 * 注意，此方法只能获取以HTTP POST方式提交的请求数据
	 * 
	 * @param request Http请求对象
	 *
	 * @return JSONArray JSON数组
	 */
	public static JSONArray getJsonArrayFromRequest(HttpServletRequest request) {
		String inputstr = getStringFromRequest(request);
		if (inputstr == null) {
			return null;
		}
		try {
			return JSONArray.fromObject(inputstr);
		} catch (JSONException jxp) {
			logger.error(jxp.getMessage());
			return null;
		}
	}

	public static String getBody4Post(String webUrl, JSONObject obj) {
		return getBody4Post(webUrl, obj, Const.HTTP_REQUEST_TIMEOUT_CONN, Const.HTTP_REQUEST_TIMEOUT_READ);
	}
	
	/**
	 * 发送普通HTTP POST请求
	 *
	 * @param webUrl HTTP请求URL
	 * @param obj 请求参数
	 *
	 * @return String 请求结果
	 */
	public static String getBody4Post(String webUrl, JSONObject obj, int connTimeout, int readTimeout) {
//		HttpClient httpclient = null;
//		HttpPost httppost = null;
//		try {
//			httpclient = new DefaultHttpClient();
//			httppost = new HttpPost(webUrl);
//
//			// 设置连接超时
//			HttpParams params = httpclient.getParams();
//			// 连接时间
//			HttpConnectionParams.setConnectionTimeout(params, 100000);
//			// 数据传输时间
//			HttpConnectionParams.setSoTimeout(params, 200000);
//
//			if (obj != null) {
//				httppost.setEntity(new StringEntity(obj.toString(), "utf-8"));
//			}
//
//			HttpResponse response = httpclient.execute(httppost);
//			// 检验状态码，如果成功接收数据
//			int code = response.getStatusLine().getStatusCode();
//			if (code == 200) {
//				String rev = EntityUtils.toString(response.getEntity());
//				return rev;
//			} else {
//				logger.error("发送http post 请求时异常: " + webUrl + "http 响应码: " + code);
//				return null;
//			}
//
//		} catch (Exception e) {
//			logger.error("发送http post 请求时异常: " + webUrl, e);
//			return null;
//		} finally {
//			if (httppost != null) {
//				httppost.releaseConnection();
//			}
//			if (httpclient != null) {
//				httpclient.getConnectionManager().shutdown();
//			}
//		}
		
		String strRet = null;
		CloseableHttpClient httpClient = null;
		InputStreamReader isr = null;
		RequestConfig requestConfig = null;
		CloseableHttpResponse response = null;
        HttpPost httpPost = new HttpPost(webUrl);
        
        try {  
			Builder builder = RequestConfig.custom();
			builder.setSocketTimeout(readTimeout);
			builder.setConnectTimeout(connTimeout);
			builder.setCookieSpec(CookieSpecs.BEST_MATCH);
			requestConfig = builder.build();
			httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig).build();
			RequestConfig localConfig = RequestConfig.copy(requestConfig)
			        .setCookieSpec(CookieSpecs.BROWSER_COMPATIBILITY)
			        .build();
			httpPost.setConfig(localConfig);
			
			if (obj != null) {
				httpPost.setEntity(new StringEntity(obj.toString(), "UTF-8"));	
			}
            // 执行get请求.    
            response = httpClient.execute(httpPost); 
            // 获取响应实体    
            HttpEntity entityRes = response.getEntity();  
            if (entityRes != null) {
            	strRet = EntityUtils.toString(entityRes);
            }  
        } catch (ClientProtocolException e) {  
            logger.error(ExceptionUtil.getExceptionInfo(e));
        } catch (ParseException e) {  
            logger.error(ExceptionUtil.getExceptionInfo(e));
        } catch (IOException e) {  
            logger.error(ExceptionUtil.getExceptionInfo(e));
        } catch (JSONException e) {
			logger.error(ExceptionUtil.getExceptionInfo(e));
		} finally {  
            // 关闭连接,释放资源    
            try { 
            	if (response != null) {
            		response.close();
            		response = null;
            	}
            	if (httpClient != null) {
                	httpClient.close();  
                	httpClient = null;
            	}
            	if (httpPost != null) {
            		httpPost = null;
            	}
            	if (isr != null) {
            		isr.close();
					isr = null;
            	}
            } catch (IOException e) {  
            	logger.error(ExceptionUtil.getExceptionInfo(e));
            	throw new RuntimeException(e);
            }  
        }  
		return strRet;
		
	}

	/**
	 * 发送普通HTTP GET请求
	 *
	 * @param webUrl HTTP请求URL
	 *
	 * @return String 请求结果
	 */
	public static String getBody4Get(String webUrl) {
		return getBody4Get(webUrl, Const.HTTP_REQUEST_TIMEOUT_CONN,
				Const.HTTP_REQUEST_TIMEOUT_READ);
	}
	
	/**
	 * 发送普通HTTP GET请求
	 *
	 * @param webUrl HTTP请求URL
	 *
	 * @return String 请求结果
	 */
	public static String getBody4Get(String webUrl, int connTimeout, int readTimeout) {
//		HttpClient httpclient = null;
//		HttpGet httpGet = null;
//		try {
//			httpclient = new DefaultHttpClient();
//			httpGet = new HttpGet(webUrl);
//
//			// 设置连接超时
//			HttpParams params = httpclient.getParams();
//			HttpConnectionParams.setConnectionTimeout(params, 100000);
//			HttpConnectionParams.setSoTimeout(params, 200000);
//
//			HttpResponse response = httpclient.execute(httpGet);
//			// 检验状态码，如果成功接收数据
//			int code = response.getStatusLine().getStatusCode();
//			if (code == 200) {
//				String rev = EntityUtils.toString(response.getEntity());
//				return rev;
//			} else {
//				logger.error("发送http get 请求时异常: " + webUrl + "http 响应码: " + code);
//				return null;
//			}
//
//		} catch (Exception e) {
//			logger.error("发送http get 请求时异常: " + webUrl, e);
//			return null;
//		} finally {
//			if (httpGet != null) {
//				httpGet.releaseConnection();
//			}
//			if (httpclient != null) {
//				httpclient.getConnectionManager().shutdown();
//			}
//		}
		
		String strRet = null;
		CloseableHttpClient httpClient = null;
		InputStreamReader isr = null;
		RequestConfig requestConfig = null;
		CloseableHttpResponse response = null;
        HttpGet httpget = new HttpGet(webUrl);
        
        try {  
			Builder builder = RequestConfig.custom();
			builder.setSocketTimeout(readTimeout);
			builder.setConnectTimeout(connTimeout);
			builder.setCookieSpec(CookieSpecs.BEST_MATCH);
			requestConfig = builder.build();
			httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig).build();
			RequestConfig localConfig = RequestConfig.copy(requestConfig)
			        .setCookieSpec(CookieSpecs.BROWSER_COMPATIBILITY)
			        .build();
			
			httpget.setConfig(localConfig);
            // 执行get请求.    
            response = httpClient.execute(httpget); 
            // 获取响应实体    
            HttpEntity entityRes = response.getEntity();  
            if (entityRes != null) {
            	strRet = EntityUtils.toString(entityRes);
            }  
        } catch (ClientProtocolException e) {  
            logger.error(ExceptionUtil.getExceptionInfo(e));
        } catch (ParseException e) {  
            logger.error(ExceptionUtil.getExceptionInfo(e));
        } catch (IOException e) {  
            logger.error(ExceptionUtil.getExceptionInfo(e));
        } catch (JSONException e) {
			logger.error(ExceptionUtil.getExceptionInfo(e));
		} finally {  
            // 关闭连接,释放资源    
            try { 
            	if (response != null) {
            		response.close();
            		response = null;
            	}
            	if (httpClient != null) {
                	httpClient.close();  
                	httpClient = null;
            	}
            	if (httpget != null) {
            		httpget = null;
            	}
            	if (isr != null) {
            		isr.close();
					isr = null;
            	}
            } catch (IOException e) {  
            	logger.error(ExceptionUtil.getExceptionInfo(e));
            	throw new RuntimeException(e);
            }  
        }  
		return strRet;
	}
	
	/**
     * 发送post请求
     * @param url       请求的url
     * @param param     '?'的参数
     * @param body      请求body的内容
     * @param headers   header参数
     * @param charset   字符集
     * @return
     */
    public static String doPost(String url, 
                Map<String, String> params, HttpEntity body, 
                Map<String,String> headers, String charset){
        
        HttpClient httpClient = null;  
        HttpPost httpPost = null;  
        String result = null;  
        try{  
            httpClient = HttpClients.createDefault();
            
            if(params != null) {
              //设置参数  
                Iterator<Entry<String, String>> iterator = params.entrySet().iterator();
                
                int i = 0;
                while(iterator.hasNext()){
                    
                    if(i == 0) {
                        url = url + "?";
                    } else {
                        url = url + "&";
                    }
                    Entry<String,String> elem = iterator.next();  
                    url = url + elem.getKey() + "=" + elem.getValue();  
                    i++;
                } 
            }
            
            httpPost = new HttpPost(url);
            
            if(body != null) {
                httpPost.setEntity(body);
            }
            
            // set header
            if(headers != null) {
                for(Map.Entry<String, String> entry : headers.entrySet()) {
                    httpPost.setHeader(entry.getKey(), entry.getValue());
                }
            }
            
//            httpPost.setHeader("Content-Type", "application/xml");
            httpPost.setHeader("charset",charset);
            
            HttpResponse response = httpClient.execute(httpPost);  
            if(response != null){  
                HttpEntity resEntity = response.getEntity();  
                if(resEntity != null){  
                    result = EntityUtils.toString(resEntity,charset);  
                }  
            }  
        }catch(Exception ex){  
            ex.printStackTrace();  
        } finally {
            if(httpPost != null) {
                httpPost.releaseConnection();
            }
        }
        return result;  
    }
	
	/**
     * 发送post请求
     * 
     * @param httpClient client 类型
     * @param url       请求的url
     * @param param     '?'的参数
     * @param body      请求baody的内容
     * @param headers   header参数
     * @param charset   字符集
     * @return
     */
    public static String doPost(CloseableHttpClient httpClient, String url, 
                Map<String, String> params, String body, 
                Map<String,String> headers, String charset){
        
//        HttpClient httpClient = null;  
        HttpPost httpPost = null;  
        String result = null;  
        try{  
//            httpClient = HttpClients.createDefault();
            
            if(params != null) {
              //设置参数  
                Iterator<Entry<String, String>> iterator = params.entrySet().iterator();
                
                int i = 0;
                while(iterator.hasNext()){
                    
                    if(i == 0) {
                        url = url + "?";
                    } else {
                        url = url + "&";
                    }
                    Entry<String,String> elem = iterator.next();  
                    url = url + elem.getKey() + "=" + elem.getValue();  
                    i++;
                } 
            }
            
            httpPost = new HttpPost(url);
            
            if(StringUtils.isNotEmpty(body)) {
                HttpEntity bodyEntity = new ByteArrayEntity(body.getBytes("UTF-8"));
                httpPost.setEntity(bodyEntity);
            }
            
            // set header
            if(headers != null) {
                for(Map.Entry<String, String> entry : headers.entrySet()) {
                    httpPost.setHeader(entry.getKey(), entry.getValue());
                }
            }
            
//            httpPost.setHeader("Content-Type", "application/xml");
            httpPost.setHeader("charset",charset);
            
            HttpResponse response = httpClient.execute(httpPost);  
            if(response != null){  
                HttpEntity resEntity = response.getEntity();  
                if(resEntity != null){  
                    result = EntityUtils.toString(resEntity,charset);  
                }  
            }  
        }catch(Exception ex){  
            ex.printStackTrace();  
        } finally {
            if(httpPost != null) {
                httpPost.releaseConnection();
            }
        }
        return result;  
    }
    
    /**
     * 发送Post请求
     * 
     * @param url       请求的url
     * @param param     '?'的参数
     * @param body      请求baody的内容
     * @param headers   header参数
     * @param charset   字符集
     * @return
     */
    public static String doPostSSL(String url, Map<String, String> params, String body, 
            Map<String,String> headers, String charset) throws Exception {
        CloseableHttpClient httpClient =  createSSLClientDefault();
        return doPost(httpClient, url, params, body, headers, charset);
    }
    
    /**
     * 发送Post请求
     * 
     * @param url       请求的url
     * @param param     '?'的参数
     * @param body      请求baody的内容
     * @param headers   header参数
     * @param charset   字符集
     * @return
     */
    public static String doPost(String url, Map<String, String> params, String body, 
            Map<String,String> headers, String charset) {
        CloseableHttpClient httpClient = HttpClients.createDefault();  
        return doPost(httpClient, url, params, body, headers, charset);
    }
    
    /**
     * 发送get请求
     * 
     * @param httpClient client类型
     * @param url       请求的url
     * @param params       '?'的参数
     * @param body      请求baody的内容
     * @param headers   header内容
     * @param charset   字符集
     * @return
     */
    public static String doGet(CloseableHttpClient httpClient, String url,Map<String,String> params, 
                            Map<String,String> headers, String charset){
        
//        CloseableHttpClient httpClient = null;  
        HttpGet httpGet = null;
        URIBuilder ub = new URIBuilder();  
        
        String result = null;  
        try{  
//            httpClient = HttpClients.createDefault();
            ub = new URIBuilder();  
            ub.setPath(url); 
            
            if(params != null) {
                //设置参数  
                ArrayList<NameValuePair> pairs = new ArrayList<NameValuePair>();
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    pairs.add(new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue())));
                }
                ub.setParameters(pairs);
            }
            
            
            httpGet = new HttpGet(ub.build());
            
            // set header
            if(headers != null) {
                for(Map.Entry<String, String> entry : headers.entrySet()) {
                    httpGet.setHeader(entry.getKey(), entry.getValue());
                }
            }
            
//            httpGet.setHeader("Content-Type", "application/xml");
            httpGet.setHeader("charset",charset);
            
            HttpResponse response = httpClient.execute(httpGet);  
            if(response != null){  
                HttpEntity resEntity = response.getEntity();  
                if(resEntity != null){  
                    result = EntityUtils.toString(resEntity,charset);  
                }  
            }  
        }catch(Exception ex){  
            ex.printStackTrace();  
        } finally {
            if(httpGet != null) {
                httpGet.releaseConnection();
            }
        }
        return result;  
    }
    
    /**
     * 发送get请求
     * 
     * @param url       请求的url
     * @param params       '?'的参数
     * @param body      请求baody的内容
     * @param headers   header内容
     * @param charset   字符集
     * @return
     */
    public static String doGetSSL(String url,Map<String,String> params, 
            Map<String,String> headers, String charset) throws Exception {
        CloseableHttpClient httpClient =  createSSLClientDefault();
        return doGet(httpClient, url, params, headers, charset);
    }
    
    /**
     * 发送get请求
     * 
     * @param url       请求的url
     * @param params       '?'的参数
     * @param body      请求baody的内容
     * @param headers   header内容
     * @param charset   字符集
     * @return
     */
    public static String doGet(String url,Map<String,String> params, 
            Map<String,String> headers, String charset) {
        CloseableHttpClient httpClient = HttpClients.createDefault();  
        return doGet(httpClient, url, params, headers, charset);
    }
    
    /**
     * 获取 https 的 client
     * 
     * @return
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    public static CloseableHttpClient createSSLClientDefault() 
            throws NoSuchAlgorithmException, KeyManagementException{
        SSLContext sslcontext = SSLContext.getInstance("TLS");  
        X509TrustManager tm = new X509TrustManager() {  
            
                /**
                 * 验证客户端证书
                 */
                @Override  
                public void checkClientTrusted(X509Certificate[] chain,  
                        String authType) throws CertificateException {  
                }  
                
                /**
                 * 验证服务端证书
                 *
                 * @param chain
                 *            证书链
                 * @param authType
                 *            使用的密钥交换算法，当使用来自服务器的密钥时authType为RSA
                 */
                @Override  
                public void checkServerTrusted(X509Certificate[] chain,  
                        String authType) throws CertificateException { 
                    if (chain == null || chain.length == 0)
                        throw new IllegalArgumentException("null or zero-length certificate chain");
                    if (authType == null || authType.length() == 0)
                        throw new IllegalArgumentException("null or zero-length authentication type");

                    boolean br = false;
                    Principal principal = null;
                    for (X509Certificate x509Certificate : chain) {
                        principal = x509Certificate.getSubjectX500Principal();
                        if (principal != null) {
                            br = true;
                            return;
                        }
                    }
                    if (!br) {
                        throw new CertificateException("服务端证书验证失败！");
                    }
                }  
                
                /**
                 * 返回CA发行的证书
                 */
                @Override  
                public X509Certificate[] getAcceptedIssuers() {  
                    return new X509Certificate[0];
                }  
        };  
        sslcontext.init(null, new TrustManager[]{tm}, null);  
        // Allow TLSv1 protocol only
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslcontext,
                new String[] { "TLSv1" },
                null,
                SSLConnectionSocketFactory.getDefaultHostnameVerifier());
        CloseableHttpClient httpclient = HttpClients.custom()
                .setSSLSocketFactory(sslsf)
                .build();
        return httpclient;
    }
}
