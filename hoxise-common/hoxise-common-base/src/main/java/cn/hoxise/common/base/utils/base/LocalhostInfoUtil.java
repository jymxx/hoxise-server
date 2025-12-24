package cn.hoxise.common.base.utils.base;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootVersion;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

/**
 * @author 永远的十七岁
 * @description: 获取启动信息等
 * @date 2022/11/8 23:45
 */
@Component
public class LocalhostInfoUtil {

    @Value("${server.port}")
    private String port;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    public String getLocalhost(){
        return "http://localhost:"+port+contextPath;
    }

    public String getInternetInfo() throws UnknownHostException {
        InetAddress addr = InetAddress.getLocalHost();
        return "http://" + addr.getHostAddress() + ":"+port+contextPath;
    }

    public String getSwaggerUrl(){
        return "http://localhost:"+port+contextPath+"/doc.html";
    }

    public String getVersionInfo(){
        String jdkVersion = System.getProperty("java.version");
        String springBootVersion = SpringBootVersion.getVersion();
        return "JDK版本:"+jdkVersion+"; SpringBoot版本:"+springBootVersion;
    }

    public String getInfo(){
        Map<String, String> getenv = System.getenv();
        String domain= getenv.get("USERDOMAIN_ROAMINGPROFILE");
        String userName = getenv.get("USERNAME");
        String os = getenv.get("OS");
        return "计算机名: " + domain + "; 用户名： " + userName + "; 当前系统: "+os;
    }


}
