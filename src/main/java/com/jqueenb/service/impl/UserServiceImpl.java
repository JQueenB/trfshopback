package com.jqueenb.service.impl;

import com.jqueenb.common.Const;
import com.jqueenb.common.ServerResponse;
import com.jqueenb.dao.UserInfoMapper;
import com.jqueenb.pojo.UserInfo;
import com.jqueenb.service.IUserService;
import com.jqueenb.utils.MD5Utils;
import com.jqueenb.utils.TokenCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    UserInfoMapper userInfoMapper;

    @Override
    public ServerResponse login(String username, String password) {
        //    step1:参数的非空校验
        if (username == null || username.equals("")) {
            return ServerResponse.createByError("用户名不能为空");
        }
        if (password == null || password.equals("")) {
            return ServerResponse.createByError("密码不能为空");
        }
        //    step2:检查用户名是否存在
        int result = userInfoMapper.checkUsername(username);
        if (result == 0) {
            return ServerResponse.createByError("该用户不存在");
        }
        //    step3:根据用户名和密码查找用户信息
        UserInfo userInfo = userInfoMapper.selectUserInfoByUsernameAndPassword(username, MD5Utils.getMD5Code(password));
        if (userInfo == null) {
            return ServerResponse.createByError("密码错误");
        }
        //    step4:返回结果
        userInfo.setPassword("");
        return ServerResponse.createBySuccess(userInfo);
    }

    @Override
    public ServerResponse register(UserInfo userInfo) {
        //step1:参数校验
        if (userInfo == null) {
            return ServerResponse.createByError("必须有参数");
        }
        //step2:校验用户名
        int result = userInfoMapper.checkUsername(userInfo.getUsername());
        if (result > 0) {
            return ServerResponse.createByError("该用户已存在");
        }
        //step3:校验邮箱
        int result_email = userInfoMapper.checkEmail(userInfo.getEmail());
        if (result_email > 0) {
            return ServerResponse.createByError("邮箱已存在");
        }
        //step4:注册
        userInfo.setRole(Const.RoleEnum.ROLE_CUSTOMER.getCode());
        userInfo.setPassword(MD5Utils.getMD5Code(userInfo.getPassword()));
        int count = userInfoMapper.insert(userInfo);
        if (count > 0) {
            return ServerResponse.createBySuccess("注册成功");
        }
        //step5:返回结果
        return ServerResponse.createByError("注册失败");
    }

    @Override
    public ServerResponse forget_get_question(String username) {
        //step1:参数校验
        if(username==null||username.equals("")){
            return ServerResponse.createByError("用户名不能为空");
        }
        //step2:校验username
        int result=userInfoMapper.checkUsername(username);
        if(result==0){
            //用户名不存在
            return ServerResponse.createByError("用户名不存在，请重新输入");
        }
        //step3:查找密保问题
        String question=userInfoMapper.selectQuestionByUsername(username);
        if(question==null||question.equals("")){
            return ServerResponse.createBySuccess(question);
        }
        return ServerResponse.createBySuccess(question);
    }

    @Override
    public ServerResponse forget_check_answer(String username, String question, String answer) {
        //step1:参数校验
        if(username==null||username.equals("")){
            return ServerResponse.createByError("用户名不能为空");
        }
        if(question==null||question.equals("")){
            return ServerResponse.createByError("问题不能为空");
        }
        if(answer==null||answer.equals("")){
            return ServerResponse.createByError("答案不能为空");
        }
        //step2:根据username，question，answer查询
        int result=userInfoMapper.selectByUsernameAndQuestionAndAnswer(username,question,answer);
        if(result==0){
            //答案错误
            return ServerResponse.createByError("答案错误");
        }
        //step3:服务端生成一个token保存并将token返回给客户端
        String forgetToken=UUID.randomUUID().toString();
        //guava cache
        TokenCache.set(username,forgetToken);
        return ServerResponse.createBySuccess(forgetToken);
    }

    @Override
    public ServerResponse forget_reset_password(String username, String password, String forgetToken) {
        //step1:参数校验
        if(username==null||username.equals("")){
            return ServerResponse.createByError("用户名不能为空");
        }
        if(password==null||password.equals("")){
            return ServerResponse.createByError("密码不能为空");
        }
        if(forgetToken==null||forgetToken.equals("")){
            return ServerResponse.createByError("token不能为空");
        }
        //step2:token校验
        String token = TokenCache.get(username);
        if(token==null){
            return ServerResponse.createByError("token过期");
        }
        if(!token.equals(forgetToken)){
            return ServerResponse.createByError("无效的token");
        }
        //step3:修改密码
        int result=userInfoMapper.updateUserPassword(username,MD5Utils.getMD5Code(password));
        if(result>0){
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByError("密码修改失败");
    }

    @Override
    public ServerResponse check_valid(String str, String type) {
        //stept1:参数非空校验
        if(str==null||str.equals("")){
            return ServerResponse.createByError("用户名或者邮箱不能为空");
        }
        if(type==null||type.equals("")){
            return ServerResponse.createByError("校验的类型参数不能为空");
        }
        //stept2:type:username-->校验用户名str
        //           :email-->校验邮箱str
        if(type.equals("username")){
            int result=userInfoMapper.checkUsername(str);
            if(result>0){
                //用户已存在
                return ServerResponse.createByError("用户名已存在");
            }else{
                return ServerResponse.createBySuccess();
            }
        }else if(type.equals("email")){
            int result=userInfoMapper.checkEmail(str);
            if(result>0){
                //邮箱已存在
                return ServerResponse.createByError("邮箱已存在");
            }else{
                return ServerResponse.createBySuccess();
            }
        }else{
            return ServerResponse.createByError("参数类型错误");
        }
    }

    @Override
    public ServerResponse reset_password(String username, String passwordOld, String passwordNew) {
        //stept1:参数非空校验
        if(passwordOld==null||passwordOld.equals("")){
            return ServerResponse.createByError("用户名旧密码不能为空");
        }
        //stept2:根据username和passwordOld
        UserInfo userInfo=userInfoMapper.selectUserInfoByUsernameAndPassword(username,MD5Utils.getMD5Code(passwordOld));
        if(userInfo==null){
            return ServerResponse.createByError("旧密码错误");
        }
        //stept3:修改密码
        userInfo.setPassword(MD5Utils.getMD5Code(passwordNew));
        int result=userInfoMapper.updateByPrimaryKey(userInfo);
        if(result>0){
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByError("密码修改失败");
    }

    @Override
    public ServerResponse update_information(UserInfo user) {
        //stept1:参数校验
        if(user==null){
            return ServerResponse.createByError("参数不能为空");
        }
        //stept2:更新用户信息
        int result=userInfoMapper.updateUserBySelectActive(user);
        if(result>0){
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByError("更新个人信息失败");
    }

    @Override
    public UserInfo findUserInfoByUserid(Integer userId) {
        return userInfoMapper.selectByPrimaryKey(userId);
    }
}
