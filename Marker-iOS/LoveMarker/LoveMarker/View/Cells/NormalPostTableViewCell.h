//
//  NormalPostTableViewCell.h
//  LoveMarker
//
//  Created by BigHead_Chen on 8/1/16.
//  Copyright © 2016 Eason. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "AvatarUIImageView.h"

@interface NormalPostTableViewCell : UITableViewCell

@property (strong,nonatomic) AvatarUIImageView* avatarUIImageView;
@property (nonatomic,strong) UILabel *nicknameUILabel;
@property (nonatomic,strong) UILabel *simpleProfileLable;
@property (nonatomic,strong,getter=getMarkerContentLabel) UILabel *markerContentLabel;
@property (nonatomic,strong,getter=getLocationLabel) UILabel *locationLabel;
@property (nonatomic,strong,getter=getTimeLabel) UILabel *timeLabel;

@end
