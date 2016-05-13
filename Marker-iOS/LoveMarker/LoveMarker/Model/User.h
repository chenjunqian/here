//
//  User.h
//  LoveMarker
//
//  Created by BigHead_Chen on 5/11/16.
//  Copyright © 2016 Eason. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface User : NSObject

@property (nonatomic,strong,setter=setNickname:,getter=getNickname) NSString   *nickname ;
@property (nonatomic,strong,setter=setUsername:,getter=getUsername) NSString   *username ;
@property (nonatomic,strong,setter=setPassword:,getter=getPassword) NSString *password ;
@property (nonatomic,strong,setter=setGender:,getter=getGender) NSString *gender ;
@property (nonatomic,strong,setter=setAvatar:,getter=getAvatar) NSString *avatar;
@property (nonatomic,strong,setter=setBirthday:,getter=getBirthday) NSString *birthday ;
@property (nonatomic,strong,setter=setUserid:,getter=getUserid) NSString *userid ;
@property (nonatomic,strong,setter=setPushKey:,getter=getPushKey) NSString *pushKey ;
@property (nonatomic,strong,setter=setSimpleProfile:,getter=getSimpleProfile) NSString *simpleProfile;
@property (nonatomic,strong,setter=setLongProfile:,getter=getLongProfile) NSString *longProfile ;
@property (nonatomic,strong,setter=setAvatarThumb:,getter=getAvatarThumb) NSString *avatarThumb;

@end
