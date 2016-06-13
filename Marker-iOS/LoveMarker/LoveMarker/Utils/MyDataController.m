//
//  MyDataController.m
//  LoveMarker
//
//  Created by BigHead_Chen on 6/12/16.
//  Copyright © 2016 Eason. All rights reserved.
//

#import "MyDataController.h"
#import "CoreDataUser.h"
#import <CoreData/CoreData.h>

@implementation MyDataController

__strong static id instacne = nil;

+(instancetype)getInstanc{
    if (!instacne) {
        instacne = [self init];
    }
    return instacne;
}

-(id)init{
    self = [super init];
    if (!self) {
        return nil;
    }

    [self initializeCoreData];
    
    return self;
}

-(void)initializeCoreData{
    NSURL *modelURL = [[NSBundle mainBundle] URLForResource:@"CoreDataModel" withExtension:@"momd"];
    NSManagedObjectModel *mom = [[NSManagedObjectModel alloc] initWithContentsOfURL:modelURL];
    NSAssert(mom != nil, @"Error initializing Managed Object Model");
    
    NSPersistentStoreCoordinator *psc = [[NSPersistentStoreCoordinator alloc] initWithManagedObjectModel:mom];
    NSManagedObjectContext *moc = [[NSManagedObjectContext alloc] initWithConcurrencyType:NSMainQueueConcurrencyType];
    [moc setPersistentStoreCoordinator:psc];
    [self setManagedObjectContext:moc];
    
    NSFileManager *fileManage = [NSFileManager defaultManager];
    NSURL *documentURL = [[fileManage URLsForDirectory:NSDocumentDirectory  inDomains:NSUserDomainMask] lastObject];
    NSURL *storeURL = [documentURL URLByAppendingPathComponent:@"CoreDataModel.sqlite"];
    
    dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0), ^{
        NSError *error = nil;
        NSPersistentStoreCoordinator *psc = [[self managedObjectContext] persistentStoreCoordinator];
        NSPersistentStore *store = [psc addPersistentStoreWithType:NSSQLiteStoreType configuration:nil URL:storeURL options:nil error:&error];
        
        NSAssert(store != nil, @"Error initializing PSC: %@\n%@", [error localizedDescription], [error userInfo]);
    });
}

-(NSArray*)getUserCoreDataWithUsername:(NSString*)username{
    NSFetchRequest *request = [NSFetchRequest fetchRequestWithEntityName:@"CoreDataUser"];
    [request setPredicate:[NSPredicate predicateWithFormat:@"username == %@",username]];
    NSError *error = nil;\
    NSArray *results = [_managedObjectContext executeFetchRequest:request error:&error];
    if (!results) {
        NSAssert(NO, @"Error fetch Core Data CoreDataUser context: %@\n%@", [error localizedDescription], [error userInfo]);
    }
    
    return results;
}

-(void)saveOrUpdataUserCoreDataWithUsername:(NSString*)username password:(NSString*)password{
    NSFetchRequest *request = [[NSFetchRequest alloc] init];
    [request setEntity:[NSEntityDescription entityForName:@"CoreDataUser" inManagedObjectContext:_managedObjectContext]];
    [request setPredicate:[NSPredicate predicateWithFormat:@"username == %@",username]];
    NSError *error = nil;
    NSArray *results = [_managedObjectContext executeFetchRequest:request error:&error];
    
    if (results.count>0) {
        CoreDataUser *user = [results objectAtIndex:0];
        [user setUsername:username];
        [user setPassword:password];
    }else{
        CoreDataUser *newUser = [NSEntityDescription insertNewObjectForEntityForName:@"CoreDataUser" inManagedObjectContext:_managedObjectContext];
        [newUser setUsername:username];
        [newUser setPassword:password];
    }
    
    if ([[self managedObjectContext] save:&error] == NO) {
        NSAssert(NO, @"Error saving CoreDataUser context: %@\n%@", [error localizedDescription], [error userInfo]);
    }

}

@end
