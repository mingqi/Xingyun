//
//  DishDetailViewController.m
//  Xingyun2
//
//  Created by Mingqi Shao on 3/6/13.
//  Copyright (c) 2013 Mingqi Shao. All rights reserved.
//

#import "DishDetailViewController.h"
#import <QuartzCore/QuartzCore.h>

@interface DishDetailViewController ()

@property (strong, nonatomic) IBOutlet UIButton *cartButton;
@end

@implementation DishDetailViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    self.cartButton.layer.cornerRadius = 10;
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
