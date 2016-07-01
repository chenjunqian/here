//
//  ClickableUIView.m
//  LoveMarker
//
//  Created by BigHead_Chen on 6/27/16.
//  Copyright © 2016 Eason. All rights reserved.
//

#import "ClickableUIView.h"
#import <objc/runtime.h>

@interface ClickableUIView()


@end

@implementation ClickableUIView

static char WhenViewSingleClick;
/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect {
    // Drawing code
}
*/

-(void)runBlockForKey:(void*)blockKey{
    WhenClickBlock block = objc_getAssociatedObject(self, blockKey);
    if (block) block();
}

-(void)setBlock:block forKey:(void*)blockKey{
    self.userInteractionEnabled = YES;
    objc_setAssociatedObject(self, blockKey, block, OBJC_ASSOCIATION_COPY_NONATOMIC);
}

-(void)whenSingleClick:(WhenClickBlock)block{
    UITapGestureRecognizer* tapGesture = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(viewWasClick)];
    [self addGestureRecognizer:tapGesture];
    [self setBlock:block forKey:&WhenViewSingleClick];
}

-(void)viewWasClick{
    [self runBlockForKey:&WhenViewSingleClick];
}

@end
