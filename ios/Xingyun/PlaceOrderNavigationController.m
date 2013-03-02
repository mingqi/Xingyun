//
//  PlaceOrderViewController.m
//  Xingyun
//
//  Created by Mingqi Shao on 2/24/13.
//  Copyright (c) 2013 Mingqi Shao. All rights reserved.
//

#import "PlaceOrderNavigationController.h"
#import "RestaurantViewController.h"

@interface PlaceOrderNavigationController ()

@property (nonatomic, strong) RestaurantViewController *restaurantViewController;

@end

@implementation PlaceOrderNavigationController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        self.title = @"我要定餐";
        self.tabBarItem.title = @"我要定餐";
        
        
        self.restaurantViewController = [[RestaurantViewController alloc] initWithNibName:@"RestaurantViewController" bundle:nil];
        
        self.viewControllers = @[self.restaurantViewController];
    
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    NSLog(@"Navigation tab View: x=%.1f, y=%.1f, width=%.1f, height=%.1f",
          self.navigationBar.bounds.origin.x,
          self.navigationBar.bounds.origin.y,
          self.navigationBar.bounds.size.width,
          self.navigationBar.bounds.size.height);
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
