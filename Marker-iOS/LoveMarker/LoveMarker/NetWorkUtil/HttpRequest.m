//
//  HttpRequest.m
//  LoveMarker
//
//  Created by BigHead_Chen on 5/4/16.
//  Copyright © 2016 Eason. All rights reserved.
//

#import "HttpRequest.h"
#import "HttpConfiguration.h"
#import "HttpResponseHandler.h"

@interface HttpRequest()

@end

@implementation HttpRequest

+(void) BasicHttpRequestPOSTWithUrl:(NSString *)url andPostDictionary:(NSDictionary *)dictionnary
                 responseData:(void (^)(id ))handler{
    AFHTTPSessionManager *manager = [AFHTTPSessionManager manager];
    NSURL *realUrl = [NSURL URLWithString:url];
    [manager POST:realUrl.absoluteString parameters:dictionnary progress:^(NSProgress * _Nonnull uploadProgress) {
        
    } success:^(NSURLSessionDataTask * _Nonnull task, id  _Nullable responseObject) {
        handler(responseObject);
        NSLog(@"POST Success responseObject : %@  task : %@",responseObject,task);
    } failure:^(NSURLSessionDataTask * _Nullable task, NSError * _Nonnull error) {
        handler(error);
        NSLog(@"POST Failed %@",error);
    }];
    
}

+(void) BasicHttpRequestGetWithUrl:(NSString *)url :(NSDictionary *)dictionnary
                responseData:(void (^)(id ))handler;{
    AFHTTPSessionManager *manager = [AFHTTPSessionManager manager];
    NSURL *realUrl = [NSURL URLWithString:url];
    [manager GET:realUrl.absoluteString parameters:dictionnary progress:^(NSProgress * _Nonnull downloadProgress) {
        
    } success:^(NSURLSessionDataTask * _Nonnull task, id  _Nullable responseObject) {
        handler(responseObject);
        NSLog(@"GET Success responseObject : %@  task : %@",responseObject,task);
    } failure:^(NSURLSessionDataTask * _Nullable task, NSError * _Nonnull error) {
        handler(error);
        NSLog(@"GET Failed erro: %@  NSURLSessionDataTask %@",error , task);
    }];
}

+(void) loginWithUsername:(NSString *)username password:(NSString *)password pushKey:(NSString *)pushKey responseData:(void (^)(id ))handler{

    NSMutableDictionary *mutableDictionary = [NSMutableDictionary dictionary];
    [mutableDictionary setObject:username forKey:@"username"];
    [mutableDictionary setObject:password forKey:@"password"];
    [mutableDictionary setObject:pushKey forKey:@"pushKey"];
    
    [self BasicHttpRequestPOSTWithUrl:[HttpConfiguration getLoginUrl] andPostDictionary:mutableDictionary responseData:handler];
}

@end
