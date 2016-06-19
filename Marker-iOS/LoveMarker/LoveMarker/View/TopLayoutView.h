//
//  TopLayoutView.h
//  LoveMarker
//
//  Created by BigHead_Chen on 5/14/16.
//  Copyright © 2016 Eason. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface TopLayoutView : UINavigationBar

-(id)initWithContext:(id)context title:(NSString*)title andFrame:(CGRect)frame;

@property(strong,nonatomic) UINavigationItem* navigationItem;
@property(strong,nonatomic) UIBarButtonItem* leftButton;
@property(strong,nonatomic) UIBarButtonItem* rightButton;

@end
