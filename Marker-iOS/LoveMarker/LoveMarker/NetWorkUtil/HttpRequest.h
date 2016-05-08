//
//  HttpRequest.h
//  LoveMarker
//
//  Created by BigHead_Chen on 5/4/16.
//  Copyright © 2016 Eason. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "AFHTTPSessionManager.h"
#import "HttpResponseHandler.h"

@interface HttpRequest : NSObject

+ (void) BasicHttpRequestPOSTWithUrl:(NSString *)url andPostDictionary:(NSDictionary *)dictionnary
                  andResponseHandler:(HttpResponseHandler *) handler;
+ (void) BasicHttpRequestGetWithUrl:(NSString*) url :(NSDictionary*) dictionnary
                 andResponseHandler:(HttpResponseHandler *) handler;
+ (void) loginWithUsername:(NSString *)username password:(NSString *)password pushKey:(NSString *)pushKey responseHandler:(HttpResponseHandler *) handler;
@end
