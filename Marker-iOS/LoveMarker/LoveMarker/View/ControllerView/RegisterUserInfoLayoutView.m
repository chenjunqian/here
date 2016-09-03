//
//  RegisterUserInfoLayoutView.m
//  LoveMarker
//
//  Created by BigHead_Chen on 6/24/16.
//  Copyright © 2016 Eason. All rights reserved.
//

#import "RegisterUserInfoLayoutView.h"
#import "ColorUtil.h"
#import "TopLayoutView.h"
#import "UserInforCell.h"

@interface RegisterUserInfoLayoutView()

@property (strong,nonatomic) TopLayoutView* topLayoutView;
@property (strong,nonatomic) UserInforCell* genderCell;
@property (strong,nonatomic) UserInforCell* birthdayCell;

@end

@implementation RegisterUserInfoLayoutView


-(id)initViewContext:(id)context title:(NSString*)title frame:(CGRect)frame{
    self = [super initWithFrame:frame];
    
    if (self) {
        _topLayoutView = [[TopLayoutView alloc] initWithContext:context title:title andFrame:CGRectMake(0, 20, self.frame.size.width, 40)];
        [self addSubview:_topLayoutView];
        
        _genderLayout = [[UIView alloc] init];
        _genderLayout.translatesAutoresizingMaskIntoConstraints = NO;
        [_genderLayout setBackgroundColor:[UIColor whiteColor]];
//        _genderLayout.layer.borderWidth = 0.25;
//        _genderLayout.layer.borderColor = [[UIColor lightGrayColor] CGColor];
        [self addSubview:_genderLayout];
        
        _genderCell = [[UserInforCell alloc] init];
        _genderCell.translatesAutoresizingMaskIntoConstraints = NO;
        _genderCell.titleUILabel.text = NSLocalizedString(@"register_your_gender", nil);
        _genderCell.titleUILabel.textColor = [ColorUtil tealBlueColor];
        _genderCell.backgroundColor = [UIColor whiteColor];
//        _genderCell.layer.borderWidth = 0.25;
//        _genderCell.layer.borderColor = [[UIColor lightGrayColor] CGColor];
        [self addSubview:_genderCell];
        
        _maleView = [[ClickableUIView alloc] init];
        _maleView.translatesAutoresizingMaskIntoConstraints = NO;
        [_genderLayout addSubview:_maleView];
        
        _femaleView = [[ClickableUIView alloc] init];
        _femaleView.translatesAutoresizingMaskIntoConstraints = NO;
        [_genderLayout addSubview:_femaleView];
        
        _genderMiddleLineView = [[UIView alloc] init];
        _genderMiddleLineView.translatesAutoresizingMaskIntoConstraints = NO;
        [_genderMiddleLineView setBackgroundColor:[UIColor lightGrayColor]];
        [_genderLayout addSubview:_genderMiddleLineView];
        
        _maleImageView = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"male_sign_24px"]];
        _maleImageView.translatesAutoresizingMaskIntoConstraints = NO;
        [_maleView addSubview:_maleImageView];
        
        _maleCheckView = [[UIImageView alloc] init];
        _maleCheckView.translatesAutoresizingMaskIntoConstraints = NO;
        [_maleCheckView setImage:[UIImage imageNamed:@"ic_check_12pt_2x"]];
        [_maleView addSubview:_maleCheckView];
        
        _femaleImageView = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"female_sign_24px"]];
        _femaleImageView.translatesAutoresizingMaskIntoConstraints = NO;
        [_femaleView addSubview:_femaleImageView];
        
        _femalCheckView = [[UIImageView alloc] init];
        _femalCheckView.translatesAutoresizingMaskIntoConstraints = NO;
        [_femalCheckView setImage:[UIImage imageNamed:@"ic_check_12pt_2x"]];
        [_femaleView addSubview:_femalCheckView];
        
        _birthdayCell = [[UserInforCell alloc] init];
        _birthdayCell.translatesAutoresizingMaskIntoConstraints = NO;
        _birthdayCell.titleUILabel.text = NSLocalizedString(@"register_your_birthday", nil);
        _birthdayCell.titleUILabel.textColor = [ColorUtil tealBlueColor];
        _birthdayCell.backgroundColor = [UIColor whiteColor];
