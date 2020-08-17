package com.zlsoft.patten;

/**
 * @version 1.0.0
 * @RESTful：Create-post Read-get update-put/path delete-delete
 * @package: com.zlsoft.patten
 * @ClassName: Downloader.java
 * @author: L.Z QQ.191288065@qq.com
 * @Description 广力年报-年报基层框架API
 * @createTime 2020年07月14日 14:54:00
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Downloader {
    /**
     * 抓取一个网页内容：
     * @return
     * @throws IOException
     */
    public static List<String> download() throws IOException {
        HttpURLConnection conn = (HttpURLConnection) new URL("http://www.163.com").openConnection();
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }
}
