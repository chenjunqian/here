//
//  PublishUIView.h
//  LoveMarker
//
//  Created by BigHead_Chen on 8/5/16.
//  Copyright © 2016 Eason. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "TopLayoutView.h"

@interface PublishUIView : UIView
@property (strong,nonatomic) TopLayoutView* topLayoutView;
@property (strong,nonatomic) UICollectionView* collectionView;
@property (strong,nonatomic) UILabel* hintUILabel;
@property (strong,nonatomic) UITextField* tagUITextField;
@property (strong,nonatomic) UITextField* locationDescriptionUITextField;
@property (strong,nonatomic) UIButton* publishButton;

@property (strong,nonatomic) UIView* locationDescriptionUILabelHolder;
@property (strong,nonatomic) UIView* tagUILabelHolder;


-(id)initViewContext:(id)context title:(NSString*)title frame:(CGRect)frame;
@end
