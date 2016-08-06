//
//  LastHourPostViewController.m
//  LoveMarker
//
//  Created by BigHead_Chen on 8/3/16.
//  Copyright © 2016 Eason. All rights reserved.
//

#import "LastHourPostViewController.h"
#import "NormalPostTableViewCell.h"
#import "PostList.h"
#import "HttpRequest.h"
#import "ErrorState.h"
#import "NormalPostTableViewCell.h"
#import "Post.h"
#import "ErrorState.h"
#import "User.h"
#import "NSObject+ObjectMap.h"
#import "ColorUtil.h"

@interface LastHourPostViewController ()

@property (strong,nonatomic) PostList *lastHourPostList;
@property (strong,nonatomic) UITableView* lastHourTableView;
@property (strong,nonatomic) UIView* noPostUIView;
@property (strong,nonatomic) UIRefreshControl* lastHourRefreshControl;
@property (nonatomic) NSInteger lastHourIndex;
@property (nonatomic) Boolean isShowCurrentPostList;
@end

@implementation LastHourPostViewController

@synthesize lastHourTableView,lastHourRefreshControl,lastHourPostList,lastHourIndex,noPostUIView;

- (void)viewDidLoad {
    [super viewDidLoad];
    [self initView];
    [self getLastHourPostByIndex:lastHourIndex isLoadMore:NO];
}

-(void)initView{
    [self.view setFrame:CGRectMake(0, 0, self.view.frame.size.width, self.view.frame.size.height - 120)];
    
    lastHourPostList = [[PostList alloc] init];
    lastHourIndex = 20;
    lastHourTableView = [[UITableView alloc] initWithFrame:self.view.frame];
    lastHourTableView.delegate = self;
    lastHourTableView.dataSource = self;
    lastHourTableView.separatorStyle = UITableViewCellSeparatorStyleNone;
    [self.view addSubview:lastHourTableView];
    lastHourRefreshControl = [[UIRefreshControl alloc] init];
    [lastHourRefreshControl addTarget:self action:@selector(refreshAction:) forControlEvents:UIControlEventValueChanged];
    [lastHourTableView addSubview:lastHourRefreshControl];
    
    noPostUIView = [[UIView alloc] initWithFrame:self.view.frame];
    noPostUIView.backgroundColor = [ColorUtil viewBackgroundGrey];
    [self.view addSubview:noPostUIView];
    [noPostUIView setHidden:YES];
    UILabel* noPostUILabel = [[UILabel alloc] init];
    noPostUILabel.text = NSLocalizedString(@"there_is_no_last_hour_post", nil);
    noPostUILabel.textColor = [ColorUtil tealBlueColor];
    noPostUILabel.translatesAutoresizingMaskIntoConstraints = NO;
    [noPostUIView addSubview:noPostUILabel];
    
    [noPostUIView addConstraint:[NSLayoutConstraint constraintWithItem:noPostUILabel attribute:NSLayoutAttributeCenterX relatedBy:NSLayoutRelationEqual toItem:noPostUIView attribute:NSLayoutAttributeCenterX multiplier:1 constant:0]];
        [noPostUIView addConstraint:[NSLayoutConstraint constraintWithItem:noPostUILabel attribute:NSLayoutAttributeCenterY relatedBy:NSLayoutRelationEqual toItem:noPostUIView attribute:NSLayoutAttributeCenterY multiplier:1 constant:0]];
}

-(IBAction)refreshAction:(id)sender{
    lastHourIndex = 20;
    [self getLastHourPostByIndex:lastHourIndex isLoadMore:NO];
}

-(void)getLastHourPostByIndex:(NSInteger)index isLoadMore:(Boolean)isLoadMore{
    if (!isLoadMore) {
        lastHourIndex = 20;
        [lastHourRefreshControl beginRefreshing];
    }else{
        lastHourIndex = lastHourIndex + 20;
    }
    
    [HttpRequest getLastHourPostByNumberOfPost:lastHourIndex handler:^(ResponseResult *response, NSObject *resultObject) {
        if (!isLoadMore) {
            [lastHourRefreshControl endRefreshing];
            [lastHourPostList.postList removeAllObjects];
        }
        if (response && response.status == Error_Code_Correct) {
            [noPostUIView setHidden:YES];
            [lastHourTableView setHidden:NO];
            [lastHourPostList setChildPostWith:(NSDictionary*)resultObject targetPostList:lastHourPostList.postList];
            [lastHourTableView reloadData];
        }else {
            [noPostUIView setHidden:NO];
            [lastHourTableView setHidden:YES];
        }
    }];
}

-(void)tableView:(UITableView *)tableView willDisplayCell:(UITableViewCell *)cell forRowAtIndexPath:(NSIndexPath *)indexPath{
    if (lastHourTableView.contentOffset.y >= lastHourTableView.contentSize.height - lastHourTableView.frame.size.height) {
        //load more post when table view scroll to the end
        [self getLastHourPostByIndex:lastHourIndex isLoadMore:YES];
    }
}

-(UITableViewCell*)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
    static NSString* currentPostTableViewCell = @"LastHourPostTableViewCell";
    NormalPostTableViewCell* lastHourtCell;
    lastHourtCell = [tableView dequeueReusableCellWithIdentifier:currentPostTableViewCell];
        
    if (!lastHourtCell) {
        lastHourtCell = [[NormalPostTableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:currentPostTableViewCell];
    }
        
    if (lastHourPostList.postList!=nil) {
        Post* post = lastHourPostList.postList[indexPath.row];
        lastHourtCell.markerContentLabel.text = post.tag;
        lastHourtCell.timeLabel.text = [post getTimeWithTimestamp:post.time];
        lastHourtCell.locationLabel.text = post.address;
        [HttpRequest getUserInfoByUsername:post.username handler:^(ResponseResult *response, NSObject *resultObject) {
            if (response.status == Error_Code_Correct) {
                User* user = [NSObject objectOfClass:@"User" fromJSON:(NSDictionary*)resultObject];
                [HttpRequest downloadAvatarWithUrl:user.avatar UIImageView:lastHourtCell.avatarUIImageView];
                lastHourtCell.nicknameUILabel.text = user.nickname;
                lastHourtCell.simpleProfileLable.text = user.simpleProfile;
            }
            
        }];
    }
        
    
    return lastHourtCell;
}

-(NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    if (lastHourPostList.postList) {
        return lastHourPostList.postList.count;
    }else{
        return 0;
    }
}

-(CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath{
    return 135;
}
- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
