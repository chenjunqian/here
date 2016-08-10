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

@synthesize topLayoutView , scrollView , contentView , hintUILabel , collectionView , locationDescriptionUITextField , tagUITextField , locationDescriptionUILabelHolder,tagUILabelHolder,publishButton;

-(id)initViewContext:(id)context title:(NSString*)title frame:(CGRect)frame{
    self = [super initWithFrame:frame];
    
    if (self) {
        topLayoutView = [[TopLayoutView alloc] initWithoutButtom:context title:title andFrame:CGRectMake(0, 20, self.frame.size.width, 50)];
        [self addSubview:topLayoutView];
        
        scrollView = [[UIScrollView alloc] initWithFrame:CGRectMake(0, 70, frame.size.width, frame.size.height - 70 - 50)];
        scrollView.contentSize = CGSizeMake(frame.size.width, 550);
        [self addSubview:scrollView];
        
        contentView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, frame.size.width, 550)];
        contentView.backgroundColor = [ColorUtil viewBackgroundGrey];
        [scrollView addSubview:contentView];
        
        hintUILabel = [[UILabel alloc] init];
        hintUILabel.translatesAutoresizingMaskIntoConstraints = NO;
        hintUILabel.textColor = [ColorUtil textColorSubBlack];
        hintUILabel.backgroundColor = [ColorUtil viewBackgroundGrey];
        hintUILabel.text = NSLocalizedString(@"hint_label_text", nil);
        [contentView addSubview:hintUILabel];
        
        UICollectionViewFlowLayout* flowLayout = [[UICollectionViewFlowLayout alloc] init];
        collectionView = [[UICollectionView alloc] initWithFrame:CGRectMake(0, 0, 320, 320) collectionViewLayout:flowLayout];
        collectionView.translatesAutoresizingMaskIntoConstraints =NO;
        collectionView.backgroundColor = [ColorUtil viewBackgroundGrey];
        [contentView addSubview:collectionView];
        
        locationDescriptionUILabelHolder = [[UIView alloc] init];
        locationDescriptionUILabelHolder.translatesAutoresizingMaskIntoConstraints = NO;
        locationDescriptionUILabelHolder.backgroundColor = [UIColor whiteColor];
        [contentView addSubview:locationDescriptionUILabelHolder];
        
        locationDescriptionUITextField = [[UITextField alloc] init];
        locationDescriptionUITextField.translatesAutoresizingMaskIntoConstraints = NO;
        locationDescriptionUITextField.textColor = [UIColor blackColor];
        locationDescriptionUITextField.backgroundColor = [ColorUtil viewBackgroundGrey];
        locationDescriptionUITextField.placeholder = NSLocalizedString(@"location_hint_text", nil);
        locationDescriptionUITextField.textAlignment = NSTextAlignmentCenter;
        locationDescriptionUITextField.font = [UIFont systemFontOfSize:12];
        locationDescriptionUITextField.textColor = [UIColor grayColor];
        [locationDescriptionUILabelHolder addSubview:locationDescriptionUITextField];
        
        tagUILabelHolder = [[UIView alloc] init];
        tagUILabelHolder.translatesAutoresizingMaskIntoConstraints = NO;
        tagUILabelHolder.backgroundColor = [UIColor whiteColor];
        [contentView addSubview:tagUILabelHolder];
        
        tagUITextField = [[UITextField alloc] init];
        tagUITextField.translatesAutoresizingMaskIntoConstraints = NO;
        tagUITextField.textColor = [UIColor blackColor];
        tagUITextField.backgroundColor = [ColorUtil viewBackgroundGrey];
        tagUITextField.placeholder = NSLocalizedString(@"add_mark_hint", nil);
        tagUITextField.textAlignment = NSTextAlignmentCenter;
        tagUITextField.contentHorizontalAlignment = UIControlContentHorizontalAlignmentCenter;
        tagUITextField.font = [UIFont systemFontOfSize:12];
        tagUITextField.textColor = [UIColor grayColor];
        [tagUILabelHolder addSubview:tagUITextField];

        publishButton = [[UIButton alloc] init];
        publishButton.translatesAutoresizingMaskIntoConstraints = NO;
        [publishButton setTitle:NSLocalizedString(@"publish_post_button_text", nil) forState:UIControlStateNormal];
        [publishButton setBackgroundColor:[ColorUtil themeColor]];
        publishButton.layer.cornerRadius = 5;
        publishButton.layer.masksToBounds = YES;
        [contentView addSubview:publishButton];
    }
    
    return self;
}


-(void)layoutSubviews{
    NSDictionary* views = NSDictionaryOfVariableBindings(topLayoutView,hintUILabel,collectionView,locationDescriptionUILabelHolder,locationDescriptionUITextField,tagUILabelHolder,tagUITextField,publishButton);
    [contentView addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:|-10-[hintUILabel]" options:0 metrics:0 views:views]];
    [contentView addConstraint:[NSLayoutConstraint constraintWithItem:hintUILabel attribute:NSLayoutAttributeCenterX relatedBy:NSLayoutRelationEqual toItem:contentView attribute:NSLayoutAttributeCenterX multiplier:1 constant:0]];
    
    [contentView addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:|-20-[collectionView]-20-|" options:0 metrics:0 views:views]];
    [self addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:[hintUILabel]-10-[collectionView(160)]" options:0 metrics:0 views:views]];
    
    [contentView addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:|-0-[tagUILabelHolder]-0-|" options:0 metrics:0 views:views]];
    [contentView addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:[collectionView]-0-[tagUILabelHolder]" options:0 metrics:0 views:views]];
    [tagUILabelHolder addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:|-30-[tagUITextField]-30-|" options:0 metrics:0 views:views]];
    [tagUILabelHolder addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:|-15-[tagUITextField(30)]-15-|" options:0 metrics:0 views:views]];
    
    [contentView addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:|-0-[locationDescriptionUILabelHolder]-0-|" options:0 metrics:0 views:views]];
    [contentView addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:[tagUILabelHolder]-20-[locationDescriptionUILabelHolder]" options:0 metrics:0 views:views]];
    [locationDescriptionUILabelHolder addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:|-30-[locationDescriptionUITextField]-30-|" options:0 metrics:0 views:views]];
    [locationDescriptionUILabelHolder addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:|-15-[locationDescriptionUITextField(30)]-15-|" options:0 metrics:0 views:views]];
    
    [contentView addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"H:|-40-[publishButton]-40-|" options:0 metrics:0 views:views]];
    [contentView addConstraints:[NSLayoutConstraint constraintsWithVisualFormat:@"V:[locationDescriptionUILabelHolder]-50-[publishButton(40)]" options:0 metrics:0 views:views]];
}

@end
