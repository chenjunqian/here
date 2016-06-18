//
//  RegisterViewController.m
//  LoveMarker
//
//  Created by BigHead_Chen on 6/18/16.
//  Copyright © 2016 Eason. All rights reserved.
//

#import "RegisterViewController.h"
#import "RegisterLayoutView.h"
#import "UnitViewUtil.h"
#import "CommomUtils.h"

@interface RegisterViewController ()

@property(strong,nonatomic)RegisterLayoutView *globalLayout;
@property(strong,nonatomic)NSString* usernameString;
@property(strong,nonatomic)NSString* passwordString;

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
    _usernameString = _globalLayout.usernameTextField.text;
    _passwordString = _globalLayout.passwordTextField.text;
    
    if (![CommomUtils isEmptyString:_usernameString]&& ![CommomUtils isEmptyString:_passwordString]) {
        if (![CommomUtils isValidateEmail:_usernameString]) {
            [UnitViewUtil showLoginAlertWithMessage:NSLocalizedString(@"user_not_found", nil) actionOK:NSLocalizedString(@"action_ok", nil) context:self];
        }else if (![CommomUtils isValidateMobile:_usernameString]){
            [UnitViewUtil showLoginAlertWithMessage:NSLocalizedString(@"user_not_found", nil) actionOK:NSLocalizedString(@"action_ok", nil) context:self];
        }
        
        
    }
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
}

@end
