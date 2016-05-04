//
//  MainMapUIControllerViewController.m
//  LoveMarker
//
//  Created by BigHead_Chen on 4/4/16.
//  Copyright © 2016 Eason. All rights reserved.
//

#import "MainMapUIControllerViewController.h"

@interface MainMapUIControllerViewController ()

@end

@implementation MainMapUIControllerViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    [self.view setBackgroundColor:[UIColor whiteColor]];
    UILabel *testLabel = [[UILabel alloc] initWithFrame:CGRectMake(0, 0, 200, 50)];
    [testLabel setText:@"This main map view"];
    testLabel.textAlignment = NSTextAlignmentCenter;
    [testLabel setCenter:self.view.center];
    [self.view addSubview:testLabel];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

@end
