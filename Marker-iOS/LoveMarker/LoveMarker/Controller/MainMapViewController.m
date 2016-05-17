//
//  MainMapViewController.m
//  LoveMarker
//
//  Created by BigHead_Chen on 5/12/16.
//  Copyright © 2016 Eason. All rights reserved.
//

#import "MainMapViewController.h"

@interface MainMapViewController ()

@end

@implementation MainMapViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
    UILabel *label = [[UILabel alloc] initWithFrame:CGRectMake(0, 0, 80, 50)];
    [label setText:@"MainMapViewController"];
    [label setTextColor:[UIColor blackColor]];
    [self.view addSubview:label];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
