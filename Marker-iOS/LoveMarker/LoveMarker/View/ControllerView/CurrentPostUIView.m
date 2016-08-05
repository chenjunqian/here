//
//  CurrentPostUIView.m
//  LoveMarker
//
//  Created by BigHead_Chen on 8/2/16.
//  Copyright © 2016 Eason. All rights reserved.
//

#import "CurrentPostUIView.h"

@implementation CurrentPostUIView

/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect {
    // Drawing code
}
*/
@synthesize  tableView , refreshControl , topLayoutView,segmentedControl ,switchUIView;

-(id)initViewContext:(id)context title:(NSString*)title frame:(CGRect)frame{
    self = [super initWithFrame:frame];
    
    if (self) {
        switchUIView = [[UIView alloc] initWithFrame:CGRectMake(0, 70, self.frame.size.width, self.frame.size.height - 120)];
        [self addSubview:switchUIView];
        
        topLayoutView = [[TopLayoutView alloc] initWithoutButtom:context title:title andFrame:CGRectMake(0, 20, self.frame.size.width, 50)];
        [self addSubview:topLayoutView];
        
        tableView = [[UITableView alloc] initWithFrame:CGRectMake(0, 0, self.frame.size.width, self.frame.size.height - 120)];
        [switchUIView addSubview:tableView];
        
        refreshControl = [[UIRefreshControl alloc] init];
        [tableView addSubview:refreshControl];
        
        NSArray* arr = [[NSArray alloc] initWithObjects:NSLocalizedString(@"current_post_title", nil),NSLocalizedString(@"last_hour_post_title", nil), nil];
        segmentedControl = [[UISegmentedControl alloc] initWithItems:arr];
        segmentedControl.tintColor = [UIColor whiteColor];
        segmentedControl.center = CGPointMake(frame.size.width/2, 25);
        [segmentedControl setTitleTextAttributes:[NSDictionary dictionaryWithObjectsAndKeys:[UIColor whiteColor],NSForegroundColorAttributeName, nil] forState:UIControlStateNormal];
        topLayoutView.navigationItem.titleView = segmentedControl;
        
    }
    
    return self;
}

@end
