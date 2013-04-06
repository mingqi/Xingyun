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
    footerLabel.text = @"点击加载更多...";
    footerLabel.textColor = [UIColor blackColor];
    footerLabel.textAlignment = NSTextAlignmentCenter;
    footerLabel.font = [UIFont systemFontOfSize:12];
    
    
    self.hub = [[MBProgressHUD alloc] initWithView:self.view];
    [self.view addSubview: self.hub];
    [self.hub hide:YES];
    
    self.tableView.tableFooterView = footerView;
    self.tableView.tableFooterView.userInteractionEnabled = YES;
    [footerView addSubview:footerLabel];
    
    self.tableViewManager = [[DishListTableViewManager alloc] init];
    self.tableViewManager.tableView = self.tableView;
    self.tableViewManager.hub = self.hub;
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


- (void) tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath{    
    [self performSegueWithIdentifier:@"dishDetailSegue" sender:self];
}



@end

@interface DishListTableViewManager()

@property (nonatomic, strong) NSMutableArray *dishList;

@end

@implementation DishListTableViewManager

- (DishListTableViewManager *) init
{
    if(self == [super init])
    {
        self.dishList = [NSMutableArray arrayWithCapacity:10];
        self.pageNumber = 1;
        self.category = 0;
    }
    return self;
}

- (void) reloadTableView
{
    Restfulservice *service = [Restfulservice getService];
    [self.hub show:YES];
    [self.dishList removeAllObjects];
    [service loadMenuItems:self category:0  pageNum:1];
//    [service loadMenuItems:<#(id<LoadMenuItemDelegate>)#> pageNum:<#(NSInteger)#>]
}

- (void) loadMoreCellTableView
{
    
}

- (void) successLoad:(NSArray *)menuItems hasMore:(BOOL)hasMore
{
    NSLog(@"successLoad %@", menuItems);
    [self.dishList addObjectsFromArray:menuItems];
    [self.hub hide:YES];
    [self.tableView reloadData];    
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
