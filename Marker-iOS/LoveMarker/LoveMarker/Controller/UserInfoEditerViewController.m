//
//  UserInfoEditerViewController.m
//  LoveMarker
//
//  Created by BigHead_Chen on 7/26/16.
//  Copyright © 2016 Eason. All rights reserved.
//

#import "UserInfoEditerViewController.h"
#import "User.h"
#import "HttpRequest.h"
#import "NSObject+ObjectMap.h"
#import "LoginStatus.h"
#import "ErrorState.h"
#import "CommomUtils.h"
#import "EditUserInfoUIView.h"
#import "UnitViewUtil.h"

@interface UserInfoEditerViewController ()

@property (strong,nonatomic) EditUserInfoUIView* editUserInfoUIView;
@property (nonatomic) Boolean isUserInfoCheck ;
@property (nonatomic) UserInfoEditorMode editorType;
@property (strong , nonatomic) User* user;

@end

@implementation UserInfoEditerViewController


- (void)viewDidLoad {
    [super viewDidLoad];
    [self initView];
}

//根据传递进来的 type 来判断是修改用户信息的那个部分
-(void)setEditerType:(UserInfoEditorMode)type{
    _editorType =type;
}

-(void)initView{
    _user = [[LoginStatus getInstance] getUser];
    if (!_user) {
        return;
    }
    
    _editUserInfoUIView = [[EditUserInfoUIView alloc] initWithContext:self title:NSLocalizedString(@"editor_page", nil) editerType:_editorType frame:CGRectMake(0, 0, self.view.frame.size.width, self.view.frame.size.height)];
    [self.view addSubview:_editUserInfoUIView];
    
    _editUserInfoUIView.topLayout.rightButton = [[UIBarButtonItem alloc] initWithBarButtonSystemItem:UIBarButtonSystemItemDone target:self action:@selector(confirmAction:)];
    [_editUserInfoUIView.topLayout.navigationItem setRightBarButtonItem:_editUserInfoUIView.topLayout.rightButton];
    [_editUserInfoUIView.topLayout.rightButton setTitleTextAttributes:@{NSForegroundColorAttributeName:[UIColor whiteColor]} forState:UIControlStateNormal];
    [_editUserInfoUIView.topLayout.rightButton setTitlePositionAdjustment:UIOffsetMake(-10, 0) forBarMetrics:0];
    
    
    if (_editorType == USER_NAME_EDITER) {
        _editUserInfoUIView.nicknameEditerTextView.text = _user.nickname;
        
    }else if (_editorType == GENDER_EDITER){
        
        [_editUserInfoUIView.maleView whenSingleClick:^{
            _editUserInfoUIView.femalCheckView.hidden = YES;
            _editUserInfoUIView.maleCheckView.hidden = NO;
            [_editUserInfoUIView setGenderType:MALE];
        }];
        
        [_editUserInfoUIView.femaleView whenSingleClick:^{
            _editUserInfoUIView.femalCheckView.hidden = NO;
            _editUserInfoUIView.maleCheckView.hidden = YES;
            [_editUserInfoUIView setGenderType:FEMALE];
        }];
        
        
    }else if (_editorType == SIMPLE_PROFILE_EDITER){
        _editUserInfoUIView.simpleProfileEditerTextView.text = _user.simpleProfile;
        
    }else if (_editorType == LONG_PROFILE_EDITER){
        _editUserInfoUIView.longProfileEditerTextView.text = _user.longProfile;
    }else if(_editorType == BIRTHDAY_EDITER){
        self.view = _editUserInfoUIView;
        [_editUserInfoUIView.okButton addTarget:self action:@selector(confirmAction:) forControlEvents:UIControlEventTouchDown];
        [_editUserInfoUIView.cancelButton addTarget:self action:@selector(birthdayViewCancelButtonAction:) forControlEvents:UIControlEventTouchDown];
    }
}

-(IBAction)birthdayViewCancelButtonAction:(id)sender{
    [self dismissViewControllerAnimated:NO completion:nil];
}

