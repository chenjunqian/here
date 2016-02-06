package com.eason.marker.emchat.chatuidemo.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.eason.marker.emchat.chatuidemo.domain.EMUser;
import com.eason.marker.R;
import com.eason.marker.emchat.applib.controller.HXSDKHelper;
import com.eason.marker.emchat.chatuidemo.DemoHXSDKHelper;
import com.squareup.picasso.Picasso;

public class UserUtils {
    /**
     * 根据username获取相应user，由于demo没有真实的用户数据，这里给的模拟的数据；
     * @param username
     * @return
     */
    public static EMUser getUserInfo(String username){
		if (((DemoHXSDKHelper)HXSDKHelper.getInstance()).getContactList()==null){
			return new EMUser(username);
		}
        EMUser EMUser = ((DemoHXSDKHelper)HXSDKHelper.getInstance()).getContactList().get(username);
        if(EMUser == null){
            EMUser = new EMUser(username);
        }
            
        if(EMUser != null){
            //demo没有这些数据，临时填充
        	if(TextUtils.isEmpty(EMUser.getNick()))
        		EMUser.setNick(username);
        }
        return EMUser;
    }
    
    /**
     * 设置用户头像
     * @param username
     */
    public static void setUserAvatar(Context context, String username, ImageView imageView){
    	EMUser EMUser = getUserInfo(username);
        if(EMUser != null && EMUser.getAvatar() != null){
            Picasso.with(context).load(EMUser.getAvatar()).placeholder(R.drawable.default_avatar_ori).into(imageView);
        }else{
            Picasso.with(context).load(R.drawable.default_avatar_ori).into(imageView);
        }
    }
    
    /**
     * 设置当前用户头像
     */
	public static void setCurrentUserAvatar(Context context, ImageView imageView) {
		EMUser EMUser = ((DemoHXSDKHelper)HXSDKHelper.getInstance()).getUserProfileManager().getCurrentUserInfo();
		if (EMUser != null && EMUser.getAvatar() != null) {
			Picasso.with(context).load(EMUser.getAvatar()).placeholder(R.drawable.default_avatar_ori).into(imageView);
		} else {
			Picasso.with(context).load(R.drawable.default_avatar_ori).into(imageView);
		}
	}
    
    /**
     * 设置用户昵称
     */
    public static void setUserNick(String username,TextView textView){
    	EMUser EMUser = getUserInfo(username);
    	if(EMUser != null){
    		textView.setText(EMUser.getNick());
    	}else{
    		textView.setText(username);
    	}
    }
    
    /**
     * 设置当前用户昵称
     */
    public static void setCurrentUserNick(TextView textView){
    	EMUser EMUser = ((DemoHXSDKHelper)HXSDKHelper.getInstance()).getUserProfileManager().getCurrentUserInfo();
    	if(textView != null){
    		textView.setText(EMUser.getNick());
    	}
    }
    
    /**
     * 保存或更新某个用户
     */
	public static void saveUserInfo(EMUser newEMUser) {
		if (newEMUser == null || newEMUser.getUsername() == null) {
			return;
		}
		((DemoHXSDKHelper) HXSDKHelper.getInstance()).saveContact(newEMUser);
	}
    
}
