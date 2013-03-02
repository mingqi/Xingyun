//
//  ActivityListViewContrller.m
//  Xingyun
//
//  Created by Mingqi Shao on 2/24/13.
//  Copyright (c) 2013 Mingqi Shao. All rights reserved.
//

#import "ActivityListViewController.h"

@interface ActivityListViewController ()

@property (weak, nonatomic) UIPageControl *pageControl;
@property (weak, nonatomic) IBOutlet UIScrollView *scrollView;

@end

@implementation ActivityListViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
        self.tabBarItem.title = @"餐厅活动";
    }
    
    NSLog(@"ActivityListViewContrller init with nib name, %@", nibNameOrNil);
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    NSLog(@"Tab bar: x=%.1f, y=%.1f, width=%.1f, height=%.1f",
          self.tabBarController.tabBar.bounds.origin.x,
          self.tabBarController.tabBar.bounds.origin.y,
          self.tabBarController.tabBar.bounds.size.width,
          self.tabBarController.tabBar.bounds.size.height);
    
    NSLog(@"scrollView view: x=%.1f, y=%.1f, width=%.1f, height=%.1f",
          self.scrollView.bounds.origin.x,
          self.scrollView.bounds.origin.y,
          self.scrollView.bounds.size.width,
          self.scrollView.bounds.size.height);    
    
    
    UIScrollView *scr=[[UIScrollView alloc] initWithFrame:CGRectMake(0, 0, self.view.frame.size.width, self.view.frame.size.height)];
    scr.autoresizingMask=UIViewAutoresizingNone;
    scr.pagingEnabled = YES;
    [self.view addSubview:scr];
    [self setupScrollView:scr];

    
    NSLog(@"activity view: x=%.1f, y=%.1f, width=%.1f, height=%.1f",
          self.view.bounds.origin.x,
          self.view.bounds.origin.y,
          self.view.bounds.size.width,
          self.view.bounds.size.height);
    
    
    UIPageControl *pageControl = [[UIPageControl alloc] initWithFrame:CGRectMake(160, 400, 0, 0)];
    pageControl.numberOfPages = 3;
    pageControl.pageIndicatorTintColor = [UIColor redColor];
    pageControl.currentPageIndicatorTintColor = [UIColor blackColor];
    [self.view addSubview: pageControl];
    
    NSLog(@"pageControl view: x=%.1f, y=%.1f, width=%.1f, height=%.1f",
          pageControl.frame.origin.x,
          pageControl.frame.origin.y,
          pageControl.frame.size.width,
          pageControl.frame.size.height);
}

- (void)setupScrollView:(UIScrollView*)scrMain {
    for (int i=1; i<=3; i++) {
        // create image
        NSString *imageName = [NSString stringWithFormat:@"images/activity/%02i.jpg",i];
       
        UIImage *image = [UIImage imageNamed:imageName];
        NSLog(@"image obj: %@", image);
        // create imageView
        UIImageView *imgV = [[UIImageView alloc] initWithFrame:CGRectMake((i-1)*scrMain.frame.size.width, 0, scrMain.frame.size.width, scrMain.frame.size.height)];
        // set scale to fill
        imgV.contentMode=UIViewContentModeScaleToFill;
        // set image
        [imgV setImage:image];
        // apply tag to access in future
        imgV.tag=i+1;
        // add to scrollView
        [scrMain addSubview:imgV];
    }
    // set the content size to 10 image width
    [scrMain setContentSize:CGSizeMake(scrMain.frame.size.width*3, scrMain.frame.size.height)];

}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
