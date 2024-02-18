package com.itgrail.grail.codegen.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
public class HttpClientUtil {

	/**
	 * 封装HTTP GET方法
	 *
	 * @param
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String get(String url) throws ClientProtocolException, IOException {
		HttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet();
		// 设置请求和传输超时时间
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(60000)
				.setConnectTimeout(60000).build();
		httpGet.setConfig(requestConfig);
		httpGet.setURI(URI.create(url));
		HttpResponse response = httpClient.execute(httpGet);
		String httpEntityContent = getHttpEntityContent(response);
		httpGet.abort();
		return httpEntityContent;
	}

	/**
	 * 封装HTTP GET方法
	 *
	 * @param
	 * @param
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String get(String url, Map<String, String> paramMap)
			throws ClientProtocolException, IOException {
		HttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet();
		// 设置请求和传输超时时间
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(60000)
				.setConnectTimeout(60000).build();
		httpGet.setConfig(requestConfig);
		List<NameValuePair> formparams = setHttpParams(paramMap);
		String param = URLEncodedUtils.format(formparams, "UTF-8");
		httpGet.setURI(URI.create(url + "?" + param));
		HttpResponse response = httpClient.execute(httpGet);
		String httpEntityContent = getHttpEntityContent(response);
		httpGet.abort();
		return httpEntityContent;
	}

	/**
	 * 设置请求参数
	 *
	 * @param
	 * @return
	 */
	private static List<NameValuePair> setHttpParams(Map<String, String> paramMap) {
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		Set<Map.Entry<String, String>> set = paramMap.entrySet();
		for (Map.Entry<String, String> entry : set) {
			formparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
		return formparams;
	}

	/**
	 * 获得响应HTTP实体内容
	 *
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 */
	private static String getHttpEntityContent(HttpResponse response)
			throws IOException, UnsupportedEncodingException {
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			InputStream is = entity.getContent();
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			String line = br.readLine();
			StringBuilder sb = new StringBuilder();
			while (line != null) {
				sb.append(line + "\n");
				line = br.readLine();
			}
			return sb.toString();
		}
		return "";
	}

	/**
	 * post请求以及参数是json
	 * @param url
	 * @param jsonParams
	 * @return
	 */
	public static String doPostForJson(String url, String jsonParams) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		String responseContent = "";
		HttpPost httpPost = new HttpPost(url);
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(180 * 1000)
				.setConnectionRequestTimeout(180 * 1000).setSocketTimeout(180 * 1000)
				.setRedirectsEnabled(true).build();
		httpPost.setConfig(requestConfig);
		httpPost.setHeader("Content-Type", "application/json");
		try {
			httpPost.setEntity(new StringEntity(jsonParams,
					ContentType.create("application/json", "utf-8")));
			HttpResponse response = httpClient.execute(httpPost);
			if (response != null && response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				responseContent = EntityUtils.toString(entity, "utf-8");
			}
			if (response.getStatusLine().getStatusCode() == 500) {
				HttpEntity entity = response.getEntity();
				responseContent = EntityUtils.toString(entity, "utf-8");
			}
		}
		catch (Exception e) {
			log.error("call football add app fail,e{}", e);
		}
		finally {
			if (null != httpClient) {
				try {
					httpClient.close();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return responseContent;
	}

}