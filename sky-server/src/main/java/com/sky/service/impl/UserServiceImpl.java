package com.sky.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.sky.constant.MessageConstant;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.exception.LoginFailedException;
import com.sky.mapper.UserMapper;
import com.sky.properties.WeChatProperties;
import com.sky.service.UserService;
import com.sky.utils.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    WeChatProperties weChatProperties;

    @Autowired
    UserMapper userMapper;

    private static final String WX_LOGIN = "https://api.weixin.qq.com/sns/jscode2session";

    public User wxLogin(UserLoginDTO userLoginDTO) {
        //调用微信接口服务，读取当前用户的openid、
       String openId = getOpenid(userLoginDTO.getCode());
        if (openId == null) {
            throw new LoginFailedException(MessageConstant.LOGIN_FAILED);
        }
        //判断当前用户是否为新用户，在自己的数据库中查找
        User user = userMapper.getByOpenid(openId);
        //如果是新用户，则添加用户信息
        if (user == null) {
            user = User.builder().openid(openId)
                    .createTime(LocalDateTime.now())
                    .build();
            userMapper.insert(user);
        }
        //返回User对象
        return user;
    }

    private String getOpenid(String code) {
        Map<String,String> map = new HashMap<>();
        map.put("appid",weChatProperties.getAppid());
        map.put("secret",weChatProperties.getSecret());
        map.put("js_code",code);
        map.put("grant_type","authorization_code");
        //判断openid是否为空，为空表示失败
        String json = HttpClientUtil.doGet(WX_LOGIN,map);
        JSONObject jsonObject = JSONObject.parseObject(json);
        String openId = jsonObject.getString("openid");
        return openId;
    }

}
