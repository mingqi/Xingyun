//
//  PromotionViewController.m
//  Xingyun2
//
//  Created by Mingqi Shao on 3/7/13.
//  Copyright (c) 2013 Mingqi Shao. All rights reserved.
//

#import "PromotionViewController.h"
#import "Restfulservice.h"
#import "MBProgressHUD.h"


@interface PromotionViewController ()

@property (strong, nonatomic) IBOutlet UIPageControl *pageControl;
@property (strong, nonatomic) IBOutlet UIScrollView *scrollView;
@property (strong, nonatomic) MBProgressHUD *hub;
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
    //[self setupScrollView:self.scrollView];
    //Restfulservice *service = [[Restfulservice alloc] initWiteRemoteAddress:@"http://127.0.0.1:8000/xingyun"];
    Restfulservice *service = [Restfulservice getService];
    //[MBProgressHUD showHUDAddedTo:self.view animated:YES];
    self.hub = [[MBProgressHUD alloc] initWithView:self.view];
    [self.view addSubview:self.hub];
    [service getActivities:self withProgressHUB:self.hub];
    
    NSLog(@"ending... viewDidLoad");

}

- (void) successLoadActivities:(NSArray *)images
{
    NSLog(@"This is successLoad: %@", images);
    for (int i=0; i<[images count]; i++) {
        // create imageView
        UIImageView *imageView = [images objectAtIndex:(i)];
        [imageView setFrame: CGRectMake(i*self.scrollView.frame.size.width, 0, self.scrollView.frame.size.width, self.scrollView.frame.size.height)];
        
        // set scale to fill
        imageView.contentMode=UIViewContentModeScaleToFill;
        // apply tag to access in future
        imageView.tag=i+2;
        // add to scrollView
        [self.scrollView addSubview:imageView];
    }
    // set the content size to 10 image width
    [self.scrollView setContentSize:CGSizeMake(self.scrollView.frame.size.width*[images count], self.scrollView.frame.size.height)];
    self.pageControl.frame = CGRectMake(160, 400, 0, 0);
    self.pageControl.numberOfPages = [images count];
    [self.view addSubview: self.pageControl];
}

- (void) failureLoadActivities:(NSError *)error
{
    NSMutableArray *images = [NSMutableArray arrayWithCapacity:3];
    for (int i=1; i<=3; i++) {
        NSString *imageName = [NSString stringWithFormat:@"prom%02i.jpg",i];
        UIImage *image = [UIImage imageNamed:imageName];
        UIImageView *imageView = [[UIImageView alloc] initWithImage:image];
        [images addObject:imageView];
    }
    
    [self successLoadActivities:images];
}

- (void)setupScrollView:(UIScrollView*)scrMain {
    /*
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
    */
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
