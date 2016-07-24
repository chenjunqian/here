//
//  ProfilePageViewController.m
//  LoveMarker
//
//  Created by BigHead_Chen on 5/19/16.
//  Copyright © 2016 Eason. All rights reserved.
//

#import "ProfilePageViewController.h"
#import "ProfilePageView.h"
#import "MyMarkerUIViewController.h"
#import "LoginStatus.h"
#import "User.h"
#import "ColorUtil.h"
#import "HttpRequest.h"
#import "CommomUtils.h"

@interface ProfilePageViewController()

@property (strong,nonatomic) ProfilePageView *profilePageView;
@property (strong,nonatomic) UIView *toLoginView;
@property (strong,nonatomic) UILabel *upLabel;
@property (strong,nonatomic) UILabel *downLabel;
@property (strong,nonatomic) UIButton *toLoginButton;

@end

@implementation ProfilePageViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    if ([[LoginStatus getInstance] getIsUserModel]) {
        [self initProfileView];
    }else{
        [self initLoginView];
    }
    
}

-(void)initProfileView{
    User* user = [[LoginStatus getInstance] getUser];
    
    _profilePageView = [[ProfilePageView alloc] initWithContext:self title:NSLocalizedString(@"me", nil) frame:CGRectMake(0, 0, self.view.frame.size.width, self.view.frame.size.height)];
    [self.view addSubview:_profilePageView];
    
    _profilePageView.nicknameUIView.parameterUILabel.text = user.nickname;
    _profilePageView.genderUIView.parameterUILabel.text = user.gender;
    _profilePageView.birthdayUIView.parameterUILabel.text = user.birthday;
    _profilePageView.simpleProfileContentUILabel.text = user.simpleProfile;
    _profilePageView.longProfileContentUILabel.text = user.longProfile;
    _profilePageView.usernameUIView.parameterUILabel.text = user.username;
    
    [_profilePageView.myMarkerUIView whenSingleClick:^{
        MyMarkerUIViewController* myMarkerView = [[MyMarkerUIViewController alloc] init];
        [self presentViewController:myMarkerView animated:YES completion:^{
            
        }];
    }];
    
    [HttpRequest downloadAvatarWithUrl:user.avatar handler:^(NSURLResponse *response, NSString *filePath, NSError *error) {
        if (![CommomUtils isEmptyString:filePath]) {
            NSData* avatarData = [ NSData dataWithContentsOfFile:filePath];
            UIImage* avatarImage = [UIImage imageWithData:avatarData];
            
            if (avatarImage!=nil) {
                [_profilePageView.avatarUIImageView setImage:avatarImage];
            }
        }
    }];
}

-(void)initLoginView{
    _toLoginView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, self.view.frame.size.width, self.view.frame.size.height)];
    [self.view addSubview:_toLoginView];
    
    _upLabel = [[UILabel alloc] init];
    _upLabel.translatesAutoresizingMaskIntoConstraints = NO;
    _upLabel.textColor = [ColorUtil tealBlueColor];
    [_upLabel setText:NSLocalizedString(@"you_have_not_logined", nil)];
    [_toLoginView addSubview:_upLabel];
    
    _downLabel = [[UILabel alloc] init];
    _downLabel.translatesAutoresizingMaskIntoConstraints = NO;
    [_downLabel setText:NSLocalizedString(@"please_login_first", nil)];
    _downLabel.textColor = [ColorUtil tealBlueColor];
    [_toLoginView addSubview:_downLabel];
    
    _toLoginButton = [[UIButton alloc] init];
    [_toLoginButton setTitle:NSLocalizedString(@"login", nil) forState:UIControlStateNormal];
    _toLoginButton.translatesAutoresizingMaskIntoConstraints = NO;
    [_toLoginView addSubview:_toLoginButton];
    
    NSDictionary* views = NSDictionaryOfVariableBindings(_toLoginView,_upLabel,_downLabel,_toLoginButton);
    [_toLoginView addConstraint:[NSLayoutConstraint constraintWithItem:_toLoginButton attribute:NSLayoutAttributeCenterX relatedBy:NSLayoutRelationEqual toItem:_toLoginView attribute:NSLayoutAttributeCenterX multiplier:1 constant:0]];
    [_toLoginView addConstraint:[NSLayoutConstraint constraintWithItem:_toLoginButton attribute:NSLayoutAttributeCenterY relatedBy:NSLayoutRelationEqual toItem:_toLoginView attribute:NSLayoutAttributeCenterY multiplier:1 constant:0]];
    
    [_toLoginView addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:[_downLabel]-5-[_toLoginButton]" options:0 metrics:0 views:views]];
    [_toLoginView addConstraint:[NSLayoutConstraint constraintWithItem:_downLabel attribute:NSLayoutAttributeCenterX relatedBy:NSLayoutRelationEqual toItem:_toLoginView attribute:NSLayoutAttributeCenterX multiplier:1 constant:0]];
    
    [_toLoginView addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:[_upLabel]-5-[_downLabel]" options:0 metrics:0 views:views]];
    [_toLoginView addConstraint:[NSLayoutConstraint constraintWithItem:_upLabel attribute:NSLayoutAttributeCenterX relatedBy:NSLayoutRelationEqual toItem:_toLoginView attribute:NSLayoutAttributeCenterX multiplier:1 constant:0]];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
}

@end
