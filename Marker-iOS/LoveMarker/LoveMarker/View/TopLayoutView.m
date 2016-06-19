//
//  TopLayoutView.m
//  LoveMarker
//
//  Created by BigHead_Chen on 5/14/16.
//  Copyright © 2016 Eason. All rights reserved.
//

#import "TopLayoutView.h"

@interface TopLayoutView ()


@end

@implementation TopLayoutView

@synthesize navigationItem,leftButton,rightButton;

__strong id instance = nil;

-(id)initWithContext:(id)context title:(NSString*)title andFrame:(CGRect)frame{
    self= [super initWithFrame:frame];
    
    if (self) {
        [self initViewWithTitle:title];
        
        instance = context;
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
    
    [self setBarTintColor:[UIColor redColor]];
}

-(IBAction)back:(id)sender{
    if(instance){
        [instance dismissViewControllerAnimated:YES completion:^{
            
        }];
    }
}

-(void)layoutSubviews{
    [leftButton setTitleTextAttributes:@{NSForegroundColorAttributeName:[UIColor whiteColor]} forState:UIControlStateNormal];
    [leftButton setTitlePositionAdjustment:UIOffsetMake(10, 0) forBarMetrics:0];
    
    [rightButton setTitlePositionAdjustment:UIOffsetMake(10, 0) forBarMetrics:0];
}

@end
