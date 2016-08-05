//
//  NormalPostTableViewCell.m
//  LoveMarker
//
//  Created by BigHead_Chen on 8/1/16.
//  Copyright © 2016 Eason. All rights reserved.
//

#import "NormalPostTableViewCell.h"
#import "ColorUtil.h"

@interface NormalPostTableViewCell()
@property (nonatomic,strong) UIImageView *heartImageView;
@property (nonatomic,strong) UIImageView *locationImageView;
@property (nonatomic,strong) UIButton *deleteButton;
@property (nonatomic,strong) UIView *bottomLine;
@property (nonatomic,strong) UIView *userInfoUIView;;
@end

@implementation NormalPostTableViewCell

-(id)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier{
    self = [super initWithStyle:style reuseIdentifier:reuseIdentifier];
    if (self) {
        [self setBackgroundColor:[UIColor whiteColor]];
        
        _avatarUIImageView = [[AvatarUIImageView alloc] init];
        _avatarUIImageView.translatesAutoresizingMaskIntoConstraints = NO;
        [self addSubview:_avatarUIImageView];
        
        _userInfoUIView = [[UIView alloc] init];
        _userInfoUIView.translatesAutoresizingMaskIntoConstraints = NO;
        [self addSubview:_userInfoUIView];
        
        _nicknameUILabel = [[UILabel alloc] init];
        _nicknameUILabel.translatesAutoresizingMaskIntoConstraints = NO;
        [_nicknameUILabel setTextColor:[ColorUtil tealBlueColor]];
        [_simpleProfileLable setFont:[UIFont systemFontOfSize:15]];
        [_userInfoUIView addSubview:_nicknameUILabel];
        
        _simpleProfileLable = [[UILabel alloc] init];
        _simpleProfileLable.translatesAutoresizingMaskIntoConstraints = NO;
        [_simpleProfileLable setTextColor:[ColorUtil textColorSubBlack]];
        [_simpleProfileLable setFont:[UIFont systemFontOfSize:12]];
        _simpleProfileLable.numberOfLines = 1;
        [_userInfoUIView addSubview:_simpleProfileLable];
        
        _heartImageView = [[UIImageView alloc]init];
        _heartImageView.translatesAutoresizingMaskIntoConstraints = NO;
        [_heartImageView setImage:[UIImage imageNamed:@"marker_ic"]];
        [self addSubview:_heartImageView];
        
        _markerContentLabel = [[UILabel alloc] init];
        _markerContentLabel.translatesAutoresizingMaskIntoConstraints = NO;
        [_markerContentLabel setTextColor:[UIColor blackColor]];
        [_markerContentLabel setFont:[UIFont systemFontOfSize:14]];
        [self addSubview:_markerContentLabel];
        
        _timeLabel = [[UILabel alloc] init];
        _timeLabel.translatesAutoresizingMaskIntoConstraints = NO;
        [_timeLabel setTextColor:[UIColor grayColor]];
        [_timeLabel setFont:[UIFont systemFontOfSize:12]];
        [self addSubview:_timeLabel];
        
        _locationImageView = [[UIImageView alloc] init];
        _locationImageView.translatesAutoresizingMaskIntoConstraints = NO;
        [_locationImageView setImage:[UIImage imageNamed:@"ic_location_on_18pt"]];
        [self addSubview:_locationImageView];
        
        _locationLabel = [[UILabel alloc] init];
        _locationLabel.translatesAutoresizingMaskIntoConstraints = NO;
        [_locationLabel setTextColor:[UIColor grayColor]];
        [_locationLabel setFont:[UIFont systemFontOfSize:12]];
        _locationLabel.numberOfLines = 0;
        [self addSubview:_locationLabel];
        
        _bottomLine = [[UIView alloc] init];
        _bottomLine.translatesAutoresizingMaskIntoConstraints = NO;
        _bottomLine.backgroundColor = [ColorUtil viewBackgroundGrey];
        [self addSubview:_bottomLine];
    }
    
    return self;
    
}

-(void)layoutSubviews{
    NSDictionary *views = NSDictionaryOfVariableBindings(_avatarUIImageView,_nicknameUILabel,_simpleProfileLable,_userInfoUIView,_heartImageView,_markerContentLabel,_timeLabel,_locationLabel,_locationImageView,_bottomLine);
    
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:|-20-[_avatarUIImageView(50)]" options:0 metrics:0 views:views]];
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:|-10-[_avatarUIImageView(50)]" options:0 metrics:0 views:views]];
    
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:[_avatarUIImageView]-10-[_userInfoUIView]" options:0 metrics:0 views:views]];
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:|-20-[_userInfoUIView]" options:0 metrics:0 views:views]];
    
    [_userInfoUIView addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:|-0-[_nicknameUILabel]" options:0 metrics:0 views:views]];
    [_userInfoUIView addConstraint:[NSLayoutConstraint constraintWithItem:_nicknameUILabel attribute:NSLayoutAttributeCenterY relatedBy:NSLayoutRelationEqual toItem:_userInfoUIView attribute:NSLayoutAttributeCenterY multiplier:1 constant:0]];
    
    [_userInfoUIView addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:[_nicknameUILabel]-10-[_simpleProfileLable]" options:0 metrics:0 views:views]];
    [_userInfoUIView addConstraint:[NSLayoutConstraint constraintWithItem:_simpleProfileLable attribute:NSLayoutAttributeCenterY relatedBy:NSLayoutRelationEqual toItem:_userInfoUIView attribute:NSLayoutAttributeCenterY multiplier:1 constant:0]];
    
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:[_userInfoUIView]-10-[_heartImageView(18)]" options:0 metrics:0 views:views]];
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:|-70-[_heartImageView(18)]" options:0 metrics:0 views:views]];
    
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:[_userInfoUIView]-10-[_markerContentLabel]" options:0 metrics:0 views:views]];
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:[_heartImageView]-5-[_markerContentLabel]" options:0 metrics:0 views:views]];
    
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:[_heartImageView]-15-[_timeLabel]" options:0 metrics:0 views:views]];
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:|-70-[_timeLabel]" options:0 metrics:0 views:views]];
    
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:[_timeLabel]-5-[_locationImageView(22)]" options:0 metrics:0 views:views]];
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:|-70-[_locationImageView(18)]" options:0 metrics:0 views:views]];
    
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:[_timeLabel]-5-[_locationLabel]" options:0 metrics:0 views:views]];
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:[_locationImageView]-5-[_locationLabel]-10-|" options:0 metrics:0 views:views]];
    
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:|-70-[_bottomLine(>=200)]-0-|" options:0 metrics:0 views:views]];
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:[_locationImageView]-10-[_bottomLine(1)]-0-|" options:0 metrics:0 views:views]];
    
}


- (void)setSelected:(BOOL)selected animated:(BOOL)animated {
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}

@end
