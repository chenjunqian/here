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
    NSString *emailRegex = @"[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}";
    NSPredicate *emailTest = [NSPredicate predicateWithFormat:@"SELF MATCHES %@", emailRegex];
    return [emailTest evaluateWithObject:email];
}

/*手机号码验证 MODIFIED BY HELENSONG*/
+(BOOL) isValidateMobile:(NSString *)mobile
{
    //手机号以13， 15，18开头，八个 \d 数字字符
    NSString *phoneRegex = @"^((13[0-9])|(15[^4,\\D])|(18[0,0-9]))\\d{8}$";
    NSPredicate *phoneTest = [NSPredicate predicateWithFormat:@"SELF MATCHES %@",phoneRegex];
    //    NSLog(@"phoneTest is %@",phoneTest);
    return [phoneTest evaluateWithObject:mobile];
}


@end
