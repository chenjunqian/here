//
//  TagCollectionViewCell.m
//  LoveMarker
//
//  Created by BigHead_Chen on 8/7/16.
//  Copyright © 2016 Eason. All rights reserved.
//

#import "TagCollectionViewCell.h"

@implementation TagCollectionViewCell

-(id)initWithFrame:(CGRect)frame{
    self = [super initWithFrame:frame];
    
    if (self) {
        self.tagUILabel = [[UILabel alloc] initWithFrame:CGRectMake(0, 0, 60, 20)];
        self.tagUILabel.translatesAutoresizingMaskIntoConstraints = NO;
        self.tagUILabel.textColor = [UIColor grayColor];
        self.tagUILabel.textAlignment = NSTextAlignmentCenter;
        [self.tagUILabel setFont:[UIFont systemFontOfSize:13]];
        [self addSubview:self.tagUILabel];
    }
    
    return self;
}

-(void)layoutSubviews{
    [self addConstraint:[NSLayoutConstraint constraintWithItem:self.tagUILabel attribute:NSLayoutAttributeCenterX relatedBy:NSLayoutRelationEqual toItem:self attribute:NSLayoutAttributeCenterX multiplier:1 constant:0]];
    [self addConstraint:[NSLayoutConstraint constraintWithItem:self.tagUILabel attribute:NSLayoutAttributeCenterY relatedBy:NSLayoutRelationEqual toItem:self attribute:NSLayoutAttributeCenterY multiplier:1 constant:0]];
}

@end
