//
//  UnitViewUtil.h
//  LoveMarker
//
//  Created by BigHead_Chen on 6/18/16.
//  Copyright © 2016 Eason. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>

@interface UnitViewUtil : NSObject
+(void)showWarningAlertWithMessage:(NSString*)message actionOK:(NSString*)okTitle context:(id)context;
+(void)showWarningAlertWithMessage:(NSString *)message actionOK:(NSString *)okTitle actionCancle:(NSString *)cancelTitle context:(id)context okButtonHandler:(void (^)())handler;
@end
