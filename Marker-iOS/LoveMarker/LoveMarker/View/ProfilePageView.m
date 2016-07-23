//
//  ProfilePageView.m
//  LoveMarker
//
//  Created by BigHead_Chen on 7/9/16.
//  Copyright © 2016 Eason. All rights reserved.
//

#import "ProfilePageView.h"
#import "ColorUtil.h"

@implementation ProfilePageView

@synthesize topLayoutView , avatarUIView , avatarUIImageView , changeAvatarUILabel , myMarkerUIView , nicknameUIView , genderUIView , birthdayUIView ,
simpleProfileUIView , simpleProfileUILabel , simpleProfileContentUILabel , longProfileUIView , longProfileUILabel , longProfileContentUILabel ,usernameUIView , passwordUIView;

-(id)initWithContext:(id)context title:(NSString*)topTitle frame:(CGRect)frame{
    self = [super initWithFrame:frame];
    
    if (self) {
        topLayoutView = [[TopLayoutView alloc] initWithoutButtom:context title:topTitle andFrame:CGRectMake(0, 20, self.frame.size.width, 50)];
        [topLayoutView.leftButton setAccessibilityElementsHidden:YES];
        [self addSubview:topLayoutView];
        
        avatarUIView  = [[ClickableUIView alloc] init];
        avatarUIView.translatesAutoresizingMaskIntoConstraints = NO;
        avatarUIView.backgroundColor = [UIColor whiteColor];
        [self addSubview:avatarUIView];
        
        avatarUIImageView = [[AvatarUIImageView alloc] init];
        avatarUIImageView.translatesAutoresizingMaskIntoConstraints = NO;
        [avatarUIView addSubview:avatarUIImageView];
        
        changeAvatarUILabel = [[UILabel alloc] init];
        changeAvatarUILabel.textColor = [ColorUtil textColorSubBlack];
        changeAvatarUILabel.translatesAutoresizingMaskIntoConstraints = NO;
        changeAvatarUILabel.text = NSLocalizedString(@"change_avatar", nil);
        [avatarUIView addSubview:changeAvatarUILabel];
        
        myMarkerUIView = [[UserInforCell alloc] init];
        myMarkerUIView.titleUILabel.text = NSLocalizedString(@"my_marker", nil);
        myMarkerUIView.parameterUILabel.hidden = YES;
        myMarkerUIView.translatesAutoresizingMaskIntoConstraints = NO;
        [self addSubview:myMarkerUIView];
        
        nicknameUIView = [[UserInforCell alloc] init];
        nicknameUIView.titleUILabel.text = NSLocalizedString(@"my_nickname", nil);
        nicknameUIView.parameterUILabel.textColor = [ColorUtil textColorSubBlack];
        nicknameUIView.translatesAutoresizingMaskIntoConstraints = NO;
        [self addSubview:nicknameUIView];
        
        genderUIView = [[UserInforCell alloc] init];
        genderUIView.titleUILabel.text = NSLocalizedString(@"my_gender", nil);
        genderUIView.parameterUILabel.textColor = [ColorUtil textColorSubBlack];
        genderUIView.translatesAutoresizingMaskIntoConstraints = NO;
        [self addSubview:genderUIView];
        
        birthdayUIView = [[UserInforCell alloc] init];
        birthdayUIView.titleUILabel.text = NSLocalizedString(@"my_birthday", nil);
        birthdayUIView.parameterUILabel.textColor = [ColorUtil textColorSubBlack];
        birthdayUIView.translatesAutoresizingMaskIntoConstraints = NO;
        [self addSubview:birthdayUIView];
        
        simpleProfileUIView = [[ClickableUIView alloc] init];
        simpleProfileUIView.backgroundColor = [UIColor whiteColor];
        simpleProfileUIView.translatesAutoresizingMaskIntoConstraints = NO;
        [self addSubview:simpleProfileUIView];
        
        simpleProfileUILabel = [[UILabel alloc] init];
        simpleProfileUILabel.text = NSLocalizedString(@"my_simple_profile", nil);
        simpleProfileUILabel.translatesAutoresizingMaskIntoConstraints = NO;
        [simpleProfileUIView addSubview:simpleProfileUILabel];
        
        simpleProfileContentUILabel = [[UILabel alloc] init];
        simpleProfileContentUILabel.text = NSLocalizedString(@"my_simple_profile_content_hint", nil);
        simpleProfileContentUILabel.textColor = [ColorUtil textColorSubBlack];
        simpleProfileContentUILabel.numberOfLines = 0;
        simpleProfileContentUILabel.font = [UIFont systemFontOfSize:12];
        simpleProfileContentUILabel.translatesAutoresizingMaskIntoConstraints = NO;
        [simpleProfileUIView addSubview:simpleProfileContentUILabel];
        
        longProfileUIView = [[ClickableUIView alloc] init];
        longProfileUIView.backgroundColor = [UIColor whiteColor];
        longProfileUIView.translatesAutoresizingMaskIntoConstraints = NO;
        [self addSubview:longProfileUIView];
        
        longProfileUILabel = [[UILabel alloc] init];
        longProfileUILabel.text = NSLocalizedString(@"my_long_profile", nil);
        longProfileUILabel.translatesAutoresizingMaskIntoConstraints = NO;
        [longProfileUIView addSubview:longProfileUILabel];
        
        longProfileContentUILabel = [[UILabel alloc] init];
        longProfileContentUILabel.text = NSLocalizedString(@"my_long_profile_content_hint", nil);
        longProfileContentUILabel.textColor = [ColorUtil textColorSubBlack];
        longProfileContentUILabel.numberOfLines = 0;
        longProfileContentUILabel.font = [UIFont systemFontOfSize:12];
        longProfileContentUILabel.translatesAutoresizingMaskIntoConstraints = NO;
        [longProfileUIView addSubview:longProfileContentUILabel];
        
        usernameUIView = [[UserInforCell alloc] init];
        usernameUIView.titleUILabel.text = NSLocalizedString(@"my_username", nil);
        usernameUIView.translatesAutoresizingMaskIntoConstraints = NO;
        usernameUIView.parameterUILabel.hidden = NO;
        [self addSubview:usernameUIView];
        
        passwordUIView = [[UserInforCell alloc] init];
        passwordUIView.titleUILabel.text = NSLocalizedString(@"my_password", nil);
        passwordUIView.translatesAutoresizingMaskIntoConstraints = NO;
        passwordUIView.parameterUILabel.hidden = YES;
        [self addSubview:passwordUIView];
    }
    
    return self;
}

