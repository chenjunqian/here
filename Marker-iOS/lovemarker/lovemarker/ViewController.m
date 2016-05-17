//
//  ViewController.m
//  LoveMarker
//
//  Created by BigHead_Chen on 4/4/16.
//  Copyright © 2016 Eason. All rights reserved.
//

#import "ViewController.h"
#import "MainMapViewController.h"
#import "CurrentPostViewController.h"
#import "NearbyViewController.h"
#import "ProfilePageViewController.h"
#import "PublishViewController.h"
#import "LoginViewController.h"
#import "HttpRequest.h"
#import "HttpConfiguration.h"

@interface ViewController ()

@end

@implementation ViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    MainMapViewController *mapViewController = [[MainMapViewController alloc] init];
    [mapViewController.view setBackgroundColor:[UIColor whiteColor]];
    mapViewController.tabBarItem.title = @"map";
    
    mapViewController.tabBarItem.image = [[UIImage imageNamed:@"ic_home_white_18pt_2x"] imageWithRenderingMode:UIImageRenderingModeAlwaysTemplate];
    
    CurrentPostViewController *currentPostViewController = [[CurrentPostViewController alloc] init];
    [currentPostViewController.view setBackgroundColor:[UIColor whiteColor]];
    currentPostViewController.tabBarItem.title = @"current";
    currentPostViewController.tabBarItem.image = [[UIImage imageNamed:@"ic_access_time_white_18dp"] imageWithRenderingMode:UIImageRenderingModeAlwaysTemplate];
    
    NearbyViewController *nearbyViewController = [[NearbyViewController alloc] init];
    [nearbyViewController.view setBackgroundColor:[UIColor whiteColor]];
    nearbyViewController.tabBarItem.title = @"nearby";
    nearbyViewController.tabBarItem.image = [[UIImage imageNamed:@"ic_format_list_numbered_white_18dp"] imageWithRenderingMode:UIImageRenderingModeAlwaysTemplate];
    
    ProfilePageViewController *profileViewController = [[ProfilePageViewController alloc] init];
    [profileViewController.view setBackgroundColor:[UIColor whiteColor]];
    profileViewController.tabBarItem.title = @"profile";
    profileViewController.tabBarItem.image = [[UIImage imageNamed:@"ic_account_box_white_18dp"] imageWithRenderingMode:UIImageRenderingModeAlwaysTemplate];
    
    LoginViewController *loginViewController = [[LoginViewController alloc] init];
    [loginViewController.view setBackgroundColor:[UIColor whiteColor]];
    loginViewController.tabBarItem.title = @"profile";
    loginViewController.tabBarItem.image = [[UIImage imageNamed:@"ic_account_box_white_18dp"] imageWithRenderingMode:UIImageRenderingModeAlwaysTemplate];
    
    PublishViewController *publishViewController = [[PublishViewController alloc] init];
    [publishViewController.view setBackgroundColor:[UIColor whiteColor]];
//    publishViewController.tabBarItem.title = @"profile";
    publishViewController.tabBarItem.image = [[UIImage imageNamed:@"ic_add_box_white_18pt_2x"] imageWithRenderingMode:UIImageRenderingModeAlwaysTemplate];
    
    self.viewControllers = @[mapViewController,currentPostViewController,publishViewController,nearbyViewController,loginViewController];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
