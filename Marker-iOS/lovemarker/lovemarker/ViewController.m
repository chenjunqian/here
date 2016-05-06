//
//  ViewController.m
//  LoveMarker
//
//  Created by BigHead_Chen on 4/4/16.
//  Copyright © 2016 Eason. All rights reserved.
//

#import "ViewController.h"
#import "MainMapUIControllerViewController.h"
#import "HttpRequest.h"
#import "HttpConfiguration.h"

@interface ViewController ()

@end

@implementation ViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view, typically from a nib.
    UIImageView *splashImageView = [[UIImageView alloc] initWithFrame:CGRectMake(0, 0, self.view.frame.size.width, self.view.frame.size.height)];
    [splashImageView setImage:[UIImage imageNamed:@"splash_background"]];
    
    [self.view addSubview:splashImageView];
    
    NSTimer *timer = [NSTimer scheduledTimerWithTimeInterval:2.0 target:self selector:@selector(onTick:) userInfo:nil repeats:NO];
    [[NSRunLoop mainRunLoop] addTimer:timer forMode:NSRunLoopCommonModes];
    
    HttpRequest *request = [[HttpRequest alloc] init];
    [request BasicHttpRequestGetWithUrl:@"http://www.baidu.com/" :nil];

}



- (void)onTick:(NSTimer *)time {
    
    [ self presentViewController:[[MainMapUIControllerViewController alloc] init] animated: YES completion:nil];

}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
