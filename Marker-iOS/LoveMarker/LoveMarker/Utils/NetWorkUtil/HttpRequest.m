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
#import "AFImageDownloader.h"
#import "UIImageView+AFNetworking.h"

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

+(void)uploadTaskForMultiPartPOSTRequestWithUrl:(NSString*)urlString parameters:(NSDictionary*)parameters fileURLWithPath:(NSString*)fileURLWithPath fileName:(NSString*)filename handler:(HttpResponseHandler)handler{
    
    NSMutableURLRequest *request = [[AFHTTPRequestSerializer serializer] multipartFormRequestWithMethod:@"POST" URLString:urlString parameters:parameters constructingBodyWithBlock:^(id<AFMultipartFormData> formData) {
        [formData appendPartWithFileURL:[NSURL fileURLWithPath:fileURLWithPath] name:@"file" fileName:filename mimeType:@"image/jpeg" error:nil];
    } error:nil];
    
    AFURLSessionManager *manager = [[AFURLSessionManager alloc] initWithSessionConfiguration:[NSURLSessionConfiguration defaultSessionConfiguration]];
    
    NSURLSessionUploadTask *uploadTask = [manager uploadTaskWithStreamedRequest:request
                  progress:^(NSProgress * _Nonnull uploadProgress) {
                      // This is not called back on the main queue.
                      // You are responsible for dispatching to the main queue for UI updates
                      dispatch_async(dispatch_get_main_queue(), ^{
                          //Update the progress view
//                          [progressView setProgress:uploadProgress.fractionCompleted];
                      });
                  }
                  completionHandler:^(NSURLResponse * _Nonnull response, id  _Nullable responseObject, NSError * _Nullable error) {
                      if (error) {
                          NSLog(@"Error: %@", error);
                      } else {
                          ResponseResult *responseResult = [[ResponseResult alloc] init];
                          [responseResult setObject:responseObject[@"resultData"]];
                          [responseResult setErrorMessage:responseObject[@"errorMessage"]];
                          [responseResult setStatus:[responseObject[@"status"] integerValue]];
                          handler(responseResult,[responseResult getObject]);
                          NSLog(@"%@ %@", response, responseObject);
                      }
                  }];
    
    [uploadTask resume];
}

