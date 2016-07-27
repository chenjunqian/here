//
//  UserInforCell.m
//  LoveMarker
//
//  Created by BigHead_Chen on 7/9/16.
//  Copyright © 2016 Eason. All rights reserved.
//

#import "UserInforCell.h"
#import "ColorUtil.h"

@implementation UserInforCell

@synthesize titleUILabel , parameterUILabel;

-(id)initWithFrame:(CGRect)frame{
    self = [super initWithFrame:frame];
    
    if (self) {
        self.backgroundColor = [UIColor whiteColor];
        
        titleUILabel = [[UILabel alloc] init];
        titleUILabel.translatesAutoresizingMaskIntoConstraints = NO;
        titleUILabel.textColor = [UIColor blackColor];
        [self addSubview:titleUILabel];
        
        parameterUILabel = [[UILabel alloc] init];
        parameterUILabel.translatesAutoresizingMaskIntoConstraints = NO;
        parameterUILabel.textColor = [ColorUtil textColorSubBlack];
        [self addSubview:parameterUILabel];
        
    }
    
    return self;
}


-(void)layoutSubviews{
    NSDictionary* views = NSDictionaryOfVariableBindings(titleUILabel,parameterUILabel);
    
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:|-10-[titleUILabel]" options:0 metrics:0 views:views]];
    [self addConstraint:[NSLayoutConstraint constraintWithItem:titleUILabel attribute:NSLayoutAttributeCenterY relatedBy:NSLayoutRelationEqual toItem:self attribute:NSLayoutAttributeCenterY multiplier:1 constant:0]];
    
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:[parameterUILabel]-10-|" options:0 metrics:0 views:views]];
    [self addConstraint:[NSLayoutConstraint constraintWithItem:parameterUILabel attribute:NSLayoutAttributeCenterY relatedBy:NSLayoutRelationEqual toItem:self attribute:NSLayoutAttributeCenterY multiplier:1 constant:0]];

}


@end
