//
//  MyMarkerUIView.h
//  LoveMarker
//
//  Created by BigHead_Chen on 7/19/16.
//  Copyright © 2016 Eason. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface MyMarkerUIView : UIView

@property (strong,nonnull) UIRefreshControl* refreshControl;
@property (strong,nonatomic,nullable) UITableView* tableView;

-(_Nullable id)initWithContext:(_Nullable id)context title:( NSString* _Nullable )title frame:(CGRect)frame;
@end
