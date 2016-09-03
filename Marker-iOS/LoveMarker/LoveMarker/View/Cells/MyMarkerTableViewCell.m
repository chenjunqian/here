//
//  MyMarkerTableViewCell.m
//  LoveMarker
//
//  Created by BigHead_Chen on 5/22/16.
//  Copyright © 2016 Eason. All rights reserved.
//

#import "MyMarkerTableViewCell.h"
#import "ColorUtil.h"

@interface MyMarkerTableViewCell()
@property (nonatomic,strong) UIImageView *heartImageView;
@property (nonatomic,strong) UIImageView *locationImageView;
@property (nonatomic,strong) UIView *bottomLine;;
@end

@implementation MyMarkerTableViewCell

-(id)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier{
    self = [super initWithStyle:style reuseIdentifier:reuseIdentifier];
    if (self) {
        [self setBackgroundColor:[UIColor whiteColor]];
        
        _heartImageView = [[UIImageView alloc]init];
        _heartImageView.translatesAutoresizingMaskIntoConstraints = NO;
        [_heartImageView setImage:[UIImage imageNamed:@"marker_ic"]];
        [self addSubview:_heartImageView];
        
        _markerContentLabel = [[UILabel alloc] init];
        _markerContentLabel.translatesAutoresizingMaskIntoConstraints = NO;
        [_markerContentLabel setTextColor:[UIColor blackColor]];
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
        
        _deleteButton = [[UIButton alloc] init];
        _deleteButton.translatesAutoresizingMaskIntoConstraints = NO;
        [_deleteButton setImage:[UIImage imageNamed:@"ic_delete_black_18dp"] forState:UIControlStateNormal];
        [self addSubview:_deleteButton];
        
        _bottomLine = [[UIView alloc] init];
        _bottomLine.translatesAutoresizingMaskIntoConstraints = NO;
        _bottomLine.backgroundColor = [ColorUtil viewBackgroundGrey];
        [self addSubview:_bottomLine];
    }
    
    return self;

}

-(void)layoutSubviews{
    NSDictionary *views = NSDictionaryOfVariableBindings(_heartImageView,_markerContentLabel,_timeLabel,_locationLabel,_locationImageView,_deleteButton,_bottomLine);
    
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:|-20-[_heartImageView(18)]" options:0 metrics:0 views:views]];
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:|-50-[_heartImageView(18)]" options:0 metrics:0 views:views]];
    
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:|-20-[_markerContentLabel]" options:0 metrics:0 views:views]];
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:[_heartImageView]-5-[_markerContentLabel]" options:0 metrics:0 views:views]];
    
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:[_heartImageView]-15-[_timeLabel]" options:0 metrics:0 views:views]];
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:|-50-[_timeLabel]" options:0 metrics:0 views:views]];
    
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:[_timeLabel]-5-[_locationImageView(22)]" options:0 metrics:0 views:views]];
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:|-50-[_locationImageView(18)]" options:0 metrics:0 views:views]];
    
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:[_timeLabel]-5-[_locationLabel]" options:0 metrics:0 views:views]];
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:[_locationImageView]-5-[_locationLabel]-10-|" options:0 metrics:0 views:views]];
    
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:|-50-[_bottomLine(>=200)]-0-|" options:0 metrics:0 views:views]];
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:[_locationImageView]-10-[_bottomLine(1)]-0-|" options:0 metrics:0 views:views]];
    
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:|-20-[_deleteButton]" options:0 metrics:0 views:views]];
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:[_deleteButton]-10-|" options:0 metrics:0 views:views]];
    
}

-(NSInteger)getCellHight{
    return self.frame.size.height;
}

@end
