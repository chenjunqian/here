//
//  UnitViewUtil.m
//  LoveMarker
//
//  Created by BigHead_Chen on 6/18/16.
//  Copyright © 2016 Eason. All rights reserved.
//

#import "UnitViewUtil.h"

@implementation UnitViewUtil


+(void)showWarningAlertWithMessage:(NSString*)message actionOK:(NSString*)okTitle context:(id)context{
    UIAlertController *warningAlter = [UIAlertController alertControllerWithTitle:@"" message:message preferredStyle:UIAlertControllerStyleAlert];
    
    UIAlertAction* okAction = [UIAlertAction actionWithTitle:okTitle style:UIAlertActionStyleDefault handler:^(UIAlertAction * _Nonnull action) {
        [warningAlter dismissViewControllerAnimated:YES completion:nil];
    }];
    
    [warningAlter addAction:okAction];
    
    [context presentViewController:warningAlter animated:YES completion:nil];
}

+(void)showWarningAlertWithMessage:(NSString *)message actionOK:(NSString *)okTitle actionCancle:(NSString *)cancelTitle context:(id)context okButtonHandler:(void (^)())handler{
    UIAlertController *warningAlter = [UIAlertController alertControllerWithTitle:@"" message:message preferredStyle:UIAlertControllerStyleAlert];
    
    UIAlertAction* okAction = [UIAlertAction actionWithTitle:okTitle style:UIAlertActionStyleDefault handler:^(UIAlertAction * _Nonnull action) {
        handler();
    }];
    
    UIAlertAction* cancelAction = [UIAlertAction actionWithTitle:cancelTitle style:UIAlertActionStyleDefault handler:^(UIAlertAction * _Nonnull action) {
        [warningAlter dismissViewControllerAnimated:YES completion:nil];
    }];
    
    [warningAlter addAction:cancelAction];
    [warningAlter addAction:okAction];
    
    [context presentViewController:warningAlter animated:YES completion:nil];
}
@end
