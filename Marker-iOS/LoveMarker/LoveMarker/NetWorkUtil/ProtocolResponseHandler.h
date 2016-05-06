//
//  HttpResponseHandler.h
//  LoveMarker
//
//  Created by BigHead_Chen on 5/7/16.
//  Copyright © 2016 Eason. All rights reserved.
//

#import <Foundation/Foundation.h>

@protocol ProtocolResponseHandler <NSObject>

@required
-(void) responseWithString:(NSString *)responseString andObject:(NSObject *) object;

@end