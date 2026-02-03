package cn.hoxise.module.self.service;

import cn.hoxise.module.self.controller.dto.RwTransAcceptFileDTO;
import cn.hoxise.module.self.controller.dto.RwTransPreCheckDTO;
import cn.hoxise.module.self.controller.dto.RwTransStartDTO;
import cn.hoxise.module.self.mq.message.RwTransMessage;
import cn.hoxise.module.self.pojo.bo.RwSubTransBO;

import java.util.List;

/**
 * 环世界
 *
 * @author hoxise
 * @since 2026/2/3 上午9:41
 */
public interface RwTransService {

    /**
     * 预检
     *
     * @param dto 参数
     * @return 预检查ID
     * @author hoxise
     * @since 2026/02/03 19:42:33
     */
    String preCheck(RwTransPreCheckDTO dto);

    /**
     * 接收文件
     *
     * @param dto 参数
     * @author hoxise
     * @since 2026/02/03 19:42:35
     */
    void acceptFile(RwTransAcceptFileDTO dto);

    /**
     * 开始翻译
     *
     * @param dto 参数
     * @author hoxise
     * @since 2026/02/03 19:42:36
     */
    void startTrans(RwTransStartDTO dto);

    /**
     * getTransList 获取翻译状态
     *
     * @param preCheckId 预检查ID
     * @author hoxise
     * @since 2026/02/03 20:38:36
     */
    List<RwSubTransBO> getTransList(String preCheckId);

    /**
     * getTransPrompt 获取系统提示词
     *
     * @author hoxise
     * @since 2026/02/03 20:38:45
     */
    String getTransPrompt();

    /**
     * handleTrans
     *
     * @param message 消息
     * @author hoxise
     * @since 2026/02/03 20:38:10
     */
    void handleTrans(RwTransMessage message);
}
