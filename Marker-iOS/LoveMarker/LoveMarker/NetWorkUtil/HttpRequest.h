//
//  HttpRequest.h
//  LoveMarker
//
//  Created by BigHead_Chen on 5/4/16.
//  Copyright © 2016 Eason. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "AFHTTPSessionManager.h"

@interface HttpRequest : NSObject

- (void) BasicHttpRequestPOSTWithUrl:(NSString*) url :(NSDictionary*) dictionnary;
- (void) BasicHttpRequestGetWithUrl:(NSString*) url :(NSDictionary*) dictionnary;
@end
