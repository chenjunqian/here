//
//  HttpConfiguration.m
//  LoveMarker
//
//  Created by BigHead_Chen on 5/5/16.
//  Copyright © 2016 Eason. All rights reserved.
//

#import "HttpConfiguration.h"

@implementation HttpConfiguration

static HttpConfiguration *instance;

+(HttpConfiguration*)getInstacne{
    @synchronized (self) {
        if (instance == nil) {
            instance = [[HttpConfiguration alloc] init];
        }
    }
    
    return instance;
}

+(NSString *)getServerUrlDomain{
    return @"http://42.96.208.219/";
}

+(NSString *)getLoginUrl{
    return [[self getServerUrlDomain] stringByAppendingString:@"login/"];
}

+(NSString*) getRegisterUrl{
    return [[self getServerUrlDomain] stringByAppendingString:@"register/"];
}

+(NSString*) getUrlGetPostByLocation{
    return [[self getServerUrlDomain] stringByAppendingString:@"get_post_by_location/"];
}


+(NSString*) getUrlUpdateLocation{
    return [[self getServerUrlDomain] stringByAppendingString:@"update_user_location/"];
}

+(NSString*) getUtlCheckUserExist{
    return [[self getServerUrlDomain] stringByAppendingString:@"check_user_is_exist/"];
}

+(NSString*) getUrlUploadAvatar{
    return [[self getServerUrlDomain] stringByAppendingString:@"upload_avatar/"];
}

+(NSString*) getUrlGetPostTag{
    return [[self getServerUrlDomain] stringByAppendingString:@"get_post_tag/"];
}

+(NSString*) getUrlGetUserInfoByUsername{
    return [[self getServerUrlDomain] stringByAppendingString:@"get_user_info_by_username/"];
}

+(NSString*) getUrlModifyUserInfo{
    return [[self getServerUrlDomain] stringByAppendingString:@"modify_user_info/"];
}

+(NSString*) getUrlUrlMedia{
    return [[self getServerUrlDomain] stringByAppendingString:@"media/"];
}

+(NSString*) getUrlUrlGetPostByUsername{
    return [[self getServerUrlDomain] stringByAppendingString:@"get_post_by_username/"];
}

+(NSString*) getUrlUrlGetCurrentPost{
    return [[self getServerUrlDomain] stringByAppendingString:@"get_current_post/"];
}

+(NSString*) getUrlUrlGetCurrentOneHourPost{
    return [[self getServerUrlDomain] stringByAppendingString:@"get_current_one_hour_post/"];
}

+(NSString*) getUrlUrlGetCurrentPostOnly{
    return [[self getServerUrlDomain] stringByAppendingString:@"get_the_current_post/"];
}

+(NSString*) getUrlUrlDeletePostById{
    return [[self getServerUrlDomain] stringByAppendingString:@"delete_post_by_id/"];
}

+(NSString*) getUrlUrlReportPost{
    return [[self getServerUrlDomain] stringByAppendingString:@"report_post/"];
}

+(NSString*) getUrlUrlReportIssue{
    return [[self getServerUrlDomain] stringByAppendingString:@"report_issue/"];
}

+(NSString*) getUrlUrlGetUserInfoById{
    return [[self getServerUrlDomain] stringByAppendingString:@"get_user_info_by_useid/"];
}

+(NSString*) getUrlGetNotificationMessageByUsername{
    return [[self getServerUrlDomain] stringByAppendingString:@"get_notification_message_by_username/"];
}

+(NSString*) getUrlGetSystemNotifycationMessage{
    return [[self getServerUrlDomain] stringByAppendingString:@"get_system_notification_message/"];
}

+(NSString*) getUrlUpdatePushKey{
    return [[self getServerUrlDomain] stringByAppendingString:@"update_push_key/"];
}
@end
