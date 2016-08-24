package com.smile.spider.http;


import org.apache.commons.collections.MapUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class HttpClientUtils {

    public static InputStream getStream(String url, Map<String, String> params, Map<String, String> headerMap) {
        try {
            CloseableHttpResponse response = get(url, params, headerMap);
            final HttpEntity entity = response.getEntity();
            if (entity != null) {
                return entity.getContent();
            }
        } catch (Exception e) {
//            throw new HttpClientException("地址访问失败！",e,url,params);
        }
        return null;
    }

    public static byte[] getByteArray(String url, Map<String, String> params, Map<String, String> headerMap) {
        CloseableHttpResponse response = null;
        try {
            response = get(url, params, headerMap);
            final HttpEntity entity = response.getEntity();
            if (entity != null) {
                return EntityUtils.toByteArray(entity);
            }
        } catch (Exception e) {
//            throw new HttpClientException("地址访问失败！",e,url,params);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (Exception e) {

            }
        }
        return null;
    }

    public static String getString(String url, Map<String, String> params, Map<String, String> headerMap, String charset) {
        CloseableHttpResponse response = null;
        try {
            response = get(url, params, headerMap);
            String result = null;
            HttpEntity entity = response.getEntity();
            if (entity != null) {
               // result = EntityUtils.toString(entity, charset);
                result = entityToString(entity,charset);
            }
            EntityUtils.consume(entity);
            return result;
        } catch (Exception e) {
//            throw new HttpClientException("地址访问失败！",e,url,params);
        } finally {
            try {
                if (response != null)
                    response.close();
            } catch (Exception e) {
                //ignore
            }
        }
        return null;
    }

    public static String getString(URI uri, Map<String, String> headerMap, String charset) {
        CloseableHttpResponse response = null;
        try {
            response = get(uri, headerMap);
            String result = null;
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                result = entityToString(entity,charset);
            }
            EntityUtils.consume(entity);
            return result;
        } catch (Exception e) {
//            throw new HttpClientException("地址访问失败！",e,uri,charset);
        } finally {
            try {
                if (response != null)
                    response.close();
            } catch (Exception e) {
                //ignore
            }
        }
        return null;
    }

    @SuppressWarnings("deprecation")
    public static String getString(String url, Map<String, String> params, Map<String, String> headerMap) {
        return getString(url, params, headerMap, HTTP.UTF_8);
    }

    public static String getString(String url, Map<String, String> params) {
        return getString(url, params, null);
    }

    public static String doGetStringWithCharset(String url, String charset) {
        return getString(url, null, null, charset);
    }

    public static String getString(String url) {
        return getString(url, null);
    }

    @SuppressWarnings("deprecation")
    public static String getString(URI uri) {
        return getString(uri, null, HTTP.UTF_8);
    }

    public static InputStream doPostStream(String url, Map<String, String> params, Map<String, String> headerMap) {
        try {
            CloseableHttpResponse response = post(url, params, headerMap);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                return entity.getContent();
            }
        } catch (Exception e) {
//            throw new HttpClientException("提交数据失败！",e,url,params);
        }
        return null;
    }

    public static String postString(String url, Map<String, String> params, Map<String, String> headerMap, String charset) {
        CloseableHttpResponse response = null;
        try {
            response = post(url, params, headerMap);
            String result = null;
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity, charset);
            }
            EntityUtils.consume(entity);
            return result;
        } catch (Exception e) {
//            throw new HttpClientException("提交数据失败！",e,url,params);
        } finally {
            try {
                if (response != null)
                    response.close();
            } catch (Exception e) {
                //ignore
            }
        }
        return null;
    }

    @SuppressWarnings("deprecation")
    public static String postString(String url, Map<String, String> params, Map<String, String> headerMap) {
        return postString(url, params, headerMap, HTTP.UTF_8);
    }

    public static String postString(String url, Map<String, String> params) {
        return postString(url, params, null);
    }

    public static String postStringWithCharset(String url, String charset) {
        return postString(url, null, null, charset);
    }

    public static String postString(String url) {
        return postString(url, null);
    }


    private static CloseableHttpResponse post(URI uri, Map<String, String> headerMap, HttpEntity entity) {
        CloseableHttpResponse response;
        try {
            CloseableHttpClient httpClient = HttpClientFactory.getHttpClient();
            HttpPost httpPost = new HttpPost(uri);
            if (MapUtils.isNotEmpty(headerMap)) {
                for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                    httpPost.addHeader(entry.getKey(), entry.getValue());
                }
            }
            if (entity != null) {
                httpPost.setEntity(entity);
            }
            response = httpClient.execute(httpPost);

            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                httpPost.abort();
            }
            return response;
        } catch (Exception e) {
//            throw new HttpClientException("提交数据失败！",e,uri);
        }
        return null;
    }

    private static CloseableHttpResponse post(URI uri, Map<String, String> headerMap) {
        return post(uri, headerMap, null);
    }

    private static CloseableHttpResponse post(String url, Map<String, String> params, Map<String, String> headerMap) {
        try {
            URIBuilder uriBuilder = new URIBuilder(url);
            if (MapUtils.isNotEmpty(params)) {
                List<NameValuePair> nvps = new ArrayList<NameValuePair>();
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    NameValuePair pair = new BasicNameValuePair(entry.getKey(),entry.getValue());
                    nvps.add(pair);
                }
                uriBuilder.setParameters(nvps);
            }
            return post(uriBuilder.build(), headerMap);
        } catch (Exception e) {
//            throw new HttpClientException("提交数据失败！",e,url,params);
        }
        return null;
    }

    private static CloseableHttpResponse get(URI uri, Map<String, String> headerMap) {
        CloseableHttpResponse response = null;
        try {
            CloseableHttpClient httpClient = HttpClientFactory.getHttpClient();
            HttpGet httpGet = new HttpGet(uri);
            if (MapUtils.isNotEmpty(headerMap)) {
                for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                    httpGet.addHeader(entry.getKey(), entry.getValue());
                }
            }
            response = httpClient.execute(httpGet);

            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                httpGet.abort();
            }
            return response;
        } catch (Exception e) {
//            throw new HttpClientException("地址访问失败！",e,uri);
        }
        return null;
    }

    private static CloseableHttpResponse get(String url, Map<String, String> params, Map<String, String> headerMap) {
        CloseableHttpResponse response = null;
        try {
            URIBuilder uriBuilder = new URIBuilder(url);
            if (MapUtils.isNotEmpty(params)) {
                List<NameValuePair> nvps = new ArrayList<NameValuePair>();
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    NameValuePair pair = new BasicNameValuePair(entry.getKey(), URLEncoder.encode(entry.getValue(), "utf-8"));
                    nvps.add(pair);
                }
                uriBuilder.setParameters(nvps);
            }
            response = get(uriBuilder.build(), headerMap);

            return response;
        } catch (Exception e) {
//            throw new HttpClientException("提交数据失败！",e,url,params);
        }
        return null;
    }

    public static String upload(String url, String strParaFileName, String strFilePath, Map<String, String> paramMap, String charset) throws IOException {
        CloseableHttpResponse response = null;
        String result = "";
        try {
            CloseableHttpClient httpClient = HttpClientFactory.getHttpClient();
            //请求处理页面
            HttpPost httppost = new HttpPost(url);
            //创建待处理的文件
            FileBody file = new FileBody(new File(strFilePath));

            //对请求的表单域进行填充
            MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
            multipartEntityBuilder.setCharset(Charset.forName("utf-8"));
            multipartEntityBuilder.addPart(strParaFileName, file);

            for (String key : paramMap.keySet()) {
                multipartEntityBuilder.addTextBody(key,paramMap.get(key));
            }

            //设置请求
            httppost.setEntity(multipartEntityBuilder.build());

            httppost.addHeader("User-Agent", "Mozilla/4.0");

            //执行
            response = httpClient.execute(httppost);

            int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode != 200) {
                httppost.abort();
            }

            if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                   result = entityToString(entity,null);
                }
                EntityUtils.consume(entity);
            }
        } catch (Exception e) {
//            throw new HttpClientException("上传文件失败！",e,url,strParaFileName,strFilePath,paramMap);
        } finally {
            try {
                if (response != null)
                    response.close();
            } catch (Exception e) {
                //ignore
            }
        }
        return result;
    }


    private static String entityToString(HttpEntity entity,String charsetName) throws IOException {

        Charset charSet = null;

        byte[] bytes = EntityUtils.toByteArray(entity);

        if(charsetName!=null){
            charSet = Charset.forName(charsetName);
        }else {

            ContentType contentType = ContentType.getOrDefault(entity);

            if (contentType != null)
                charSet = contentType.getCharset();

            // 如果头部中没有，需要 查看页面源码，这个方法虽然不能说完全正确，因为有些粗糙的网页编码者没有在页面中写头部编码信息
            if (charSet == null) {

                String regEx = "(?=<meta).*?(?<=charset=[\\'|\\\"]?)([[a-z]|[A-Z]|[0-9]|-]*)";

                Pattern p = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);

                Matcher m = p.matcher(new String(bytes));  // 默认编码转成字符串，因为我们的匹配中无中文，所以串中可能的乱码对我们没有影响

                boolean result = m.find();

                if (m.groupCount() == 1) {
                    charSet = Charset.forName(m.group(1));
                } else {
                    charSet = null;
                }
            }
        }

        if (charSet != null) {
           return  new String(bytes, charSet);
        }else
           return  new String(bytes,Charset.forName("utf-8"));  //默认utf-8?
    }
 
}
