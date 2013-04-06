//
//  DishListViewController.m
//  Xinyun2
//
//  Created by Mingqi Shao on 3/4/13.
//  Copyright (c) 2013 Mingqi Shao. All rights reserved.
//

#import "DishListViewController.h"
#import <QuartzCore/QuartzCore.h>
#import "Restfulservice.h"
#import "UIImageView+AFNetworking.h"

@interface DishListViewController ()

- (IBAction)checkOutAction:(UIBarButtonItem *)sender;
@property (strong, nonatomic) IBOutlet UITableView *tableView;
@property (strong, nonatomic) MBProgressHUD * hub;
@property (strong, nonatomic) DishListTableViewManager *tableViewManager;

@end

@implementation DishListViewController

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
    NSLog(@"Dish Lish View Controller viewDidLoad");
    
        /*** draw table's footer view ***/
    UIView *footerView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, self.tableView.bounds.size.width, 40)];
    footerView.backgroundColor = [UIColor whiteColor];
    UILabel *footerLabel = [[UILabel alloc] initWithFrame:CGRectMake(0, 0, 320, 40)];
    footerLabel.text = @"";
    footerLabel.textColor = [UIColor blackColor];
    footerLabel.textAlignment = NSTextAlignmentCenter;
    footerLabel.font = [UIFont systemFontOfSize:12];
    
    UITapGestureRecognizer *singleTap = [[UITapGestureRecognizer alloc]
                                         initWithTarget:self action:@selector(handleSingleTapOnFooter:)];

    [footerView addGestureRecognizer:singleTap];
    
    self.hub = [[MBProgressHUD alloc] initWithView:self.view];
    [self.view addSubview: self.hub];
    [self.hub hide:YES];
    
    self.tableView.tableFooterView = footerView;
    self.tableView.tableFooterView.userInteractionEnabled = YES;
    [footerView addSubview:footerLabel];
    
    self.tableViewManager = [[DishListTableViewManager alloc] init];
    self.tableViewManager.tableView = self.tableView;
    self.tableViewManager.hub = self.hub;
    self.tableViewManager.tableFooterLabel = footerLabel;
    self.tableView.dataSource = self.tableViewManager;
    
    [self.tableViewManager reloadTableView];
}

- (void) viewDidAppear:(BOOL)animated
{
    NSLog(@"Dish Lish View Controller viewDidAppear");
    
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void) prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender{
//    NSLog(@"prepare for segue");
}

- (IBAction)checkOutAction:(UIBarButtonItem *)sender {
    [self performSegueWithIdentifier:@"checkoutSegue" sender:self];
}


- (IBAction)categoryChanged:(id)sender {
    UISegmentedControl *segmentedControl = (UISegmentedControl*) sender;
    NSInteger selectedIndex = [segmentedControl selectedSegmentIndex];
    NSLog(@"categoryChanged: %d", selectedIndex);
    self.tableViewManager.category = selectedIndex;
    [self.tableViewManager reloadTableView];
}

- (void) tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath{    
    [self performSegueWithIdentifier:@"dishDetailSegue" sender:self];
}

- (void)scrollViewDidEndDecelerating:(UIScrollView *)aScrollView
{
    NSArray *visibleRows = [self.tableView visibleCells];
    UITableViewCell *lastVisibleCell = [visibleRows lastObject];
    NSIndexPath *path = [self.tableView indexPathForCell:lastVisibleCell];
    if( path.row == [self.tableView numberOfRowsInSection:0]-1)
    {
        [self handleSingleTapOnFooter:nil];
    }
}

- (IBAction) handleSingleTapOnFooter: (UIGestureRecognizer *) sender {
    NSLog(@"The footer was tapped!");
    if(self.tableViewManager.hasMore)
    {
        [self.tableViewManager loadMoreCellTableView];
    }
}

@end

@interface DishListTableViewManager()

@property (nonatomic, strong) NSMutableArray *dishList;
@property (nonatomic) NSInteger pageNumber;

@end

@implementation DishListTableViewManager

- (DishListTableViewManager *) init
{
    if(self == [super init])
    {
        self.dishList = [NSMutableArray arrayWithCapacity:10];
        self.pageNumber = 0;
        self.category = 0;
        self.hasMore = NO;
    }
    return self;
}

- (void) reloadTableView
{
    self.pageNumber = 0;
    self.hasMore = NO;
    Restfulservice *service = [Restfulservice getService];
    [self.hub show:YES];
    [self.dishList removeAllObjects];
    [service loadMenuItems:self category:self.category  pageNum:self.pageNumber+1];
}

- (void) loadMoreCellTableView
{
    [self.hub show:YES];
    Restfulservice *service = [Restfulservice getService];
    [service loadMenuItems:self category:self.category  pageNum:self.pageNumber+1];
}

- (void) successLoad:(NSArray *)menuItems pageNumber:(NSInteger) pageNum hasMore:(BOOL)hasMore
{
    self.pageNumber = pageNum;
    [self.dishList addObjectsFromArray:menuItems];
    [self.hub hide:YES];
    [self.tableView reloadData];
    if([self.dishList count] == 0)
    {
        self.tableFooterLabel.text = @"没有菜品";
        self.hasMore = NO;
    }else{
        self.hasMore = hasMore;
        if( hasMore)
        {
            self.tableFooterLabel.text = @"点击或者下拉加载更多菜品";
        }else{
            self.tableFooterLabel.text = @"已经到最后了，没有更多菜品可加载了";
        }
    }
}

- (void) failureLoad:(NSError *)error
{
    [self.hub hide:YES];
}

- (NSInteger) tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return [self.dishList count];
}

- (UITableViewCell *) tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    MenuItem *menuItem = [self.dishList objectAtIndex:indexPath.row];
    
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:@"cell"];
    UIButton *cartButton = (UIButton *)[cell viewWithTag:4];
    cartButton.layer.cornerRadius = 5;
    UIImageView *imageView = (UIImageView *) [cell viewWithTag:1];
    [imageView setImageWithURL:[NSURL URLWithString:menuItem.imageURL]];
    
    return cell;
}

@end
