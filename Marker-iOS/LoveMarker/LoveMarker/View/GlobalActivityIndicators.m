//
//  GlobalActivityIndicators.m
//  LoveMarker
//
//  Created by BigHead_Chen on 7/5/16.
//  Copyright © 2016 Eason. All rights reserved.
//

#import "GlobalActivityIndicators.h"
#import "ColorUtil.h"

@implementation GlobalActivityIndicators

/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect {
    // Drawing code
}
*/
@synthesize containnerView , indicatorLabel , activityIndicatorView;

-(id)initWithTitle:(NSString*)title frame:(CGRect)frame{
    self = [super initWithFrame:frame];
    
    if (self) {
        self.backgroundColor = [[UIColor alloc] initWithRed:0 green:0 blue:0 alpha:0.5];
        
        containnerView = [[UIView alloc] init];
        containnerView.translatesAutoresizingMaskIntoConstraints = NO;
        containnerView.layer.cornerRadius = 8;
        [containnerView setBackgroundColor:[ColorUtil viewBackgroundGrey]];
        [self addSubview:containnerView];
        
        
        indicatorLabel = [[UILabel alloc] init];
        indicatorLabel.translatesAutoresizingMaskIntoConstraints = NO;
        indicatorLabel.text = title;
        indicatorLabel.textColor = [ColorUtil tealBlueColor];
        [containnerView addSubview:indicatorLabel];
        
        activityIndicatorView = [[UIActivityIndicatorView alloc] init];
        activityIndicatorView.translatesAutoresizingMaskIntoConstraints = NO;
        activityIndicatorView.color = [ColorUtil tealBlueColor];
        [containnerView addSubview:activityIndicatorView];
    }
    
    return self;
}

-(void)layoutSubviews{
    NSDictionary* views = NSDictionaryOfVariableBindings(containnerView,indicatorLabel,activityIndicatorView);
    
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:[containnerView(<=200)]" options:0 metrics:0 views:views]];
    [self addConstraint:[NSLayoutConstraint constraintWithItem:containnerView attribute:NSLayoutAttributeCenterX relatedBy:NSLayoutRelationEqual toItem:self attribute:NSLayoutAttributeCenterX multiplier:1 constant:0]];
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:[containnerView(>=40)]" options:0 metrics:0 views:views]];
    [self addConstraint:[NSLayoutConstraint constraintWithItem:containnerView attribute:NSLayoutAttributeCenterY relatedBy:NSLayoutRelationEqual toItem:self attribute:NSLayoutAttributeCenterY multiplier:1 constant:0]];
    
    [containnerView addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:|-10-[indicatorLabel]-10-[activityIndicatorView]" options:0 metrics:0 views:views]];
    [containnerView addConstraint:[NSLayoutConstraint constraintWithItem:indicatorLabel attribute:NSLayoutAttributeCenterY relatedBy:NSLayoutRelationEqual toItem:containnerView attribute:NSLayoutAttributeCenterY multiplier:1 constant:0]];
    
    [containnerView addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:[activityIndicatorView]-10-|" options:0 metrics:0 views:views]];
    [containnerView addConstraint:[NSLayoutConstraint constraintWithItem:activityIndicatorView attribute:NSLayoutAttributeCenterY relatedBy:NSLayoutRelationEqual toItem:containnerView attribute:NSLayoutAttributeCenterY multiplier:1 constant:0]];
    
}

-(void)setTitle:(NSString*)title{
    indicatorLabel.text = title;
}

@end
