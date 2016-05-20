//
//  ProfilePageViewController.m
//  LoveMarker
//
//  Created by BigHead_Chen on 5/19/16.
//  Copyright © 2016 Eason. All rights reserved.
//

#import "ProfilePageViewController.h"
#import "TopLayoutView.h"

@interface ProfilePageViewController ()

@property(nonatomic,strong,getter=getAvatarUIView) UIView *avatarLayout;
@property(nonatomic,strong,getter=getAvatarImageView) UIImageView *avatarImageView;
@property(nonatomic,strong,getter=getNicknameLabel) UILabel *nicknameLabel;
@property(nonatomic,strong,getter=getSimpleProfileLabel) UILabel *simpleProfileLabel;
@property(nonatomic,strong,getter=getLongProfileLabel) UILabel *longProfileLabel;
@property(nonatomic,strong) UITableView *myMarkerTableView;

@end

@implementation ProfilePageViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    [self initView];
}

-(void)initView{
    [self.view setBackgroundColor:[super colorWithHexString:@"F3F3F3"]];
    
    TopLayoutView *topLayoutView = [[TopLayoutView alloc] initWithFrame:CGRectMake(0, 20, self.view.frame.size.width, 40)];
    [[topLayoutView getBackBtn] setHidden:YES];
    [[topLayoutView getTitleLabel] setText:NSLocalizedString(@"me", nil)];
    [self.view addSubview:topLayoutView];
    
    _avatarLayout = [[UIView alloc] init];
    _avatarLayout.translatesAutoresizingMaskIntoConstraints = NO;
    [_avatarLayout setBackgroundColor:[UIColor whiteColor]];
    [self.view addSubview:_avatarLayout];
    
    _avatarImageView = [[UIImageView alloc] init];
    _avatarImageView.translatesAutoresizingMaskIntoConstraints = NO;
    [_avatarLayout addSubview:_avatarImageView];
    
    _nicknameLabel = [[UILabel alloc] init];
    _nicknameLabel.translatesAutoresizingMaskIntoConstraints = NO;
    [_nicknameLabel setText:@"nick name"];
    [_avatarLayout addSubview:_nicknameLabel];
    
    _simpleProfileLabel = [[UILabel alloc] init];
    _simpleProfileLabel.translatesAutoresizingMaskIntoConstraints = NO;
    [_simpleProfileLabel setTextColor:[UIColor grayColor]];
    [_simpleProfileLabel setText:@"this is simple profile text label"];
    [_avatarLayout addSubview:_simpleProfileLabel];
    
    UIView *longProfileLayout = [[UIView alloc]init];
    longProfileLayout.translatesAutoresizingMaskIntoConstraints = NO;
    [longProfileLayout setBackgroundColor:[UIColor whiteColor]];
    [self.view addSubview:longProfileLayout];
    
    UILabel *aboutMeLabel = [[UILabel alloc] init];
    aboutMeLabel.translatesAutoresizingMaskIntoConstraints = NO;
    [aboutMeLabel setFont:[UIFont systemFontOfSize:12]];
    [aboutMeLabel setText:@"about me"];
    [longProfileLayout addSubview:aboutMeLabel];
    
    _longProfileLabel = [[UILabel alloc] init];
    _longProfileLabel.translatesAutoresizingMaskIntoConstraints = NO;
    [_longProfileLabel setTextColor:[UIColor grayColor]];
    [_longProfileLabel setText:@"this is long profile label test text l l l l l l l l l l l "];
    [longProfileLayout addSubview:_longProfileLabel];

    
    _myMarkerTableView = [[UITableView alloc] init];
    _myMarkerTableView.translatesAutoresizingMaskIntoConstraints = NO;
    [self.view addSubview:_myMarkerTableView];
    
    NSDictionary *views = NSDictionaryOfVariableBindings(topLayoutView,_avatarLayout,_avatarImageView,_nicknameLabel,_simpleProfileLabel,longProfileLayout,_longProfileLabel,aboutMeLabel,_myMarkerTableView);
    
    [self.view addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:[topLayoutView]-0-[_avatarLayout(80)]" options:0 metrics:0 views:views]];
    [self.view addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:|-0-[_avatarLayout(>=200)]-0-|" options:0 metrics:0 views:views]];
    //avatar image view contraints
    [_avatarLayout addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:|-5-[_avatarImageView(60)]" options:0 metrics:0 views:views]];
    [_avatarLayout addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:|-5-[_avatarImageView(60)]" options:0 metrics:0 views:views]];
    //nickname label constraints
    [_avatarLayout addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:[_avatarImageView]-5-[_nicknameLabel]" options:0 metrics:0 views:views]];
    [_avatarLayout addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:|-5-[_nicknameLabel]" options:0 metrics:0 views:views]];
    //simple profile label
    [_avatarLayout addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:[_avatarImageView]-5-[_simpleProfileLabel]" options:0 metrics:0 views:views]];
    [_avatarLayout addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:[_nicknameLabel]-3-[_simpleProfileLabel]" options:0 metrics:0 views:views]];
    
    //long profile layout
    [self.view addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:[_avatarLayout]-1-[longProfileLayout(>=50)]" options:0 metrics:0 views:views]];
    [self.view addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:|-0-[longProfileLayout(>=200)]-0-|" options:0 metrics:0 views:views]];
    //about me constraints
    [longProfileLayout addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:|-5-[aboutMeLabel]" options:0 metrics:0 views:views]];
    [longProfileLayout addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:|-5-[aboutMeLabel]" options:0 metrics:0 views:views]];
    //long profile constraints
    [longProfileLayout addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:[aboutMeLabel]-3-[_longProfileLabel]" options:0 metrics:0 views:views]];
    [longProfileLayout addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:|-5-[_longProfileLabel(>=200)]-3-|" options:0 metrics:0 views:views]];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
}

@end
