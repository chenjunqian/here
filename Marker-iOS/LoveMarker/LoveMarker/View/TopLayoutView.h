//
//  TopLayoutView.h
//  LoveMarker
//
//  Created by BigHead_Chen on 5/14/16.
//  Copyright © 2016 Eason. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface TopLayoutView : UIView

@property (nonatomic,strong,getter=getBackBtn) UIButton *backButton;
@property (nonatomic,strong,getter=getTitleLabel) UILabel *titleLabel;

@end
