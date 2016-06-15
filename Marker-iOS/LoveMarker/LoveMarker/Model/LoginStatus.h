//
//  LoginStatus.h
//  LoveMarker
//
//  Created by BigHead_Chen on 5/23/16.
//  Copyright © 2016 Eason. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "User.h"

@interface LoginStatus : NSObject

@property (nonatomic,strong,getter=getUser,setter=setUser:) User* user;

+(void)initInstanc;
+(instancetype)getInstance;
-(Boolean)getIsUserModel;
-(void)logout;
-(void)autoLogin;
@end
