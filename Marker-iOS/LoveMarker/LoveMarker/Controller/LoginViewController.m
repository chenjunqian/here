//
//  LoginViewController.m
//  LoveMarker
//
//  Created by BigHead_Chen on 5/17/16.
//  Copyright © 2016 Eason. All rights reserved.
//

#import "LoginViewController.h"
#import "TopLayoutView.h"
#import "HttpRequest.h"
#import "LoginStatus.h"
#import "User.h"
#import "ErrorState.h"
#import "ResponseResult.h"
#import "NSObject+ObjectMap.h"
#import "MyDataController.h"
#import "CoreDataUser.h"
#import "CommomUtils.h"
#import "RegisterViewController.h"
#import "UnitViewUtil.h"
#import "ColorUtil.h"

@interface LoginViewController ()

@property (nonatomic,strong) UILabel *usernameLabel;
@property (nonatomic,strong) UILabel *passwordLabel;
@property (nonatomic,strong) UITextField *usernameTextField;
@property (nonatomic,strong) UITextField *passwordTextField;
@property (nonatomic,strong) UIButton *loginButton;
@property (nonatomic,strong) UIButton *registerButton;
@property (nonatomic,strong) UIButton *forgetPasswordButton;

@property (nonatomic,strong) NSString *usernameString;
@property (nonatomic,strong) NSString *passwordString;

@end

@implementation LoginViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    [self initView];
}

-(void)viewWillAppear:(BOOL)animated{
    if ([[LoginStatus getInstance] getIsUserModel]) {
        [self dismissViewControllerAnimated:YES completion:nil];
    }
}

