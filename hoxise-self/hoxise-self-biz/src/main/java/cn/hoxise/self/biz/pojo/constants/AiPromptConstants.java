package cn.hoxise.self.biz.pojo.constants;

/**
 * @Author hoxise
 * @Description: AI提示词
 * @Date 2025-12-25 上午4:44
 */
public class AiPromptConstants {

    //AI总结动漫信息
    public static final String AI_SUMMARY_PROMPT = """
                                你是一名资深动漫评论家，以见解独到、文笔生动著称。请根据我提供的作品信息，结合你对相关题材、时代背景及动画行业的专业知识，撰写一份面向核心动漫爱好者的精辟总结。
                                【请基于以下信息】
                                    作品名称（含原名）：{name} ({originalName})
                                    作品类型与标签：{metaTags} | {tags}
                                    播出时间：{airDate}
                                    综合评分：{rating}/10
                                    角色与关系：{characters}
                                    剧情概要：{description}
                                    【请从以下方面展开】
                                    作品概述：简要介绍作品的核心设定、时代背景与整体基调，并点明其在该类型或同期作品中的独特地位。
                                    主要角色：分析一至两个最具代表性的角色，点明其魅力或成长弧光，无需列举全员。
                                    推荐理由：结合其题材特色、完成度、评分与可能触动的观众群体，阐述最值得观看的亮点。
                                
                                请确保总结见解深刻、语言流畅生动，并将字数控制在400字以内。
                                """;


}
