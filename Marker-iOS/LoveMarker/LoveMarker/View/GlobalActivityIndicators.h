//
//  GlobalActivityIndicators.h
//  LoveMarker
//
//  Created by BigHead_Chen on 7/5/16.
//  Copyright © 2016 Eason. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface GlobalActivityIndicators : UIView

-(id)initWithTitle:(NSString*)title frame:(CGRect)frame;
-(void)setTitle:(NSString*)title;

@property (strong,nonatomic) UIView* containnerView;
@property (strong,nonatomic) UILabel* indicatorLabel;
@property (strong,nonatomic) UIActivityIndicatorView* activityIndicatorView;

@end
