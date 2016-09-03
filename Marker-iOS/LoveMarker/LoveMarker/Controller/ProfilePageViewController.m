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
#import "ErrorState.h"
#import "NSObject+ObjectMap.h"
#import "GlobalActivityIndicators.h"
#import "UnitViewUtil.h"
#import "UserInfoEditerViewController.h"
#import "LoginViewController.h"
#import "AboutUsViewController.h"

@interface ProfilePageViewController()

@property (strong,nonatomic) ProfilePageView *profilePageView;
@property (strong,nonatomic) UIView *toLoginView;
@property (strong,nonatomic) UILabel *upLabel;
@property (strong,nonatomic) UILabel *downLabel;
@property (strong,nonatomic) UIButton *toLoginButton;
@property (strong,nonatomic) UIImagePickerController* imagePickerController;

@end

@implementation ProfilePageViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    [self setViewBaseOnLoginStatus];
}

-(void)viewWillAppear:(BOOL)animated{
    [self setViewBaseOnLoginStatus];
    [self refreshUserInfoDataByUser:[[LoginStatus getInstance] getUser]];
}

-(void)initProfileView{
    User* user = [[LoginStatus getInstance] getUser];
    
    _profilePageView = [[ProfilePageView alloc] initWithContext:self title:NSLocalizedString(@"me", nil) frame:CGRectMake(0, 0, self.view.frame.size.width, self.view.frame.size.height)];
    [self.view addSubview:_profilePageView];
    
    [self refreshUserInfoDataByUser:user];
    
    [_profilePageView.refreshControl addTarget:self action:@selector(refrshControlAction:) forControlEvents:UIControlEventValueChanged];
    
    [HttpRequest downloadAvatarWithUrl:[[LoginStatus getInstance] getUser].avatar UIImageView:_profilePageView.avatarUIImageView];
    
    [_profilePageView.myMarkerUIView whenSingleClick:^{
        MyMarkerUIViewController* myMarkerView = [[MyMarkerUIViewController alloc] init];
        [self presentViewController:myMarkerView animated:YES completion:^{
            
        }];
    }];
    
    [_profilePageView.avatarUIView whenSingleClick:^{
        _imagePickerController = [[UIImagePickerController alloc] init];
        _imagePickerController.sourceType = UIImagePickerControllerSourceTypeSavedPhotosAlbum;
        _imagePickerController.delegate = self;
        _imagePickerController.allowsEditing = YES;
        [self presentViewController:_imagePickerController animated:YES completion:nil];
    }];
    
    [self setViewClickListener];
}

-(void)setViewBaseOnLoginStatus{
    if ([[LoginStatus getInstance] getIsUserModel]) {
        if (_toLoginView) {
            [_toLoginView setHidden:YES];
        }
        
        if (_profilePageView) {
            [_profilePageView setHidden:NO];
        }else{
            [self initProfileView];
        }
        
    }else{
        if (_profilePageView) {
            [_profilePageView setHidden:YES];
        }
        
        if (_toLoginView) {
            [_toLoginView setHidden:NO];
        }else{
            [self initLoginView];
        }
    }
}

-(void)setViewClickListener{
    [_profilePageView.nicknameUIView whenSingleClick:^{
        UserInfoEditerViewController* editor = [[UserInfoEditerViewController alloc] init];
        [editor setEditerType:USER_NAME_EDITER];
        [self presentViewController:editor animated:YES completion:nil];
    }];
    
    [_profilePageView.genderUIView whenSingleClick:^{
        UserInfoEditerViewController* editor = [[UserInfoEditerViewController alloc] init];
        [editor setEditerType:GENDER_EDITER];
        [self presentViewController:editor animated:YES completion:nil];
    }];
    
    [_profilePageView.birthdayUIView whenSingleClick:^{
        UserInfoEditerViewController* editor = [[UserInfoEditerViewController alloc] init];
        [editor setEditerType:BIRTHDAY_EDITER];
        //为了半透明背景的效果
        editor.providesPresentationContextTransitionStyle = YES;
        editor.definesPresentationContext = YES;
        editor.modalPresentationStyle = UIModalPresentationOverCurrentContext;
        [self presentViewController:editor animated:NO completion:nil];
    }];
    
    [_profilePageView.simpleProfileUIView whenSingleClick:^{
        UserInfoEditerViewController* editor = [[UserInfoEditerViewController alloc] init];
        [editor setEditerType:SIMPLE_PROFILE_EDITER];
        [self presentViewController:editor animated:YES completion:nil];
    }];
    
    [_profilePageView.longProfileUIView whenSingleClick:^{
        UserInfoEditerViewController* editor = [[UserInfoEditerViewController alloc] init];
        [editor setEditerType:LONG_PROFILE_EDITER];
        [self presentViewController:editor animated:YES completion:nil];
    }];
    
    [_profilePageView.passwordUIView whenSingleClick:^{
        UserInfoEditerViewController* editor = [[UserInfoEditerViewController alloc] init];
        [editor setEditerType:PASSWORD_EDITER];
        [self presentViewController:editor animated:YES completion:nil];
    }];
    
    
    [_profilePageView.logoutUIView whenSingleClick:^{
        [UnitViewUtil showWarningAlertWithMessage:NSLocalizedString(@"are_you_sure_you_want_to_logout", nil) actionOK:NSLocalizedString(@"ok", nil) actionCancle:NSLocalizedString(@"cancel", nil) context:self okButtonHandler:^{
            [[LoginStatus getInstance] logout];
            [self setViewBaseOnLoginStatus];
        }];

    }];
    
    [_profilePageView.aboutUsUIView whenSingleClick:^{
        AboutUsViewController* aboutUs = [[AboutUsViewController alloc] init];
        [self presentViewController:aboutUs animated:YES completion:^{
            
        }];
    }];
}

