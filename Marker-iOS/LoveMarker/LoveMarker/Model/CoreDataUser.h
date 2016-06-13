//
//  CoreDataUser.h
//  LoveMarker
//
//  Created by BigHead_Chen on 6/14/16.
//  Copyright © 2016 Eason. All rights reserved.
//

#import <CoreData/CoreData.h>

@interface CoreDataUser : NSManagedObject

@property (nonatomic,strong) NSString *username , *password;

@end
