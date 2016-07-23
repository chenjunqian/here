//
//  Post.h
//  LoveMarker
//
//  Created by BigHead_Chen on 7/23/16.
//  Copyright © 2016 Eason. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface Post : NSObject

@property (nonatomic) double longtitude;
@property (nonatomic) double lattitude;
@property (strong,nonatomic) NSString* city;
@property (strong,nonatomic) NSString* cityCode;
@property (strong,nonatomic) NSString* address ;
@property (strong,nonatomic) NSString* username ;
@property (strong,nonatomic) NSString* like;
@property (strong,nonatomic) NSString* postId ;
@property (strong,nonatomic) NSString* tag ;
@property (strong,nonatomic) NSString* time;

-(NSString*)getTimeWithTimestamp:(NSString *)timestamp;
@end
