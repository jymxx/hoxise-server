package cn.hoxise.common.log.service;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OperateLogBaseDTO {

    private Long userId;

    private String module;

    private String name;

    private Integer type;

    private String content;

    private String requestMethod;

    private String requestUrl;

    private String userIp;

    private String userAgent;

    private String javaMethod;

    private String javaMethodArgs;

    private LocalDateTime startTime;

    private Integer duration;

    private Integer resultCode;

    private String resultMsg;

    private String resultData;

}
