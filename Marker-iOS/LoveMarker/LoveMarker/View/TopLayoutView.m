//
//  TopLayoutView.m
//  LoveMarker
//
//  Created by BigHead_Chen on 5/14/16.
//  Copyright © 2016 Eason. All rights reserved.
//

#import "TopLayoutView.h"

@implementation TopLayoutView

-(id)initWithFrame:(CGRect)frame{
    self= [super initWithFrame:frame];
    
    if (self) {
        self.backButton = [[UIButton alloc] init];
        self.backButton.translatesAutoresizingMaskIntoConstraints = NO;
        [self.backButton setBackgroundColor:[UIColor whiteColor]];
        self.backButton.hidden = YES;
        [self addSubview:self.backButton];
        
        self.titleLabel = [[UILabel alloc] init];
        self.titleLabel.translatesAutoresizingMaskIntoConstraints = NO;
        [self.titleLabel setTextColor:[UIColor whiteColor]];
        [self addSubview:self.titleLabel];
        
        [self setBackgroundColor:[UIColor redColor]];
        

    }
    
    return self;
}

-(void)layoutSubviews{
    NSDictionary *views = NSDictionaryOfVariableBindings(_backButton,_titleLabel);
    NSDictionary *metrics = @{@"margin":@5,@"height":@30};
    NSArray *buttonHConts = [NSLayoutConstraint constraintsWithVisualFormat:@"H:|-margin-[_backButton(height)]" options:0 metrics:metrics views:views];
    [self addConstraints:buttonHConts];
    [_backButton setFrame:CGRectMake(0, 0, 30, 30)];
    [self addConstraint: [NSLayoutConstraint constraintWithItem:_backButton attribute:NSLayoutAttributeCenterY relatedBy:NSLayoutRelationEqual toItem:self attribute:NSLayoutAttributeCenterY multiplier:1 constant:0]];
    [self.titleLabel setFrame:CGRectMake(0, 0, 80, 35)];
    [self addConstraint: [NSLayoutConstraint constraintWithItem:_titleLabel attribute:NSLayoutAttributeCenterY relatedBy:NSLayoutRelationEqual toItem:self attribute:NSLayoutAttributeCenterY multiplier:1 constant:0]];
    [self addConstraint: [NSLayoutConstraint constraintWithItem:_titleLabel attribute:NSLayoutAttributeCenterX relatedBy:NSLayoutRelationEqual toItem:self attribute:NSLayoutAttributeCenterX multiplier:1 constant:0]];
}

@end
