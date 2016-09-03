//
//  NearbyViewController.m
//  LoveMarker
//
//  Created by BigHead_Chen on 5/19/16.
//  Copyright © 2016 Eason. All rights reserved.
//

#import "NearbyViewController.h"
#import "NearbyPageUIView.h"
#import "PostList.h"
#import "HttpRequest.h"
#import "ErrorState.h"
#import "NormalPostTableViewCell.h"
#import "Post.h"
#import "ErrorState.h"
#import "User.h"
#import "NSObject+ObjectMap.h"

@interface NearbyViewController()

@property (strong,nonatomic) NearbyPageUIView* nearbyPageUIView;
@property (strong,nonatomic) PostList *nearByPostList;
@property (nonatomic) NSInteger index;

@end

@implementation NearbyViewController

@synthesize nearByPostList , nearbyPageUIView;

- (void)viewDidLoad {
    [super viewDidLoad];
    [self initView];
    [self getNearByPostByIndex:self.index isLoadMore:NO];
}

-(void) initView{
    self.index = 20;
    
    nearByPostList = [[PostList alloc] init];
    
    nearbyPageUIView = [[NearbyPageUIView alloc] initViewContext:self title:NSLocalizedString(@"nearby_page", nil) frame:CGRectMake(0, 0, self.view.frame.size.width, self.view.frame.size.height)];
    [self.view addSubview:nearbyPageUIView];
    nearbyPageUIView.tableView.delegate = self;
    nearbyPageUIView.tableView.dataSource = self;
    nearbyPageUIView.tableView.separatorStyle = UITableViewCellSeparatorStyleNone;
    
    [nearbyPageUIView.refreshControl addTarget:self action:@selector(refreshAction:) forControlEvents:UIControlEventValueChanged];
    
}

-(IBAction)refreshAction:(id)sender{
    self.index = 20;
    [self getNearByPostByIndex:self.index isLoadMore:NO];
}

-(void)getNearByPostByIndex:(NSInteger)index isLoadMore:(Boolean)isLoadMore{
    if (!isLoadMore && ![nearbyPageUIView.refreshControl isRefreshing]) {
        self.index = 20;
        [nearbyPageUIView.refreshControl beginRefreshing];
    }else{
        self.index = self.index + 20;
    }
    
    [HttpRequest getCurrentPostByNumberOfPost:self.index handler:^(ResponseResult *response, NSObject *resultObject) {
        if (!isLoadMore) {
            [nearbyPageUIView.refreshControl endRefreshing];
        }
        if (response && response.status == Error_Code_Correct) {
            [nearByPostList setChildPostWith:(NSDictionary*)resultObject targetPostList:nearByPostList.postList];
            [nearbyPageUIView.tableView reloadData];
        }
    }];
}

-(void)tableView:(UITableView *)tableView willDisplayCell:(UITableViewCell *)cell forRowAtIndexPath:(NSIndexPath *)indexPath{
    if (nearbyPageUIView.tableView.contentOffset.y >= nearbyPageUIView.tableView.contentSize.height - nearbyPageUIView.tableView.frame.size.height) {
        //load more post when table view scroll to the end
        [self getNearByPostByIndex:self.index isLoadMore:YES];
    }
}

-(UITableViewCell*)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
    static NSString* myMarkerTableViewCell = @"NormalPostTableViewCell";
    NormalPostTableViewCell* cell = [tableView dequeueReusableCellWithIdentifier:myMarkerTableViewCell];
    
    if (cell == nil) {
        cell = [[NormalPostTableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:myMarkerTableViewCell];
        if (nearByPostList.postList!=nil) {
            Post* post = nearByPostList.postList[indexPath.row];
                        cell.markerContentLabel.text = post.tag;
            cell.timeLabel.text = [post getTimeWithTimestamp:post.time];
            cell.locationLabel.text = post.address;
            [HttpRequest getUserInfoByUsername:post.username handler:^(ResponseResult *response, NSObject *resultObject) {
                if (response.status == Error_Code_Correct) {
                    User* user = [NSObject objectOfClass:@"User" fromJSON:(NSDictionary*)resultObject];
                    [HttpRequest downloadAvatarWithUrl:user.avatar UIImageView:cell.avatarUIImageView];
                    cell.nicknameUILabel.text = user.nickname;
                    cell.simpleProfileLable.text = user.simpleProfile;
                }

            }];
        }
    }
    
    return cell;
}

-(NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    if (nearByPostList.postList) {
        return nearByPostList.postList.count;
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
