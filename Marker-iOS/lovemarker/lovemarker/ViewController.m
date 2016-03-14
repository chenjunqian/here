//
//  ViewController.m
//  LoveMarker
//
//  Created by BigHead_Chen on 3/12/16.
//  Copyright © 2016 Eason. All rights reserved.
//

#import "ViewController.h"
#import "MapViewController.h"

@interface ViewController ()

@end

@implementation ViewController

- (void)delayPresent:(NSTimer *)timer{
    MapViewController *mapview = [[MapViewController alloc] initWithNibName:@"MapView" bundle:nil];
    
    [self presentViewController:mapview animated:YES completion:^{
        NSLog(@"Goto map view");
    }];
}


- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view, typically from a nib.
    UIImage *splashImage = [[UIImage alloc] init];
    splashImage = [UIImage imageNamed:@"splash_background"];
    UIImageView *splashImageView = [[UIImageView alloc] initWithFrame:CGRectMake(0, 20, self.view.frame.size.width, self.view.frame.size.height-20)];
    splashImageView.backgroundColor = [UIColor grayColor];
    [splashImageView setImage:splashImage];
    [self.view addSubview:splashImageView];
    
    [NSTimer scheduledTimerWithTimeInterval:3.0 target:self selector:@selector(delayPresent:) userInfo:nil repeats:NO];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
