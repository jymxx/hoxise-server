package cn.hoxise.common.base.utils.img;

import cn.hutool.http.HttpUtil;

import java.io.InputStream;

/**
 * @Author hoxise
 * @Description: 图片工具
 * @Date 2025-12-23 上午11:45
 */
public class ImgUtil {

    /**
     * @description:
     * @param	imgUrl 比如说 网络图片的url
     * @author: hoxise
     * @date: 2025/12/23 上午11:46
     */
    public static InputStream downloadImg(String imgUrl){
        return HttpUtil.createGet(imgUrl)
                .execute()
                .bodyStream();
    }
}
