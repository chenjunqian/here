//
//  NearbyPageUIView.m
//  LoveMarker
//
//  Created by BigHead_Chen on 8/1/16.
//  Copyright © 2016 Eason. All rights reserved.
//

#import "NearbyPageUIView.h"

@implementation NearbyPageUIView

/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect {
    // Drawing code
}
*/

@synthesize  tableView , refreshControl , topLayoutView;

-(id)initViewContext:(id)context title:(NSString*)title frame:(CGRect)frame{
    self = [super initWithFrame:frame];
    
    if (self) {
        topLayoutView = [[TopLayoutView alloc] initWithoutButtom:context title:title andFrame:CGRectMake(0, 20, self.frame.size.width, 50)];
        [self addSubview:topLayoutView];
        
        tableView = [[UITableView alloc] initWithFrame:CGRectMake(0, 70, self.frame.size.width, self.frame.size.height - 120)];
        [self addSubview:tableView];
        
        refreshControl = [[UIRefreshControl alloc] init];
        [tableView addSubview:refreshControl];
    }

    return self;
}

@end
