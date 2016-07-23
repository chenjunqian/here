//
//  Post.m
//  LoveMarker
//
//  Created by BigHead_Chen on 7/23/16.
//  Copyright © 2016 Eason. All rights reserved.
//

#import "Post.h"
#import "CommomUtils.h"

@implementation Post

@synthesize longtitude,lattitude,city,cityCode,address,username,like ,postId,tag,time;

-(NSString*)getTimeWithTimestamp:(NSString *)timestamp{
    NSDateFormatter* dateFormater = [[NSDateFormatter alloc] init];
    double currentTime = [[NSDate date] timeIntervalSince1970]*1000;
    double tempTimstamp = [timestamp doubleValue];
    double difference = currentTime-tempTimstamp;
    
    if (difference >= 1000*60*60*24*365.00) {
        [dateFormater setDateFormat:@"yyyy-MM-dd"];
    }else if(difference >= 1000*60*60*24.00){
        [dateFormater setDateFormat:@"MM-dd  hh:mm"];
    }else {
        [dateFormater setDateFormat:@"hh:mm"];
    }
    
    NSDate* date = [CommomUtils timestampToDate:timestamp];
    return [[dateFormater stringFromDate:date] description];
}

@end
