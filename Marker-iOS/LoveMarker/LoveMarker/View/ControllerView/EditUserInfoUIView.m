//
//  EditUserInfoUIView.m
//  LoveMarker
//
//  Created by BigHead_Chen on 7/26/16.
//  Copyright © 2016 Eason. All rights reserved.
//

#import "EditUserInfoUIView.h"
#import "ColorUtil.h"

@implementation EditUserInfoUIView

/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect {
    // Drawing code
}
*/

@synthesize topLayout,nicknameEditerTextView,genderLayout,genderMiddleLineView,maleView,maleLabel,maleCheckView,femaleView,femaleLabel,femalCheckView,datePicker,simpleProfileEditerTextView,longProfileEditerTextView;

-(id)initWithContext:(id)context title:(NSString *)topTitle editerType:(UserInfoEditorMode)type frame:(CGRect)frame{
    self = [super initWithFrame:frame];
    
    topLayout = [[TopLayoutView alloc] initWithContext:self title:NSLocalizedString(@"editor_page", nil) andFrame:CGRectMake(0, 20, self.frame.size.width, 50)];
    [self addSubview:topLayout];
    
    
    if (self) {
        switch (type) {
            case USER_NAME_EDITER:
                [self initUsernameEditer];
                topLayout.navigationItem.title = NSLocalizedString(@"nickname_editor", nil);
                break;
            case GENDER_EDITER:
                [self initGenderEditer];
                topLayout.navigationItem.title = NSLocalizedString(@"gender_editor", nil);
                break;
            case BIRTHDAY_EDITER:
                [self initBirthdayEditer];
                topLayout.navigationItem.title = NSLocalizedString(@"birthday_editor", nil);
                break;
            case SIMPLE_PROFILE_EDITER:
                
                break;
            case LONG_PROFILE_EDITER:
                
                break;
            case PASSWORD_EDITER:
                
                break;
            default:
                break;
        }
    }
    
    return self;
}

-(void)setGenderType:(GenderType)genderType{
    _isCheckGender = genderType;
}

-(void)initUsernameEditer{
    nicknameEditerTextView = [[UITextView alloc] initWithFrame:CGRectMake(0, 90, self.frame.size.width, 35)];
    [self addSubview:nicknameEditerTextView];
}

