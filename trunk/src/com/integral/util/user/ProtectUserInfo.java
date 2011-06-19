package com.integral.util.user;

import java.util.ArrayList;
import java.util.List;

import com.integral.system.user.bean.UserInfo;

/** 
 * <p>Description: [保护用户信息]</p>
 * @author  <a href="mailto: swpigris81@126.com">Chao Dai</a>
 * @createDate 2011-6-19
 */
public class ProtectUserInfo {
    /**
     * <p>Discription:[保护用户信息,去掉密码等敏感信息]</p>
     * @param userList
     * @return
     * @author: 代超
     * @update: 2011-6-19 代超[变更描述]
     */
    public static List protectUserInfo(List<UserInfo> userList){
        List<UserInfo> list = new ArrayList();
        if(userList !=null){
            for(UserInfo userInfo : userList){
                UserInfo user = new UserInfo();
                user.setAddress(userInfo.getAddress());
                user.setCity(userInfo.getCity());
                user.setEmail(userInfo.getEmail());
                user.setPhoneNo(userInfo.getPhoneNo());
                user.setPrivence(userInfo.getPrivence());
                user.setTelphoneNo(userInfo.getTelphoneNo());
                user.setUserCode(userInfo.getUserCode());
                user.setUserId(userInfo.getUserId());
                user.setUserName(userInfo.getUserName());
                user.setZip(userInfo.getZip());
                list.add(user);
            }
        }
        return list;
    }
}
