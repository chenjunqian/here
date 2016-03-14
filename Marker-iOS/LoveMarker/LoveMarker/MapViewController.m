//
//  MapViewController.m
//  LoveMarker
//
//  Created by BigHead_Chen on 3/13/16.
//  Copyright © 2016 Eason. All rights reserved.
//

#import "MapViewController.h"

@implementation MapViewController


-(void)viewDidLoad{
    [super viewDidLoad];
    UILabel *label = [[UILabel alloc] initWithFrame:CGRectMake(0, 0, 200, 100)];
    label.text = @"this is map view";
    
    [self.view addSubview:label];
}

@end
