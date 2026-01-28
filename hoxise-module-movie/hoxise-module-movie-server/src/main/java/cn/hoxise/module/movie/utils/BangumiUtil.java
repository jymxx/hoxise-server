package cn.hoxise.module.movie.utils;

import cn.hoxise.common.base.exception.ServiceException;
import cn.hoxise.common.base.utils.img.ImgUtil;
import cn.hoxise.common.file.pojo.FileStorageDTO;
import cn.hoxise.common.file.utils.FileStorageUtil;
import cn.hoxise.module.movie.pojo.constants.MovieConstants;
import cn.hoxise.module.movie.pojo.dto.BangumiCharacterResponse;
import cn.hoxise.module.movie.pojo.dto.BangumiEpisodesResponse;
import cn.hoxise.module.movie.pojo.dto.BangumiSearchSubjectReq;
import cn.hoxise.module.movie.pojo.dto.BangumiSearchSubjectResponse;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * BangumiUtil
 *
 * @author hoxise
 * @since 2026/01/14 15:02:30
 */
@Slf4j
@Component
public class BangumiUtil {

    private static final String BANGUMI_API_URL = "https://api.bgm.tv/v0/";

    @Resource private FileStorageUtil fileStorageUtil;

    /**
     * 根据req查询数据
     *
     * @param req 请求参数
     * @return Bangumi查询条目结果
     * @author hoxise
     * @since 2026/01/14 15:02:37
     */
    public static BangumiSearchSubjectResponse searchSubjects(BangumiSearchSubjectReq req){
        try{
            String body = HttpUtil.createPost(BANGUMI_API_URL+"search/subjects")
                    .body(JSONObject.toJSONString(req))
                    .execute()
                    .body();
            BangumiSearchSubjectResponse response = JSONObject.parseObject(body, BangumiSearchSubjectResponse.class);
            log.info(response.toString());
            return response;
        }catch (Exception e){
            log.error("Bangumi API 调用异常:{0}",e);
            throw new ServiceException("Bangumi API 调用异常");
        }
    }

    /**
     * 根据id查询指定条目
     *
     * @param subjectId 条目id
     * @return Bangumi条目信息
     * @author hoxise
     * @since 2026/01/14 15:02:46
     */
    public static BangumiSearchSubjectResponse.Subject getSubject(String subjectId){
        try{
            String body = HttpUtil.createGet(BANGUMI_API_URL+"subjects/"+subjectId)
                    .execute()
                    .body();
            BangumiSearchSubjectResponse.Subject subject = JSONObject.parseObject(body, BangumiSearchSubjectResponse.Subject.class);
            log.info(subject.toString());
            return subject;
        }catch (Exception e){
            log.error("Bangumi API 调用异常:{0}",e);
            throw new ServiceException("Bangumi API 调用异常");
        }
    }

    /**
     * 根据id查询指定条目角色
     *
     * @param subjectId 条目id
     * @return 角色
     * @author hoxise
     * @since 2026/01/14 15:03:34
     */
    public static List<BangumiCharacterResponse.CharacterInfo> getCharacter(String subjectId){
        try{
            String body = HttpUtil.createGet(BANGUMI_API_URL+"subjects/"+subjectId+"/characters")
                    .execute()
                    .body();
            List<BangumiCharacterResponse.CharacterInfo> characters = JSONArray.parseArray(body, BangumiCharacterResponse.CharacterInfo.class);
            log.info(characters.toString());
            return characters;
        }catch (Exception e){
            log.error("Bangumi API 调用异常:{0}",e);
            throw new ServiceException("Bangumi API 调用异常");
        }
    }

    /**
     * 根据id查询指定条目章节
     *
     * @param subjectId 条目id
     * @return Bangumi剧集信息响应类
     * @author hoxise
     * @since 2026/01/14 15:03:46
     */
    public static BangumiEpisodesResponse getEpisodes(String subjectId){
        try{
            String body = HttpUtil.createGet(BANGUMI_API_URL+"episodes")
                    .form("subject_id", subjectId)
                    .execute()
                    .body();
            BangumiEpisodesResponse episodes = JSONObject.parseObject(body, BangumiEpisodesResponse.class);
            log.info(episodes.toString());
            return episodes;
        }catch (Exception e){
            log.error("Bangumi API 调用异常:{0}",e);
            throw new ServiceException("Bangumi API 调用异常");
        }
    }

    /**
     * 处理图片 返回存储object地址
     *
     * @param url bangumi的在线网络图片地址
     * @param subjectName 名称
     * @return 存储object地址
     * @author hoxise
     * @since 2026/01/14 15:03:59
     */
    public String handleImgBangumi(String url, String subjectName){
        if (StrUtil.isBlank(url)){
            return "";
        }
        //截取后缀
        String filename = subjectName +"_Bangumi_Img.jpg";
        FileStorageDTO fileStorageDTO = fileStorageUtil.uploadFile(ImgUtil.downloadImg(url), MovieConstants.BANGUMI_MINIO_FLODER, filename);
        return fileStorageDTO.getObjectName();
    }
}
