//
//  PublishViewController.m
//  LoveMarker
//
//  Created by BigHead_Chen on 5/19/16.
//  Copyright © 2016 Eason. All rights reserved.
//

#import "PublishViewController.h"
#import "PublishUIView.h"
#import "TagCollectionViewCell.h"
#import "ColorUtil.h"
#import "HttpRequest.h"
#import "ErrorState.h"
#import "PostTag.h"

@interface PublishViewController ()

@property (strong,nonatomic) PublishUIView* publishView;
@property (strong,nonatomic) NSArray* postTagArray;

@end

@implementation PublishViewController

@synthesize publishView,postTagArray;

- (void)viewDidLoad {
    [super viewDidLoad];
    [self initView];
    [self getPostTag];
}

-(void)initView{
    publishView = [[PublishUIView alloc] initViewContext:self title:NSLocalizedString(@"publish_page_title", nil) frame:CGRectMake(0, 0, self.view.frame.size.width, self.view.frame.size.height)];
    [self.view addSubview:publishView];
    publishView.collectionView.delegate = self;
    publishView.collectionView.dataSource = self;
    [publishView.collectionView registerClass:[TagCollectionViewCell class] forCellWithReuseIdentifier:@"TagCollectionViewCell"];
}

-(void)getPostTag{
    [HttpRequest getPostTag:^(ResponseResult *response, NSObject *resultObject) {
        if (response && response.status == Error_Code_Correct) {
            NSString* tagString = [((NSDictionary*)resultObject) objectForKey:@"tag"];
            postTagArray = [tagString componentsSeparatedByString:@"@@"];
            [publishView.collectionView reloadData];
        }
        
    }];
}

-(NSInteger)collectionView:(UICollectionView *)collectionView numberOfItemsInSection:(NSInteger)section{
    if (postTagArray) {
        return postTagArray.count;
    }
    
    return 0;
}

-(UICollectionViewCell*)collectionView:(UICollectionView *)collectionView cellForItemAtIndexPath:(NSIndexPath *)indexPath{
    static NSString * CellIdentifier = @"TagCollectionViewCell";
    TagCollectionViewCell* cell;
    cell = [collectionView dequeueReusableCellWithReuseIdentifier:CellIdentifier forIndexPath:indexPath];
    cell.layer.cornerRadius = 8;
    cell.layer.masksToBounds = YES;
    cell.layer.borderWidth = 0.5f;
    cell.backgroundColor = [ColorUtil viewBackgroundGrey];
    cell.layer.borderColor = [[UIColor lightGrayColor] CGColor];
    if (postTagArray) {
        cell.tagUILabel.text = postTagArray[indexPath.row];
    }
    return cell;
}

-(BOOL)collectionView:(UICollectionView *)collectionView shouldSelectItemAtIndexPath:(NSIndexPath *)indexPath{
    return YES;
}

-(void)collectionView:(UICollectionView *)collectionView didSelectItemAtIndexPath:(NSIndexPath *)indexPath{
    TagCollectionViewCell * cell = (TagCollectionViewCell *)[collectionView cellForItemAtIndexPath:indexPath];
    [publishView.tagUITextField setText:cell.tagUILabel.text];
}


- (CGSize)collectionView:(UICollectionView *)collectionView layout:(UICollectionViewLayout*)collectionViewLayout sizeForItemAtIndexPath:(NSIndexPath *)indexPath{
    return CGSizeMake(80, 30);
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
