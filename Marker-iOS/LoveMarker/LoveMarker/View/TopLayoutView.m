//
//  TopLayoutView.m
//  LoveMarker
//
//  Created by BigHead_Chen on 5/14/16.
//  Copyright © 2016 Eason. All rights reserved.
//

#import "TopLayoutView.h"
#import "ColorUtil.h"

@interface TopLayoutView ()

@property (strong) id instance;

@end

@implementation TopLayoutView

@synthesize navigationItem,leftButton,rightButton;

-(id)initWithContext:(id)context title:(NSString*)title andFrame:(CGRect)frame{
    self= [super initWithFrame:frame];
    
    if (self) {
        _instance = context;
        [self initViewWithTitle:title];
    }
    
    return self;
}

-(void)initViewWithTitle:(NSString*)title{
    navigationItem = [[UINavigationItem alloc] initWithTitle:title];
    [self setTitleTextAttributes:@{NSForegroundColorAttributeName:[UIColor whiteColor]}];
    
    leftButton = [[UIBarButtonItem alloc] initWithBarButtonSystemItem:UIBarButtonSystemItemCancel target:self action:@selector(back:)];
    
    rightButton = [[UIBarButtonItem alloc] init];
    
    [self pushNavigationItem:navigationItem animated:YES];
    [navigationItem setLeftBarButtonItem:leftButton];
    [navigationItem setRightBarButtonItem:rightButton];
    
    [self setBarTintColor:[ColorUtil themeColor]];
}

-(IBAction)back:(id)sender{
    if(_instance){
        [_instance dismissViewControllerAnimated:YES completion:nil];
    }
}

-(void)layoutSubviews{
    [leftButton setTitleTextAttributes:@{NSForegroundColorAttributeName:[UIColor whiteColor]} forState:UIControlStateNormal];
    [leftButton setTitlePositionAdjustment:UIOffsetMake(10, 0) forBarMetrics:0];
    
    [rightButton setTitlePositionAdjustment:UIOffsetMake(10, 0) forBarMetrics:0];
}

@end
