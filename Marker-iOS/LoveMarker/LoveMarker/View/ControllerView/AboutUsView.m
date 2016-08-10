//
//  AboutUsView.m
//  LoveMarker
//
//  Created by BigHead_Chen on 8/10/16.
//  Copyright © 2016 Eason. All rights reserved.
//

#import "AboutUsView.h"
#import "ColorUtil.h"

@implementation AboutUsView

/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect {
    // Drawing code
}
*/
@synthesize topLayoutView , appInfoUIView , appIconImageView , appNameUILabel , versionCodeUILabel , appDescriptionUILabel , shareUIView , shareUILabel , feedbackUIView , feedbackUILabel;

-(id)initViewContext:(id)context title:(NSString*)title frame:(CGRect)frame{
    self = [super initWithFrame:frame];
    
    if (self) {
        self.backgroundColor = [ColorUtil viewBackgroundGrey];
        
        topLayoutView = [[TopLayoutView alloc] initWithContext:context title:title andFrame:CGRectMake(0, 20, frame.size.width, 50)];
        [self addSubview:topLayoutView];
        
        appInfoUIView = [[UIView alloc] init];
        appInfoUIView.translatesAutoresizingMaskIntoConstraints = NO;
        appInfoUIView.backgroundColor = [UIColor whiteColor];
        appInfoUIView.layer.cornerRadius = 8;
        [self addSubview:appInfoUIView];
        
        appIconImageView = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"app_icon"]];
        appIconImageView.translatesAutoresizingMaskIntoConstraints = NO;
        [appInfoUIView addSubview:appIconImageView];
        
        appNameUILabel = [[UILabel alloc] init];
        appNameUILabel.translatesAutoresizingMaskIntoConstraints = NO;
        appNameUILabel.text = NSLocalizedString(@"app_name", nil);
        [appInfoUIView addSubview:appNameUILabel];
        
        versionCodeUILabel = [[UILabel alloc] init];
        versionCodeUILabel.translatesAutoresizingMaskIntoConstraints = NO;
        versionCodeUILabel.text = NSLocalizedString(@"version_code", nil);
        versionCodeUILabel.textColor = [ColorUtil textColorSubBlack];
        [versionCodeUILabel setFont:[UIFont systemFontOfSize:13]];
        [appInfoUIView addSubview:versionCodeUILabel];
        
        appDescriptionUILabel = [[UILabel alloc] init];
        appDescriptionUILabel.translatesAutoresizingMaskIntoConstraints = NO;
        appDescriptionUILabel.text = NSLocalizedString(@"app_description", nil);
        appDescriptionUILabel.numberOfLines = 0 ;
        appDescriptionUILabel.textColor = [ColorUtil textColorSubBlack];
        [appDescriptionUILabel setFont:[UIFont systemFontOfSize:13]];
        [appInfoUIView addSubview:appDescriptionUILabel];
        
        shareUIView = [[ClickableUIView alloc] init];
        shareUIView.translatesAutoresizingMaskIntoConstraints = NO;
        shareUIView.backgroundColor = [UIColor whiteColor];
        shareUIView.layer.cornerRadius = 8;
        [self addSubview:shareUIView];
        
        shareUILabel = [[UILabel alloc] init];
        shareUILabel.translatesAutoresizingMaskIntoConstraints = NO;
        shareUILabel.text = NSLocalizedString(@"share", nil);
        [shareUIView addSubview:shareUILabel];
        
        feedbackUIView = [[ClickableUIView alloc] init];
        feedbackUIView.translatesAutoresizingMaskIntoConstraints = NO;
        feedbackUIView.backgroundColor = [UIColor whiteColor];
        feedbackUIView.layer.cornerRadius = 8;
        [self addSubview:feedbackUIView];
        
        feedbackUILabel = [[UILabel alloc] init];
        feedbackUILabel.translatesAutoresizingMaskIntoConstraints = NO;
        feedbackUILabel.text = NSLocalizedString(@"feedback", nil);
        [feedbackUIView addSubview:feedbackUILabel];
    }
    
    return self;
}

-(void)layoutSubviews{
    NSDictionary* views = NSDictionaryOfVariableBindings(topLayoutView , appInfoUIView , appIconImageView , appNameUILabel , versionCodeUILabel , appDescriptionUILabel , shareUIView , shareUILabel , feedbackUIView , feedbackUILabel);
    
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:[topLayoutView]-30-[appInfoUIView]" options:0 metrics:0 views:views]];
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:|-30-[appInfoUIView]-30-|" options:0 metrics:0 views:views]];
    
    [appInfoUIView addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:|-15-[appIconImageView(70)]" options:0 metrics:0 views:views]];
    [appInfoUIView addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:|-15-[appIconImageView(70)]" options:0 metrics:0 views:views]];
    
    [appInfoUIView addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:|-15-[appNameUILabel]" options:0 metrics:0 views:views]];
    [appInfoUIView addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:[appIconImageView]-15-[appNameUILabel]" options:0 metrics:0 views:views]];
    
    [appInfoUIView addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:[appNameUILabel]-10-[versionCodeUILabel]" options:0 metrics:0 views:views]];
    [appInfoUIView addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:[appIconImageView]-15-[versionCodeUILabel]" options:0 metrics:0 views:views]];
    
    [appInfoUIView addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:[versionCodeUILabel]-10-[appDescriptionUILabel]-15-|" options:0 metrics:0 views:views]];
    [appInfoUIView addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:[appIconImageView]-15-[appDescriptionUILabel]-15-|" options:0 metrics:0 views:views]];
    
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:[appInfoUIView]-20-[shareUIView(35)]" options:0 metrics:0 views:views]];
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:|-30-[shareUIView]-30-|" options:0 metrics:0 views:views]];
    [shareUIView addConstraint:[NSLayoutConstraint constraintWithItem:shareUILabel attribute:NSLayoutAttributeCenterX relatedBy:NSLayoutRelationEqual toItem:shareUIView attribute:NSLayoutAttributeCenterX multiplier:1 constant:0]];
    [shareUIView addConstraint:[NSLayoutConstraint constraintWithItem:shareUILabel attribute:NSLayoutAttributeCenterY relatedBy:NSLayoutRelationEqual toItem:shareUIView attribute:NSLayoutAttributeCenterY multiplier:1 constant:0]];
    
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:[shareUIView]-20-[feedbackUIView(35)]" options:0 metrics:0 views:views]];
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:|-30-[feedbackUIView]-30-|" options:0 metrics:0 views:views]];
    [feedbackUIView addConstraint:[NSLayoutConstraint constraintWithItem:feedbackUILabel attribute:NSLayoutAttributeCenterX relatedBy:NSLayoutRelationEqual toItem:feedbackUIView attribute:NSLayoutAttributeCenterX multiplier:1 constant:0]];
    [feedbackUIView addConstraint:[NSLayoutConstraint constraintWithItem:feedbackUILabel attribute:NSLayoutAttributeCenterY relatedBy:NSLayoutRelationEqual toItem:feedbackUIView attribute:NSLayoutAttributeCenterY multiplier:1 constant:0]];
}

@end
