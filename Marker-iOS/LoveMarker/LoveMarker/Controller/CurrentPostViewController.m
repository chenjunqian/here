//
//  CurrentPostViewController.m
//  LoveMarker
//
//  Created by BigHead_Chen on 5/19/16.
//  Copyright © 2016 Eason. All rights reserved.
//

#import "CurrentPostViewController.h"
#import "CurrentPostUIView.h"
#import "PostList.h"
#import "HttpRequest.h"
#import "ErrorState.h"
#import "NormalPostTableViewCell.h"
#import "Post.h"
#import "ErrorState.h"
#import "User.h"
#import "NSObject+ObjectMap.h"
#import "LastHourPostViewController.h"

@interface CurrentPostViewController ()

@property (strong,nonatomic) CurrentPostUIView* currentPostUIView;
@property (strong,nonatomic) LastHourPostViewController* lastHourPostViewController;
@property (strong,nonatomic) PostList *currentPostList;
@property (nonatomic) NSInteger currentIndex;
@property (nonatomic) Boolean isShowCurrentPostList;

@end

@implementation CurrentPostViewController

@synthesize currentPostList,currentPostUIView,lastHourPostViewController;

- (void)viewDidLoad {
    [super viewDidLoad];
    [self initView];
    [self getCurrentPostByIndex:self.currentIndex isLoadMore:NO];
    [self isShowCurrentPostView:YES];
}

-(void)initView{
    currentPostUIView = [[CurrentPostUIView alloc] initViewContext:self title:@""  frame:CGRectMake(0, 0, self.view.frame.size.width, self.view.frame.size.height)];
    [self.view addSubview:currentPostUIView];
    
    currentPostList = [[PostList alloc] init];
    
    self.currentIndex = 20;
    
    currentPostUIView.tableView.delegate = self;
    currentPostUIView.tableView.dataSource = self;
    currentPostUIView.tableView.separatorStyle = UITableViewCellSeparatorStyleNone;
    [currentPostUIView.refreshControl addTarget:self action:@selector(refreshAction:) forControlEvents:UIControlEventValueChanged];
    
    [currentPostUIView.segmentedControl addTarget:self action:@selector(segmentedControlAction:) forControlEvents:UIControlEventValueChanged];
    [currentPostUIView.segmentedControl setSelectedSegmentIndex:0];
    
    lastHourPostViewController = [[LastHourPostViewController alloc] init];
    [currentPostUIView.switchUIView addSubview:lastHourPostViewController.view];
}

-(void)isShowCurrentPostView:(Boolean)isShowCurrentPostList{
    _isShowCurrentPostList = isShowCurrentPostList;
    if (isShowCurrentPostList) {
        [lastHourPostViewController.view setHidden:YES];
        [currentPostUIView.tableView setHidden:NO];
        
    }else{
        [lastHourPostViewController.view setHidden:NO];
        [currentPostUIView.tableView setHidden:YES];
    }
}

-(IBAction)segmentedControlAction:(id)sender{
    UISegmentedControl* control = (UISegmentedControl*)sender;
    switch (control.selectedSegmentIndex) {
        case CUURENT_POST:
            [self isShowCurrentPostView:YES];
            break;
            
        case LAST_ONE_HOUR:
            [self isShowCurrentPostView:NO];
            break;
        default:
            break;
    }
}

-(IBAction)refreshAction:(id)sender{
    self.currentIndex = 20;
    [self getCurrentPostByIndex:self.currentIndex isLoadMore:NO];
}

-(void)getCurrentPostByIndex:(NSInteger)index isLoadMore:(Boolean)isLoadMore{
    if (!isLoadMore && ![currentPostUIView.refreshControl isRefreshing]) {
        self.currentIndex = 20;
        [currentPostUIView.refreshControl beginRefreshing];
    }else{
        self.currentIndex = self.currentIndex + 20;
    }
    
    [HttpRequest getCurrentPostByNumberOfPost:self.currentIndex handler:^(ResponseResult *response, NSObject *resultObject) {
        if (!isLoadMore) {
            [currentPostUIView.refreshControl endRefreshing];
            [currentPostList.postList removeAllObjects];
        }
        if (response && response.status == Error_Code_Correct) {
            [currentPostList setChildPostWith:(NSDictionary*)resultObject targetPostList:currentPostList.postList];
            [currentPostUIView.tableView reloadData];
        }
    }];
}

-(void)tableView:(UITableView *)tableView willDisplayCell:(UITableViewCell *)cell forRowAtIndexPath:(NSIndexPath *)indexPath{
    if (currentPostUIView.tableView.contentOffset.y >= currentPostUIView.tableView.contentSize.height - currentPostUIView.tableView.frame.size.height) {
        //load more post when table view scroll to the end
        [self getCurrentPostByIndex:self.currentIndex isLoadMore:YES];
    }
}

-(UITableViewCell*)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
    static NSString* currentPostTableViewCell = @"CurrentPostTableViewCell";
    NormalPostTableViewCell* currentCell;

        currentCell = [tableView dequeueReusableCellWithIdentifier:currentPostTableViewCell];
        
        if (!currentCell) {
            currentCell = [[NormalPostTableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:currentPostTableViewCell];
        }
        
        if (currentPostList.postList!=nil && currentPostList.postList.count>0) {
            Post* post = currentPostList.postList[indexPath.row];
            currentCell.markerContentLabel.text = post.tag;
            currentCell.timeLabel.text = [post getTimeWithTimestamp:post.time];
            currentCell.locationLabel.text = post.address;
            [HttpRequest getUserInfoByUsername:post.username handler:^(ResponseResult *response, NSObject *resultObject) {
                if (response.status == Error_Code_Correct) {
                    User* user = [NSObject objectOfClass:@"User" fromJSON:(NSDictionary*)resultObject];
                    [HttpRequest downloadAvatarWithUrl:user.avatar UIImageView:currentCell.avatarUIImageView];
                    currentCell.nicknameUILabel.text = user.nickname;
                    currentCell.simpleProfileLable.text = user.simpleProfile;
                }
                
            }];
        }
    
    return currentCell;
}

-(NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    if (currentPostList.postList) {
        return currentPostList.postList.count;
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
