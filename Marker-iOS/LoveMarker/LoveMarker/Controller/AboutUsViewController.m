//
//  AboutUsViewController.m
//  LoveMarker
//
//  Created by BigHead_Chen on 8/10/16.
//  Copyright © 2016 Eason. All rights reserved.
//

#import "AboutUsViewController.h"
#import "AboutUsView.h"
#import <Social/Social.h>

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
    
    [aboutUsView.shareUIView whenSingleClick:^{
        //创建SLComposeViewController对象，并定义为SLServiceTypeSinaWeibo，即是要实现新浪微博的分享
        SLComposeViewController *composeViewController = [SLComposeViewController composeViewControllerForServiceType:SLServiceTypeSinaWeibo];
        //这里可以添加“if ([SLComposeViewController isAvailableForServiceType:SLServiceTypeSinaWeibo])”判断你的苹果设备是否有绑定微博账号
        //以下方法用来查看用户点击的是哪个按钮，以及点击按钮后该执行的方法
        SLComposeViewControllerCompletionHandler completionHandle = ^(SLComposeViewControllerResult result)
        {
            [composeViewController dismissViewControllerAnimated:YES completion:nil];
            
            switch (result) {
                    //如果点击的是取消的按钮
                case SLComposeViewControllerResultCancelled:
                default:
                    NSLog(@"cancelled...");
                    break;
                    //如果点击的是确定的按钮
                case SLComposeViewControllerResultDone:
                    NSLog(@"posted...");
                    break;
            }
        };
        //定义图片和文字
        [composeViewController addImage:[UIImage imageNamed:@"wwk.png"]];
        [composeViewController setInitialText:@"大家好我叫王伟楷"];
        //将上面的block加进来
        [composeViewController setCompletionHandler:completionHandle];
        //模态视图呈现，如果是ipad则要popover视图呈现
        [self presentViewController:composeViewController animated:YES completion:nil];
    }];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}


@end
