//
//  PostList.m
//  LoveMarker
//
//  Created by BigHead_Chen on 7/23/16.
//  Copyright © 2016 Eason. All rights reserved.
//

#import "PostList.h"
#import "Post.h"
#import "NSObject+ObjectMap.h"

@implementation PostList

@synthesize postList;

-(id)init{
    if (self) {
        self.postList = [[NSMutableArray alloc] init];
    }
    
    return self;
}

-(void)setChildPostWith:(NSDictionary*)childGroup targetPostList:(NSMutableArray*)targetList{
    NSArray* tempList = [(NSDictionary*)childGroup objectForKey:@"postList"];
    for (int i = 0; i<tempList.count; i++) {
        Post* post = [NSObject objectOfClass:@"Post" fromJSON:tempList[i]];
        [targetList addObject:post];
    }
    
}
@end
