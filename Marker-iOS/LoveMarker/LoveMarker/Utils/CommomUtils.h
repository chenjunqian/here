//
//  CommomUtils.h
//  LoveMarker
//
//  Created by BigHead_Chen on 5/21/16.
//  Copyright © 2016 Eason. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>

@interface CommomUtils : NSObject

-(void)CommomVisualFormatConstraints:(UIView*)parentView visualFormat:(NSString*)formatString views:(NSDictionary*)views;
+(BOOL)isEmptyString:(NSString*)string;
+(BOOL)isValidateEmail:(NSString *)email;
+(BOOL) isValidateMobile:(NSString *)mobile;
+(NSUInteger)getMixStringLength:(NSString*)string;
+(NSDate*)timestampToDate:(NSString*)timestamp;
@end
