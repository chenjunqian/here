//
//  LoginStatus.m
//  LoveMarker
//
//  Created by BigHead_Chen on 5/23/16.
//  Copyright © 2016 Eason. All rights reserved.
//

#import "LoginStatus.h"
#import "CoreDataUser.h"
#import "MyDataController.h"
#import "HttpRequest.h"
#import "ErrorState.h"
#import "NSObject+ObjectMap.h"
#import "ResponseResult.h"

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
    [[[MyDataController alloc]init] saveOrUpdataUserCoreDataWithUsername:self.user.username password:self.user.password key:KEY_VALUE isLogout:YES];
    self.user = nil;
}

-(void)loginWithUsername:(NSString*)username password:(NSString*)password pushKey:(NSString*)pushKey successHandler:(LoginSuccessHandler)successHandler
           failedHandler:(LoginFailedHandler)failedHandler{
    
    [HttpRequest loginWithUsername:username password:password pushKey:pushKey responseData:^(ResponseResult *response, NSObject *resultObject) {
        ResponseResult* result = (ResponseResult*)response;
        if (resultObject!=nil&&(result.status) == Error_Code_Correct) {
            User* user = [NSObject objectOfClass:@"User" fromJSON:(NSDictionary*)resultObject];
            [[LoginStatus getInstance] setUser:user];
            [[[MyDataController alloc]init] saveOrUpdataUserCoreDataWithUsername:user.username password:user.password key:KEY_VALUE isLogout:NO];
            NSArray *result = [[[MyDataController alloc]init] getUserCoreDataWithUsername:user.username];
            successHandler();
            NSLog(@"fetch result :%@",result);
            
        }else if((result.status) == Error_Code_User_Not_Found){
            failedHandler(result.status);
        }else if ((result.status) == ERROR_CODE_USER_OR_PASSWORD_INVALID){
            failedHandler(result.status);
        }
        
    }];
}

-(void)autoLogin{
    CoreDataUser* user = [[[[MyDataController alloc] init] getUserCoreDataWithDefualKey] firstObject];
    if (user && !user.isLogout) {
        [HttpRequest loginWithUsername:user.username password:user.password pushKey:@"" responseData:^(NSObject *response, NSObject *resultObject) {
            ResponseResult* result = (ResponseResult*)response;
            if (resultObject!=nil&&(result.status) == Error_Code_Correct) {
                User* user = [NSObject objectOfClass:@"User" fromJSON:(NSDictionary*)resultObject];
                [[LoginStatus getInstance] setUser:user];
                [[[MyDataController alloc]init] saveOrUpdataUserCoreDataWithUsername:user.username password:user.password key:KEY_VALUE isLogout:NO];
                NSLog(@"aotu login success");
                
            }else{
                
            }
        }];
    }
}

@end
