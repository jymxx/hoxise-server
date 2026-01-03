package cn.hoxise.self.movie.pojo.dto;

import cn.hoxise.self.movie.pojo.enums.bangumi.BangumiSubjectTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author hoxise
 * @Description: Bangumi搜索请求参数传输对象
 * @Date 2025-12-23 上午9:23
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BangumiSearchSubjectReq {
    /**
     * 搜索关键词（必需）
     */
    private String keyword;

    /**
     * 排序规则
     * - match: meilisearch 的默认排序，按照匹配程度
     * - heat: 收藏人数
     * - rank: 排名由高到低
     * - score: 评分
     */
    private String sort = "match";

    /**
     * 搜索过滤条件
     */
    private BangumiSearchFilter filter;

    /**
     * Bangumi搜索过滤条件
     */
    @Data
    @Builder
    public static class BangumiSearchFilter {
        /**
         * 条目类型，参照 SubjectType enum，多值之间为 `或` 的关系 {@link BangumiSubjectTypeEnum}
         */
        private List<Integer> type;

        /**
         * 公共标签。多个值之间为 `且` 关系。可以用 `-` 排除标签。比如 `-科幻` 可以排除科幻标签
         */
        private List<String> meta_tags;

        /**
         * 标签，可以多次出现。多值之间为 `且` 关系
         */
        private List<String> tag;

        /**
         * 播出日期/发售日期，日期必需为 `YYYY-MM-DD` 格式。多值之间为 `且` 关系
         */
        private List<String> air_date;

        /**
         * 用于搜索指定评分的条目，多值之间为 `且` 关系
         */
        private List<String> rating;

        /**
         * 用于按照评分人数筛选条目，多值之间为 `且` 关系，格式与 rating 相同
         */
        private List<String> rating_count;

        /**
         * 用于搜索指定排名的条目，多值之间为 `且` 关系
         */
        private List<String> rank;

        /**
         * NSFW内容过滤
         * 无权限的用户会直接忽略此字段，不会返回R18条目
         * 默认或者 null 会返回包含 R18 的所有搜索结果
         * true 只会返回 R18 条目
         * false 只会返回非 R18 条目
         */
        private Boolean nsfw;
    }
}
