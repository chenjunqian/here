//
//  MainMapViewController.m
//  LoveMarker
//
//  Created by BigHead_Chen on 5/12/16.
//  Copyright © 2016 Eason. All rights reserved.
//

#import "MainMapViewController.h"
#import "LoginController.h"

@interface MainMapViewController ()
@property (weak, nonatomic) IBOutlet UIButton *testButton;

@end

@implementation MainMapViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
    
    [_testButton addTarget:self action:@selector(testBtnAction:) forControlEvents:UIControlEventTouchDown];
}
- (IBAction)testBtnAction:(id)sender {
        [ self presentViewController:[[LoginController alloc] init] animated: YES completion:nil];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

@end
