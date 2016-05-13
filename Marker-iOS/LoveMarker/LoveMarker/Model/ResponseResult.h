//
//  ResponseResult.h
//  LoveMarker
//
//  Created by BigHead_Chen on 5/12/16.
//  Copyright © 2016 Eason. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface ResponseResult : NSObject

@property (nonatomic,strong,setter=setObject:,getter=getObject) NSObject *object;
@property (nonatomic,setter=setStatus:,getter=getStatus) NSObject *status;
@property (nonatomic,strong,setter=setErrorMessage: ,getter=getErrorMessage) NSObject *errorMessage;

//-(NSObject*)getResponseObject;
//
//-(NSInteger*)getResponseStatus;
//
//-(NSObject*)getErrorMessage;
@end
