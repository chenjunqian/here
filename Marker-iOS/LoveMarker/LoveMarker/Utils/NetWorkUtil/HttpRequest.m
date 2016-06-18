//
//  HttpRequest.m
//  LoveMarker
//
//  Created by BigHead_Chen on 5/4/16.
//  Copyright © 2016 Eason. All rights reserved.
//

#import "HttpRequest.h"
#import "HttpConfiguration.h"
#import "User.h"
#import "ResponseResult.h"

@interface HttpRequest()

@end

@implementation HttpRequest

/*
   Basic http request method , in this project all the POST request should base on this method
 */
+(void) BasicHttpRequestPOSTWithUrl:(NSString *)url andPostDictionary:(NSDictionary *)dictionnary
                     responseData:(void (^)(ResponseResult *response,NSObject *resultObject))handler{
    
    AFHTTPSessionManager *manager = [AFHTTPSessionManager manager];
    NSURL *realUrl = [NSURL URLWithString:url];    [manager POST:realUrl.absoluteString parameters:dictionnary progress:^(NSProgress * _Nonnull uploadProgress) {
        
    } success:^(NSURLSessionDataTask * _Nonnull task, id  _Nullable responseObject) {
        ResponseResult *response = [[ResponseResult alloc] init];
        [response setObject:responseObject[@"resultData"]];
        [response setErrorMessage:responseObject[@"errorMessage"]];
        [response setStatus:[responseObject[@"status"] integerValue]];
        handler(response,[response getObject]);
    } failure:^(NSURLSessionDataTask * _Nullable task, NSError * _Nonnull error) {
        handler(nil,error);
    }];
    
}

/*
   Basic http request method , in this project all the GET request should base on this method
 */
+(void) BasicHttpRequestGetWithUrl:(NSString *)url :(NSDictionary *)dictionnary
                responseData:(void (^)(ResponseResult *response,NSObject *resultObject))handler;{
    
    AFHTTPSessionManager *manager = [AFHTTPSessionManager manager];
    NSURL *realUrl = [NSURL URLWithString:url];
    
    [manager GET:realUrl.absoluteString parameters:dictionnary progress:^(NSProgress * _Nonnull downloadProgress) {
        
    } success:^(NSURLSessionDataTask * _Nonnull task, id  _Nullable responseObject) {
        ResponseResult *response = [[ResponseResult alloc] init];
        [response setObject:responseObject[@"resultData"]];
        [response setErrorMessage:responseObject[@"errorMessage"]];
        [response setStatus:[responseObject[@"status"] integerValue]];
        handler(response,[response getObject]);
    } failure:^(NSURLSessionDataTask * _Nullable task, NSError * _Nonnull error) {
        handler(nil,error);
    }];
}

+(void) loginWithUsername:(NSString *)username password:(NSString *)password pushKey:(NSString *)pushKey responseData:(void (^)(ResponseResult *response,NSObject *resultObject))handler{

    NSMutableDictionary *mutableDictionary = [NSMutableDictionary dictionary];
    [mutableDictionary setObject:username forKey:@"username"];
    [mutableDictionary setObject:password forKey:@"password"];
    [mutableDictionary setObject:pushKey forKey:@"pushKey"];
    
    [self BasicHttpRequestPOSTWithUrl:[HttpConfiguration getLoginUrl] andPostDictionary:mutableDictionary responseData:handler];
}

+(void) checkIsUserExistWithUsername:(NSString*)username responseData:(void (^)(ResponseResult* responese,NSObject* resultObject))handler{
    NSMutableDictionary *mutableDictionary = [NSMutableDictionary dictionary];
    [mutableDictionary setObject:username forKey:@"username"];
    [self BasicHttpRequestPOSTWithUrl:[HttpConfiguration getUtlCheckUserExist] andPostDictionary:mutableDictionary responseData:handler];
}

@end
