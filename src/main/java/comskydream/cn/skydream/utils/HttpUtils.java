package comskydream.cn.skydream.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;

import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import reactor.util.annotation.Nullable;


import javax.net.ssl.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jayson
 * @date 2020/9/18 10:45
 */
@Component
@Slf4j
public class HttpUtils {

    @Autowired
    private PoolingHttpClientConnectionManager connMgr;

    private RequestConfig requestConfig;

    private int MAX_TIMEOUT;

    /**
     * 构造一个默认20000ms超时的链接
     */
    public HttpUtils() {
        this.MAX_TIMEOUT = 20000;
        initRequestConfig();
    }

    public HttpUtils(int MAX_TIMEOUT) {
        this.MAX_TIMEOUT = MAX_TIMEOUT;
        initRequestConfig();
    }

    /**
     * 初始化请求配置
     */
    private void initRequestConfig() {
        RequestConfig.Builder configBuilder = RequestConfig.custom();
        // 设置连接超时
        configBuilder.setConnectTimeout(MAX_TIMEOUT);
        // 设置读取超时
        configBuilder.setSocketTimeout(MAX_TIMEOUT);
        // 设置从连接池获取连接实例的超时
        configBuilder.setConnectionRequestTimeout(MAX_TIMEOUT);
        requestConfig = configBuilder.build();
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public PoolingHttpClientConnectionManager getPoolingHttpClientConnectionManager() {
        PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager();
        poolingHttpClientConnectionManager.setMaxTotal(100);
        poolingHttpClientConnectionManager.setDefaultMaxPerRoute(20);
        poolingHttpClientConnectionManager.setValidateAfterInactivity(1000);
        return poolingHttpClientConnectionManager;
    }

    /**
     * get请求不带参数
     *
     * @param apiUrl 请求地址
     * @return
     * @throws Exception
     */
    public String doGet(String apiUrl) throws Exception {
        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(connMgr).setDefaultRequestConfig(requestConfig)
                .build();
        String httpStr = null;
        CloseableHttpResponse response = null;
        try {
            HttpGet httpGet = new HttpGet(apiUrl);
            response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            if (entity == null) {
                return null;
            }
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                httpStr = EntityUtils.toString(entity, "utf-8");
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            if (response != null) {
                EntityUtils.consume(response.getEntity());
            }
        }
        return httpStr;
    }

    /**
     * get请求，携带参数
     *
     * @param apiUrl 请求地址
     * @param param  请求参数 k-v形式
     * @return
     * @throws Exception
     */
    public String doGet(String apiUrl, Map<String, Object> param) throws Exception {
        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(connMgr).setDefaultRequestConfig(requestConfig)
                .build();
        String httpStr = null;
        CloseableHttpResponse response = null;
        try {
            List<NameValuePair> pairList = new ArrayList<>();
            for (Map.Entry<String, Object> entry : param.entrySet()) {
                NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry.getValue().toString());
                pairList.add(pair);
            }
            String s = URLEncodedUtils.format(pairList, Consts.UTF_8).replaceAll("[+]", "");
            HttpGet httpGet = new HttpGet(URI.create(apiUrl + "?" + s));
            httpGet.addHeader("Content-type", "application/x-www-form-urlencoded");
            response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            if (entity == null) {
                return null;
            }
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                httpStr = EntityUtils.toString(entity, "utf-8");
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            if (response != null) {
                EntityUtils.consume(response.getEntity());
            }
        }
        return httpStr;
    }

    /**
     * 发送 POST 请求（HTTP），K-V形式
     *
     * @param apiUrl API接口URL
     * @param params 参数map
     * @return
     */
    public String doPost(String apiUrl, Map<String, Object> params) throws IOException {
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connMgr).setDefaultRequestConfig(requestConfig).build();
        String httpStr = null;