-(void)layoutSubviews{
    NSDictionary* views = NSDictionaryOfVariableBindings(topLayoutView , avatarUIView , avatarUIImageView , changeAvatarUILabel , myMarkerUIView , nicknameUIView , genderUIView , birthdayUIView ,simpleProfileUIView , simpleProfileUILabel , simpleProfileContentUILabel , longProfileUIView , longProfileUILabel , longProfileContentUILabel , usernameUIView , passwordUIView);
    
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:|-0-[avatarUIView]-0-|" options:0 metrics:0 views:views]];
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:[topLayoutView]-0-[avatarUIView]" options:0 metrics:0 views:views]];
    
    [avatarUIView addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:|-10-[avatarUIImageView(70)]" options:0 metrics:0 views:views]];
    [avatarUIView addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:|-10-[avatarUIImageView(70)]-10-|" options:0 metrics:0 views:views]];
    
    [avatarUIView addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:[changeAvatarUILabel]-10-|" options:0 metrics:0 views:views]];
    [avatarUIView addConstraint:[NSLayoutConstraint constraintWithItem:changeAvatarUILabel attribute:NSLayoutAttributeCenterY relatedBy:NSLayoutRelationEqual toItem:avatarUIView attribute:NSLayoutAttributeCenterY multiplier:1 constant:0]];
    
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:|-0-[myMarkerUIView]-0-|" options:0 metrics:0 views:views]];
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:[avatarUIView]-10-[myMarkerUIView(30)]" options:0 metrics:0 views:views]];
    
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:|-0-[nicknameUIView]-0-|" options:0 metrics:0 views:views]];
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:[myMarkerUIView]-10-[nicknameUIView(30)]" options:0 metrics:0 views:views]];
    
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:|-0-[genderUIView]-0-|" options:0 metrics:0 views:views]];
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:[nicknameUIView]-1-[genderUIView(30)]" options:0 metrics:0 views:views]];
    
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:|-0-[birthdayUIView]-0-|" options:0 metrics:0 views:views]];
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:[genderUIView]-1-[birthdayUIView(30)]" options:0 metrics:0 views:views]];
    
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:|-0-[simpleProfileUIView]-0-|" options:0 metrics:0 views:views]];
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:[birthdayUIView]-1-[simpleProfileUIView]" options:0 metrics:0 views:views]];
    [simpleProfileUIView addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:|-10-[simpleProfileUILabel]" options:0 metrics:0 views:views]];
    [simpleProfileUIView addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:|-10-[simpleProfileUILabel]" options:0 metrics:0 views:views]];
    [simpleProfileUIView addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:|-10-[simpleProfileContentUILabel]-10-|" options:0 metrics:0 views:views]];
    [simpleProfileUIView addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:[simpleProfileUILabel]-10-[simpleProfileContentUILabel]-10-|" options:0 metrics:0 views:views]];
    
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:|-0-[longProfileUIView]-0-|" options:0 metrics:0 views:views]];
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:[simpleProfileUIView]-1-[longProfileUIView]" options:0 metrics:0 views:views]];
    [longProfileUIView addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:|-10-[longProfileUILabel]" options:0 metrics:0 views:views]];
    [longProfileUIView addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:|-10-[longProfileUILabel]" options:0 metrics:0 views:views]];
    [longProfileUIView addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:|-10-[longProfileContentUILabel]-10-|" options:0 metrics:0 views:views]];
    [longProfileUIView addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:[longProfileUILabel]-10-[longProfileContentUILabel]-10-|" options:0 metrics:0 views:views]];
    
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:|-0-[usernameUIView]-0-|" options:0 metrics:0 views:views]];
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:[longProfileUIView]-10-[usernameUIView(30)]" options:0 metrics:0 views:views]];
    
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:|-0-[passwordUIView]-0-|" options:0 metrics:0 views:views]];
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:[usernameUIView]-1-[passwordUIView(30)]" options:0 metrics:0 views:views]];
}

@end
