package com.eason.here.HttpUtil;

import com.eason.here.util.LogUtil;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

/**
 * 上传文件到服务器
 * Created by Eason on 9/22/15.
 */
public class UpLoadFileRunable<T> implements Runnable {


        private final String url;
        private String filePath;
        private Map<String, String> map;
        private HttpResponseHandler httpResponseHandler;
        private Class<T> tClass;

        public UpLoadFileRunable(String url, final Map<String, String> map, String filePath, final HttpResponseHandler httpResponseHandler, final Class<T> tClass) {
            this.url = url;
            this.map = map;
            this.filePath = filePath;
            this.httpResponseHandler = httpResponseHandler;
            this.tClass = tClass;
        }

        @Override
        public void run() {
            try {
                final String newLine = "\r\n";
                final String boundaryPrefix = "--";

                /**
                 * 定义数据分隔线
                 */
                String BOUNDARY = "========7d4a6d158c9";

                /**
                 * url post参数
                 */
                String params = "";

                // 服务器的域名
                URL realUrl = new URL(url);
                HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
                // 设置为POST情
                conn.setRequestMethod("POST");
                // 发送POST请求必须设置如下两行
                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.setUseCaches(false);
                // 设置请求头参数
                conn.setRequestProperty("connection", "Keep-Alive");
                conn.setRequestProperty("Charsert", "UTF-8");
                conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

                /**
                 * 向HttpUrlConnection 中添加参数
                 */
                Iterator<String> iter = map.keySet().iterator();
                while (iter.hasNext()) {
                    String key = iter.next();
                    String value = map.get(key);
                    conn.addRequestProperty(key,value);
                }

                /**
                 * 数据输出流
                 */
                DataOutputStream outputStream = new DataOutputStream(conn.getOutputStream());
                File file = new File(filePath);
                StringBuffer sb = new StringBuffer();
                sb.append(boundaryPrefix);
                sb.append(BOUNDARY);
                sb.append(newLine);
                sb.append("Content-Disposition: form-data;name=\"file\";filename=\"" + filePath
                        + "\"" + newLine);
                sb.append("Content-Type:application/octet-stream");
                // 参数头设置完以后需要两个换行，然后才是参数内容
                sb.append(newLine);
                sb.append(newLine);
                // 将参数头的数据写入到输出流中
                outputStream.write(sb.toString().getBytes());

                /**
                 * 数据输入流,用于读取文件数据
                 */
                DataInputStream in = new DataInputStream(new FileInputStream(
                        file));
                byte[] bufferOut = new byte[1024];
                int bytes = 0;
                // 每次读1KB数据,并且将文件数据写入到输出流中
                while ((bytes = in.read(bufferOut)) != -1) {
                    outputStream.write(bufferOut, 0, bytes);
                }
                // 最后添加换行
                outputStream.write(newLine.getBytes());
                in.close();

                // 定义最后数据分隔线，即--加上BOUNDARY再加上--。
                byte[] end_data = (newLine + boundaryPrefix + BOUNDARY + boundaryPrefix + newLine)
                        .getBytes();
                // 写上结尾标识
                outputStream.write(end_data);
                outputStream.flush();
                outputStream.close();

                /**
                 * 接收服务器的响应
                 */
                InputStream is = conn.getInputStream();
                int length;
                StringBuffer b = new StringBuffer();
                while ((length = is.read()) != -1) {
                    b.append((char) length);
                }

                String response = new String(b.toString().getBytes(),"UTF-8");
                httpResponseHandler.response(response,tClass);

            } catch (Exception e) {
                LogUtil.e("UpLoadFileRunable","Upload file exception");
                e.printStackTrace();
            }

        }

}
