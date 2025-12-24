package cn.hoxise.self.biz.utils;

import cn.hoxise.common.base.exception.ServiceException;
import cn.hoxise.common.base.utils.img.ImgUtil;
import cn.hoxise.common.file.api.dto.FileStorageDTO;
import cn.hoxise.common.file.utils.FileStorageUtil;
import cn.hoxise.self.biz.pojo.constants.MovieConstants;
import cn.hoxise.self.biz.pojo.dto.BangumiCharacterResponse;
import cn.hoxise.self.biz.pojo.dto.BangumiEpisodesResponse;
import cn.hoxise.self.biz.pojo.dto.BangumiSearchSubjectReq;
import cn.hoxise.self.biz.pojo.dto.BangumiSearchSubjectResponse;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author hoxise
 * @Description: Bangumi
 * @Date 2025-12-23 上午2:22
 */
@Slf4j
@Component
public class BangumiUtil {

    private static final String BANGUMI_API_URL = "https://api.bgm.tv/v0/";

    /**
     * @description: 根据req查询数据
     * @author: hoxise
     * @date: 2025/12/23 上午10:58
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
     * @description: 根据id查询指定条目
     * @author: hoxise
     * @date: 2025/12/23 上午10:58
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
     * @description: 根据id查询指定条目角色
     * @author: hoxise
     * @date: 2025/12/23 上午10:58
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
     * @description: 根据id查询指定条目章节
     * @author: hoxise
     * @date: 2025/12/23 上午10:58
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
     * @Author: hoxise
     * @Description: 处理图片 返回本地minio地址
     * @Date: 2025/12/22 下午1:33
     */
    public static String handleImgBangumi(String url, String subjectName){
        if (StrUtil.isBlank(url)){
            return "";
        }
        //截取后缀
        String filename = subjectName +"_Bangumi_Img.jpg";
        FileStorageDTO fileStorageDTO = FileStorageUtil.uploadFile(ImgUtil.downloadImg(url), MovieConstants.BANGUMI_MINIO_FLODER, filename);
        return fileStorageDTO.getObjectName();
    }
}
