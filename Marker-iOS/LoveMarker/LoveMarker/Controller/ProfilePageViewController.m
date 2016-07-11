//
//  ProfilePageViewController.m
//  LoveMarker
//
//  Created by BigHead_Chen on 5/19/16.
//  Copyright © 2016 Eason. All rights reserved.
//

#import "ProfilePageViewController.h"
#import "ProfilePageView.h"

@interface ProfilePageViewController()

@property (strong,nonatomic) ProfilePageView *profilePageView;

@end

@implementation ProfilePageViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    [self initView];
}

-(void)initView{
    _profilePageView = [[ProfilePageView alloc] initWithContext:self title:NSLocalizedString(@"me", nil) frame:CGRectMake(0, 0, self.view.frame.size.width, self.view.frame.size.height)];
    [self.view addSubview:_profilePageView];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
}

@end
