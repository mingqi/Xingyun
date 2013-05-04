//
//  DishConfirmViewController.m
//  Xinyun2
//
//  Created by Mingqi Shao on 3/5/13.
//  Copyright (c) 2013 Mingqi Shao. All rights reserved.
//

#import "DishConfirmViewController.h"
#import <QuartzCore/QuartzCore.h>
#import "ShoppingCartManager.h"
#import "UIImageView+AFNetworking.h"

@interface DishConfirmViewController ()
@property (strong, nonatomic) IBOutlet UITableView *tableView;
@property (strong, nonatomic) IBOutlet UILabel *totalPriceLabel;
@property (nonatomic, strong) ShoppingCartManager *cartManager;
- (IBAction)nextStepAction:(UIBarButtonItem *)sender;
@end

@implementation DishConfirmViewController

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
    self.cartManager = [ShoppingCartManager getInstance];
    
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
    self.tableView.tableFooterView = footerView;
    
    [self updateTotalPrice];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void) updateTotalPrice
{
    self.totalPriceLabel.text = [NSString stringWithFormat:@"总计: %.1f元", [[self.cartManager getTotalPrice] floatValue]];
}

/**
 * UITableViewDataSource implement
 **/

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView{
    return 1;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:@"cell"];
    UIImageView *image = (UIImageView *) [cell viewWithTag:6];
    UILabel *titleLabel = (UILabel *)[cell viewWithTag:1];
    UILabel *priceLabe = (UILabel *) [cell viewWithTag:2];
    UIView *plusButton = [cell viewWithTag:3];
    UIView *minusButton = [cell viewWithTag:4];
    UILabel *quantityLabel = (UILabel *) [cell viewWithTag:5];
    
    plusButton.layer.cornerRadius = 3;
    minusButton.layer.cornerRadius = 3;
    
    NSArray *cartItemArray = [self.cartManager getItemArray];
    ShoppingCartItem *cartItem = [cartItemArray objectAtIndex:indexPath.row];
    titleLabel.text = cartItem.title;
    priceLabe.text = [NSString stringWithFormat:@"%.1f", [cartItem.price floatValue]];
    quantityLabel.text = [NSString stringWithFormat:@"%d", cartItem.quantity];
    NSString *imageURL = [cartItem getImageURLWithResolution:@"100x100"];
    [image setImageWithURL:[NSURL URLWithString:imageURL]];
    
    return cell;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    return [[self.cartManager getItemArray] count];
}
- (IBAction)plusButtonTaped:(id)sender {
    UIButton *button = (UIButton *)sender;
    UITableViewCell* cell = (UITableViewCell*)[[button superview] superview];
    int row = [self.tableView indexPathForCell:cell].row;
    NSArray *cartItemArray = [self.cartManager getItemArray];
    ShoppingCartItem *cartItem = [cartItemArray objectAtIndex:row];
    [self.cartManager updateItemQuantity:cartItem.menuItemId quantity:cartItem.quantity +1];
    
    [self updateTotalPrice];
    [self.tableView reloadData];
}
- (IBAction)minusButtonTaped:(id)sender {
    UIButton *button = (UIButton *)sender;
    UITableViewCell* cell = (UITableViewCell*)[[button superview] superview];
    int row = [self.tableView indexPathForCell:cell].row;
    NSArray *cartItemArray = [self.cartManager getItemArray];
    ShoppingCartItem *cartItem = [cartItemArray objectAtIndex:row];
    
    if(cartItem.quantity > 1){
        [self.cartManager updateItemQuantity:cartItem.menuItemId quantity:cartItem.quantity -1];
    }else{
        [self.cartManager removeItem:cartItem.menuItemId];
    }
    
    [self updateTotalPrice];
    [self.tableView reloadData];
    
}

- (IBAction)nextStepAction:(UIBarButtonItem *)sender {
    [self performSegueWithIdentifier:@"orderConfirmSegue" sender:self];
}
@end
