//
//  MyMarkerUIView.m
//  LoveMarker
//
//  Created by BigHead_Chen on 7/19/16.
//  Copyright © 2016 Eason. All rights reserved.
//

#import "MyMarkerUIView.h"
#import "TopLayoutView.h"

@interface MyMarkerUIView()

@property (strong,nonatomic) TopLayoutView *topLayoutView;

@end

@implementation MyMarkerUIView

/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect {
    // Drawing code
}
*/

@synthesize tableView;

-(id)initWithContext:(id)context title:(NSString*)title frame:(CGRect)frame{
    self = [super initWithFrame:frame];
    
    if (self) {
        _topLayoutView = [[TopLayoutView alloc] initWithContext:context title:title andFrame:CGRectMake(0, 20, self.frame.size.width, 50)];
        [self addSubview:_topLayoutView];
        
        tableView = [[UITableView alloc] init];
        tableView.translatesAutoresizingMaskIntoConstraints = NO;
        tableView.backgroundColor = [UIColor whiteColor];
        [self addSubview:tableView];
    }
    
    return self;
}

-(void)layoutSubviews{
    NSDictionary* views = NSDictionaryOfVariableBindings(_topLayoutView,tableView);
    
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:[_topLayoutView]-0-[tableView]-0-|" options:0 metrics:0 views:views]];
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:|-0-[tableView]-0-|" options:0 metrics:0 views:views]];
}

@end
