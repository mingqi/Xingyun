//
//  NavigationHomeViewController.m
//  Xingyun2
//
//  Created by Mingqi Shao on 5/2/13.
//  Copyright (c) 2013 Mingqi Shao. All rights reserved.
//

#import "NavigationHomeViewController.h"

@interface NavigationHomeViewController ()

@end

@implementation NavigationHomeViewController

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
    [self drawHomeWithImageHeight:148.3];
}

- (void) viewDidAppear:(BOOL)animated
{
    [self.navigationController setNavigationBarHidden:YES];
}

- (void) drawHomeWithImageHeight:(CGFloat) height
{
    CGFloat labelWidth = 90;
    CGFloat labelHeight = 25;
    
    // *** First item
    UIImageView *imageView = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"nav-1.png"]];
    imageView.frame = CGRectMake(0, 0, 320, height);
    [self.view addSubview:imageView];
    imageView.userInteractionEnabled = YES;
    UITapGestureRecognizer *singleTap = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(touchFirstItem)];
    [imageView addGestureRecognizer:singleTap];
    
    UILabel *label = [[UILabel alloc] initWithFrame:CGRectMake(320 - labelWidth, height - labelHeight, labelWidth, labelHeight)];
    label.backgroundColor = [UIColor colorWithRed:0.5 green:0.0 blue:0.25 alpha:1];
    label.text = @"活动信息 >";
    label.textColor = [UIColor whiteColor];
    label.textAlignment = NSTextAlignmentCenter;
    label.font = [UIFont systemFontOfSize:14];
    [self.view addSubview:label];
    
    UIView *separatorView = [[UIView alloc] initWithFrame:CGRectMake(0, height, 320, 5)];
    separatorView.backgroundColor = [UIColor colorWithRed:0.5 green:0.0 blue:0.25 alpha:1];
    [self.view addSubview:separatorView];
    
    // *** second item ***
    imageView = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"nav-2.png"]];
    imageView.frame = CGRectMake(0, height + 5, 320, height);
    [self.view addSubview:imageView];
    imageView.userInteractionEnabled = YES;
    singleTap = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(touchSecondItem)];
    [imageView addGestureRecognizer:singleTap];
    
    label = [[UILabel alloc] initWithFrame:CGRectMake(320 - labelWidth, (height * 2 + 5) - labelHeight, labelWidth, labelHeight)];
    label.backgroundColor = [UIColor colorWithRed:0.5 green:0.0 blue:0.25 alpha:1];
    label.text = @"餐厅预订 >";
    label.textColor = [UIColor whiteColor];
    label.textAlignment = NSTextAlignmentCenter;
    label.font = [UIFont systemFontOfSize:14];
    [self.view addSubview:label];
    
    separatorView = [[UIView alloc] initWithFrame:CGRectMake(0, height * 2 + 5, 320, 5)];
    separatorView.backgroundColor = [UIColor colorWithRed:0.5 green:0.0 blue:0.25 alpha:1];
    [self.view addSubview:separatorView];
    
    
    // *** third item ****
    imageView = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"nav-3.png"]];
    imageView.frame = CGRectMake(0, height*2 + 5*2, 320, height);
    [self.view addSubview:imageView];
    imageView.userInteractionEnabled = YES;
    singleTap = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(touchThirdItem)];
    [imageView addGestureRecognizer:singleTap];
    
    label = [[UILabel alloc] initWithFrame:CGRectMake(320 - labelWidth, (height * 3 + 5*2) - labelHeight, labelWidth, labelHeight)];
    label.backgroundColor = [UIColor colorWithRed:0.5 green:0.0 blue:0.25 alpha:1];
    label.text = @"用户注册 >";
    label.textColor = [UIColor whiteColor];
    label.textAlignment = NSTextAlignmentCenter;
    label.font = [UIFont systemFontOfSize:14];
    [self.view addSubview:label];
    
    separatorView = [[UIView alloc] initWithFrame:CGRectMake(0, height * 3 + 5*2, 320, 5)];
    separatorView.backgroundColor = [UIColor colorWithRed:0.5 green:0.0 blue:0.25 alpha:1];
    [self.view addSubview:separatorView];

}

- (void) touchFirstItem
{
    [self performSegueWithIdentifier:@"pushToPromotionView" sender:self];
}

- (void) touchSecondItem
{
    [self performSegueWithIdentifier:@"pushToResInfo" sender:self];
}

- (void) touchThirdItem
{
    [self performSegueWithIdentifier:@"pushToSignup" sender:self];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
