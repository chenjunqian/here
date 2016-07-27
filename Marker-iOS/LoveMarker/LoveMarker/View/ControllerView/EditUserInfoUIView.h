//
//  EditUserInfoUIView.h
//  LoveMarker
//
//  Created by BigHead_Chen on 7/26/16.
//  Copyright © 2016 Eason. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "UserInforCell.h"
#import "TopLayoutView.h"

typedef  enum{
    USER_NAME_EDITER = 0,
    GENDER_EDITER = 1,
    BIRTHDAY_EDITER = 2,
    SIMPLE_PROFILE_EDITER = 3,
    LONG_PROFILE_EDITER = 4 ,
    PASSWORD_EDITER = 5,
}UserInfoEditorMode;

typedef enum{
    MALE = 1,
    FEMALE = 2,
}GenderType;


@interface EditUserInfoUIView : UIView

-(id)initWithContext:(id)context title:(NSString *)topTitle editerType:(UserInfoEditorMode)type frame:(CGRect)frame;

-(void)setGenderType:(GenderType)genderType;

@property (nonatomic) NSInteger isCheckGender;

@property(strong,nonatomic) TopLayoutView* topLayout;

@property (strong,nonatomic) UITextView* nicknameEditerTextView;

@property (strong,nonatomic) UIView *genderLayout;
@property (strong,nonatomic) UIView *genderMiddleLineView;
@property (strong,nonatomic) UILabel *maleLabel;
@property (strong,nonatomic) ClickableUIView *maleView;
@property (strong,nonatomic) UIView *maleCheckView;
@property (strong,nonatomic) UILabel *femaleLabel;
@property (strong,nonatomic) ClickableUIView *femaleView;
@property (strong,nonatomic) UIView *femalCheckView;

@property (strong,nonatomic) UIDatePicker* datePicker;

@property (strong,nonatomic) UITextView* simpleProfileEditerTextView;
@property (strong,nonatomic) UITextView* longProfileEditerTextView;
@end
