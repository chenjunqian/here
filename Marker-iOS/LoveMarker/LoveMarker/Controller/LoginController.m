//
//  LoginController.m
//  LoveMarker
//
//  Created by BigHead_Chen on 5/11/16.
//  Copyright © 2016 Eason. All rights reserved.
//

#import "LoginController.h"
#import "HttpRequest.h"
#import "User.h"

@interface LoginController ()
@property (weak, nonatomic) IBOutlet UILabel *topViewTitleLabel;
@property (weak, nonatomic) IBOutlet UILabel *usernameLabel;
@property (weak, nonatomic) IBOutlet UILabel *passwordLabel;
@property (weak, nonatomic) IBOutlet UITextField *usernameTextFiled;
@property (weak, nonatomic) IBOutlet UITextField *passwordTextFiled;
@property (weak, nonatomic) IBOutlet UIButton *loginButton;
@property (weak, nonatomic) IBOutlet UIButton *registerButton;
@property (weak, nonatomic) IBOutlet UIButton *forgetPasswordButton;
@property (weak, nonatomic) IBOutlet UIButton *backButton;

@property (nonatomic,strong) NSString *inputUsername;
@property (nonatomic,strong) NSString *inputPassword;
@property (nonatomic,strong) User *user;

@end

@implementation LoginController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    [_topViewTitleLabel setText:NSLocalizedString(@"login", nil)];
    [_usernameLabel setText:NSLocalizedString(@"loginLabel", nil)];
    [_passwordLabel setText:NSLocalizedString(@"passwordLabel", nil)];
    [_loginButton setTitle:NSLocalizedString(@"login", nil) forState:UIControlStateNormal];
    [_loginButton addTarget:self action:@selector(loginAction:) forControlEvents:UIControlEventTouchDown];
    [_backButton addTarget:self action:@selector(backBttuonAction:) forControlEvents:UIControlEventTouchDown];
    [_registerButton setTitle:NSLocalizedString(@"register", nil) forState:UIControlStateNormal];
    [_forgetPasswordButton setTitle:NSLocalizedString(@"forget_password", nil) forState:UIControlStateNormal];
    [_usernameTextFiled setPlaceholder:NSLocalizedString(@"username_place_holder", nil)];
    [_passwordTextFiled setPlaceholder:NSLocalizedString(@"password_place_holder", nil)];
}

-(IBAction)loginAction:(id)sender{
    _inputUsername = _usernameTextFiled.text;
    _inputPassword = _passwordTextFiled.text;
    
    _user = [[User alloc] init];
    
    if (_inputUsername && _inputPassword) {

    }else{
        
    }
}
- (IBAction)backBttuonAction:(id)sender {
    [self dismissViewControllerAnimated:YES completion:^{
        
    }];
    
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
}

@end
