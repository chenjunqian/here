//
//  MyMarkerUIView.h
//  LoveMarker
//
//  Created by BigHead_Chen on 7/19/16.
//  Copyright © 2016 Eason. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface MyMarkerUIView : UIView

@property (strong,nonatomic) UITableView* tableView;
-(id)initWithContext:(id)context title:(NSString*)title frame:(CGRect)frame;
@end