        CloseableHttpResponse response = null;
        try {
            HttpPost httpPost = new HttpPost(apiUrl);
            List<NameValuePair> pairList = new ArrayList<NameValuePair>(params.size());
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry.getValue().toString());
                pairList.add(pair);
            }
            httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset.forName("UTF-8")));
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            if (entity == null) {
                return null;
            }
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                httpStr = EntityUtils.toString(entity, "UTF-8");
            }

        } catch (IOException e) {
            throw e;
        } finally {
            if (response != null) {
                EntityUtils.consume(response.getEntity());

            }
        }
        return httpStr;
    }

    /**
     * 发送 POST 请求（HTTP），K-V形式,并且携带header
     *
     * @param apiUrl API接口URL
     * @param params 参数map
     * @param header 请求头参数
     * @return
     */
    public String doPost(String apiUrl, Map<String, Object> params, Map<String, Object> header) throws IOException {

        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connMgr).setDefaultRequestConfig(requestConfig).build();
        String httpStr = null;
        CloseableHttpResponse response = null;
        try {
            HttpPost httpPost = new HttpPost(apiUrl);
            List<NameValuePair> pairList = new ArrayList<NameValuePair>(params.size());
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry.getValue().toString());
                pairList.add(pair);
            }
            for (Map.Entry<String, Object> entry : header.entrySet()) {
                httpPost.setHeader(entry.getKey(), entry.getValue().toString());
            }
            httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
            httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            httpPost.setEntity(new UrlEncodedFormEntity(pairList, Consts.UTF_8));
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            if (entity == null) {
                return null;
            }
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                httpStr = EntityUtils.toString(entity, "UTF-8");
            }

        } catch (IOException e) {
            throw e;
        } finally {
            if (response != null) {
                EntityUtils.consume(response.getEntity());

            }
        }
        return httpStr;
    }


    /**
     * 发送 SSL POST 请求（HTTPS），K-V形式
     *
     * @param apiUrl API接口URL
     * @param params 参数map
     * @return
     */
    public String doPostSSL(String apiUrl, Map<String, Object> params) throws IOException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
        CloseableHttpClient httpClient = HttpClients.custom().
                setSSLSocketFactory(createSSLConnSocketFactory()).
                setConnectionManager(connMgr).
                setDefaultRequestConfig(requestConfig).setConnectionManagerShared(true).build();
        CloseableHttpResponse response = null;
        String httpStr = null;

        try {
            HttpPost httpPost = new HttpPost(apiUrl);
            List<NameValuePair> pairList = new ArrayList<NameValuePair>(params.size());
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry.getValue().toString());
                pairList.add(pair);
            }
            httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset.forName("utf-8")));
            response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                return null;
            }
            HttpEntity entity = response.getEntity();
            if (entity == null) {
                return null;
            }
            httpStr = EntityUtils.toString(entity, "utf-8");
        } catch (IOException e) {
            throw e;
        } finally {
            if (response != null) {
                EntityUtils.consume(response.getEntity());
            }
        }
        return httpStr;
    }

    /**
     * 发送 SSL POST 请求（HTTPS），JSON形式
     *
     * @param apiUrl API接口URL
     * @param json   JSON对象
     * @return
     * @throws KeyStoreException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    public String doPostSSL(String apiUrl, Object json) throws IOException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
        log.debug("URL--->" + apiUrl + "   jsonBody " + json);
        CloseableHttpClient httpClient = HttpClients.custom().
                setSSLSocketFactory(createSSLConnSocketFactory()).
                setConnectionManager(connMgr).
                setDefaultRequestConfig(requestConfig).setConnectionManagerShared(true).build();
        CloseableHttpResponse response = null;
        String httpStr = null;

        try {
            HttpPost httpPost = new HttpPost(apiUrl);
            // 解决中文乱码问题
            String jsonStr = json.toString();
            StringEntity stringEntity = new StringEntity(jsonStr, "UTF-8");
            stringEntity.setContentEncoding("UTF-8");
            stringEntity.setContentType("application/json");
            httpPost.setEntity(stringEntity);
            response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                return null;
            }
            HttpEntity entity = response.getEntity();
            if (entity == null) {
                return null;
            }
            httpStr = EntityUtils.toString(entity, "utf-8");
        } catch (IOException e) {
            throw e;
        } finally {
            if (response != null) {
                EntityUtils.consume(response.getEntity());
            }
        }
        return httpStr;
    }

    /**
     * 发送 POST 请求（HTTP），不带输入数据
     *
     * @param apiUrl 请求地址
     * @return
     */
    public String doPost(String apiUrl) throws IOException {
        return doPost(apiUrl, new HashMap<String, Object>());
    }

    /**
     * 发送 POST 请求（HTTP），JSON形式
     *
     * @param apiUrl
     * @param json   json对象
     * @return
     */
    public String doPost(String apiUrl, Object json) throws IOException {
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connMgr).setDefaultRequestConfig(requestConfig).build();
        String httpStr = null;
        CloseableHttpResponse response = null;

        try {
            HttpPost httpPost = new HttpPost(apiUrl);
            StringEntity stringEntity = new StringEntity(json.toString(), "UTF-8");// 解决中文乱码问题
            stringEntity.setContentEncoding("UTF-8");
            stringEntity.setContentType("application/json");
            httpPost.setEntity(stringEntity);
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            if (entity == null) {
                return null;
            }
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                httpStr = EntityUtils.toString(entity, "UTF-8");
            }
        } catch (IOException e) {
            throw e;
        } finally {
            if (response != null) {
                EntityUtils.consume(response.getEntity());

            }
        }
        return httpStr;
    }

    /**
     * 创建SSL安全连接
     *
     * @return
     * @throws KeyStoreException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    private SSLConnectionSocketFactory createSSLConnSocketFactory() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
        SSLConnectionSocketFactory sslsf = null;
        SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {


            @Override
            public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                return true;
            }
        }).build();
        sslsf = new SSLConnectionSocketFactory(sslContext, new HostnameVerifier() {

            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }

        });

        return sslsf;
    }


    public  HttpResponse doPost(String host, String path, String method,
                                      Map<String, Object> headers,
                                      Map<String, Object> querys,
                                      Map<String, Object> bodys) throws Exception {
        HttpClient httpClient = wrapClient(host);
        HttpPost request = new HttpPost(buildUrl(host, path, querys));
        for (Map.Entry<String, Object> e : headers.entrySet()) {
            request.addHeader(e.getKey(), e.getValue().toString());
        }
        if (bodys != null) {
            List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();

            for (String key : bodys.keySet()) {
                nameValuePairList.add(new BasicNameValuePair(key, bodys.get(key).toString()));
            }
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(nameValuePairList, "utf-8");
            formEntity.setContentType("application/x-www-form-urlencoded; charset=UTF-8");
            request.setEntity(formEntity);
        }
        return httpClient.execute(request);
    }

    /**
     * Post String
     *
     * @param host
     * @param path
     * @param headers
     * @param querys
     * @param body
     * @return
     * @throws Exception
     */
    public HttpResponse doPost(String host, String path,
                               Map<String, Object> headers,
                               Map<String, Object> querys,
                               String body)
            throws Exception {
        HttpClient httpClient = wrapClient(host);

        HttpPost request = new HttpPost(buildUrl(host, path, querys));
        for (Map.Entry<String, Object> e : headers.entrySet()) {
            request.addHeader(e.getKey(), e.getValue().toString());
        }

        if (StringUtils.isNotBlank(body)) {
            request.setEntity(new StringEntity(body, "utf-8"));
        }

        return httpClient.execute(request);
    }

    /**
     * Post stream
     *
     * @param host
     * @param path
     * @param headers
     * @param querys
     * @param body
     * @return
     * @throws Exception
     */
    public HttpResponse doPost(String host, String path,
                               Map<String, Object> headers,
                               Map<String, Object> querys,
                               byte[] body)
            throws Exception {
        HttpClient httpClient = wrapClient(host);

        HttpPost request = new HttpPost(buildUrl(host, path, querys));
        for (Map.Entry<String, Object> e : headers.entrySet()) {
            request.addHeader(e.getKey(), e.getValue().toString());
        }

        if (body != null) {
            request.setEntity(new ByteArrayEntity(body));
        }

        return httpClient.execute(request);
    }

    /**
     * Put String
     *
     * @param host
     * @param path
     * @param method
     * @param headers
     * @param querys
     * @param body
     * @return
     * @throws Exception
     */
    public HttpResponse doPut(String host, String path, String method,
                              Map<String, Object> headers,
                              Map<String, Object> querys,
                              String body)
            throws Exception {
        HttpClient httpClient = wrapClient(host);

        HttpPut request = new HttpPut(buildUrl(host, path, querys));
        for (Map.Entry<String, Object> e : headers.entrySet()) {
            request.addHeader(e.getKey(), e.getValue().toString());
        }

        if (StringUtils.isNotBlank(body)) {
            request.setEntity(new StringEntity(body, "utf-8"));
        }

        return httpClient.execute(request);
    }

    /**
     * Put stream
     *
     * @param host
     * @param path
     * @param method
     * @param headers
     * @param querys
     * @param body
     * @return
     * @throws Exception
     */
    public HttpResponse doPut(String host, String path, String method,
                              Map<String, Object> headers,
                              Map<String, Object> querys,
                              byte[] body)
            throws Exception {
        HttpClient httpClient = wrapClient(host);

        HttpPut request = new HttpPut(buildUrl(host, path, querys));
        for (Map.Entry<String, Object> e : headers.entrySet()) {
            request.addHeader(e.getKey(), e.getValue().toString());
        }

        if (body != null) {
            request.setEntity(new ByteArrayEntity(body));
        }

        return httpClient.execute(request);
    }

    /**
     * Delete
     *
     * @param host
     * @param path
     * @param method
     * @param headers
     * @param querys
     * @return
     * @throws Exception
     */
    public HttpResponse doDelete(String host, String path, String method,
                                 Map<String, Object> headers,
                                 Map<String, Object> querys)
            throws Exception {
        HttpClient httpClient = wrapClient(host);

        HttpDelete request = new HttpDelete(buildUrl(host, path, querys));
        for (Map.Entry<String, Object> e : headers.entrySet()) {
            request.addHeader(e.getKey(), e.getValue().toString());
        }

        return httpClient.execute(request);
    }

    private String buildUrl(String host, String path, Map<String, Object> querys) throws UnsupportedEncodingException {
        StringBuilder sbUrl = new StringBuilder();
        sbUrl.append(host);
        if (!StringUtils.isBlank(path)) {
            sbUrl.append(path);
        }
        if (null != querys) {
            StringBuilder sbQuery = new StringBuilder();
            for (Map.Entry<String, Object> query : querys.entrySet()) {
                if (0 < sbQuery.length()) {
                    sbQuery.append("&");
                }
                if (StringUtils.isBlank(query.getKey()) && !StringUtils.isBlank(query.getValue().toString())) {
                    sbQuery.append(query.getValue());
                }
                if (!StringUtils.isBlank(query.getKey())) {
                    sbQuery.append(query.getKey());
                    if (!StringUtils.isBlank(query.getValue().toString())) {
                        sbQuery.append("=");
                        sbQuery.append(URLEncoder.encode(query.getValue().toString(), "utf-8"));
                    }
                }
            }
            if (0 < sbQuery.length()) {
                sbUrl.append("?").append(sbQuery);
            }
        }

        return sbUrl.toString();
    }

    private HttpClient wrapClient(String host) {
        HttpClient httpClient = new DefaultHttpClient();
        if (host.startsWith("https://")) {
            sslClient(httpClient);
        }

        return httpClient;
    }

    private void sslClient(HttpClient httpClient) {
        try {
            SSLContext ctx = SSLContext.getInstance("TLS");
            X509TrustManager tm = new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkClientTrusted(X509Certificate[] xcs, String str) {

                }

                public void checkServerTrusted(X509Certificate[] xcs, String str) {

                }
            };
            ctx.init(null, new TrustManager[]{tm}, null);
            SSLSocketFactory ssf = new SSLSocketFactory(ctx);
            ssf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            ClientConnectionManager ccm = httpClient.getConnectionManager();
            SchemeRegistry registry = ccm.getSchemeRegistry();
            registry.register(new Scheme("https", 443, ssf));
        } catch (KeyManagementException ex) {
            throw new RuntimeException(ex);
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }
    }


}
