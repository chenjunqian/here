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

@interface RegisterUserInfoLayoutView()

@property (strong,nonatomic) TopLayoutView* topLayoutView;

@end

@implementation RegisterUserInfoLayoutView

/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect {
    // Drawing code
}
*/

-(id)initViewContext:(id)context title:(NSString*)title frame:(CGRect)frame{
    self = [super initWithFrame:frame];
    
    if (self) {
        _topLayoutView = [[TopLayoutView alloc] initWithContext:context title:title andFrame:CGRectMake(0, 20, self.frame.size.width, 50)];
        [self addSubview:_topLayoutView];
        
        _genderLayout = [[UIView alloc] init];
        _genderLayout.translatesAutoresizingMaskIntoConstraints = NO;
        [_genderLayout setBackgroundColor:[ColorUtil viewBackgroundGrey]];
        [self addSubview:_genderLayout];
        
        _maleView = [[ClickableUIView alloc] init];
        _maleView.translatesAutoresizingMaskIntoConstraints = NO;
        [_genderLayout addSubview:_maleView];
        
        _femaleView = [[ClickableUIView alloc] init];
        _femaleView.translatesAutoresizingMaskIntoConstraints = NO;
        [_genderLayout addSubview:_femaleView];
        
        _genderMiddleLineView = [[UIView alloc] init];
        _genderMiddleLineView.translatesAutoresizingMaskIntoConstraints = NO;
        [_genderMiddleLineView setBackgroundColor:[UIColor blackColor]];
        [_genderLayout addSubview:_genderMiddleLineView];
        
        _maleLabel = [[UILabel alloc] init];
        _maleLabel.translatesAutoresizingMaskIntoConstraints = NO;
        [_maleLabel setText:NSLocalizedString(@"male", nil)];
        [_maleView addSubview:_maleLabel];
        
        _maleCheckView = [[UIView alloc] init];
        _maleCheckView.translatesAutoresizingMaskIntoConstraints = NO;
        _maleCheckView.backgroundColor = [ColorUtil themeColor];
        _maleCheckView.layer.masksToBounds = YES;
        _maleCheckView.layer.cornerRadius = 5;
        [_maleView addSubview:_maleCheckView];
        
        _femaleLabel = [[UILabel alloc] init];
        _femaleLabel.translatesAutoresizingMaskIntoConstraints = NO;
        [_femaleLabel setText:NSLocalizedString(@"female", nil)];
        [_femaleView addSubview:_femaleLabel];
        
        _femalCheckView = [[UIView alloc] init];
        _femalCheckView.translatesAutoresizingMaskIntoConstraints = NO;
        _femalCheckView.backgroundColor = [ColorUtil themeColor];
        _femalCheckView.layer.masksToBounds = YES;
        _femalCheckView.layer.cornerRadius = 5;
        [_femaleView addSubview:_femalCheckView];
        
        _datePicker = [[UIDatePicker alloc] init];
        [_datePicker setDatePickerMode:UIDatePickerModeDate];
        _datePicker.translatesAutoresizingMaskIntoConstraints = NO;
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
        [self addSubview:_nextStepButton];
        
    }
    
    return self;
}

-(void)layoutSubviews{
    NSDictionary *views = NSDictionaryOfVariableBindings(_topLayoutView,_genderLayout,_maleView,_genderMiddleLineView,_maleLabel,_maleCheckView,_femaleView,_femaleLabel,_femalCheckView,_datePicker,_nextStepButton);
    
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:[_topLayoutView]-20-[_genderLayout]" options:0 metrics:0 views:views]];
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:|-20-[_genderLayout(>=200)]-20-|" options:0 metrics:0 views:views]];
    
    [_genderLayout addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:|-0-[_maleView]-0-|" options:0 metrics:0 views:views]];
    [_genderLayout addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:|-0-[_maleView(40)]" options:0 metrics:0 views:views]];
    
    [_genderLayout addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:|-0-[_genderMiddleLineView]-0-|" options:0 metrics:0 views:views]];
    [_genderLayout addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:[_maleView]-0-[_genderMiddleLineView(1)]" options:0 metrics:0 views:views]];
    
    [_genderLayout addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:|-0-[_femaleView]-0-|" options:0 metrics:0 views:views]];
    [_genderLayout addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:[_genderMiddleLineView]-0-[_femaleView(40)]-0-|" options:0 metrics:0 views:views]];
    
    [_maleView addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:|-5-[_maleLabel]" options:0 metrics:0 views:views]];
    [_maleView addConstraint:[NSLayoutConstraint constraintWithItem:_maleLabel attribute:NSLayoutAttributeCenterY relatedBy:NSLayoutRelationEqual toItem:_maleView attribute:NSLayoutAttributeCenterY multiplier:1 constant:0]];
    
    [_maleView addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:[_maleCheckView(10)]-15-|" options:0 metrics:0 views:views]];
    [_maleView addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:[_maleCheckView(10)]-15-|" options:0 metrics:0 views:views]];
    
    [_femaleView addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:|-5-[_femaleLabel]" options:0 metrics:0 views:views]];
    [_femaleView addConstraint:[NSLayoutConstraint constraintWithItem:_femaleLabel attribute:NSLayoutAttributeCenterY relatedBy:NSLayoutRelationEqual toItem:_femaleView attribute:NSLayoutAttributeCenterY multiplier:1 constant:0]];
    
    [_femaleView addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:[_femalCheckView(10)]-15-|" options:0 metrics:0 views:views]];
    [_femaleView addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:[_femalCheckView(10)]-15-|" options:0 metrics:0 views:views]];
    
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:[_femaleView]-50-[_datePicker]" options:0 metrics:0 views:views]];
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:|-50-[_datePicker]-50-|" options:0 metrics:0 views:views]];
    
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:[_datePicker]-30-[_nextStepButton(40)]" options:0 metrics:0 views:views]];
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:|-50-[_nextStepButton]-50-|" options:0 metrics:0 views:views]];
}

@end
