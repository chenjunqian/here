//
//  PublishUIView.m
//  LoveMarker
//
//  Created by BigHead_Chen on 8/5/16.
//  Copyright © 2016 Eason. All rights reserved.
//

#import "PublishUIView.h"
#import "ColorUtil.h"

@implementation PublishUIView

@synthesize topLayoutView , hintUILabel , collectionView , locationDescriptionUITextField , tagUITextField , locationDescriptionUILabelHolder,tagUILabelHolder,publishButton;

-(id)initViewContext:(id)context title:(NSString*)title frame:(CGRect)frame{
    self = [super initWithFrame:frame];
    
    if (self) {
        topLayoutView = [[TopLayoutView alloc] initWithoutButtom:context title:title andFrame:CGRectMake(0, 20, self.frame.size.width, 50)];
        [self addSubview:topLayoutView];
        
        hintUILabel = [[UILabel alloc] init];
        hintUILabel.translatesAutoresizingMaskIntoConstraints = NO;
        hintUILabel.textColor = [ColorUtil textColorSubBlack];
        hintUILabel.backgroundColor = [ColorUtil viewBackgroundGrey];
        hintUILabel.text = NSLocalizedString(@"hint_label_text", nil);
        [self addSubview:hintUILabel];
        
        UICollectionViewFlowLayout* flowLayout = [[UICollectionViewFlowLayout alloc] init];
        collectionView = [[UICollectionView alloc] initWithFrame:CGRectMake(0, 0, 320, 320) collectionViewLayout:flowLayout];
        collectionView.translatesAutoresizingMaskIntoConstraints =NO;
        collectionView.backgroundColor = [ColorUtil viewBackgroundGrey];
        [self addSubview:collectionView];
        
        locationDescriptionUILabelHolder = [[UIView alloc] init];
        locationDescriptionUILabelHolder.translatesAutoresizingMaskIntoConstraints = NO;
        locationDescriptionUILabelHolder.backgroundColor = [UIColor whiteColor];
        [self addSubview:locationDescriptionUILabelHolder];
        
        locationDescriptionUITextField = [[UITextField alloc] init];
        locationDescriptionUITextField.translatesAutoresizingMaskIntoConstraints = NO;
        locationDescriptionUITextField.textColor = [UIColor blackColor];
        locationDescriptionUITextField.backgroundColor = [ColorUtil viewBackgroundGrey];
        locationDescriptionUITextField.placeholder = NSLocalizedString(@"location_hint_text", nil);
        locationDescriptionUITextField.textAlignment = NSTextAlignmentCenter;
        locationDescriptionUITextField.font = [UIFont fontWithName:@"" size:12];
        [locationDescriptionUILabelHolder addSubview:locationDescriptionUITextField];
        
        tagUILabelHolder = [[UIView alloc] init];
        tagUILabelHolder.translatesAutoresizingMaskIntoConstraints = NO;
        tagUILabelHolder.backgroundColor = [UIColor whiteColor];
        [self addSubview:tagUILabelHolder];
        
        tagUITextField = [[UITextField alloc] init];
        tagUITextField.translatesAutoresizingMaskIntoConstraints = NO;
        tagUITextField.textColor = [UIColor blackColor];
        tagUITextField.backgroundColor = [ColorUtil viewBackgroundGrey];
        tagUITextField.placeholder = NSLocalizedString(@"add_mark_hint", nil);
        tagUITextField.textAlignment = NSTextAlignmentCenter;
        tagUITextField.contentHorizontalAlignment = UIControlContentHorizontalAlignmentCenter;
        tagUITextField.font = [UIFont fontWithName:@"" size:12];
        [tagUILabelHolder addSubview:tagUITextField];

        publishButton = [[UIButton alloc] init];
        publishButton.translatesAutoresizingMaskIntoConstraints = NO;
        [publishButton setTitle:NSLocalizedString(@"publish_post_button_text", nil) forState:UIControlStateNormal];
        [publishButton setBackgroundColor:[ColorUtil themeColor]];
        publishButton.layer.cornerRadius = 5;
        publishButton.layer.masksToBounds = YES;
        [self addSubview:publishButton];
    }
    
    return self;
}


-(void)layoutSubviews{
    NSDictionary* views = NSDictionaryOfVariableBindings(topLayoutView,hintUILabel,collectionView,locationDescriptionUILabelHolder,locationDescriptionUITextField,tagUILabelHolder,tagUITextField,publishButton);
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:[topLayoutView]-10-[hintUILabel]" options:0 metrics:0 views:views]];
    [self addConstraint:[NSLayoutConstraint constraintWithItem:hintUILabel attribute:NSLayoutAttributeCenterX relatedBy:NSLayoutRelationEqual toItem:self attribute:NSLayoutAttributeCenterX multiplier:1 constant:0]];
    
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:|-20-[collectionView]-20-|" options:0 metrics:0 views:views]];
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:[hintUILabel]-10-[collectionView(150)]" options:0 metrics:0 views:views]];
    
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:|-0-[tagUILabelHolder]-0-|" options:0 metrics:0 views:views]];
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:[collectionView]-0-[tagUILabelHolder]" options:0 metrics:0 views:views]];
    [tagUILabelHolder addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:|-30-[tagUITextField]-30-|" options:0 metrics:0 views:views]];
    [tagUILabelHolder addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:|-10-[tagUITextField(30)]-10-|" options:0 metrics:0 views:views]];
    
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:|-0-[locationDescriptionUILabelHolder]-0-|" options:0 metrics:0 views:views]];
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:[tagUILabelHolder]-20-[locationDescriptionUILabelHolder]" options:0 metrics:0 views:views]];
    [locationDescriptionUILabelHolder addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:|-30-[locationDescriptionUITextField]-30-|" options:0 metrics:0 views:views]];
    [locationDescriptionUILabelHolder addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:|-10-[locationDescriptionUITextField(30)]-10-|" options:0 metrics:0 views:views]];
    
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:|-40-[publishButton]-40-|" options:0 metrics:0 views:views]];
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:[locationDescriptionUILabelHolder]-50-[publishButton(40)]" options:0 metrics:0 views:views]];
}

@end
