//
//  MyMarkerUIViewController.m
//  LoveMarker
//
//  Created by BigHead_Chen on 7/19/16.
//  Copyright © 2016 Eason. All rights reserved.
//

#import "MyMarkerUIViewController.h"
#import "MyMarkerUIView.h"
#import "MyMarkerTableViewCell.h"
#import "HttpRequest.h"
#import "LoginStatus.h"
#import "ErrorState.h"
#import "Post.h"
#import <objc/runtime.h>
#import "GlobalActivityIndicators.h"

@interface MyMarkerUIViewController ()

@property(strong,nonatomic) MyMarkerUIView *myMarkerUIView;

@end

@implementation MyMarkerUIViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    [self initView];
    [self getMyPost];
}

-(void)getMyPost{
    
    if (![_myMarkerUIView.refreshControl isRefreshing]) {
        [_myMarkerUIView.refreshControl beginRefreshing];
    }
    
    
    if ([[LoginStatus getInstance] getIsUserModel]) {
        [HttpRequest getPosyByUsername:[[LoginStatus getInstance] getUser].username responseData:^(ResponseResult *response, NSObject *resultObject) {
            if (response.status == Error_Code_Correct) {
                [_myMarkerList setChildPostWith:(NSDictionary*)resultObject targetPostList:_myMarkerList.postList];
                [_myMarkerUIView.tableView reloadData];
            }
            
            [_myMarkerUIView.refreshControl endRefreshing];
        }];
    }
    
}

-(void)initView{
    _myMarkerList = [[PostList alloc] init];
    
    _myMarkerUIView = [[MyMarkerUIView alloc] initWithContext:self title:NSLocalizedString(@"my_marker", nil) frame:CGRectMake(0, 0, self.view.frame.size.width, self.view.frame.size.height)];
    [self.view addSubview:_myMarkerUIView];
    _myMarkerUIView.tableView.delegate = self;
    _myMarkerUIView.tableView.dataSource =self;
    _myMarkerUIView.tableView.separatorStyle = UITableViewCellSeparatorStyleNone;
    
    [_myMarkerUIView.refreshControl addTarget:self action:@selector(refreshAction:) forControlEvents:UIControlEventValueChanged];
}

-(void)refreshAction:(id)sender{
    [self getMyPost];
}

-(UITableViewCell*)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
    static NSString* myMarkerTableViewCell = @"MyMarkerTableViewCell";
    MyMarkerTableViewCell* cell = [tableView dequeueReusableCellWithIdentifier:myMarkerTableViewCell];
    
    if (cell == nil) {
        cell = [[MyMarkerTableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:myMarkerTableViewCell];
        if (_myMarkerList.postList!=nil) {
            Post* post = _myMarkerList.postList[indexPath.row];
            cell.markerContentLabel.text = post.tag;
            cell.timeLabel.text = [post getTimeWithTimestamp:post.time];
            cell.locationLabel.text = post.address;
            objc_setAssociatedObject(cell.deleteButton, @"Post", post, OBJC_ASSOCIATION_RETAIN);
            [cell.deleteButton addTarget:self action:@selector(deletePostAction:) forControlEvents:UIControlEventTouchDown];
        }
    }

    return cell;
}

-(void)deletePostAction:(id)sender{
    Post* post = objc_getAssociatedObject(sender, @"Post");
    GlobalActivityIndicators* indicator = [[GlobalActivityIndicators alloc] initWithTitle:NSLocalizedString(@"indicator_is_registering", nil) frame:CGRectMake(0, 0, self.view.frame.size.width, self.view.frame.size.height)];
    [indicator.activityIndicatorView startAnimating];
    [self.view addSubview:indicator];
    [HttpRequest deletePostByPostId:post.postId username:post.username handler:^(ResponseResult *response, NSObject *resultObject) {
        if (response && response.status == Error_Code_Correct) {
            [_myMarkerList.postList removeObject:post];
            [_myMarkerUIView.tableView reloadData];
        }
        
        [indicator setHidden:YES];
    }];
}

-(NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    if (_myMarkerList.postList == nil) {
        return 0;
    }else{
        return _myMarkerList.postList.count;
    }
}

-(CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath{
    return 105;
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
}

@end
