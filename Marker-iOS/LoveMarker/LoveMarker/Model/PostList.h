//
//  PostList.h
//  LoveMarker
//
//  Created by BigHead_Chen on 7/23/16.
//  Copyright © 2016 Eason. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface PostList : NSObject

@property(strong,nonatomic) NSMutableArray* postList;

-(void)setChildPostWith:(NSDictionary*)childGroup targetPostList:(NSMutableArray*)targetList;
@end
