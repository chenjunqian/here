//
//  AboutUsViewController.m
//  LoveMarker
//
//  Created by BigHead_Chen on 8/10/16.
//  Copyright © 2016 Eason. All rights reserved.
//

#import "AboutUsViewController.h"
#import "AboutUsView.h"

@interface AboutUsViewController ()
@property (strong , nonatomic) AboutUsView* aboutUsView;
@end

@implementation AboutUsViewController

@synthesize aboutUsView;

- (void)viewDidLoad {
    [super viewDidLoad];
    [self initView];
}

-(void) initView{
    aboutUsView = [[AboutUsView alloc] initViewContext:self title:NSLocalizedString(@"about_us_page_title", nil) frame:CGRectMake(0, 0, self.view.frame.size.width, self.view.frame.size.height)];
    [self.view addSubview:aboutUsView];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}


@end
