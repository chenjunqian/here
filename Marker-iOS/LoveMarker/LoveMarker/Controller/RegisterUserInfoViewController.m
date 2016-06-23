//
//  RegisterUserInfoViewController.m
//  LoveMarker
//
//  Created by BigHead_Chen on 6/24/16.
//  Copyright © 2016 Eason. All rights reserved.
//

#import "RegisterUserInfoViewController.h"
#import "RegisterUserInfoLayoutView.h"

@interface RegisterUserInfoViewController ()

@end

@implementation RegisterUserInfoViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    [self initView];
}

-(void)initView{
    RegisterUserInfoLayoutView *registerUserInfoLayoutView = [[RegisterUserInfoLayoutView alloc] initViewContext:self title:NSLocalizedString(@"", nil) frame:CGRectMake(0, 0, self.view.frame.size.width, self.view.frame.size.height)];
    [self.view addSubview:registerUserInfoLayoutView];
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
