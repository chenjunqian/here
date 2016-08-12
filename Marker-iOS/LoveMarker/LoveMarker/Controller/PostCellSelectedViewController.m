//
//  PostCellSelectedViewController.m
//  LoveMarker
//
//  Created by BigHead_Chen on 8/12/16.
//  Copyright © 2016 Eason. All rights reserved.
//

#import "PostCellSelectedViewController.h"
#import "PostCellSelectedView.h"

@interface PostCellSelectedViewController ()
@property (strong,nonatomic) PostCellSelectedView* postCellSelectedView;
@property (strong,nonatomic) Post* post;
@end

@implementation PostCellSelectedViewController

@synthesize postCellSelectedView;

-(id)initWithPost:(Post*) post{
    self = [super init];
    
    if (self) {
        _post = post;
        
    }
    
    return self;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    [self initView];
}

-(void)initView{
    postCellSelectedView = [[PostCellSelectedView alloc] initWithFrame:CGRectMake(0, 0, self.view.frame.size.width, self.view.frame.size.height)];
    [self.view addSubview:postCellSelectedView];
    
    [postCellSelectedView whenSingleClick:^{
        [self dismissViewControllerAnimated:NO completion:nil];
    }];
    
    [postCellSelectedView.shareItem whenSingleClick:^{
        NSLog(@"enterUserPageItem clicked : %@ ",_post.tag);
    }];
    
    [postCellSelectedView.enterUserPageItem whenSingleClick:^{
        NSLog(@"enterUserPageItem clicked : %@ ",_post.username);
    }];
    
    [postCellSelectedView.reportItem whenSingleClick:^{
        NSLog(@"enterUserPageItem clicked : %@ ",_post.postId);
    }];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
