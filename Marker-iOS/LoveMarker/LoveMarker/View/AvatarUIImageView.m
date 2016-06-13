//
//  AvatarUIImageView.m
//  LoveMarker
//
//  Created by BigHead_Chen on 5/25/16.
//  Copyright © 2016 Eason. All rights reserved.
//

#import "AvatarUIImageView.h"

@implementation AvatarUIImageView

-(id)initWithFrame:(CGRect)frame{
    
    self = [super initWithFrame:frame];
    if (self) {
        
    }
    
    return self;
}

-(void)layoutSubviews{
    self.layer.cornerRadius = self.frame.size.height/2;
    self.layer.masksToBounds = YES;
    [self setImage:[UIImage imageNamed:@"default_avatar_ori"]];
}

@end
