//
//  MainMapPageUIView.m
//  LoveMarker
//
//  Created by BigHead_Chen on 8/3/16.
//  Copyright © 2016 Eason. All rights reserved.
//

#import "MainMapPageUIView.h"

@implementation MainMapPageUIView

@synthesize mapView,topLayoutView;

-(id)initViewContext:(id)context title:(NSString*)title frame:(CGRect)frame{
    self = [super initWithFrame:frame];
    
    if (self) {
        topLayoutView = [[TopLayoutView alloc] initWithoutButtom:context title:title andFrame:CGRectMake(0, 20, self.frame.size.width, 50)];
        [self addSubview:topLayoutView];
        
        mapView = [[MKMapView alloc] init];
        mapView.translatesAutoresizingMaskIntoConstraints = NO;
        [self addSubview:mapView];
    }
    
    return self;
}

-(void)layoutSubviews{
    NSDictionary* views = NSDictionaryOfVariableBindings(topLayoutView,mapView);
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:|-0-[mapView]-0-|" options:0 metrics:0 views:views]];
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:[topLayoutView]-0-[mapView]-0-|" options:0 metrics:0 views:views]];
}


@end
