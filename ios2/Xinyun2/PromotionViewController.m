//
//  PromotionViewController.m
//  Xingyun2
//
//  Created by Mingqi Shao on 3/7/13.
//  Copyright (c) 2013 Mingqi Shao. All rights reserved.
//

#import "PromotionViewController.h"

@interface PromotionViewController ()

@property (strong, nonatomic) IBOutlet UIPageControl *pageControl;
@property (strong, nonatomic) IBOutlet UIScrollView *scrollView;
@end

@implementation PromotionViewController

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
    self.scrollView =[[UIScrollView alloc] initWithFrame:CGRectMake(0, 0, self.view.frame.size.width, self.view.frame.size.height)];
    self.scrollView.autoresizingMask=UIViewAutoresizingNone;
    self.scrollView.pagingEnabled = YES;
    self.scrollView.delegate = self;
    [self.view addSubview:self.scrollView];
    [self setupScrollView:self.scrollView];
    
    /*
    UIPageControl *pageControl = [[UIPageControl alloc] initWithFrame:CGRectMake(160, 400, 0, 0)];
    pageControl.numberOfPages = 3;
    pageControl.pageIndicatorTintColor = [UIColor redColor];
    pageControl.currentPageIndicatorTintColor = [UIColor blackColor];
    //[self.view addSubview: pageControl];
    */
    
    self.pageControl.frame = CGRectMake(160, 400, 0, 0);
    self.pageControl.numberOfPages = 3;
    //self.pageControl.pageIndicatorTintColor = [UIColor redColor];
    //self.pageControl.currentPageIndicatorTintColor = [UIColor blackColor];
//    [self.pageControl removeFromSuperview];
    
    [self.view addSubview: self.pageControl];

}

- (void)setupScrollView:(UIScrollView*)scrMain {
    for (int i=1; i<=3; i++) {
        // create image
        NSString *imageName = [NSString stringWithFormat:@"prom%02i.jpg",i];
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

- (void)viewDidUnload {
    [self setPageControl:nil];
    [super viewDidUnload];
}


- (void)scrollViewDidEndDecelerating:(UIScrollView *)scrollView{
    int pageNumber = (int)(scrollView.contentOffset.x/self.view.frame.size.width);
    self.pageControl.currentPage = pageNumber;
}
@end
