//
//  AboutUsView.h
//  LoveMarker
//
//  Created by BigHead_Chen on 8/10/16.
//  Copyright © 2016 Eason. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "TopLayoutView.h"
#import "ClickableUIView.h"
#import "BasicLayoutView.h"

@interface AboutUsView : BasicLayoutView

@property (strong,nonatomic) TopLayoutView* topLayoutView;
@property (strong,nonatomic) UIView* appInfoUIView;
@property (strong,nonatomic) UIImageView* appIconImageView;
@property (strong,nonatomic) UILabel* appNameUILabel;
@property (strong,nonatomic) UILabel* versionCodeUILabel;
@property (strong,nonatomic) UILabel* appDescriptionUILabel;
@property (strong,nonatomic) ClickableUIView* shareUIView;
@property (strong,nonatomic) UILabel* shareUILabel;
@property (strong,nonatomic) ClickableUIView* feedbackUIView;
@property (strong,nonatomic) UILabel* feedbackUILabel;

-(id)initViewContext:(id)context title:(NSString*)title frame:(CGRect)frame;
@end
