//
//  CommomUtils.m
//  LoveMarker
//
//  Created by BigHead_Chen on 5/21/16.
//  Copyright © 2016 Eason. All rights reserved.
//

#import "CommomUtils.h"

@implementation CommomUtils

-(void)CommomVisualFormatConstraints:(UIView*)parentView visualFormat:(NSString*)formatString views:(NSDictionary*)views{
    [parentView addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:formatString options:0 metrics:0 views:views]];
}

+(BOOL)isEmptyString:(NSString*)string{
    if ([string isEqualToString:@""]||[string isEqual:nil]) {
        return YES;
    }else{
        return NO;
    }
}

/*邮箱验证 MODIFIED BY HELENSONG*/
+(BOOL)isValidateEmail:(NSString *)email
{
    NSString *emailRegex = @"^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
    NSPredicate *emailTest = [NSPredicate predicateWithFormat:@"SELF MATCHES %@", emailRegex];
    return [emailTest evaluateWithObject:email];
}

/*手机号码验证 MODIFIED BY HELENSONG*/
+(BOOL) isValidateMobile:(NSString *)mobile
{
    //手机号以13， 15，18,17开头，八个 \d 数字字符
    NSString *phoneRegex = @"^1[3,5,7,8]\\d{9}$";
    NSPredicate *phoneTest = [NSPredicate predicateWithFormat:@"SELF MATCHES %@",phoneRegex];
    //    NSLog(@"phoneTest is %@",phoneTest);
    return [phoneTest evaluateWithObject:mobile];
}


@end
