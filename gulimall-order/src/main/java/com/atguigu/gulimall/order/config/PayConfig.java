package com.atguigu.gulimall.order.config;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import com.github.wxpay.sdk.WXPayConfig;
import org.springframework.stereotype.Component;


public class PayConfig implements WXPayConfig{

    private byte[] certData;

    public PayConfig() throws Exception {
        File file = new File("D:\\java\\workspace\\apiclient_cert.p12");
        InputStream certStream = new FileInputStream(file);
        this.certData = new byte[(int) file.length()];
        certStream.read(this.certData);
        certStream.close();
    }

    public String getAppID() {
        return "wx74862e0dfcf69954";
    }

    public String getMchID() {
        return "1558950191";
    }

    public String getKey() {
        return "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb";
    }

    public InputStream getCertStream() {
        ByteArrayInputStream certBis = new ByteArrayInputStream(this.certData);
        return certBis;
    }

    public int getHttpConnectTimeoutMs() {
        return 8000;
    }

    public int getHttpReadTimeoutMs() {
        return 10000;
    }
}