-(void)refrshControlAction:(id)sender{
    [HttpRequest downloadAvatarWithUrl:[[LoginStatus getInstance] getUser].avatar UIImageView:_profilePageView.avatarUIImageView];
    [self refreshUserInfoDataByUser:[[LoginStatus getInstance] getUser]];
    [self performSelector:@selector(endRefreshing:) withObject:nil afterDelay:2.0];
}

-(void)endRefreshing:(id)sender{
    [_profilePageView.refreshControl endRefreshing];
}

-(void)refreshUserInfoDataByUser:(User*)user{
    _profilePageView.nicknameUIView.parameterUILabel.text = user.nickname;
    _profilePageView.genderUIView.parameterUILabel.text = user.gender;
    _profilePageView.birthdayUIView.parameterUILabel.text = user.birthday;
    _profilePageView.simpleProfileContentUILabel.text = user.simpleProfile;
    _profilePageView.longProfileContentUILabel.text = user.longProfile;
    _profilePageView.usernameUIView.parameterUILabel.text = user.username;
}

//如果没有登录，则显示区登录的View
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
    [_toLoginButton setTitleColor:[ColorUtil textColorSubBlack] forState:UIControlStateNormal];
    [_toLoginButton setBackgroundColor:[UIColor whiteColor]];
    [_toLoginView addSubview:_toLoginButton];
    
    [_toLoginButton addTarget:self action:@selector(toLoginView:) forControlEvents:UIControlEventTouchDown];
    
    NSDictionary* views = NSDictionaryOfVariableBindings(_toLoginView,_upLabel,_downLabel,_toLoginButton);
    [_toLoginView addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:[_toLoginButton(60)]" options:0 metrics:0 views:views]];
    [_toLoginView addConstraint:[NSLayoutConstraint constraintWithItem:_toLoginButton attribute:NSLayoutAttributeCenterX relatedBy:NSLayoutRelationEqual toItem:_toLoginView attribute:NSLayoutAttributeCenterX multiplier:1 constant:0]];
    [_toLoginView addConstraint:[NSLayoutConstraint constraintWithItem:_toLoginButton attribute:NSLayoutAttributeCenterY relatedBy:NSLayoutRelationEqual toItem:_toLoginView attribute:NSLayoutAttributeCenterY multiplier:1 constant:0]];
    
    [_toLoginView addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:[_downLabel]-5-[_toLoginButton]" options:0 metrics:0 views:views]];
    [_toLoginView addConstraint:[NSLayoutConstraint constraintWithItem:_downLabel attribute:NSLayoutAttributeCenterX relatedBy:NSLayoutRelationEqual toItem:_toLoginView attribute:NSLayoutAttributeCenterX multiplier:1 constant:0]];
    
    [_toLoginView addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:[_upLabel]-5-[_downLabel]" options:0 metrics:0 views:views]];
    [_toLoginView addConstraint:[NSLayoutConstraint constraintWithItem:_upLabel attribute:NSLayoutAttributeCenterX relatedBy:NSLayoutRelationEqual toItem:_toLoginView attribute:NSLayoutAttributeCenterX multiplier:1 constant:0]];
}

-(IBAction)toLoginView:(id)sender{
    LoginViewController* loginViewController = [[LoginViewController alloc] init];
    [self presentViewController:loginViewController animated:YES completion:nil];
}

//使用UIImagePickerController 修改头像 的回调方法
-(void) imagePickerController:(UIImagePickerController *)picker didFinishPickingMediaWithInfo:(NSDictionary<NSString *,id> *)info{
    GlobalActivityIndicators* indicator = [[GlobalActivityIndicators alloc] initWithTitle:NSLocalizedString(@"uploading", nil) frame:CGRectMake(0, 0, self.view.frame.size.width, self.view.frame.size.height)];
    [self.view addSubview:indicator];
    [indicator.activityIndicatorView startAnimating];
    
    UIImage* image = [info objectForKey:@"UIImagePickerControllerEditedImage"];
    NSArray* documents = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    NSString* path = [documents[0] stringByAppendingString:@"image.png"];
    NSData* imageData = UIImageJPEGRepresentation(image, 0.5);
    [imageData writeToFile:path atomically:YES];
    NSURL* testURL = [NSURL fileURLWithPath:path];
    
    [HttpRequest uploadAvatarWithUsername:[[LoginStatus getInstance] getUser].username fileURL:testURL
        handler:^(ResponseResult *response, NSObject *resultObject) {
            if (response.status == Error_Code_Correct) {
                [_profilePageView.avatarUIImageView setImage:image];
                
                //refresh LoginStatus after upload avatar success
                [HttpRequest getUserInfoByUsername:[[LoginStatus getInstance] getUser].username handler:^(ResponseResult *response, NSObject *resultObject) {
                    [indicator setHidden:YES];
                    if (response.status == Error_Code_Correct) {
                        User* user = [NSObject objectOfClass:@"User" fromJSON:(NSDictionary*)resultObject];
                        if (user) {
                            [[LoginStatus getInstance] setUser:user];
                        }
                    }
                }];
            }
    }];
    
    [picker dismissViewControllerAnimated:YES completion:nil];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
}

@end
