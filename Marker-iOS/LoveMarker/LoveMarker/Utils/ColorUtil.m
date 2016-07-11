//
//  ColorUtil.m
//  LoveMarker
//
//  Created by BigHead_Chen on 6/25/16.
//  Copyright © 2016 Eason. All rights reserved.
//

#import "ColorUtil.h"

@implementation ColorUtil


+(UIColor*)themeColor{
    
    UIColor* themeColor = [UIColor colorWithRed:255.0/255.0 green:59.0/255.0 blue:48.0/255.0 alpha:1.0];
    
    return themeColor;
}

+(UIColor*)tealBlueColor{
    UIColor* tealBlueColor = [UIColor colorWithRed:90.0/255.0 green:200.0/255.0 blue:250.0/255.0 alpha:1.0];
    
    return tealBlueColor;
    
}

+(UIColor*)viewBackgroundGrey{
    UIColor* viewBackgroundGrey = [UIColor colorWithRed:0xf3/255.0 green:0xf3/255.0 blue:0xf3/255.0 alpha:1.0];
    
    return viewBackgroundGrey;
}

+(UIColor*)textColorSubBlack{
    UIColor* textColorSubBlack = [UIColor colorWithRed:0xcc/255.0 green:0xcc/255.0 blue:0xcc/255.0 alpha:1.0];
    
    return textColorSubBlack;
}

@end
