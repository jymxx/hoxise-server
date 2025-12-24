package cn.hoxise.system.biz.api.user;

import cn.hoxise.system.api.user.RolePermissionApi;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hoxise
 * @Description:
 * @Date: 2024/3/3 2:48
 */
@Service
public class RolePermissionApiImpl implements RolePermissionApi {

    @Override
    public List<String> getRoleList(long userId) {
        List<String> res = new ArrayList<>();
        if (userId == 0L){
            res.add("*");
        }
        return res;
    }

}
