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

typedef void (^HttpResponseHandler)(ResponseResult *response,NSObject *resultObject);

@interface HttpRequest : NSObject

+(void) BasicHttpRequestPOSTWithUrl:(NSString *)url andPostDictionary:(NSDictionary *)dictionnary
                     responseData:(HttpResponseHandler)handler;

+ (void) BasicHttpRequestGetWithUrl:(NSString*) url :(NSDictionary*) dictionnary
                 responseData:(HttpResponseHandler)handler;

+ (void) loginWithUsername:(NSString *)username password:(NSString *)password pushKey:(NSString *)pushKey responseData:(HttpResponseHandler)handler;

+(void) checkIsUserExistWithUsername:(NSString*)username responseData:(HttpResponseHandler)handler;

+(void) registerWithUsername:(NSString*)username password:(NSString*)password pushKey:(NSString*)pushKey nickname:(NSString*)nickname gender:(NSString*)gender birthday:(NSString*)birthday responseData:(HttpResponseHandler)handler;
@end