-(void)initView {
    
    TopLayoutView *topLayoutView = [[TopLayoutView alloc] initWithContext:self title:NSLocalizedString(@"login", nil) andFrame:CGRectMake(0, 20, self.view.frame.size.width, 50)];
    [self.view addSubview:topLayoutView];
    
    _usernameLabel = [[UILabel alloc] initWithFrame:CGRectMake(0, 0, 40, 25)];
    _usernameLabel.translatesAutoresizingMaskIntoConstraints = NO;
    [_usernameLabel setText:NSLocalizedString(@"login", nil)];
    [self.view addSubview:_usernameLabel];
    
    _passwordLabel = [[UILabel alloc] initWithFrame:CGRectMake(0, 0, 40, 25)];
    _passwordLabel.translatesAutoresizingMaskIntoConstraints = NO;
    [_passwordLabel setText:NSLocalizedString(@"passwordLabel", nil)];
    [self.view addSubview:_passwordLabel];
    
    _usernameTextField = [[UITextField alloc] init];
    _usernameTextField.translatesAutoresizingMaskIntoConstraints = NO;
    [_usernameTextField setPlaceholder:NSLocalizedString(@"username_place_holder", nil)];
    [self.view addSubview:_usernameTextField];
    
    _passwordTextField = [[UITextField alloc] init];
    _passwordTextField.translatesAutoresizingMaskIntoConstraints = NO;
    [_passwordTextField setSecureTextEntry:YES];
    [_passwordTextField setPlaceholder:NSLocalizedString(@"password_place_holder", nil)];
    [self.view addSubview:_passwordTextField];
    
    _loginButton = [[UIButton alloc] init];
    [_loginButton setTitle:NSLocalizedString(@"login", nil) forState:UIControlStateNormal];
    _loginButton.translatesAutoresizingMaskIntoConstraints = NO;
    [_loginButton setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
    [_loginButton setBackgroundColor:[ColorUtil themeColor]];
    [_loginButton addTarget:self action:@selector(loginBtnAction:) forControlEvents:UIControlEventTouchDown];
    [self.view addSubview:_loginButton];
    
    _registerButton = [[UIButton alloc] init];
    [_registerButton setTitle:NSLocalizedString(@"register", nil) forState:UIControlStateNormal];
    [_registerButton setTitleColor:[ColorUtil tealBlueColor] forState:UIControlStateNormal];
    _registerButton.translatesAutoresizingMaskIntoConstraints = NO;
    [_registerButton addTarget:self action:@selector(registerBtnAction:) forControlEvents:UIControlEventTouchDown];
    [self.view addSubview:_registerButton];
    
    _forgetPasswordButton = [[UIButton alloc] init];
    [_forgetPasswordButton setTitle:NSLocalizedString(@"forget_password", nil) forState:UIControlStateNormal];
    _forgetPasswordButton.translatesAutoresizingMaskIntoConstraints = NO;
    [_forgetPasswordButton setTitleColor:[ColorUtil tealBlueColor] forState:UIControlStateNormal];
    [self.view addSubview:_forgetPasswordButton];
    
    
    NSDictionary *views = NSDictionaryOfVariableBindings(topLayoutView,_usernameLabel,_passwordLabel,_usernameTextField,_passwordTextField,_loginButton,_registerButton,_forgetPasswordButton);
    //登录username Label NSLayoutConstraint
    [self.view addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:[topLayoutView]-20-[_usernameLabel]" options:0 metrics:0 views:views]];
    [self.view addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:|-20-[_usernameLabel]" options:0 metrics:0 views:views]];
    //username TextField NSLayoutConstraint
    [self.view addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:|-20-[_usernameTextField(>=200)]-20-|" options:0 metrics:0 views:views]];
    [self.view addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:[_usernameLabel]-20-[_usernameTextField(25)]" options:0 metrics:0 views:views]];
    //password label NSLayoutConstraint
    [self.view addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:|-20-[_passwordLabel]" options:0 metrics:0 views:views]];
    [self.view addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:[_usernameTextField]-20-[_passwordLabel]" options:0 metrics:0 views:views]];
    //password textfile NSLayoutConstraint
    [self.view addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:[_passwordLabel]-20-[_passwordTextField(25)]" options:0 metrics:0 views:views]];
    [self.view addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:|-20-[_passwordTextField(>=200)]-20-|" options:0 metrics:0 views:views]];
    //register button LayoutConstraint
    [self.view addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:|-5-[_registerButton]" options:0 metrics:0 views:views]];
    [self.view addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:[_registerButton(25)]-2-|" options:0 metrics:0 views:views]];
    //forget password LayoutConstraint
    [self.view addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:[_forgetPasswordButton]-5-|" options:0 metrics:0 views:views]];
    [self.view addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:[_forgetPasswordButton(25)]-2-|" options:0 metrics:0 views:views]];
    //login button layout constraint
    [self.view addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:[_passwordTextField]-50-[_loginButton(40)]" options:0 metrics:0 views:views]];
    [self.view addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:|-50-[_loginButton(>=200)]-50-|" options:0 metrics:0 views:views]];
    [self.view addConstraint:[NSLayoutConstraint constraintWithItem:_loginButton attribute:NSLayoutAttributeCenterX relatedBy:NSLayoutRelationEqual toItem:self.view attribute:NSLayoutAttributeCenterX multiplier:1 constant:0]];
    
}

-(IBAction)loginBtnAction:(id)sender{
    _usernameString = _usernameTextField.text;
    _passwordString = _passwordTextField.text;
    if (![CommomUtils isEmptyString:_usernameString]  && ![CommomUtils isEmptyString:_usernameString]) {
        [[LoginStatus getInstance] loginWithUsername:_usernameString password:_passwordString pushKey:@"" successHandler:^{
            
            [self dismissViewControllerAnimated:YES completion:^{
                
            }];
            
        } failedHandler:^(NSInteger errorCode) {
            if(errorCode == Error_Code_User_Not_Found){
                [UnitViewUtil showLoginAlertWithMessage:NSLocalizedString(@"user_not_found", nil) actionOK:NSLocalizedString(@"action_ok", nil) context:self];
                
            }else if (errorCode== ERROR_CODE_USER_OR_PASSWORD_INVALID){
                [UnitViewUtil showLoginAlertWithMessage:NSLocalizedString(@"username_or_password_incorrect", nil) actionOK:NSLocalizedString(@"action_ok", nil) context:self];
            }
        }];
        
    }else{
        [UnitViewUtil showLoginAlertWithMessage:NSLocalizedString(@"input_correct_data", nil) actionOK:NSLocalizedString(@"action_ok", nil) context:self];
    }
    
}

-(IBAction)registerBtnAction:(id)sender{
    RegisterViewController *registerViewController = [[RegisterViewController alloc] init];
    [self presentViewController:registerViewController animated:YES completion:nil];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
}



@end
