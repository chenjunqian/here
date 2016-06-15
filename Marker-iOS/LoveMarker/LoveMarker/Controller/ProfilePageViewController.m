//
//  ProfilePageViewController.m
//  LoveMarker
//
//  Created by BigHead_Chen on 5/19/16.
//  Copyright © 2016 Eason. All rights reserved.
//

#import "ProfilePageViewController.h"
#import "TopLayoutView.h"
#import "MyMarkerTableViewCell.h"
#import "AvatarUIImageView.h"
#import "LoginStatus.h"
#import "User.h"
#import "LoginViewController.h"

@interface ProfilePageViewController ()

@property(nonatomic,strong) TopLayoutView *topLayoutView;
@property(nonatomic,strong,getter=getAvatarUIView) UIView *avatarLayout;
@property(nonatomic,strong,getter=getAvatarImageView) AvatarUIImageView *avatarImageView;
@property(nonatomic,strong,getter=getNicknameLabel) UILabel *nicknameLabel;
@property(nonatomic,strong,getter=getSimpleProfileLabel) UILabel *simpleProfileLabel;
@property(nonatomic,strong,getter=getLongProfileLabel) UILabel *longProfileLabel;
@property(nonatomic,strong,getter=getLoginLayoutView) UIView *loginLayoutView;
@property(nonatomic,strong,getter=getToLoginPageButton) UIButton *toLoginPageButton;
@property(nonatomic,strong) UITableView *myMarkerTableView;
@property(nonatomic,strong) User *user;

@end

@implementation ProfilePageViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    [self initView];
}

-(void)viewWillAppear:(BOOL)animated{
    [self loadData];
}

-(void)initView{
    [self.view setBackgroundColor:[super colorWithHexString:@"F3F3F3"]];
    
    if (![[LoginStatus getInstance] getIsUserModel]) {
        [self addLoginView];
    }else{
        [self addLoginedView];
    }

}

