//
//  UnitViewUtil.m
//  LoveMarker
//
//  Created by BigHead_Chen on 6/18/16.
//  Copyright © 2016 Eason. All rights reserved.
//

#import "UnitViewUtil.h"

@implementation UnitViewUtil


+(void)showLoginAlertWithMessage:(NSString*)message actionOK:(NSString*)okTitle context:(id)context{
    UIAlertController *loginAlert = [UIAlertController alertControllerWithTitle:@"" message:message preferredStyle:UIAlertControllerStyleAlert];
    
    UIAlertAction* okAction = [UIAlertAction actionWithTitle:okTitle style:UIAlertActionStyleDefault handler:^(UIAlertAction * _Nonnull action) {
        [loginAlert dismissViewControllerAnimated:YES completion:nil];
    }];
    
    [loginAlert addAction:okAction];
    
    [context presentViewController:loginAlert animated:YES completion:nil];
}

+(void)showEditUserInfoAlertWithMessage:(NSString*)message actionOK:(NSString*)okTitle context:(id)context{
    UIAlertController *loginAlert = [UIAlertController alertControllerWithTitle:@"" message:message preferredStyle:UIAlertControllerStyleAlert];
    
    UIAlertAction* okAction = [UIAlertAction actionWithTitle:okTitle style:UIAlertActionStyleDefault handler:^(UIAlertAction * _Nonnull action) {
        [loginAlert dismissViewControllerAnimated:YES completion:nil];
    }];
    
    [loginAlert addAction:okAction];
    
    [context presentViewController:loginAlert animated:YES completion:nil];
}

@end
