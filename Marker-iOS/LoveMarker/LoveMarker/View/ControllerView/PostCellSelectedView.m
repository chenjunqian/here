//
//  PostCellSelectedView.m
//  LoveMarker
//
//  Created by BigHead_Chen on 8/12/16.
//  Copyright © 2016 Eason. All rights reserved.
//

#import "PostCellSelectedView.h"
#import "ColorUtil.h"

@interface  PostCellSelectedView()

@property (strong,nonatomic) UILabel* shareLabel;
@property (strong,nonatomic) UILabel* enterUserPageLabel;
@property (strong,nonatomic) UILabel* reportLabel;

@property (strong,nonatomic) UIView* topUnderLine;
@property (strong,nonatomic) UIView* middleUnderLine;

@end

@implementation PostCellSelectedView

@synthesize selectorContentView,shareItem,enterUserPageItem,reportItem,topUnderLine,middleUnderLine;

-(id)initWithFrame:(CGRect)frame{
    self = [super initWithFrame:frame];
    
    if (self) {
        self.backgroundColor = [[UIColor blackColor] colorWithAlphaComponent:0.5f];
        
        selectorContentView = [[UIView alloc] init];
        selectorContentView.translatesAutoresizingMaskIntoConstraints = NO;
        selectorContentView.backgroundColor = [UIColor whiteColor];
        selectorContentView.layer.cornerRadius = 8;
        [self addSubview:selectorContentView];
        
        shareItem = [[ClickableUIView alloc] init];
        shareItem.translatesAutoresizingMaskIntoConstraints = NO;
        shareItem.backgroundColor = [UIColor whiteColor];
        [selectorContentView addSubview:shareItem];
        _shareLabel = [[UILabel alloc] init];
        _shareLabel.text = NSLocalizedString(@"share_post", nil);
        _shareLabel.translatesAutoresizingMaskIntoConstraints = NO;
        _shareLabel.textColor = [ColorUtil tealBlueColor];
        _shareLabel.font = [UIFont systemFontOfSize:13];
        [shareItem addSubview:_shareLabel];
        
        enterUserPageItem = [[ClickableUIView alloc] init];
        enterUserPageItem.translatesAutoresizingMaskIntoConstraints = NO;
        enterUserPageItem.backgroundColor = [UIColor whiteColor];
        [selectorContentView addSubview:enterUserPageItem];
        _enterUserPageLabel = [[UILabel alloc] init];
        _enterUserPageLabel.text = NSLocalizedString(@"enter_user_page", nil);
        _enterUserPageLabel.translatesAutoresizingMaskIntoConstraints = NO;
        _enterUserPageLabel.textColor = [ColorUtil tealBlueColor];
        _enterUserPageLabel.font = [UIFont systemFontOfSize:13];
        [enterUserPageItem addSubview:_enterUserPageLabel];
        
        reportItem = [[ClickableUIView alloc] init];
        reportItem.translatesAutoresizingMaskIntoConstraints = NO;
        reportItem.backgroundColor = [UIColor whiteColor];
        [selectorContentView addSubview:reportItem];
        _reportLabel = [[UILabel alloc] init];
        _reportLabel.text = NSLocalizedString(@"report_post", nil);
        _reportLabel.translatesAutoresizingMaskIntoConstraints = NO;
        _reportLabel.textColor = [ColorUtil tealBlueColor];
        _reportLabel.font = [UIFont systemFontOfSize:13];
        [reportItem addSubview:_reportLabel];
        
        topUnderLine = [[UIView alloc] init];
        topUnderLine.translatesAutoresizingMaskIntoConstraints = NO;
        topUnderLine.backgroundColor = [UIColor lightGrayColor];
        [selectorContentView addSubview:topUnderLine];
        
        middleUnderLine = [[UIView alloc] init];
        middleUnderLine.translatesAutoresizingMaskIntoConstraints = NO;
        middleUnderLine.backgroundColor = [UIColor lightGrayColor];
        [selectorContentView addSubview:middleUnderLine];
        
    }
    
    return self;
}

-(void)layoutSubviews{
    NSDictionary* views = NSDictionaryOfVariableBindings(selectorContentView,shareItem,enterUserPageItem,reportItem,_shareLabel,_enterUserPageLabel,_reportLabel,topUnderLine,middleUnderLine);
    
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:|-50-[selectorContentView]-50-|" options:0 metrics:0 views:views]];
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:[selectorContentView(105)]" options:0 metrics:0 views:views]];
    [self addConstraint:[NSLayoutConstraint constraintWithItem:selectorContentView attribute:NSLayoutAttributeCenterY relatedBy:NSLayoutRelationEqual toItem:self attribute:NSLayoutAttributeCenterY multiplier:1 constant:0]];
    
    [selectorContentView addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:|-2-[shareItem]-2-|" options:0 metrics:0 views:views]];
    [selectorContentView addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:|-2-[shareItem(33)]" options:0 metrics:0 views:views]];
    [shareItem addConstraint:[NSLayoutConstraint constraintWithItem:_shareLabel attribute:NSLayoutAttributeCenterY relatedBy:NSLayoutRelationEqual toItem:shareItem attribute:NSLayoutAttributeCenterY multiplier:1 constant:0]];
    [shareItem addConstraint:[NSLayoutConstraint constraintWithItem:_shareLabel attribute:NSLayoutAttributeCenterX relatedBy:NSLayoutRelationEqual toItem:shareItem attribute:NSLayoutAttributeCenterX multiplier:1 constant:0]];
    
    [selectorContentView addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:|-0-[topUnderLine]-0-|" options:0 metrics:0 views:views]];
    [selectorContentView addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:[shareItem]-0-[topUnderLine(1)]" options:0 metrics:0 views:views]];
    
    [selectorContentView addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:|-2-[enterUserPageItem]-2-|" options:0 metrics:0 views:views]];
    [selectorContentView addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:[topUnderLine]-0-[enterUserPageItem(33)]" options:0 metrics:0 views:views]];
    [enterUserPageItem addConstraint:[NSLayoutConstraint constraintWithItem:_enterUserPageLabel attribute:NSLayoutAttributeCenterY relatedBy:NSLayoutRelationEqual toItem:enterUserPageItem attribute:NSLayoutAttributeCenterY multiplier:1 constant:0]];
    [enterUserPageItem addConstraint:[NSLayoutConstraint constraintWithItem:_enterUserPageLabel attribute:NSLayoutAttributeCenterX relatedBy:NSLayoutRelationEqual toItem:enterUserPageItem attribute:NSLayoutAttributeCenterX multiplier:1 constant:0]];
    
    [selectorContentView addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:|-0-[middleUnderLine]-0-|" options:0 metrics:0 views:views]];
    [selectorContentView addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:[enterUserPageItem]-0-[middleUnderLine(1)]" options:0 metrics:0 views:views]];
    
    [selectorContentView addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:|-2-[reportItem]-2-|" options:0 metrics:0 views:views]];
    [selectorContentView addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:[middleUnderLine]-0-[reportItem(33)]-2-|" options:0 metrics:0 views:views]];
    [reportItem addConstraint:[NSLayoutConstraint constraintWithItem:_reportLabel attribute:NSLayoutAttributeCenterY relatedBy:NSLayoutRelationEqual toItem:reportItem attribute:NSLayoutAttributeCenterY multiplier:1 constant:0]];
    [reportItem addConstraint:[NSLayoutConstraint constraintWithItem:_reportLabel attribute:NSLayoutAttributeCenterX relatedBy:NSLayoutRelationEqual toItem:reportItem attribute:NSLayoutAttributeCenterX multiplier:1 constant:0]];
}

@end
