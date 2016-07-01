//
//  ClickableUIView.h
//  LoveMarker
//
//  Created by BigHead_Chen on 6/27/16.
//  Copyright © 2016 Eason. All rights reserved.
//

#import <UIKit/UIKit.h>

typedef void (^WhenClickBlock) ();

@interface ClickableUIView : UIView

-(void)whenSingleClick:(WhenClickBlock)block;

@end