-(IBAction)confirmAction:(id)sender{
    
    NSDate *birthday ;
    NSDateFormatter *dateFormat = [[NSDateFormatter alloc] init];
    [dateFormat setDateFormat:@"yyyy/MM/dd"];
    
    NSString *birthdayString ;
    NSArray* birthdayArray ;
    NSString* formatBirthdayString;
    
    _isUserInfoCheck = NO;
    
    if (_editorType == USER_NAME_EDITER) {
        if (![CommomUtils isEmptyString:_editUserInfoUIView.nicknameEditerTextView.text]) {
            _user.username = _editUserInfoUIView.nicknameEditerTextView.text;
            _isUserInfoCheck = YES;
        }
        
    }else if (_editorType == GENDER_EDITER){
        
        if (_editUserInfoUIView.isCheckGender == 1 && ![_user.gender isEqualToString:@"male" ]) {
            _user.gender = @"male";
            _isUserInfoCheck = YES;
            
        }else if(_editUserInfoUIView.isCheckGender == 2 && ![_user.gender isEqualToString:@"female" ]){
            _user.gender = @"female";
            _isUserInfoCheck = YES;
        }
        
    }else if (_editorType == BIRTHDAY_EDITER){
        
        birthday = _editUserInfoUIView.datePicker.date;
        birthdayString = [dateFormat stringFromDate:birthday];
        birthdayArray = [birthdayString componentsSeparatedByString:@"/"];
        formatBirthdayString = [NSString stringWithFormat:@"%@-%@-%@",birthdayArray[0],birthdayArray[1],birthdayArray[2]];
        if (![_user.birthday isEqualToString:formatBirthdayString]) {
            _user.birthday = formatBirthdayString;
            _isUserInfoCheck = YES;
        }
        
    }else if (_editorType == SIMPLE_PROFILE_EDITER){
        
        if (![_user.simpleProfile isEqualToString:_editUserInfoUIView.simpleProfileEditerTextView.text]) {
            _user.simpleProfile = _editUserInfoUIView.simpleProfileEditerTextView.text;
            _isUserInfoCheck = YES;
        }
        
    }else if (_editorType == LONG_PROFILE_EDITER){
        if (![_user.longProfile isEqualToString:_editUserInfoUIView.longProfileEditerTextView.text]) {
            _user.longProfile = _editUserInfoUIView.longProfileEditerTextView.text;
            _isUserInfoCheck = YES;
        }
        
    }else if (_editorType == PASSWORD_EDITER){
        if ([CommomUtils isEmptyString:_editUserInfoUIView.passwordTextField.text] || [CommomUtils isEmptyString:_editUserInfoUIView.confirmPasswordTextField.text]) {
            [UnitViewUtil showWarningAlertWithMessage:NSLocalizedString(@"please_input_password", nil) actionOK:NSLocalizedString(@"ok", nil) context:self];
            return;
        }else if (![_editUserInfoUIView.passwordTextField.text isEqualToString:_editUserInfoUIView.confirmPasswordTextField.text]) {
            [UnitViewUtil showWarningAlertWithMessage:NSLocalizedString(@"two_password_is_invalid", nil) actionOK:NSLocalizedString(@"ok", nil) context:self];
            
            [_editUserInfoUIView.passwordTextField setText:@""];
            [_editUserInfoUIView.confirmPasswordTextField setText:@""];
            return;
        }else if (![_user.password isEqualToString:_editUserInfoUIView.longProfileEditerTextView.text]) {
            _user.password = _editUserInfoUIView.longProfileEditerTextView.text;
            _isUserInfoCheck = YES;
        }
        
    }
    
    if (_isUserInfoCheck) {
        [self changeUserInfoByUser:_user];
    }else{
        [self dismissViewControllerAnimated:YES completion:nil];
    }
    
}

-(void)changeUserInfoByUser:(User*)user{
    [HttpRequest changeUserInfoByUser:user handler:^(ResponseResult *response, NSObject *resultObject) {
        if (response.status == Error_Code_Correct) {
            User* user = [NSObject objectOfClass:@"User" fromJSON:(NSDictionary*)resultObject];
            [[LoginStatus getInstance] setUser:user];
        }
        
        [self dismissViewControllerAnimated:YES completion:nil];
    }];
}


- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
