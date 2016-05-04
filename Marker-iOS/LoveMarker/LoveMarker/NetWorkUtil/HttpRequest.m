//
//  HttpRequest.m
//  LoveMarker
//
//  Created by BigHead_Chen on 5/4/16.
//  Copyright © 2016 Eason. All rights reserved.
//

#import "HttpRequest.h"

@implementation HttpRequest

-(void) BasicHttpRequestPOSTWithUrl:(NSString *)url :(NSDictionary *)dictionnary{
    AFHTTPSessionManager *manager = [AFHTTPSessionManager manager];
    [manager POST:url parameters:dictionnary progress:^(NSProgress * _Nonnull uploadProgress) {
        
    } success:^(NSURLSessionDataTask * _Nonnull task, id  _Nullable responseObject) {
        NSLog(@"POST Success %@",task);
    } failure:^(NSURLSessionDataTask * _Nullable task, NSError * _Nonnull error) {
        NSLog(@"POST Failed %@",error);
    }];
}

-(void) BasicHttpRequestGetWithUrl:(NSString *)url :(NSDictionary *)dictionnary{
    AFHTTPSessionManager *manager = [AFHTTPSessionManager manager];
    [manager GET:url parameters:dictionnary progress:nil success:^(NSURLSessionDataTask * _Nonnull task, id  _Nullable responseObject) {
        NSLog(@"POST Success %@",responseObject);
    } failure:^(NSURLSessionDataTask * _Nullable task, NSError * _Nonnull error) {
        NSLog(@"POST Failed %@",error);
    }];
}

@end