//        _birthdayCell.layer.borderWidth = 0.25;
//        _birthdayCell.layer.borderColor = [[UIColor lightGrayColor] CGColor];
        [self addSubview:_birthdayCell];
        
        _datePicker = [[UIDatePicker alloc] init];
        [_datePicker setDatePickerMode:UIDatePickerModeDate];
        _datePicker.translatesAutoresizingMaskIntoConstraints = NO;
        _datePicker.backgroundColor = [UIColor whiteColor];
        NSString *stringDate = @"07/15/1995";
        NSDateFormatter *dateFormatter = [[NSDateFormatter alloc]init];
        [dateFormatter setDateFormat:@"MM/dd/yyyy"];
        NSDate *initDate = [dateFormatter dateFromString:stringDate];
        [_datePicker setDate:initDate animated:YES];
        [self addSubview:_datePicker];
        
        _nextStepButton = [[UIButton alloc] init];
        _nextStepButton.translatesAutoresizingMaskIntoConstraints = NO;
        [_nextStepButton setTitle:NSLocalizedString(@"next_step", nil) forState:UIControlStateNormal];
        [_nextStepButton setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
        _nextStepButton.backgroundColor = [ColorUtil themeColor];
        _nextStepButton.layer.cornerRadius = 5;
        _nextStepButton.layer.masksToBounds = YES;
        [self addSubview:_nextStepButton];
        
    }
    
    return self;
}

-(void)layoutSubviews{
    NSDictionary *views = NSDictionaryOfVariableBindings(_topLayoutView,_genderLayout,_maleView,_genderMiddleLineView,_maleImageView,_maleCheckView,_femaleView,_femaleImageView,_femalCheckView,_datePicker,_nextStepButton,_genderCell,_birthdayCell);
    
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:[_topLayoutView]-10-[_genderCell(40)]" options:0 metrics:0 views:views]];
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:|-0-[_genderCell]-0-|" options:0 metrics:0 views:views]];
    
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:[_genderCell]-1-[_genderLayout]" options:0 metrics:0 views:views]];
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:|-0-[_genderLayout(>=200)]-0-|" options:0 metrics:0 views:views]];
    
    [_genderLayout addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:|-0-[_maleView]-0-|" options:0 metrics:0 views:views]];
    [_genderLayout addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:|-0-[_maleView(40)]" options:0 metrics:0 views:views]];
    
    [_genderLayout addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:|-20-[_genderMiddleLineView]-0-|" options:0 metrics:0 views:views]];
    [_genderLayout addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:[_maleView]-0-[_genderMiddleLineView(0.25)]" options:0 metrics:0 views:views]];
    
    [_genderLayout addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:|-0-[_femaleView]-0-|" options:0 metrics:0 views:views]];
    [_genderLayout addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:[_genderMiddleLineView]-0-[_femaleView(40)]-0-|" options:0 metrics:0 views:views]];
    
    [_maleView addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:|-20-[_maleImageView(20)]" options:0 metrics:0 views:views]];
    [_maleView addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:[_maleImageView(20)]" options:0 metrics:0 views:views]];
    [_maleView addConstraint:[NSLayoutConstraint constraintWithItem:_maleImageView attribute:NSLayoutAttributeCenterY relatedBy:NSLayoutRelationEqual toItem:_maleView attribute:NSLayoutAttributeCenterY multiplier:1 constant:0]];
    
    [_maleView addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:[_maleCheckView(15)]-15-|" options:0 metrics:0 views:views]];
    [_maleView addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:[_maleCheckView(15)]-15-|" options:0 metrics:0 views:views]];
    
    [_femaleView addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:|-20-[_femaleImageView(20)]" options:0 metrics:0 views:views]];
    [_femaleView addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:[_femaleImageView(20)]" options:0 metrics:0 views:views]];
    [_femaleView addConstraint:[NSLayoutConstraint constraintWithItem:_femaleImageView attribute:NSLayoutAttributeCenterY relatedBy:NSLayoutRelationEqual toItem:_femaleView attribute:NSLayoutAttributeCenterY multiplier:1 constant:0]];
    
    [_femaleView addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:[_femalCheckView(15)]-15-|" options:0 metrics:0 views:views]];
    [_femaleView addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:[_femalCheckView(15)]-15-|" options:0 metrics:0 views:views]];
    
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:[_femaleView]-30-[_birthdayCell(40)]" options:0 metrics:0 views:views]];
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:|-0-[_birthdayCell(>=200)]-0-|" options:0 metrics:0 views:views]];
    
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:[_birthdayCell]-1-[_datePicker]" options:0 metrics:0 views:views]];
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:|-0-[_datePicker]-0-|" options:0 metrics:0 views:views]];
    
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:[_datePicker]-30-[_nextStepButton(40)]" options:0 metrics:0 views:views]];
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:|-50-[_nextStepButton]-50-|" options:0 metrics:0 views:views]];
}

@end
