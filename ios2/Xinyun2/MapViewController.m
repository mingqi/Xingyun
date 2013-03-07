//
//  MapViewController.m
//  Xinyun2
//
//  Created by Mingqi Shao on 3/4/13.
//  Copyright (c) 2013 Mingqi Shao. All rights reserved.
//

#import "MapViewController.h"
#import <MapKit/MapKit.h>

@interface MapViewController ()
@property (strong, nonatomic) IBOutlet MKMapView *mapView;

@end

@implementation MapViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
	//设置地图中心
    CLLocationCoordinate2D coordinate;
    coordinate.latitude = 41.7640550054776f;
    coordinate.longitude = 86.15317516028881f;
    MKPointAnnotation *ann = [[MKPointAnnotation alloc] init];
    ann.coordinate = coordinate;
    [ann setTitle:@"行运餐厅"];
    [ann setSubtitle:@"人民东路银星大酒店旁"];
    //触发viewForAnnotation
    [self.mapView addAnnotation:ann];
    
    //设置显示范围
    MKCoordinateRegion region;
    region.span.latitudeDelta = 0.001;
    region.span.longitudeDelta = 0.001;
    region.center = coordinate;
    // 设置显示位置(动画)
    [self.mapView setRegion:region animated:YES];
    // 设置地图显示的类型及根据范围进行显示
    [self.mapView regionThatFits:region];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}


- (void) prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender{
    NSLog(@"prepare for segue");
}
/*
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender{
    NSLog(@"prepare for segue");
    self.hidesBottomBarWhenPushed = YES;
}
*/



@end
