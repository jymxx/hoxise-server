package cn.hoxise.module.movie.service.quarkpan;

import cn.hutool.http.HttpUtil;
import org.springframework.stereotype.Service;

/**
 * 夸克网盘接口
 *
 * @author hoxise
 * @since 2026/7/16 14:55
 */
@Service
public class QuarkPanServiceImpl implements QuarkPanService{

    String tokenUrl = "https://drive-h.quark.cn/1/clouddrive/share/sharepage/token";

    String detailUrl = "https://drive-h.quark.cn/1/clouddrive/share/sharepage/detail";


//    public String getShareToken(String shareId) {
//        String pwdId = HttpUtil.createPost(tokenUrl)
//                .body("pwd_id", shareId)
//                .execute()
//                .body();
//
//    }

}
