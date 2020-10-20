package com.kplan.phonecard.utils;

import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpUtils {
	private static final Logger logger = LoggerFactory
			.getLogger(HttpUtils.class);
	public static final String HTTP_SUCCESS = "SUCCEESS";
	public static final String HTTP_TIMEOUT_ERROR = "TIMEOUT_ERROR";
	public static final String HTTP_UNKNOWN_ERROR = "UNKNOWN_ERROR";

	/**
	 * get请求
	 * @param url
	 *            请求url
	 * @param param
	 *            请求参数map集合
	 * @param connectTimeout
	 *            超时时间，毫秒
	 * @return 数组 [0]:状态，[1]:返回信息
	 */
	public static String[] doGet(String url, Map<String, String> param, int connectTimeout) {
		String[] result = new String[2];
		result[0] = HTTP_UNKNOWN_ERROR;
		result[1] = "";
		// 创建Httpclient对象
		CloseableHttpClient httpclient = HttpClients.createDefault();

		CloseableHttpResponse response = null;
		try {
			RequestConfig rc = RequestConfig.copy(RequestConfig.DEFAULT).setConnectTimeout(connectTimeout).setSocketTimeout(connectTimeout).build();

			// 创建uri
			URIBuilder builder = new URIBuilder(url);
			if (param != null) {
				for (String key : param.keySet()) {
					builder.addParameter(key, param.get(key));
				}
			}
			URI uri = builder.build();

			// 创建http GET请求
			HttpGet httpGet = new HttpGet(uri);
			httpGet.setConfig(rc);

			// 执行请求
			response = httpclient.execute(httpGet);
			// 判断返回状态是否为200
			if (response.getStatusLine().getStatusCode() == 200) {
				String resultStr = EntityUtils.toString(response.getEntity(), "UTF-8");
				result[0] = HTTP_SUCCESS;
				result[1] = resultStr;
			}
		} catch (ConnectTimeoutException ce) {
			result[0] = HTTP_TIMEOUT_ERROR;
			result[1] = ce.toString();
		} catch (Exception e) {
			result[0] = HTTP_UNKNOWN_ERROR;
			result[1] = e.toString();
		} finally {
			try {
				if (response != null) {
					response.close();
				}
				httpclient.close();
			} catch (IOException e) {
			}
		}
		return result;
	}

	/**
	 * get请求
	 * 
	 * @param url
	 *            请求url
	 * @param connectTimeout
	 *            超时时间，毫秒
	 * @return 数组 [0]:状态，[1]:返回信息
	 */
	public static String[] doGet(String url, int connectTimeout) {
		return doGet(url, null, connectTimeout);
	}

	/**
	 * post请求
	 * 
	 * @param url
	 *            请求url
	 * @param param
	 *            请求参数map集合
	 * @param connectTimeout
	 *            超时时间，毫秒
	 * @return 数组 [0]:状态，[1]:返回信息
	 */
	public static String[] doPost(String url, Map<String, String> param, int connectTimeout) {
		String[] result = new String[2];
		result[0] = HTTP_UNKNOWN_ERROR;
		result[1] = "";
		// 创建Httpclient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		try {
			RequestConfig rc = RequestConfig.copy(RequestConfig.DEFAULT).setConnectTimeout(connectTimeout).setSocketTimeout(connectTimeout).build();
			// 创建Http Post请求
			HttpPost httpPost = new HttpPost(url);
			// 创建参数列表
			if (param != null) {
				List<NameValuePair> paramList = new ArrayList<NameValuePair>();
				for (String key : param.keySet()) {
					paramList.add(new BasicNameValuePair(key, param.get(key)));
				}
				// 模拟表单
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList, "UTF-8");
				httpPost.setEntity(entity);
			}
			httpPost.setConfig(rc);
			// 执行http请求
			response = httpClient.execute(httpPost);
			String resultStr = EntityUtils.toString(response.getEntity(), "UTF-8");
			result[0] = HTTP_SUCCESS;
			result[1] = resultStr;
		} catch (ConnectTimeoutException ce) {
			result[0] = HTTP_TIMEOUT_ERROR;
			result[1] = ce.toString();
		} catch (Exception e) {
			result[0] = HTTP_UNKNOWN_ERROR;
			result[1] = e.toString();
		} finally {
			try {
				response.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return result;
	}

	/**
	 * post请求
	 * @param url 请求url
	 * @param connectTimeout 超时时间，毫秒
	 * @return
	 */
	public static String[] doPost(String url, int connectTimeout) {
		return doPost(url, null, connectTimeout);
	}

	/**
	 * post json请求
	 * 
	 * @param url
	 *            请求url
	 * @param param
	 *            json数据
	 * @param connectTimeout
	 *            超时时间，毫秒
	 * @return
	 */
	public static String[] doPostJson(String url, String json, int connectTimeout) {
		String[] result = new String[2];
		result[0] = HTTP_UNKNOWN_ERROR;
		result[1] = "";
		// 创建Httpclient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		try {
			RequestConfig rc = RequestConfig.copy(RequestConfig.DEFAULT).setConnectTimeout(connectTimeout).setSocketTimeout(connectTimeout).build();
			// 创建Http Post请求
			HttpPost httpPost = new HttpPost(url);
			// 创建请求内容
			StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
			httpPost.setEntity(entity);
			httpPost.setConfig(rc);
			// 执行http请求
			response = httpClient.execute(httpPost);
			String resultStr = EntityUtils.toString(response.getEntity(), "utf-8");
			result[0] = HTTP_SUCCESS;
			result[1] = resultStr;
		} catch (ConnectTimeoutException ce) {
			result[0] = HTTP_TIMEOUT_ERROR;
			result[1] = ce.toString();
		} catch (Exception e) {
			result[0] = HTTP_UNKNOWN_ERROR;
			result[1] = e.toString();
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
				}
			}

		}
		return result;
	}

	public static String[] excutePost(String targetURL, String urlParameters, boolean postAsParameter, Map<String, String> map) {
		
		logger.info("targetURL:"+targetURL+"urlParameters:"+urlParameters+"postAsParameter:"+postAsParameter+"map:"+map);
		
		String[] result = new String[2];
		result[0] = HTTP_UNKNOWN_ERROR;
		result[1] = "";

		CloseableHttpClient client = null;
		CloseableHttpResponse httpResponse = null;
		HttpPost post = null;
		InputStream is = null;
		ByteArrayOutputStream baos = null;

		try {

			int connectionTimeout = 60000;

			RequestConfig rc = RequestConfig.copy(RequestConfig.DEFAULT).setConnectTimeout(connectionTimeout).setSocketTimeout(connectionTimeout).build();

			client = HttpClientBuilder.create().build();
			post = new HttpPost(targetURL);

			if (postAsParameter) {
				String[] paralist = urlParameters.split("&");
				List<NameValuePair> nvps = new ArrayList<NameValuePair>();
				for (int i = 0; i < paralist.length; i++) {
					String[] para = paralist[i].split("=");
					if (para.length == 2) {
						nvps.add(new BasicNameValuePair(para[0].trim(), para[1].trim()));
					} else {
						// patch if the parameter has empty value
						nvps.add(new BasicNameValuePair(para[0].trim(), ""));
					}
				}
				post.setEntity(new UrlEncodedFormEntity(nvps));
			} else {
				post.setEntity(new StringEntity(urlParameters));
			}

			// header
			if (map != null) {
				for (String key : map.keySet()) {
					// post.setHeader("Connection", "close");
					post.setHeader(key, map.get(key));
				}
			}

			post.setConfig(rc);
			httpResponse = client.execute(post);

			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				is = httpResponse.getEntity().getContent();

				byte[] content = new byte[1024];
				baos = new ByteArrayOutputStream();
				int length = 0;

				while ((length = is.read(content)) != -1) {
					baos.write(content, 0, length); // read data from socket
				}

				String respStr = new String(baos.toByteArray(), "utf-8").trim();

				result[0] = HTTP_SUCCESS;
				result[1] = respStr;

			}
		} catch (ConnectTimeoutException ce) {
			result[0] = HTTP_TIMEOUT_ERROR;
			result[1] = ce.toString();

		} catch (Exception e) {
			result[0] = HTTP_UNKNOWN_ERROR;
			result[1] = e.toString();

		} finally {
			try {
				is.close();
				is = null;
			} catch (Exception ee) {
			}
			try {
				baos.close();
				baos = null;
			} catch (Exception ee) {
			}
			try {
				httpResponse.close();
				httpResponse = null;
			} catch (Exception ee) {
			}
			try {
				post.releaseConnection();
				post = null;
			} catch (Exception ee) {
			}
			try {
				client.close();
				client = null;
			} catch (Exception ee) {
			}
		}

		return result;
	}

	public static String[] excutePost(String targetURL, String urlParameters, boolean postAsParameter) {
		return excutePost(targetURL, urlParameters, postAsParameter, null);
	}
}
