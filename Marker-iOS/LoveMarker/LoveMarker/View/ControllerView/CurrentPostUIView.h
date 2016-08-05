//
//  CurrentPostUIView.h
//  LoveMarker
//
//  Created by BigHead_Chen on 8/2/16.
//  Copyright © 2016 Eason. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "TopLayoutView.h"

typedef enum{
    CUURENT_POST = 0,
    LAST_ONE_HOUR = 1,
}SegmentedIndex;

@interface CurrentPostUIView : UIView

@property (strong,nonatomic) TopLayoutView* topLayoutView;
@property (strong,nonatomic) UISegmentedControl* segmentedControl;
@property (strong,nonatomic) UITableView* tableView;
@property (strong,nonatomic) UIRefreshControl* refreshControl;
@property (strong,nonatomic) UIView* switchUIView;

-(id)initViewContext:(id)context title:(NSString*)title frame:(CGRect)frame;
@end
