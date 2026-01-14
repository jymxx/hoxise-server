package cn.hoxise.common.base.utils.img;

import cn.hutool.http.HttpUtil;

import java.io.InputStream;

/**
 * ImgUtil 图片工具
 *
 * @author hoxise
 * @since 2026/01/14 06:48:55
 */
public class ImgUtil {

    /**
     * 下载图片流
     *
     * @param imgUrl 比如说 网络图片的url
     * @return 图片流
     * @author hoxise
     * @since 2026/01/14 06:48:40
     */
    public static InputStream downloadImg(String imgUrl){
        return HttpUtil.createGet(imgUrl)
                .execute()
                .bodyStream();
    }
}
