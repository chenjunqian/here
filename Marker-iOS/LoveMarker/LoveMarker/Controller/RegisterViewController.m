//
//  RegisterViewController.m
//  LoveMarker
//
//  Created by BigHead_Chen on 6/18/16.
//  Copyright © 2016 Eason. All rights reserved.
//

#import "RegisterViewController.h"
#import "RegisterLayoutView.h"
#import "RegisterUserInfoViewController.h"
#import "UnitViewUtil.h"
#import "LoginStatus.h"
#import "CommomUtils.h"
#import "HttpRequest.h"
#import "ErrorState.h"

@interface RegisterViewController ()

@property(strong,nonatomic)RegisterLayoutView *globalLayout;
@property(strong,nonatomic)NSString* usernameString;
@property(strong,nonatomic)NSString* passwordString;
@property(strong,nonatomic)NSString* nicknameString;

@end

@implementation RegisterViewController

__strong static id instanc = nil;

+(instancetype)getInstance{
    return instanc;
}


- (void)viewDidLoad {
    [super viewDidLoad];
    instanc = self;
    [self initView];
}

-(void)viewWillAppear:(BOOL)animated{
    User* testUser =[[LoginStatus getInstance] getUser];
    if (testUser) {
        [self dismissViewControllerAnimated:YES completion:nil];
    }
}

-(void)initView{
    _globalLayout = [[RegisterLayoutView alloc] initWithContext:self frame:CGRectMake(0, 0, self.view.frame.size.width, self.view.frame.size.height)];
    [self.view addSubview:_globalLayout];
    
    [_globalLayout.nextStepButton addTarget:self action:@selector(nextStepButtonAction:) forControlEvents:UIControlEventTouchDown];
}

-(IBAction)nextStepButtonAction:(id)sender{
    _usernameString = _globalLayout.usernameTextField.text;
    _passwordString = _globalLayout.passwordTextField.text;
    _nicknameString = _globalLayout.nicknameTextField.text;
    
    if (![CommomUtils isEmptyString:_usernameString]&& ![CommomUtils isEmptyString:_passwordString]) {
        //password' length must bigger than 6
        if ([_passwordString length]<6) {
            [UnitViewUtil showLoginAlertWithMessage:NSLocalizedString(@"set_password", nil) actionOK:NSLocalizedString(@"action_ok", nil) context:self];
            return;
        }else if ([CommomUtils isEmptyString:_nicknameString]){
            [UnitViewUtil showLoginAlertWithMessage:NSLocalizedString(@"please_input_nickname", nil) actionOK:NSLocalizedString(@"action_ok", nil) context:self];
            return;
        }else if([CommomUtils getMixStringLength:_nicknameString]>12){
            [UnitViewUtil showLoginAlertWithMessage:NSLocalizedString(@"nickname_length_should_not_bigger_than", nil) actionOK:NSLocalizedString(@"action_ok", nil) context:self];
            return;
        }
        
        if ([CommomUtils isValidateEmail:_usernameString]||[CommomUtils isValidateMobile:_usernameString]) {
            //check is user accout is already exist
            [HttpRequest checkIsUserExistWithUsername:_usernameString responseData:^(ResponseResult *responese, NSObject *resultObject) {
                if (responese && responese.status==Error_Code_Correct) {
                    //user account is exist
                    [UnitViewUtil showLoginAlertWithMessage:NSLocalizedString(@"user_account_is_already_exsit", nil) actionOK:NSLocalizedString(@"action_ok", nil) context:self];
                    
                }else{
                    //user account is not exist
                    RegisterUserInfoViewController *registerUserInfoController = [[RegisterUserInfoViewController alloc] init];
                    [self presentViewController:registerUserInfoController animated:YES completion:^{
                        _registerTempUser = [[User alloc] init];
                        _registerTempUser.nickname = _nicknameString;
                        _registerTempUser.username = _usernameString;
                        _registerTempUser.password = _passwordString;
                        _registerTempUser.pushKey = @"";
                    }];
                    
                }
            }];
            
        }else{
            [UnitViewUtil showLoginAlertWithMessage:NSLocalizedString(@"please_input_valid_eamil_or_phone_number", nil) actionOK:NSLocalizedString(@"action_ok", nil) context:self];
        }
        
    }else if([CommomUtils isEmptyString:_usernameString]){
        [UnitViewUtil showLoginAlertWithMessage:NSLocalizedString(@"please_input_username", nil) actionOK:NSLocalizedString(@"action_ok", nil) context:self];
    }else if([CommomUtils isEmptyString:_passwordString]){
        [UnitViewUtil showLoginAlertWithMessage:NSLocalizedString(@"please_input_password", nil) actionOK:NSLocalizedString(@"action_ok", nil) context:self];
    }
    
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
}

@end
