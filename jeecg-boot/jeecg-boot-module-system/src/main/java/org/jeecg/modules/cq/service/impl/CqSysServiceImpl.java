package org.jeecg.modules.cq.service.impl;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.cq.service.ICqSysService;
import org.jeecg.modules.message.websocket.WebSocket;
import org.jeecg.modules.system.entity.SysUser;
import org.jeecg.modules.system.service.ISysUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class CqSysServiceImpl implements ICqSysService {
    @Resource
    private WebSocket webSocket;
    @Resource
    private ISysUserService sysUserService;

    @Override
    public void syncUser(String username) {
        // 获取用户
        SysUser sysUser = sysUserService.getUserByName(username);
        //创建业务消息信息
        JSONObject obj = new JSONObject();
        obj.put("cmd", "refresh");//业务类型
        obj.put("msgId", "");//消息id
        obj.put("msgTxt", "");//消息内容
        //单个用户发送 (userId为用户id)
        webSocket.sendOneMessage(sysUser.getId(), obj.toJSONString());
    }

    @Override
    public void syncUsers(List<String> usernames) {
        // 获取用户
        List<SysUser> userList = sysUserService.getUsersByNames(usernames);
        //创建业务消息信息
        JSONObject obj = new JSONObject();
        obj.put("cmd", "refresh");//业务类型
        obj.put("msgId", "");//消息id
        obj.put("msgTxt", "");//消息内容
        //多个用户发送 (userIds为多个用户id，逗号‘,’分隔)
        webSocket.sendMoreMessage(userList.stream().map(SysUser::getId).toArray(String[]::new), obj.toJSONString());
    }

    @Override
    public void syncAllUsers() {
        // 获取用户
        List<SysUser> userList = sysUserService.getUsersByStatusAndOrg(1, "A01");
        //创建业务消息信息
        JSONObject obj = new JSONObject();
        obj.put("cmd", "refresh");//业务类型
        obj.put("msgId", "");//消息id
        obj.put("msgTxt", "");//消息内容
        //多个用户发送 (userIds为多个用户id，逗号‘,’分隔)
        webSocket.sendMoreMessage(userList.stream().map(SysUser::getId).toArray(String[]::new), obj.toJSONString());
    }

    @Override
    public void forceSyncAllUsers() {
        // 获取用户
        List<SysUser> userList = sysUserService.getUsersByStatusAndOrg(1, "A01");
        //创建业务消息信息
        JSONObject obj = new JSONObject();
        obj.put("cmd", "force_refresh");//业务类型
        obj.put("msgId", "");//消息id
        obj.put("msgTxt", "");//消息内容
        //多个用户发送 (userIds为多个用户id，逗号‘,’分隔)
        webSocket.sendMoreMessage(userList.stream().map(SysUser::getId).toArray(String[]::new), obj.toJSONString());
    }
}
