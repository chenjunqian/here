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
#import "LoginStatus.h"
#import "User.h"

@interface ViewController ()

@end

typedef enum{
    MapUIController = 100,
    CurrentPostUIViewController = 101,
    PublishUIViewController = 102,
    NearbyUIViewController = 103,
    ProfilePageUIViewController = 104,
}UITabBarItemEnum;

@implementation ViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    [self initSetting];
    
    MainMapViewController *mapViewController = [[MainMapViewController alloc] init];
    mapViewController.tabBarItem.title = NSLocalizedString(@"home_page", nil);
    mapViewController.tabBarItem.tag = 100;
    
    mapViewController.tabBarItem.image = [[UIImage imageNamed:@"ic_home_white_18pt_2x"] imageWithRenderingMode:UIImageRenderingModeAlwaysTemplate];
    
    CurrentPostViewController *currentPostViewController = [[CurrentPostViewController alloc] init];    currentPostViewController.tabBarItem.title = NSLocalizedString(@"current_page", nil);
    currentPostViewController.tabBarItem.image = [[UIImage imageNamed:@"ic_access_time_white_18dp"] imageWithRenderingMode:UIImageRenderingModeAlwaysTemplate];
    currentPostViewController.tabBarItem.tag = 101;
    
    PublishViewController *publishViewController = [[PublishViewController alloc] init];//    publishViewController.tabBarItem.title = @"profile";
    publishViewController.tabBarItem.image = [[UIImage imageNamed:@"ic_add_box_white_18pt_2x"] imageWithRenderingMode:UIImageRenderingModeAlwaysTemplate];
    publishViewController.tabBarItem.tag = 102;
    
    NearbyViewController *nearbyViewController = [[NearbyViewController alloc] init];
    nearbyViewController.tabBarItem.title = NSLocalizedString(@"nearby_page", nil);
    nearbyViewController.tabBarItem.image = [[UIImage imageNamed:@"ic_format_list_numbered_white_18dp"] imageWithRenderingMode:UIImageRenderingModeAlwaysTemplate];
    nearbyViewController.tabBarItem.tag = 103;
    
    ProfilePageViewController *profileViewController = [[ProfilePageViewController alloc] init];
    profileViewController.tabBarItem.title = NSLocalizedString(@"profile_page", nil);
    profileViewController.tabBarItem.image = [[UIImage imageNamed:@"ic_account_box_white_18dp"] imageWithRenderingMode:UIImageRenderingModeAlwaysTemplate];
    profileViewController.tabBarItem.tag = 104;

    
    self.viewControllers = @[mapViewController,currentPostViewController,publishViewController,nearbyViewController,profileViewController];
}

-(void)tabBar:(UITabBar *)tabBar didSelectItem:(UITabBarItem *)item{
    switch (item.tag) {
        case MapUIController:
            
            break;
      
        case CurrentPostUIViewController:
            
            break;

        case PublishUIViewController:
            if (![[LoginStatus getInstance] getIsUserModel]) {
                
                return;
            }
            break;

        case NearbyUIViewController:
            
            break;

        case ProfilePageUIViewController:
            
            break;

        default:
            break;
    }
}


-(void)initSetting{
    [LoginStatus initInstanc];
    [[LoginStatus getInstance] autoLogin];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
}

@end
