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
#import "HttpConfiguration.h"

@interface HttpRequest()

@end

@implementation HttpRequest

/*
   Basic http request method , in this project all the POST request should base on this method
 */
+(void) BasicHttpRequestPOSTWithUrl:(NSString *)url andPostDictionary:(NSDictionary *)dictionnary
                     responseData:(HttpResponseHandler)handler{
    
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
                responseData:(HttpResponseHandler)handler{
    
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

+(void) upLoadTaskWithUrl:(NSString*)url filePath:(NSString*)filePath handler:(HttpResponseHandler)handler{
    NSURLSessionConfiguration *configuration = [NSURLSessionConfiguration defaultSessionConfiguration];
    AFURLSessionManager *manager = [[AFURLSessionManager alloc] initWithSessionConfiguration:configuration];
    
    NSURL* URL = [NSURL URLWithString:url];
    NSURLRequest* request = [NSURLRequest requestWithURL:URL];
    
    NSURL* realFilePath = [NSURL fileURLWithPath:filePath];
    NSURLSessionUploadTask* uploadTask = [manager uploadTaskWithRequest:request fromFile:realFilePath progress:^(NSProgress * _Nonnull uploadProgress) {
        
    } completionHandler:^(NSURLResponse * _Nonnull response, id  _Nullable responseObject, NSError * _Nullable error) {
        ResponseResult *responseResult = [[ResponseResult alloc] init];
        [responseResult setObject:responseObject[@"resultData"]];
        [responseResult setErrorMessage:responseObject[@"errorMessage"]];
        [responseResult setStatus:[responseObject[@"status"] integerValue]];
        handler(responseResult,[responseResult getObject]);
    }];
    
    [uploadTask resume];
}

+(void) downloadTaskWithURL:(NSString*)url handler:(HttpDownloadHandler)handler{
    NSURLSessionConfiguration* configuration = [NSURLSessionConfiguration defaultSessionConfiguration];
    AFURLSessionManager* manager = [[AFURLSessionManager alloc] initWithSessionConfiguration:configuration];
    
    NSURL* URL = [NSURL URLWithString:url];
    NSURLRequest* request = [NSURLRequest requestWithURL:URL];
    
    NSURLSessionDownloadTask *downloadTask = [manager downloadTaskWithRequest:request
      progress:nil
                                              
      destination:^NSURL *(NSURL *targetPath, NSURLResponse *response) {
          
        NSURL *documentsDirectoryURL = [[NSFileManager defaultManager] URLForDirectory:NSDocumentDirectory inDomain:NSUserDomainMask appropriateForURL:nil create:NO error:nil];
        return [documentsDirectoryURL URLByAppendingPathComponent:[response suggestedFilename]];
          
    } completionHandler:^(NSURLResponse *response, NSURL *filePath, NSError *error) {
        NSLog(@"File downloaded to: %@", filePath);
        handler(response,[filePath path],error);
    }];
    
    [downloadTask resume];
}

+(void) loginWithUsername:(NSString *)username password:(NSString *)password pushKey:(NSString *)pushKey responseData:(HttpResponseHandler)handler{

    NSMutableDictionary *mutableDictionary = [NSMutableDictionary dictionary];
    [mutableDictionary setObject:username forKey:@"username"];
    [mutableDictionary setObject:password forKey:@"password"];
    [mutableDictionary setObject:pushKey forKey:@"pushKey"];
    
    [self BasicHttpRequestPOSTWithUrl:[HttpConfiguration getLoginUrl] andPostDictionary:mutableDictionary responseData:handler];
}

+(void) checkIsUserExistWithUsername:(NSString*)username responseData:(HttpResponseHandler)handler{
    NSMutableDictionary *mutableDictionary = [NSMutableDictionary dictionary];
    [mutableDictionary setObject:username forKey:@"username"];
    [self BasicHttpRequestPOSTWithUrl:[HttpConfiguration getUtlCheckUserExist] andPostDictionary:mutableDictionary responseData:handler];
}

+(void) registerWithUsername:(NSString*)username password:(NSString*)password pushKey:(NSString*)pushKey nickname:(NSString*)nickname gender:(NSString*)gender birthday:(NSString*)birthday responseData:(HttpResponseHandler)handler{
    
    NSMutableDictionary *mutableDictionary = [NSMutableDictionary dictionary];
    [mutableDictionary setObject:username forKey:@"username"];
    [mutableDictionary setObject:password forKey:@"password"];
    [mutableDictionary setObject:pushKey forKey:@"pushKey"];
    [mutableDictionary setObject:nickname forKey:@"nickname"];
    [mutableDictionary setObject:gender forKey:@"gender"];
    [mutableDictionary setObject:birthday forKey:@"birthday"];
    
    [self BasicHttpRequestPOSTWithUrl:[HttpConfiguration getRegisterUrl] andPostDictionary:mutableDictionary responseData:handler];
}

+(void) getPosyByUsername:(NSString*)username responseData:(HttpResponseHandler)handler{
    NSMutableDictionary *mutableDictionary = [NSMutableDictionary dictionary];
    [mutableDictionary setObject:username forKey:@"username"];
    
    [self BasicHttpRequestPOSTWithUrl:[HttpConfiguration getUrlUrlGetPostByUsername] andPostDictionary:mutableDictionary responseData:handler];
}

+(void)downloadAvatarWithUrl:(NSString*)url handler:(HttpDownloadHandler)handler{
    NSString* realUrl = [[HttpConfiguration getUrlUrlMedia] stringByAppendingString:url];
    [self downloadTaskWithURL:realUrl handler:handler];
}

@end
