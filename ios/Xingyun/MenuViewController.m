//
//  MenuViewController.m
//  Xingyun
//
//  Created by Mingqi Shao on 2/27/13.
//  Copyright (c) 2013 Mingqi Shao. All rights reserved.
//

#import "MenuViewController.h"

@interface MenuViewController ()

@property (nonatomic, strong) UISegmentedControl *topSegmentedControl;

@end

@implementation MenuViewController

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
    self.title = @"点菜";
    
    /// draw heas view
    NSInteger const HEAD_HEIGH = 44;
    UIColor * const HEAD_BACKGROUND_COLOR = [UIColor darkGrayColor];
    
    UIView *headView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, 320, HEAD_HEIGH)];
    headView.backgroundColor = HEAD_BACKGROUND_COLOR;
    [self.view addSubview:headView];
    
    [self drawTopSegmentedControl:headView];

}

- (void) drawTopSegmentedControl:(UIView *) superView{
    self.topSegmentedControl = [[UISegmentedControl alloc] initWithItems:@[@"全部", @"凉菜", @"热菜",@"其他"]];
    NSInteger const HORIZONTAL_SPACE = 7;
    NSInteger const VIRTICAL_SPACE = 7;
    UIFont  * const TITLE_FONT = [UIFont systemFontOfSize:14];
    
    self.topSegmentedControl.frame =  CGRectMake(HORIZONTAL_SPACE, VIRTICAL_SPACE, superView.bounds.size.width - 2 * HORIZONTAL_SPACE, superView.bounds.size.height - 2 * VIRTICAL_SPACE);
    self.topSegmentedControl.segmentedControlStyle = UISegmentedControlStyleBar;
    
    NSDictionary *attributes = [NSDictionary dictionaryWithObject:TITLE_FONT
                                                           forKey:UITextAttributeFont];
    [self.topSegmentedControl setTitleTextAttributes:attributes forState:UIControlStateNormal];
    
    [self.topSegmentedControl addTarget:self action:@selector(segmentValueChanged:) forControlEvents:UIControlEventValueChanged];
    
    
    
    [self.view addSubview:self.topSegmentedControl];
}

- (void) viewDidAppear:(BOOL)animated{
    self.topSegmentedControl.selectedSegmentIndex = 0;
    [self segmentValueChanged:self.topSegmentedControl];
}

- (void) segmentValueChanged:(UISegmentedControl *)segmentedControl{
    UIColor *const SELECTED_COLOR = [UIColor redColor];
    UIColor *const UNSELECTED_COLOR = [UIColor darkGrayColor];
    
    for (int i=0; i<[segmentedControl.subviews count]; i++)
    {
        if ([[segmentedControl.subviews objectAtIndex:i]isSelected] )
        {
           
            UIColor *tintcolor= SELECTED_COLOR;
            [[segmentedControl.subviews objectAtIndex:i] setTintColor:tintcolor];
        }
        if (![[segmentedControl.subviews objectAtIndex:i]isSelected] )
        {
            UIColor *tintcolor= UNSELECTED_COLOR;
            [[segmentedControl.subviews objectAtIndex:i] setTintColor:tintcolor];
        }
    }
}


- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
