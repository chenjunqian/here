//
//  MyDataController.h
//  LoveMarker
//
//  Created by BigHead_Chen on 6/12/16.
//  Copyright © 2016 Eason. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>

@interface MyDataController : NSObject

@property (strong) NSManagedObjectContext *managedObjectContext;

- (void)initializeCoreData;
+(instancetype)getInstanc;
-(NSArray*)getUserCoreDataWithUsername:(NSString*)username;
-(void)saveOrUpdataUserCoreDataWithUsername:(NSString*)username password:(NSString*)password key:(NSString*)key;
-(NSArray*)getUserCoreDataWithDefualKey;
@end
