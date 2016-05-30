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

+(instancetype)getInstance{
    if (!instance) {
        instance = [[self alloc] init];
    }
    
    return instance;
}

-(void)setUser:(User*)user{
    self.user = user;
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
