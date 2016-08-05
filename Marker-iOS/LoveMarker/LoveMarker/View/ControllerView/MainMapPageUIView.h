//
//  MainMapPageUIView.h
//  LoveMarker
//
//  Created by BigHead_Chen on 8/3/16.
//  Copyright © 2016 Eason. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <MapKit/MapKit.h>
#import "TopLayoutView.h"

@interface MainMapPageUIView : UIView
@property (strong,nonatomic) MKMapView* mapView;
@property (strong,nonatomic) TopLayoutView* topLayoutView;
-(id)initViewContext:(id)context title:(NSString*)title frame:(CGRect)frame;
@end
