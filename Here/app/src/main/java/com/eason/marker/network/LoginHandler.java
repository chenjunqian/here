package com.eason.marker.network;

import com.eason.marker.model.ErroCode;
import com.eason.marker.model.LoginStatus;
import com.eason.marker.model.User;

/**
 * 登录相关接口必须基于该类
 * Created by Eason on 9/11/15.
 */
public class LoginHandler extends HttpResponseHandler {

    @Override
    public <T> void response(String response, Class<T> t) {
        super.response(response, t);
        if (this.resultVO!=null&&this.resultVO.getStatus()== ErroCode.ERROR_CODE_CORRECT
                &&this.resultVO.getResultData()!=null){
            //设置用户登录的状态
            User user = (User)this.resultVO.getResultData();
            LoginStatus.setUser(user);
        }else{
            LoginStatus.setUser(null);
        }
    }
}
