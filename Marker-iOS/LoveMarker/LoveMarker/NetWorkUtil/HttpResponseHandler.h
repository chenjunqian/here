//
//  HttpResponseHandler.h
//  LoveMarker
//
//  Created by BigHead_Chen on 5/7/16.
//  Copyright © 2016 Eason. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "ProtocolResponseHandler.h"

@interface HttpResponseHandler : NSObject <ProtocolResponseHandler>
-(void) getResult:(void (^)(NSString *response))responseBlock;
@end
