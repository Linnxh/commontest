package com.task.business;


import org.apache.commons.lang.StringUtils;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.Map;

public class HttpUtil {
    private static final int ConnectTimeout = 30000;
    private static final int ReadTimeout = 30000;

    public static String sendPost(String url) {
        return sendPost(url, null, "application/json;charset=utf-8", null);
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url   发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param, String contentType,
                                  String cookies) {
        PrintWriter out = null;
        InputStream in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            if (contentType != null && !contentType.isEmpty()) {
                conn.setRequestProperty("Content-Type", contentType); // 设置发
            }
            if (cookies != null && !cookies.isEmpty()) {
                conn.setRequestProperty("Cookie", cookies);
            }
            //把当前项目的消息id传递出去，构建分布式消息树
//            setHttpPostAttachment(url, conn);
            conn.setConnectTimeout(ConnectTimeout);
            conn.setReadTimeout(ReadTimeout);

            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            // in = new BufferedReader(
            // new InputStreamReader(conn.getInputStream(),"UTF-8"));
            // String line;
            // while ((line = in.readLine()) != null) {
            // result += line;
            // }
            in = conn.getInputStream();
            result = StreamUtils.copyToString(in, Charset.forName("utf-8"));
        } catch (Exception e) {
//             System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
//            throw new RuntimeException(getCauseBy(url, e, param));
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {

            }
        }
        return result;
    }

    /**
     * 通过post调用外部接口时添加附件
     *
     * @param url
     * @param conn
     */
//    private static void setHttpPostAttachment(String url, URLConnection conn) {
//        if (StringUtils.isEmpty(url) || !url.contains("/services/") || conn == null) {
//            return;
//        }
//        Map<String, String> catMessageIdsMap = getCatMessageIdsMap();
//        if (catMessageIdsMap == null || catMessageIdsMap.isEmpty()) {
//            return;
//        }
//        //由于nginx默认配置下,不允许header中的keyname中包含_,
//        //因此调用外部接口给header赋值时,这里的keyname都去掉了_
//        String rootId = "catRootMessageId";
//        String parentId = "catParentMessageId";
//        String childID = "catChildMessageId";
//        if (catMessageIdsMap.containsKey("_" + rootId)) {
//            conn.setRequestProperty(rootId, catMessageIdsMap.get("_" + rootId));
//        }
//        if (catMessageIdsMap.containsKey("_" + parentId)) {
//            conn.setRequestProperty(parentId, catMessageIdsMap.get("_" + parentId));
//        }
//        if (catMessageIdsMap.containsKey("_" + childID)) {
//            conn.setRequestProperty(childID, catMessageIdsMap.get("_" + childID));
//        }
//    }


}