+(void)uploadTaskForMultiPartPOSTRequestWithUrl:(NSString*)urlString parameters:(NSDictionary*)parameters fileURL:(NSURL*)fileURL fileName:(NSString*)fielName handler:(HttpResponseHandler)handler{
    
    NSMutableURLRequest *request = [[AFHTTPRequestSerializer serializer] multipartFormRequestWithMethod:@"POST" URLString:urlString parameters:parameters constructingBodyWithBlock:^(id<AFMultipartFormData> formData) {
        [formData appendPartWithFileURL:fileURL name:@"file" fileName:fielName mimeType:@"image/jpeg" error:nil];
    } error:nil];
    
    AFURLSessionManager *manager = [[AFURLSessionManager alloc] initWithSessionConfiguration:[NSURLSessionConfiguration defaultSessionConfiguration]];
    
    NSURLSessionUploadTask *uploadTask = [manager uploadTaskWithStreamedRequest:request
                progress:^(NSProgress * _Nonnull uploadProgress) {
                    // This is not called back on the main queue.
                    // You are responsible for dispatching to the main queue for UI updates
                    dispatch_async(dispatch_get_main_queue(), ^{
                        //Update the progress view
                        //                          [progressView setProgress:uploadProgress.fractionCompleted];
                        });
                    }
                completionHandler:^(NSURLResponse * _Nonnull response, id  _Nullable responseObject, NSError * _Nullable error) {
                    if (error) {
                        NSLog(@"Error: %@", error);
                    } else {
                        ResponseResult *responseResult = [[ResponseResult alloc] init];
                        [responseResult setObject:responseObject[@"resultData"]];
                        [responseResult setErrorMessage:responseObject[@"errorMessage"]];
                        [responseResult setStatus:[responseObject[@"status"] integerValue]];
                        handler(responseResult,[responseResult getObject]);
                        NSLog(@"upload task response : %@ %@", response, responseObject);
                        }
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

+(void)downloadAvatarWithUrl:(NSString*)url UIImageView:(UIImageView*)imageView{
    NSString* realUrl = [[HttpConfiguration getUrlUrlMedia] stringByAppendingString:url];
    NSURL* imageURL = [NSURL URLWithString:realUrl];
    [imageView setImageWithURL:imageURL];
}

+(void)uploadAvatarWithUsername:(NSString*)username fileURL:(NSURL*)fileURL handler:(HttpResponseHandler)handler{
    NSMutableDictionary *mutableDictionary = [NSMutableDictionary dictionary];
    [mutableDictionary setObject:username forKey:@"username"];
    [self uploadTaskForMultiPartPOSTRequestWithUrl:[HttpConfiguration getUrlUploadAvatar] parameters:mutableDictionary fileURL:fileURL fileName:[fileURL lastPathComponent] handler:handler];
}

+(void)uploadAvatarWithUsername:(NSString*)username filePath:(NSString*)filePath handler:(HttpResponseHandler)handler{
    NSMutableDictionary *mutableDictionary = [NSMutableDictionary dictionary];
    [mutableDictionary setObject:username forKey:@"username"];
    [self uploadAvatarWithUsername:username filePath:filePath handler:handler];
}

+(void)getUserInfoByUsername:(NSString*)username handler:(HttpResponseHandler)handler{
    NSMutableDictionary *mutableDictionary = [NSMutableDictionary dictionary];
    [mutableDictionary setObject:username forKey:@"username"];
    [self BasicHttpRequestPOSTWithUrl:[HttpConfiguration getUrlGetUserInfoByUsername] andPostDictionary:mutableDictionary responseData:handler];
}

+(void)changeUserInfoByUser:(User*)user handler:(HttpResponseHandler)handler{
    NSMutableDictionary *mutableDictionary = [NSMutableDictionary dictionary];
    [mutableDictionary setObject:user.username forKey:@"username"];
    [mutableDictionary setObject:user.password forKey:@"password"];
    [mutableDictionary setObject:user.gender forKey:@"gender"];
    [mutableDictionary setObject:user.birthday forKey:@"birthday"];
    [mutableDictionary setObject:user.nickname forKey:@"nickname"];
    [mutableDictionary setObject:user.userid forKey:@"userid"];
    [mutableDictionary setObject:user.longProfile forKey:@"longProfile"];
    [mutableDictionary setObject:user.simpleProfile forKey:@"simpleProfile"];
    [self BasicHttpRequestPOSTWithUrl:[HttpConfiguration getUrlModifyUserInfo] andPostDictionary:mutableDictionary responseData:handler];
}

+(void)getCurrentPostByNumberOfPost:(NSInteger)index handler:(HttpResponseHandler)handler{
    NSMutableDictionary *mutableDictionary = [NSMutableDictionary dictionary];
    [mutableDictionary setObject:[NSString stringWithFormat:@"%ld",(long)index] forKey:@"index"];
    [self BasicHttpRequestPOSTWithUrl:[HttpConfiguration getUrlUrlGetCurrentPostOnly] andPostDictionary:mutableDictionary responseData:handler];
}

+(void)getLastHourPostByNumberOfPost:(NSInteger)index handler:(HttpResponseHandler)handler{
    NSMutableDictionary *mutableDictionary = [NSMutableDictionary dictionary];
    [mutableDictionary setObject:[NSString stringWithFormat:@"%ld",(long)index] forKey:@"index"];
    [mutableDictionary setObject:[NSString stringWithFormat:@"%ld",(long)[[NSDate date] timeIntervalSince1970]*1000] forKey:@"time"];
    [self BasicHttpRequestPOSTWithUrl:[HttpConfiguration getUrlUrlGetCurrentOneHourPost] andPostDictionary:mutableDictionary responseData:handler];
}
@end
