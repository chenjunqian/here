//
//  RegisterViewController.h
//  LoveMarker
//
//  Created by BigHead_Chen on 6/18/16.
//  Copyright © 2016 Eason. All rights reserved.
//

#import "BasicViewController.h"
#import "User.h"

@interface RegisterViewController : BasicViewController
+(instancetype)getInstance;

@property (strong,nonatomic) User* registerTempUser;
@end
