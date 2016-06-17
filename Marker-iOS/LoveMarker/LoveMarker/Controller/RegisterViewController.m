//
//  RegisterViewController.m
//  LoveMarker
//
//  Created by BigHead_Chen on 6/18/16.
//  Copyright © 2016 Eason. All rights reserved.
//

#import "RegisterViewController.h"
#import "RegisterLayoutView.h"

@interface RegisterViewController ()

@property(strong,nonatomic)RegisterLayoutView *globalLayout;

@end

@implementation RegisterViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    [self initView];
}

-(void)initView{
    _globalLayout = [[RegisterLayoutView alloc] initWithFrame:CGRectMake(0, 0, self.view.frame.size.width, self.view.frame.size.height)];
    [self.view addSubview:_globalLayout];
    
    [_globalLayout.nextStepButton addTarget:self action:@selector(nextStepButtonAction:) forControlEvents:UIControlEventTouchDown];
}

-(IBAction)nextStepButtonAction:(id)sender{
    
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
}

@end
