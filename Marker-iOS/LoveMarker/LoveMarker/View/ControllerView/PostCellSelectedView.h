//
//  PostCellSelectedView.h
//  LoveMarker
//
//  Created by BigHead_Chen on 8/12/16.
//  Copyright © 2016 Eason. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "ClickableUIView.h"

@interface PostCellSelectedView : ClickableUIView
@property (strong,nonatomic) UIView* selectorContentView;
@property (strong,nonatomic) ClickableUIView* enterUserPageItem;
@property (strong,nonatomic) ClickableUIView* shareItem;
@property (strong,nonatomic) ClickableUIView* reportItem;
@end
