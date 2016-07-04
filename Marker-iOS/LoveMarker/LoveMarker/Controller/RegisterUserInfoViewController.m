//
//  RegisterUserInfoViewController.m
//  LoveMarker
//
//  Created by BigHead_Chen on 6/24/16.
//  Copyright © 2016 Eason. All rights reserved.
//

#import "RegisterUserInfoViewController.h"
#import "RegisterUserInfoLayoutView.h"
#import "RegisterViewController.h"
#import "ErrorState.h"
#import "HttpRequest.h"
#import "UnitViewUtil.h"
#import "CommomUtils.h"
#import "LoginStatus.h"
#import "User.h"

@interface RegisterUserInfoViewController ()

@property (strong,nonatomic)RegisterUserInfoLayoutView *registerUserInfoLayoutView;
@property NSInteger isCheckGender;

@end

@implementation RegisterUserInfoViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    [self initView];
}

-(void)initView{
    _registerUserInfoLayoutView = [[RegisterUserInfoLayoutView alloc] initViewContext:self title:NSLocalizedString(@"register_info", nil) frame:CGRectMake(0, 0, self.view.frame.size.width, self.view.frame.size.height)];
    [self.view addSubview:_registerUserInfoLayoutView];
    
    _registerUserInfoLayoutView.femalCheckView.hidden = YES;
    _registerUserInfoLayoutView.maleCheckView.hidden = YES;
    [_registerUserInfoLayoutView.nextStepButton addTarget:self action:@selector(nextStep:) forControlEvents:UIControlEventTouchDown];
    
    _isCheckGender = 0;
    
    [_registerUserInfoLayoutView.maleView whenSingleClick:^{
        _isCheckGender  = 1;
        _registerUserInfoLayoutView.femalCheckView.hidden = YES;
        _registerUserInfoLayoutView.maleCheckView.hidden = NO;
        
    }];
    
    [_registerUserInfoLayoutView.femaleView whenSingleClick:^{
        _isCheckGender  = 2;
        _registerUserInfoLayoutView.femalCheckView.hidden = NO;
        _registerUserInfoLayoutView.maleCheckView.hidden = YES;
    }];

}

-(IBAction)nextStep:(id)sender{
    NSDate *birthday = _registerUserInfoLayoutView.datePicker.date;
    NSDateFormatter *dateFormat = [[NSDateFormatter alloc] init];
    [dateFormat setDateFormat:@"yyyy/MM/dd"];
    NSString *birthdayString = [dateFormat stringFromDate:birthday];
    
    if (_isCheckGender == 0) {
        [UnitViewUtil showLoginAlertWithMessage:NSLocalizedString(@"please_select_gender_first", nil) actionOK:NSLocalizedString(@"action_ok", nil) context:self];
        return;
    }else if([CommomUtils isEmptyString:birthdayString]){
        [UnitViewUtil showLoginAlertWithMessage:NSLocalizedString(@"please_select_birthday_first", nil) actionOK:NSLocalizedString(@"action_ok", nil) context:self];
        return;
    }

    NSArray* birthdayArray = [birthdayString componentsSeparatedByString:@"/"];
    NSString* formatBirthdayString = [NSString stringWithFormat:@"%@-%@-%@",birthdayArray[0],birthdayArray[1],birthdayArray[2]];
    NSLog(@"date : %@",formatBirthdayString);
    User* user = [RegisterViewController getInstance].registerTempUser;
    user.birthday = formatBirthdayString;
    if (_isCheckGender == 1) {
        user.gender = @"male";
    }else if(_isCheckGender == 2){
        user.gender = @"female";
    }
    
    [HttpRequest registerWithUsername:user.username password:user.password pushKey:user.pushKey nickname:user.nickname gender:user.gender birthday:user.birthday responseData:^(ResponseResult *response, NSObject *resultObject) {
        if (resultObject!=nil&&response.status == Error_Code_Correct) {
            [[LoginStatus getInstance] setUser:user];
            //注册完毕后马上登陆
            [[LoginStatus getInstance] loginWithUsername:user.username password:user.password pushKey:user.pushKey successHandler:^{
                [self dismissViewControllerAnimated:YES completion:nil];
            } failedHandler:^(NSInteger errorCode) {
                
            }];
        }
        
    }];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
}

@end
