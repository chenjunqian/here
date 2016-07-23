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
#import "NSObject+ObjectMap.h"

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
    
    if ([[LoginStatus getInstance] getIsUserModel]) {
        [HttpRequest getPosyByUsername:[[LoginStatus getInstance] getUser].username responseData:^(ResponseResult *response, NSObject *resultObject) {
            if (response.status == Error_Code_Correct) {
                [_myMarkerList setChildPostWith:(NSDictionary*)resultObject targetPostList:_myMarkerList.postList];
                [_myMarkerUIView.tableView reloadData];
            }
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
        }
    }

    return cell;
}

-(NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    if (_myMarkerList.postList == nil) {
        return 0;
    }else{
        return _myMarkerList.postList.count;
    }
}

-(CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath{
//    static NSString* myMarkerTableViewCell = @"MyMarkerTableViewCell";
//    MyMarkerTableViewCell* cell = [tableView dequeueReusableCellWithIdentifier:myMarkerTableViewCell];
    
//    return cell.markerContentLabel.frame.size.height + cell.timeLabel.frame.size.height + cell.locationLabel.frame.size.height +1 ;
    return 105;
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
}

@end