-(void)initGenderEditer{
    genderLayout = [[UIView alloc] init];
    genderLayout.translatesAutoresizingMaskIntoConstraints = NO;
    genderLayout.backgroundColor = [UIColor whiteColor];
//    [genderLayout setBackgroundColor:[ColorUtil viewBackgroundGrey]];
    [self addSubview:genderLayout];
    
    maleView = [[ClickableUIView alloc] init];
    maleView.translatesAutoresizingMaskIntoConstraints = NO;
    [genderLayout addSubview:maleView];
    
    femaleView = [[ClickableUIView alloc] init];
    femaleView.translatesAutoresizingMaskIntoConstraints = NO;
    [genderLayout addSubview:femaleView];
    
    genderMiddleLineView = [[UIView alloc] init];
    genderMiddleLineView.translatesAutoresizingMaskIntoConstraints = NO;
    [genderMiddleLineView setBackgroundColor:[ColorUtil viewBackgroundGrey]];
    [genderLayout addSubview:genderMiddleLineView];
    
    maleLabel = [[UILabel alloc] init];
    maleLabel.translatesAutoresizingMaskIntoConstraints = NO;
    [maleLabel setText:NSLocalizedString(@"male", nil)];
    [maleView addSubview:maleLabel];
    
    maleCheckView = [[UIView alloc] init];
    maleCheckView.translatesAutoresizingMaskIntoConstraints = NO;
    maleCheckView.backgroundColor = [ColorUtil themeColor];
    maleCheckView.layer.masksToBounds = YES;
    maleCheckView.layer.cornerRadius = 5;
    maleCheckView.hidden = YES;
    [maleView addSubview:maleCheckView];
    
    femaleLabel = [[UILabel alloc] init];
    femaleLabel.translatesAutoresizingMaskIntoConstraints = NO;
    [femaleLabel setText:NSLocalizedString(@"female", nil)];
    [femaleView addSubview:femaleLabel];
    
    femalCheckView = [[UIView alloc] init];
    femalCheckView.translatesAutoresizingMaskIntoConstraints = NO;
    femalCheckView.backgroundColor = [ColorUtil themeColor];
    femalCheckView.layer.masksToBounds = YES;
    femalCheckView.layer.cornerRadius = 5;
    femalCheckView.hidden = YES;
    [femaleView addSubview:femalCheckView];
    
    NSDictionary *views = NSDictionaryOfVariableBindings(topLayout,genderLayout,maleView,genderMiddleLineView,maleLabel,maleCheckView,femaleView,femaleLabel,femalCheckView);
    
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:[topLayout]-20-[genderLayout]" options:0 metrics:0 views:views]];
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:|-0-[genderLayout(>=200)]-0-|" options:0 metrics:0 views:views]];
    
    [genderLayout addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:|-0-[maleView]-0-|" options:0 metrics:0 views:views]];
    [genderLayout addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:|-0-[maleView(40)]" options:0 metrics:0 views:views]];
    
    [genderLayout addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:|-0-[genderMiddleLineView]-0-|" options:0 metrics:0 views:views]];
    [genderLayout addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:[maleView]-0-[genderMiddleLineView(1)]" options:0 metrics:0 views:views]];
    
    [genderLayout addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:|-0-[femaleView]-0-|" options:0 metrics:0 views:views]];
    [genderLayout addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:[genderMiddleLineView]-0-[femaleView(40)]-0-|" options:0 metrics:0 views:views]];
    
    [maleView addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:|-5-[maleLabel]" options:0 metrics:0 views:views]];
    [maleView addConstraint:[NSLayoutConstraint constraintWithItem:maleLabel attribute:NSLayoutAttributeCenterY relatedBy:NSLayoutRelationEqual toItem:maleView attribute:NSLayoutAttributeCenterY multiplier:1 constant:0]];
    
    [maleView addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:[maleCheckView(10)]-15-|" options:0 metrics:0 views:views]];
    [maleView addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:[maleCheckView(10)]-15-|" options:0 metrics:0 views:views]];
    
    [femaleView addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:|-5-[femaleLabel]" options:0 metrics:0 views:views]];
    [femaleView addConstraint:[NSLayoutConstraint constraintWithItem:femaleLabel attribute:NSLayoutAttributeCenterY relatedBy:NSLayoutRelationEqual toItem:femaleView attribute:NSLayoutAttributeCenterY multiplier:1 constant:0]];
    
    [femaleView addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:[femalCheckView(10)]-15-|" options:0 metrics:0 views:views]];
    [femaleView addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:[femalCheckView(10)]-15-|" options:0 metrics:0 views:views]];
}

-(void)initBirthdayEditer{
    
    datePicker = [[UIDatePicker alloc] init];
    [datePicker setDatePickerMode:UIDatePickerModeDate];
    datePicker.translatesAutoresizingMaskIntoConstraints = NO;
    NSString *stringDate = @"07/15/1995";
    NSDateFormatter *dateFormatter = [[NSDateFormatter alloc]init];
    [dateFormatter setDateFormat:@"MM/dd/yyyy"];
    NSDate *initDate = [dateFormatter dateFromString:stringDate];
    [datePicker setDate:initDate animated:YES];
    [self addSubview:datePicker];
    
    NSDictionary *views = NSDictionaryOfVariableBindings(topLayout,datePicker);
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:[topLayout]-50-[datePicker]" options:0 metrics:0 views:views]];
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:|-50-[datePicker]-50-|" options:0 metrics:0 views:views]];
}

@end
