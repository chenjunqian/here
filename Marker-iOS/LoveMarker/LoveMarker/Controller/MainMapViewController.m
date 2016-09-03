//
//  MainMapViewController.m
//  LoveMarker
//
//  Created by BigHead_Chen on 5/19/16.
//  Copyright © 2016 Eason. All rights reserved.
//

#import "MainMapViewController.h"
#import "MainMapPageUIView.h"

@interface MainMapViewController ()

@property (strong,nonatomic) MainMapPageUIView* mainMapPageUIView;
@property (strong,nonatomic) CLLocationManager* locationManager;

@end

@implementation MainMapViewController

@synthesize mainMapPageUIView,locationManager;

- (void)viewDidLoad {
    [super viewDidLoad];
    [self initView];
    [self initData];
}

-(void)initView{
    mainMapPageUIView = [[MainMapPageUIView alloc] initViewContext:self title:NSLocalizedString(@"home_page", nil) frame:CGRectMake(0, 0, self.view.frame.size.width, self.view.frame.size.height)];
    [self.view addSubview:mainMapPageUIView];
    
    mainMapPageUIView.mapView.userTrackingMode = MKUserTrackingModeFollow;
    mainMapPageUIView.mapView.delegate = self;
    mainMapPageUIView.mapView.showsUserLocation = YES;
}

-(void)initData{
    locationManager = [[CLLocationManager alloc] init];
    if ([[UIDevice currentDevice].systemVersion doubleValue] >= 8.0) {
        
        [locationManager requestAlwaysAuthorization];
    }
    
    CLAuthorizationStatus status = [CLLocationManager authorizationStatus];
    if (![CLLocationManager locationServicesEnabled]) {
        NSLog(@"定位服务当前可能尚未打开，请设置打开！");
        return;
    }
    
    if (status == kCLAuthorizationStatusNotDetermined) {
        [locationManager requestWhenInUseAuthorization];
    }else if (status== kCLAuthorizationStatusAuthorizedWhenInUse || status == kCLAuthorizationStatusAuthorizedAlways){
        
        locationManager.delegate = self;
        locationManager.desiredAccuracy = kCLLocationAccuracyBest;
        CLLocationDistance distance = 100.0;
        locationManager.distanceFilter = distance;
        [locationManager startUpdatingLocation];
        
    }
}

-(void)locationManager:(CLLocationManager *)manager didUpdateLocations:(NSArray<CLLocation *> *)locations{
    CLLocation *location=[locations firstObject];//取出第一个位置
    CLLocationCoordinate2D coordinate=location.coordinate;//位置坐标
    NSLog(@"经度：%f,纬度：%f,海拔：%f,航向：%f,行走速度：%f",coordinate.longitude,coordinate.latitude,location.altitude,location.course,location.speed);
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
