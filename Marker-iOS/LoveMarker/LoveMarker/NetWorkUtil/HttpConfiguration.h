//
//  HttpConfiguration.h
//  LoveMarker
//
//  Created by BigHead_Chen on 5/5/16.
//  Copyright © 2016 Eason. All rights reserved.
//

#import <Foundation/Foundation.h>

#define LoginUrl @"http://42.96.208.219/"

@interface HttpConfiguration : NSObject

@property NSString *UrlServerDomain;

+(HttpConfiguration*) getInstacne;
+(NSString*) getServerUrlDomain;
+(NSString*) getLoginUrl;
+(NSString*) getRegisterUrl;
+(NSString*) getUrlGetPostByLocation;
+(NSString*) getUrlUpdateLocation;
+(NSString*) getUtlCheckUserExist;
+(NSString*) getUrlUploadAvatar;
+(NSString*) getUrlGetPostTag;
+(NSString*) getUrlGetUserInfoByUsername;
+(NSString*) getUrlModifyUserInfo;
+(NSString*) getUrlUrlMedia;
+(NSString*) getUrlUrlGetPostByUsername;
+(NSString*) getUrlUrlGetCurrentPost;
+(NSString*) getUrlUrlGetCurrentOneHourPost;
+(NSString*) getUrlUrlGetCurrentPostOnly;
+(NSString*) getUrlUrlDeletePostById;
+(NSString*) getUrlUrlReportPost;
+(NSString*) getUrlUrlReportIssue;
+(NSString*) getUrlUrlGetUserInfoById;
+(NSString*) getUrlGetNotificationMessageByUsername;
+(NSString*) getUrlGetSystemNotifycationMessage;
+(NSString*) getUrlUpdatePushKey;

@end
