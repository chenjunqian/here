//
//  LocationStatus.m
//  LoveMarker
//
//  Created by BigHead_Chen on 8/25/16.
//  Copyright © 2016 Eason. All rights reserved.
//

#import "LocationStatus.h"

@implementation LocationStatus

static id instance;

-(id)init{
    self = [super init];
    
    if (self) {
        instance = self;
    }
    
    return self;
}

+(id)getInstance{
    if (!instance){
        instance = [self init];
    }
    return instance;
}

-(NSString*)getCityName{
    return @"";
}

-(NSString*)getCityCode{
    return @"";
}

-(NSString*)getAddress{
    return @"";
}

@end
