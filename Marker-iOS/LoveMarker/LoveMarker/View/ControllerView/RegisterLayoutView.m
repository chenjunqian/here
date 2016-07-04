//
//  RegisterLayoutView.m
//  LoveMarker
//
//  Created by BigHead_Chen on 6/17/16.
//  Copyright © 2016 Eason. All rights reserved.
//

#import "RegisterLayoutView.h"
#import "TopLayoutView.h"
#import "ColorUtil.h"

@interface RegisterLayoutView()

@property (strong,nonatomic) TopLayoutView *topLayoutView;

@end

@implementation RegisterLayoutView

/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect {
    // Drawing code
}
*/

@synthesize usernameTextField , passwordTextField , nextStepButton;


-(id)initWithContext:(id)context frame:(CGRect)frame{
    self = [super initWithFrame:frame];
    
    if (self) {
        _topLayoutView = [[TopLayoutView alloc] initWithContext:context title:NSLocalizedString(@"register", nil) andFrame:CGRectMake(0, 20, self.frame.size.width, 50)];
        [self addSubview:_topLayoutView];
        
        _nicknameTextField = [[UITextField alloc] init];
        _nicknameTextField.translatesAutoresizingMaskIntoConstraints = NO;
        [_nicknameTextField setPlaceholder:NSLocalizedString(@"please_input_nickname", nil)];
        [self addSubview:_nicknameTextField];
        
        usernameTextField = [[UITextField alloc] init];
        usernameTextField.translatesAutoresizingMaskIntoConstraints = NO;
        usernameTextField.placeholder = NSLocalizedString(@"input_phone_number_or_email", nil);
        
        passwordTextField = [[UITextField alloc] init];
        passwordTextField.translatesAutoresizingMaskIntoConstraints = NO;
        [passwordTextField setSecureTextEntry:YES];
        passwordTextField.placeholder = NSLocalizedString(@"set_password", nil);
        
        nextStepButton = [[UIButton alloc] init];
        nextStepButton.translatesAutoresizingMaskIntoConstraints = NO;
        nextStepButton.backgroundColor = [ColorUtil themeColor];
        [nextStepButton setTitle:NSLocalizedString(@"next_step", nil) forState:UIControlStateNormal];
        [nextStepButton setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
        
        [self addSubview:usernameTextField];
        [self addSubview:passwordTextField];
        [self addSubview:nextStepButton];
        
    }
    
    return self;
}

-(void)layoutSubviews{
    NSDictionary *views = NSDictionaryOfVariableBindings(_topLayoutView,_nicknameTextField, usernameTextField,passwordTextField,nextStepButton);
    
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:[_topLayoutView]-20-[_nicknameTextField(25)]" options:0 metrics:0 views:views]];
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:|-20-[_nicknameTextField(>=200)]-20-|" options:0 metrics:0 views:views]];
    
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:[_nicknameTextField]-20-[usernameTextField(25)]" options:0 metrics:0 views:views]];
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:|-20-[usernameTextField(>=200)]-20-|" options:0 metrics:0 views:views]];
    
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:[usernameTextField]-20-[passwordTextField(25)]" options:0 metrics:0 views:views]];
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:|-20-[passwordTextField(>=200)]-20-|" options:0 metrics:0 views:views]];
    
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:[passwordTextField]-20-[nextStepButton(40)]" options:0 metrics:0 views:views]];
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:|-50-[nextStepButton(>=200)]-50-|" options:0 metrics:0 views:views]];
}

@end
