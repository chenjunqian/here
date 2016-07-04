//
//  RegisterLayoutView.h
//  LoveMarker
//
//  Created by BigHead_Chen on 6/17/16.
//  Copyright © 2016 Eason. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface RegisterLayoutView : UIView

-(id)initWithContext:(id)context frame:(CGRect)frame;
@property (strong,nonatomic) UITextField *nicknameTextField;
@property (strong,nonatomic) UITextField* usernameTextField;
@property (strong,nonatomic) UITextField* passwordTextField;
@property (strong,nonatomic) UIButton* nextStepButton;



@end
