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
#import "User.h"

typedef void (^HttpResponseHandler)(ResponseResult *response,NSObject *resultObject);

typedef void (^HttpDownloadHandler)(NSURLResponse *response,NSString* filePath,NSError* error);

typedef void (^HttpAvatarDownloadHandler)(UIImage *image);

@interface HttpRequest : NSObject

/************basic POST or GET function************/
+(void) BasicHttpRequestPOSTWithUrl:(NSString *)url andPostDictionary:(NSDictionary *)dictionnary
                     responseData:(HttpResponseHandler)handler;

+ (void) BasicHttpRequestGetWithUrl:(NSString*) url :(NSDictionary*) dictionnary
                 responseData:(HttpResponseHandler)handler;
/************basic POST or GET function************/

/************upload and download function************/
+(void) upLoadTaskWithUrl:(NSString*)url filePath:(NSString*)filePath handler:(HttpResponseHandler)handler;

+(void)uploadTaskForMultiPartPOSTRequestWithUrl:(NSString*)urlString parameters:(NSDictionary*)parameters fileURLWithPath:(NSString*)fileURLWithPath fileName:(NSString*)filename handler:(HttpResponseHandler)handler;

+(void)uploadTaskForMultiPartPOSTRequestWithUrl:(NSString*)urlString parameters:(NSDictionary*)parameters fileURL:(NSURL*)fileURL fileName:(NSString*)fielName handler:(HttpResponseHandler)handler;

+(void) downloadTaskWithURL:(NSString*)url handler:(HttpDownloadHandler)handler;
/************upload and download function************/



+ (void) loginWithUsername:(NSString *)username password:(NSString *)password pushKey:(NSString *)pushKey responseData:(HttpResponseHandler)handler;

+(void) checkIsUserExistWithUsername:(NSString*)username responseData:(HttpResponseHandler)handler;

+(void) registerWithUsername:(NSString*)username password:(NSString*)password pushKey:(NSString*)pushKey nickname:(NSString*)nickname gender:(NSString*)gender birthday:(NSString*)birthday responseData:(HttpResponseHandler)handler;

+(void) getPosyByUsername:(NSString*)username responseData:(HttpResponseHandler)handler;

+(void)downloadAvatarWithUrl:(NSString*)url UIImageView:(UIImageView*)imageView;

+(void)uploadAvatarWithUsername:(NSString*)username fileURL:(NSURL*)fileURL handler:(HttpResponseHandler)handler;

+(void)uploadAvatarWithUsername:(NSString*)username filePath:(NSString*)filePath handler:(HttpResponseHandler)handler;

+(void)getUserInfoByUsername:(NSString*)username handler:(HttpResponseHandler)handler;

+(void)changeUserInfoByUser:(User*)user handler:(HttpResponseHandler)handler;

+(void)getCurrentPostByNumberOfPost:(NSInteger)index handler:(HttpResponseHandler)handler;

+(void)getLastHourPostByNumberOfPost:(NSInteger)index handler:(HttpResponseHandler)handler;

+(void)getPostTag:(HttpResponseHandler)handler;
@end
