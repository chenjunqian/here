//
//  LocationStatus.h
//  LoveMarker
//
//  Created by BigHead_Chen on 8/25/16.
//  Copyright © 2016 Eason. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "Location.h"

@interface LocationStatus : NSObject

@property (strong,nonatomic) Location* location;

+(id)getInstance;
-(NSString*)getCityName;
-(NSString*)getCityCode;
-(NSString*)getAddress;

@end
