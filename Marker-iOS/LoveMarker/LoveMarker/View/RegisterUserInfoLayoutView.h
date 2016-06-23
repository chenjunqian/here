//
//  RegisterUserInfoLayoutView.h
//  LoveMarker
//
//  Created by BigHead_Chen on 6/24/16.
//  Copyright © 2016 Eason. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface RegisterUserInfoLayoutView : UIView

-(id)initViewContext:(id)context title:(NSString*)title frame:(CGRect)frame;

@property (strong,nonatomic) UITextField *nicknameTextField;
@property (strong,nonatomic) UIView *genderLayout;
@property (strong,nonatomic) UILabel *genderLabel;
@property (strong,nonatomic) UIButton *maleButton;
@property (strong,nonatomic) UIButton *femaleButton;
@property (strong,nonatomic) UIButton *nextStepButton;

@end
