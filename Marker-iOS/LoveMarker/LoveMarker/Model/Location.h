//
//  Location.h
//  LoveMarker
//
//  Created by BigHead_Chen on 8/23/16.
//  Copyright © 2016 Eason. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface Location : NSObject

@property (strong,nonatomic) NSString* latitude;
@property (strong,nonatomic) NSString* longitude;
@property (strong,nonatomic) NSString* cityName;
@property (strong,nonatomic) NSString* cityCode;
@property (strong,nonatomic) NSString* address;

@end
