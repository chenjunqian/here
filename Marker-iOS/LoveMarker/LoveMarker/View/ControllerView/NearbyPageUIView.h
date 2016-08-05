//
//  NearbyPageUIView.h
//  LoveMarker
//
//  Created by BigHead_Chen on 8/1/16.
//  Copyright © 2016 Eason. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "TopLayoutView.h"

@interface NearbyPageUIView : UIView

@property (strong,nonatomic) UITableView* tableView;
@property (strong,nonatomic) UIRefreshControl* refreshControl;
@property (strong,nonatomic) TopLayoutView* topLayoutView;

-(id)initViewContext:(id)context title:(NSString*)title frame:(CGRect)frame;

@end
