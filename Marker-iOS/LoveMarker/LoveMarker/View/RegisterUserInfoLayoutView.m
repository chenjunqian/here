//
//  RegisterUserInfoLayoutView.m
//  LoveMarker
//
//  Created by BigHead_Chen on 6/24/16.
//  Copyright © 2016 Eason. All rights reserved.
//

#import "RegisterUserInfoLayoutView.h"
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
        
        _nicknameTextField = [[UITextField alloc] init];
        _nicknameTextField.translatesAutoresizingMaskIntoConstraints = NO;
        [_nicknameTextField setPlaceholder:NSLocalizedString(@"please_input_nickname", nil)];
        [self addSubview:_nicknameTextField];
        
        _genderLayout = [[UIView alloc] init];
        _genderLayout.translatesAutoresizingMaskIntoConstraints = NO;
        [_genderLayout setBackgroundColor:[UIColor lightGrayColor]];
        [self addSubview:_genderLayout];
        
        _genderLabel = [[UILabel alloc] init];
        _genderLabel.translatesAutoresizingMaskIntoConstraints = NO;
        [_genderLabel setText:NSLocalizedString(@"please_select_gender", nil)];
        [_genderLabel setTextColor:[UIColor whiteColor]];
        [_genderLayout addSubview:_genderLabel];
        
        _maleButton = [[UIButton alloc] init];
        _maleButton.translatesAutoresizingMaskIntoConstraints = NO;
        [_maleButton setTitle:NSLocalizedString(@"male", nil) forState:UIControlStateNormal];
        [_maleButton setTitleColor:[UIColor blueColor] forState:UIControlStateNormal];
        [_genderLayout addSubview:_maleButton];
        
        _femaleButton = [[UIButton alloc] init];
        _femaleButton.translatesAutoresizingMaskIntoConstraints = NO;
        [_femaleButton setTitle:NSLocalizedString(@"female", nil) forState:UIControlStateNormal];
        [_femaleButton setTitleColor:[UIColor blueColor] forState:UIControlStateNormal];
        [_genderLayout addSubview:_femaleButton];
        
        _nextStepButton = [[UIButton alloc] init];
        _nextStepButton.translatesAutoresizingMaskIntoConstraints = NO;
        [_nextStepButton setTitle:NSLocalizedString(@"next_step", nil) forState:UIControlStateNormal];
        [_nextStepButton setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
        _nextStepButton.backgroundColor = [UIColor redColor];
        [self addSubview:_nextStepButton];
    }
    
    return self;
}

-(void)layoutSubviews{
    NSDictionary *views = NSDictionaryOfVariableBindings(_topLayoutView,_nicknameTextField,_genderLayout,_genderLabel,_maleButton,_femaleButton,_nextStepButton);
    
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:[_topLayoutView]-20-[_nicknameTextField(25)]" options:0 metrics:0 views:views]];
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:|-20-[_nicknameTextField(>=200)]-20-|" options:0 metrics:0 views:views]];
    
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:[_nicknameTextField]-20-[_genderLayout]" options:0 metrics:0 views:views]];
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:|-20-[_genderLayout(>=200)]-20-|" options:0 metrics:0 views:views]];
    
    [_genderLayout addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:|-5-[_genderLabel]" options:0 metrics:0 views:views]];
    [_genderLayout addConstraint:[NSLayoutConstraint constraintWithItem:_genderLabel attribute:NSLayoutAttributeCenterX relatedBy:NSLayoutRelationEqual toItem:_genderLayout attribute:NSLayoutAttributeCenterX multiplier:1 constant:0]];
    
    [_genderLayout addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:|-50-[_maleButton]" options:0 metrics:0 views:views]];
    [_genderLayout addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:[_genderLabel]-10-[_maleButton]-10-|" options:0 metrics:0 views:views]];
    
    [_genderLayout addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:[_femaleButton]-50-|" options:0 metrics:0 views:views]];
    [_genderLayout addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:[_genderLabel]-10-[_femaleButton]-10-|" options:0 metrics:0 views:views]];
    
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:[_genderLayout]-20-[_nextStepButton(40)]" options:0 metrics:0 views:views]];
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:|-50-[_nextStepButton(>=200)]-50-|" options:0 metrics:0 views:views]];
}

@end
