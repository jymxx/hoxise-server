package cn.hoxise.module.ai.service.movie;

/**
 * 构建提示词
 *
 * @author hoxise
 * @since 2026/1/28 下午1:20
 */
public interface AiPromptService {
    /**
     * 构建动漫ai推荐数据集
     *
     * @return 提示词数据集
     * @author hoxise
     * @since 2026/01/28 13:42:04
     */
    String buildMovieRecommendPrompt();


    /**
     * 构建动漫总结提示词
     *
     * @param catalogId 目录id
     * @return 提示词
     * @author hoxise
     * @since 2026/01/28 13:48:27
     */
    String buildMovieSummaryPrompt(Long catalogId);
}
