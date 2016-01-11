package com.eason.marker.http_util;

import com.eason.marker.util.LogUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * 上传文件到服务器
 * Created by Eason on 9/22/15.
 */
public class UpLoadFileRunable<T> implements Runnable {

    private final String TAG = "UpLoadFileRunable";
    private final String url;
    private String filePath;
    private String fileType;
    private Map<String, String> map;
    private HttpResponseHandler httpResponseHandler;
    private Class<T> tClass;

    /**
     * 上传文件到服务器
     *
     * @param url
     * @param map 传的参数
     * @param filePath 文件路径
     * @param fileType 上传文件类型
     * @param httpResponseHandler
     * @param tClass
     */
    public UpLoadFileRunable(String url, final Map<String, String> map, String filePath, String fileType, final HttpResponseHandler httpResponseHandler, final Class<T> tClass) {
        this.url = url;
        this.map = map;
        this.filePath = filePath;
        this.fileType = fileType;
        this.httpResponseHandler = httpResponseHandler;
        this.tClass = tClass;
    }

    @Override
    public void run() {
        try {

            String BOUNDARY = "----WebKitFormBoundaryT1HoybnYeFOGFlBR";

            StringBuilder sb = new StringBuilder();
            for (String key : map.keySet()) {
                sb.append("--" + BOUNDARY + "\r\n");
                sb.append("Content-Disposition: form-data; name=\"" + key + "\""
                        + "\r\n");
                sb.append("\r\n");
                sb.append(map.get(key) + "\r\n");
            }

            File file = new File(filePath);
            String newFileName = file.getName();

            /**
             * 上传文件的头
             */
            sb.append("--" + BOUNDARY + "\r\n");
            sb.append("Content-Disposition: form-data; name=\"" + fileType
                    + "\"; filename=\"" + newFileName + "\"" + "\r\n");
            sb.append("Content-Type: image/jpeg" + "\r\n");
            sb.append("\r\n");

            byte[] headerInfo = sb.toString().getBytes("UTF-8");
            byte[] endInfo = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("UTF-8");
            LogUtil.d(TAG, sb.toString());

            URL realUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type",
                    "multipart/form-data; boundary=" + BOUNDARY);
            conn.setRequestProperty("Content-Length", String
                    .valueOf(headerInfo.length + file.length()
                            + endInfo.length));

            conn.setDoOutput(true);

            OutputStream out = conn.getOutputStream();
            InputStream in = new FileInputStream(file);
            out.write(headerInfo);

            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) != -1)
                out.write(buf, 0, len);

            out.write(endInfo);
            in.close();
            out.close();

            if (conn.getResponseCode() == 200) {
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

            } else {
                LogUtil.e(TAG, "Response Code : " + conn.getResponseCode());
            }
        } catch (Exception e) {
            LogUtil.e("UpLoadFileRunable","Upload file exception "+e);
            e.printStackTrace();
        }

    }

}
