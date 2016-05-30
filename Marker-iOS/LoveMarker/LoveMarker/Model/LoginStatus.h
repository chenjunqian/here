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

@property (nonatomic,strong,getter=getUser) User* user;
@property (nonatomic,strong) NSString* testString;

+(instancetype)getInstance;
-(Boolean)getIsUserModel;
-(void)logout;
-(void)setUser:(User*)user;
@end
