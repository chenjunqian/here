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

@interface UserInfoEditerViewController ()

@property (strong,nonatomic) EditUserInfoUIView* editUserInfoUIView;
@property (nonatomic) Boolean isUserInfoCheck ;
@property (nonatomic) UserInfoEditorMode editorType;

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
    _editUserInfoUIView = [[EditUserInfoUIView alloc] initWithContext:self title:NSLocalizedString(@"editor_page", nil) editerType:_editorType frame:CGRectMake(0, 0, self.view.frame.size.width, self.view.frame.size.height)];
    [self.view addSubview:_editUserInfoUIView];
    
    _editUserInfoUIView.topLayout.rightButton = [[UIBarButtonItem alloc] initWithBarButtonSystemItem:UIBarButtonSystemItemDone target:self action:@selector(confirmAction:)];
    [_editUserInfoUIView.topLayout.navigationItem setLeftBarButtonItem:_editUserInfoUIView.topLayout.rightButton];
    [_editUserInfoUIView.topLayout.rightButton setTitleTextAttributes:@{NSForegroundColorAttributeName:[UIColor whiteColor]} forState:UIControlStateNormal];
    [_editUserInfoUIView.topLayout.rightButton setTitlePositionAdjustment:UIOffsetMake(10, 0) forBarMetrics:0];
    
    [_editUserInfoUIView.topLayout.rightButton setTitle:NSLocalizedString(@"ok", nil)];
    [_editUserInfoUIView.topLayout.rightButton setAction:@selector(confirmAction:)];
    
    if (_editorType == GENDER_EDITER){
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
        
    }
}

-(IBAction)confirmAction:(id)sender{
    User* user = [[LoginStatus getInstance] getUser];
    
    NSDate *birthday ;
    NSDateFormatter *dateFormat = [[NSDateFormatter alloc] init];
    [dateFormat setDateFormat:@"yyyy/MM/dd"];
    
    NSString *birthdayString ;
    NSArray* birthdayArray ;
    NSString* formatBirthdayString;
    
    _isUserInfoCheck = NO;
    
    if (_editorType == USER_NAME_EDITER) {
        if (![CommomUtils isEmptyString:_editUserInfoUIView.nicknameEditerTextView.text]) {
            user.username = _editUserInfoUIView.nicknameEditerTextView.text;
            _isUserInfoCheck = YES;
        }
    }else if (_editorType == GENDER_EDITER){
        
        if (_editUserInfoUIView.isCheckGender == 1 && ![user.gender isEqualToString:@"male" ]) {
            user.gender = @"male";
            _isUserInfoCheck = YES;
            
        }else if(_editUserInfoUIView.isCheckGender == 2 && ![user.gender isEqualToString:@"female" ]){
            user.gender = @"female";
            _isUserInfoCheck = YES;
        }
        
    }else if (_editorType == BIRTHDAY_EDITER){
        
        birthday = _editUserInfoUIView.datePicker.date;
        birthdayString = [dateFormat stringFromDate:birthday];
        birthdayArray = [birthdayString componentsSeparatedByString:@"/"];
        formatBirthdayString = [NSString stringWithFormat:@"%@-%@-%@",birthdayArray[0],birthdayArray[1],birthdayArray[2]];
        if (![user.birthday isEqualToString:formatBirthdayString]) {
            user.birthday = formatBirthdayString;
            _isUserInfoCheck = YES;
            NSLog(@"date : %@",formatBirthdayString);
        }
        
    }else if (_editorType == SIMPLE_PROFILE_EDITER){
        
//        if (![user.simpleProfile isEqualToString:_editUserInfoUIView.simpleProfileEditerTextView.text]) {
//            user.simpleProfile = _editUserInfoUIView.simpleProfileEditerTextView.text;
//            _isUserInfoCheck = YES;
//        }
        
    }else if (_editorType == LONG_PROFILE_EDITER){
        
//        if (![user.longProfile isEqualToString:_editUserInfoUIView.longProfileEditerTextView.text]) {
//            user.longProfile = _editUserInfoUIView.longProfileEditerTextView.text;
//            _isUserInfoCheck = YES;
//        }
        
    }else if (_editorType == PASSWORD_EDITER){
        
//        if (![user.password isEqualToString:_editUserInfoUIView.longProfileEditerTextView.text]) {
//            user.password = _editUserInfoUIView.longProfileEditerTextView.text;
//            _isUserInfoCheck = YES;
//        }
        
    }
    
    if (_isUserInfoCheck) {
        [self changeUserInfoByUser:user];
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
