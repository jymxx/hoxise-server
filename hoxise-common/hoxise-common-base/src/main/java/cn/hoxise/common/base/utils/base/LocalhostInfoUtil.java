package cn.hoxise.common.base.utils.base;

import org.springframework.boot.SpringBootVersion;

import java.util.Map;

/**
 * 获取环境信息工具
 *
 * @author hoxise
 * @since 2026/1/14 下午7:14
 */
public class LocalhostInfoUtil {

//    public static String printInfo(){
//        String commandLog = """
//
//                    --------------------------------------------------------------------------------
//                                        项目启动成功!!!
//                         本地访问地址: %s
//                         网络访问地址: %s
//                         Swagger地址: %s
//                         %s
//                         %s
//                    --------------------------------------------------------------------------------
//
//                    """.formatted(getLocalhost(), getInternetInfo(), getSwaggerUrl(), getVersionInfo(), getInfo());
//    }

//    public String getLocalhost(){
//        return "http://localhost:"+port+contextPath;
//    }
//
//    public String getInternetInfo() throws UnknownHostException {
//        InetAddress addr = InetAddress.getLocalHost();
//        return "http://" + addr.getHostAddress() + ":"+port+contextPath;
//    }
//
//    public String getSwaggerUrl(){
//        return "http://localhost:"+port+contextPath+"/doc.html";
//    }


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
