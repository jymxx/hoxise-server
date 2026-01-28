package cn.hoxise.common.security.operatelog.core.service;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * OperateLogBaseDTO 操作日志BaseDTO
 *
 * @author hoxise
 * @since 2026/01/14 07:10:50
 */
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
