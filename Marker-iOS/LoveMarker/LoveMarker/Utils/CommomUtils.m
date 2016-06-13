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

@end
