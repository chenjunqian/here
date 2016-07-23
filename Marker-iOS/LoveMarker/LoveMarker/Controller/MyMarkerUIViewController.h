//
//  MyMarkerUIViewController.h
//  LoveMarker
//
//  Created by BigHead_Chen on 7/19/16.
//  Copyright © 2016 Eason. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "PostList.h"
#import "BasicViewController.h"

@interface MyMarkerUIViewController : BasicViewController <UITableViewDelegate,UITableViewDataSource>

@property (strong,nonatomic) PostList *myMarkerList;

@end
