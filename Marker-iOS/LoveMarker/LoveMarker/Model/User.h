//
//  User.h
//  LoveMarker
//
//  Created by BigHead_Chen on 5/11/16.
//  Copyright © 2016 Eason. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface User : NSObject

@property (nonatomic,strong) NSString *username , *password , *gender ,
                                      *avatar,* birthday , *userid ,
                                      *pushKey , *simpleProfile,*longProfile ,
                                      *avatarThumb;

@end
