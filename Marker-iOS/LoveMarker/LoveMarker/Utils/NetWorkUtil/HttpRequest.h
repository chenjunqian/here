//
//  HttpRequest.h
//  LoveMarker
//
//  Created by BigHead_Chen on 5/4/16.
//  Copyright © 2016 Eason. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "AFHTTPSessionManager.h"
#import "ResponseResult.h"

@interface HttpRequest : NSObject

+(void) BasicHttpRequestPOSTWithUrl:(NSString *)url andPostDictionary:(NSDictionary *)dictionnary
                     responseData:(void (^)(ResponseResult *response,NSObject *resultObject))handler;

+ (void) BasicHttpRequestGetWithUrl:(NSString*) url :(NSDictionary*) dictionnary
                 responseData:(void (^)(ResponseResult *response,NSObject *resultObject))handler;

+ (void) loginWithUsername:(NSString *)username password:(NSString *)password pushKey:(NSString *)pushKey responseData:(void (^)(ResponseResult *response,NSObject *resultObject))handler;

+(void) checkIsUserExistWithUsername:(NSString*)username responseData:(void (^)(ResponseResult* responese,NSObject* resultObject))handler;
@end
