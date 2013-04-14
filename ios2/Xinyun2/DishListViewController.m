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
#import "ShoppingCartManager.h"
#import "DishDetailViewController.h"

@interface DishListViewController ()

- (IBAction)checkOutAction:(UIBarButtonItem *)sender;
@property (strong, nonatomic) IBOutlet UITableView *tableView;
@property (strong, nonatomic) MBProgressHUD * hub;
@property (strong, nonatomic) DishListTableViewManager *tableViewManager;
@property (strong, nonatomic) ShoppingCartManager *cartManager;
@property (strong, nonatomic) IBOutlet UIBarButtonItem *nextBarButton;

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
    
    self.cartManager = [ShoppingCartManager getInstance];
    [self.tableViewManager reloadTableView];
    
}

- (void) updateRightBar
{
    NSInteger itemUnitsInCart = [self.cartManager getItemUnits];
    if( itemUnitsInCart <= 0)
    {
        self.navigationItem.rightBarButtonItem.title=@"下一步";
    }else{
        self.navigationItem.rightBarButtonItem.title= [NSString stringWithFormat:@"购物车:%d", itemUnitsInCart];
    }
}

- (void) viewDidAppear:(BOOL)animated
{
    [self.tableView reloadData];
    [self updateRightBar];
    
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (IBAction)checkOutAction:(UIBarButtonItem *)sender {
    NSLog(@"ddd %d", [self.cartManager getItemUnits]);
    if([self.cartManager getItemUnits] > 0){
        [self performSegueWithIdentifier:@"checkoutSegue" sender:self];
    }else{
        [self performSegueWithIdentifier:@"orderConfirmSegue" sender:self];
    }
}


- (IBAction)categoryChanged:(id)sender {
    UISegmentedControl *segmentedControl = (UISegmentedControl*) sender;
    NSInteger selectedIndex = [segmentedControl selectedSegmentIndex];
    NSLog(@"categoryChanged: %d", selectedIndex);
    self.tableViewManager.category = selectedIndex;
    [self.tableViewManager reloadTableView];
}

- (IBAction)cartButtonTap:(id)sender {
    UIButton *button = (UIButton *)sender;
    // Get the UITableViewCell which is the superview of the UITableViewCellContentView which is the superview of the UIButton
    UITableViewCell* cell = (UITableViewCell*)[[button superview] superview];
    int row = [self.tableView indexPathForCell:cell].row;
    MenuItem *menuItem = [self.tableViewManager getMenuItemOfRow:row];
    
    
    ShoppingCartItem *cartItem = [self.cartManager findoutShoppingItem:menuItem.menuItemId];
    if(cartItem == nil)
    {
        // not in cart yet, so add to cart
        button.backgroundColor = [UIColor grayColor];
        cartItem = [[ShoppingCartItem alloc] init];
        cartItem.title = menuItem.title;
        cartItem.price = menuItem.price;
        cartItem.menuItemId = menuItem.menuItemId;
        cartItem.quantity = 1;
        cartItem.imageURL = menuItem.imageURL;
        [self.cartManager addItem:cartItem];
    }else{
        // in cart already
        [self.cartManager removeItem:cartItem.menuItemId];
    }
    
    [self.tableView reloadData];
    [self updateRightBar];
}

- (void) tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    [self performSegueWithIdentifier:@"dishDetailSegue" sender:[self.tableView cellForRowAtIndexPath:indexPath]];
}

- (void) prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender
{
    if([segue.identifier isEqualToString:@"dishDetailSegue"])
    {
        NSInteger row =  [self.tableView indexPathForCell: (UITableViewCell *) sender].row;
        DishDetailViewController *detailVC = segue.destinationViewController;
        detailVC.menuItem = [self.tableViewManager getMenuItemOfRow:row];
    }
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
    if(self.tableViewManager.hasMore)
    {
        [self.tableViewManager loadMoreCellTableView];
    }
}

@end

@interface DishListTableViewManager()

@property (nonatomic, strong) NSMutableArray *dishList;
@property (nonatomic) NSInteger pageNumber;
@property (nonatomic, strong) ShoppingCartManager * cartManager;

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
        self.cartManager = [ShoppingCartManager getInstance];
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

- (MenuItem *) getMenuItemOfRow:(NSInteger) row
{
    return [self.dishList objectAtIndex:row];
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
    
    ShoppingCartItem *cartItem = [self.cartManager findoutShoppingItem:menuItem.menuItemId];
    if(cartItem == nil)
    {
        // item not in cart yet
        cartButton.backgroundColor = [UIColor colorWithRed:0.5 green:0.0 blue:0.25 alpha:1];
        [cartButton setTitle:@"点菜" forState:UIControlStateNormal];
    }else{
        // item in cart already
        cartButton.backgroundColor = [UIColor grayColor];
        [cartButton setTitle:@"已点" forState:UIControlStateNormal];

    }
    [cartButton removeFromSuperview];
    
    UIImageView *imageView = (UIImageView *) [cell viewWithTag:1];
    NSString *imageURL = [menuItem getImageURLWithResolution:@"100x100"];
    NSLog(@"image URL: %@", imageURL);
    [imageView setImageWithURL: [NSURL URLWithString:imageURL]];
    UILabel *titleLabel = (UILabel *)[cell viewWithTag:2];
    UILabel *priceLabel = (UILabel *)[cell viewWithTag:3];
    titleLabel.text = menuItem.title;
    priceLabel.text = [NSString stringWithFormat:@"%.1f元", [menuItem.price floatValue]];
    return cell;
}

@end
