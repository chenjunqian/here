//
//  LoginStatus.m
//  LoveMarker
//
//  Created by BigHead_Chen on 5/23/16.
//  Copyright © 2016 Eason. All rights reserved.
//

#import "LoginStatus.h"

@implementation LoginStatus

__strong static id instance = nil;

+(void)initInstanc{
    if (!instance) {
        instance = [[self alloc] init];
    }
}

+(instancetype)getInstance{
    if (!instance) {
        instance = [[self alloc] init];
    }
    
    return instance;
}

-(Boolean)getIsUserModel{
    if (self.user) {
        return YES;
    }else{
        return NO;
    }
}

-(void)logout{
    self.user = nil;
}

@end
