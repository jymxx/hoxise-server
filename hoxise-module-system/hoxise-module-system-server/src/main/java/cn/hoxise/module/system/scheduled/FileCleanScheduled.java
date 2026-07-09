package cn.hoxise.module.system.scheduled;

import cn.dev33.satoken.context.mock.SaTokenContextMockUtil;
import cn.dev33.satoken.stp.StpUtil;
import cn.hoxise.module.system.scheduled.properties.FileCleanProperties;
import cn.hoxise.module.system.service.file.SystemFileService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 文件清理定时任务
 *
 * @author hoxise
 * @since 2026/04/12
 */
@Component
@ConditionalOnProperty(prefix = "hoxise.file-storage.file-clean", name = "enabled", havingValue = "true")
@Slf4j
public class FileCleanScheduled {

    @Resource
    private SystemFileService systemFileService;

    @Resource
    private FileCleanProperties fileCleanProperties;

    /**
     * 清理过期文件
     */
    @Scheduled(cron = "${hoxise.file-storage.file-clean.cron}")
    public void cleanOrphanFiles() {
        SaTokenContextMockUtil.setMockContext(()->{
            // 模拟登录
            StpUtil.login("system-file-clean");
            log.info("开始执行过期文件清理任务");
            try {
                int count = systemFileService.cleanExpireFiles(fileCleanProperties.getExpireDays());
                log.info("过期文件清理任务完成，清理文件数: {}", count);
            } catch (Exception e) {
                log.error("过期文件清理任务执行失败", e);
            }finally {
                StpUtil.logout(); // 注销登录
            }
        });
    }
}