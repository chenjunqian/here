//
//  MyMarkerTableViewCell.h
//  LoveMarker
//
//  Created by BigHead_Chen on 5/22/16.
//  Copyright © 2016 Eason. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface MyMarkerTableViewCell : UITableViewCell

@property (nonatomic,strong,getter=getMarkerContentLabel) UILabel *markerContentLabel;
@property (nonatomic,strong,getter=getLocationLabel) UILabel *locationLabel;
@property (nonatomic,strong,getter=getTimeLabel) UILabel *timeLabel;

+(NSInteger)getCellHight;
@end