-(void)addLoginedView{
    _topLayoutView = [[TopLayoutView alloc] initWithFrame:CGRectMake(0, 20, self.view.frame.size.width, 40)];
    [[_topLayoutView getBackBtn] setHidden:YES];
    [[_topLayoutView getTitleLabel] setText:NSLocalizedString(@"me", nil)];
    [self.view addSubview:_topLayoutView];
    
    _avatarLayout = [[UIView alloc] init];
    _avatarLayout.translatesAutoresizingMaskIntoConstraints = NO;
    [_avatarLayout setBackgroundColor:[UIColor whiteColor]];
    [self.view addSubview:_avatarLayout];
    
    _avatarImageView = [[AvatarUIImageView alloc] init];
    _avatarImageView.translatesAutoresizingMaskIntoConstraints = NO;
    [_avatarLayout addSubview:_avatarImageView];
    
    _nicknameLabel = [[UILabel alloc] init];
    _nicknameLabel.translatesAutoresizingMaskIntoConstraints = NO;
    [_avatarLayout addSubview:_nicknameLabel];
    
    _simpleProfileLabel = [[UILabel alloc] init];
    _simpleProfileLabel.translatesAutoresizingMaskIntoConstraints = NO;
    [_simpleProfileLabel setTextColor:[UIColor grayColor]];    [_avatarLayout addSubview:_simpleProfileLabel];
    
    UIView *longProfileLayout = [[UIView alloc]init];
    [longProfileLayout sizeToFit];
    longProfileLayout.translatesAutoresizingMaskIntoConstraints = NO;
    [longProfileLayout setBackgroundColor:[UIColor whiteColor]];
    [self.view addSubview:longProfileLayout];
    
    UILabel *aboutMeLabel = [[UILabel alloc] init];
    aboutMeLabel.translatesAutoresizingMaskIntoConstraints = NO;
    [aboutMeLabel setFont:[UIFont systemFontOfSize:12]];
    [aboutMeLabel setText:NSLocalizedString(@"about_me", nil)];
    [longProfileLayout addSubview:aboutMeLabel];
    
    _longProfileLabel = [[UILabel alloc] init];
    _longProfileLabel.translatesAutoresizingMaskIntoConstraints = NO;
    [_longProfileLabel setTextColor:[UIColor grayColor]];
    [_longProfileLabel setText:@""];
    [_longProfileLabel setLineBreakMode:NSLineBreakByCharWrapping];
    [_longProfileLabel setNumberOfLines:0];
    [longProfileLayout addSubview:_longProfileLabel];
    
    _myMarkerTableView = [[UITableView alloc] init];
    _myMarkerTableView.translatesAutoresizingMaskIntoConstraints = NO;
    _myMarkerTableView.delegate = self;
    _myMarkerTableView.dataSource = self;
    [_myMarkerTableView setBackgroundColor:[UIColor whiteColor]];
    [self.view addSubview:_myMarkerTableView];
    
    NSDictionary *views = NSDictionaryOfVariableBindings(_topLayoutView,_avatarLayout,_avatarImageView,_nicknameLabel,_simpleProfileLabel,longProfileLayout,_longProfileLabel,aboutMeLabel,_myMarkerTableView);
    
    [self.view addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:[_topLayoutView]-0-[_avatarLayout(80)]" options:0 metrics:0 views:views]];
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
    [self.view addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:[_avatarLayout]-1-[longProfileLayout]" options:0 metrics:0 views:views]];
    [self.view addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:|-0-[longProfileLayout(>=200)]-0-|" options:0 metrics:0 views:views]];
    //about me constraints
    [longProfileLayout addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:|-5-[aboutMeLabel]" options:0 metrics:0 views:views]];
    [longProfileLayout addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:|-5-[aboutMeLabel]" options:0 metrics:0 views:views]];
    //long profile constraints
    [longProfileLayout addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:[aboutMeLabel]-3-[_longProfileLabel]-10-|" options:0 metrics:0 views:views]];
    [longProfileLayout addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:|-5-[_longProfileLabel(>=200)]-3-|" options:0 metrics:0 views:views]];
    //table view constraints
    [self.view addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:[longProfileLayout]-0-[_myMarkerTableView(>=100)]-0-|" options:0 metrics:0 views:views]];
    [self.view addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:|-0-[_myMarkerTableView]-0-|" options:0 metrics:0 views:views]];
}

-(void)addLoginView{
    _loginLayoutView = [[UIView alloc] init];
    _loginLayoutView.translatesAutoresizingMaskIntoConstraints = NO;
    [_loginLayoutView setBackgroundColor:[super colorWithHexString:@"F3F3F3"]];
    [self.view addSubview:_loginLayoutView];
    
    _toLoginPageButton = [[UIButton alloc] init];
    _toLoginPageButton.translatesAutoresizingMaskIntoConstraints = NO;
    [_toLoginPageButton setTitle:NSLocalizedString(@"login", nil) forState:UIControlStateNormal];
    [_toLoginPageButton setTitleColor:[UIColor blueColor] forState:UIControlStateNormal];
    [_toLoginPageButton setBackgroundColor:[UIColor whiteColor]];
    [_toLoginPageButton addTarget:self action:@selector(toLoginPageSelector:) forControlEvents:UIControlEventTouchDown];
    [_loginLayoutView addSubview:_toLoginPageButton];
    
    NSDictionary *views = NSDictionaryOfVariableBindings(_loginLayoutView,_toLoginPageButton);
    
    //login layout constraints
    [self.view addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:|-0-[_loginLayoutView]-0-|" options:0 metrics:0 views:views]];
    [self.view addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:|-20-[_loginLayoutView(>=100)]-0-|" options:0 metrics:0 views:views]];
    
    [_loginLayoutView addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:[_toLoginPageButton(40)]" options:0 metrics:0 views:views]];
    [_loginLayoutView addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:[_toLoginPageButton(150)]" options:0 metrics:0 views:views]];
    [_loginLayoutView addConstraint:[NSLayoutConstraint constraintWithItem:_toLoginPageButton attribute:NSLayoutAttributeCenterX relatedBy:NSLayoutRelationEqual toItem:_loginLayoutView attribute:NSLayoutAttributeCenterX multiplier:1 constant:0]];
    [_loginLayoutView addConstraint:[NSLayoutConstraint constraintWithItem:_toLoginPageButton attribute:NSLayoutAttributeCenterY relatedBy:NSLayoutRelationEqual toItem:_loginLayoutView attribute:NSLayoutAttributeCenterY multiplier:1 constant:0]];
}

-(UITableViewCell*)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
    static NSString *MyMarkerTablerCell = @"MyMarkerTablerCell";
    MyMarkerTableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:MyMarkerTablerCell];
    if (cell==nil) {
        cell = [[MyMarkerTableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:MyMarkerTablerCell];
    }
    
    return cell;
}

-(CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath{
    return [MyMarkerTableViewCell getCellHight];
}


- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    return 0;
}

-(IBAction)toLoginPageSelector:(id)sender{
    LoginViewController *loginViewController = [[LoginViewController alloc] init];
    [self presentViewController:loginViewController animated:YES completion:^{
        
    }];
}

-(void)loadData{
    if ([[LoginStatus getInstance] getIsUserModel]) {
        self.user = [[LoginStatus getInstance] getUser];
        if (self.user.avatar) {
//            [self.avatarImageView setImage:[UIImage imageWithData:[self.user getAvatar]]];
        }
        
        [self.nicknameLabel setText:[self.user getNickname]];
        [self.simpleProfileLabel setText:[self.user getSimpleProfile]];
        [self.longProfileLabel setText:[self.user getLongProfile]];
    }
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
}

@end
