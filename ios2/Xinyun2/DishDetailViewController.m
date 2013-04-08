//
//  DishDetailViewController.m
//  Xingyun2
//
//  Created by Mingqi Shao on 3/6/13.
//  Copyright (c) 2013 Mingqi Shao. All rights reserved.
//

#import "DishDetailViewController.h"
#import <QuartzCore/QuartzCore.h>
#import "UIImageView+AFNetworking.h"
#import "ShoppingCartManager.h"

@interface DishDetailViewController ()

@property (strong, nonatomic) IBOutlet UILabel *titleLabel;
@property (strong, nonatomic) IBOutlet UILabel *priceLabel;
@property (strong, nonatomic) IBOutlet UIButton *cartButton;
@property (strong, nonatomic) IBOutlet UIImageView *image;
@property (strong, nonatomic) ShoppingCartManager *cartManager;
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
    self.titleLabel.text = self.menuItem.title;
    self.priceLabel.text = [NSString stringWithFormat:@"%.1f元", [self.menuItem.price floatValue]];
    [self.image setImageWithURL:[NSURL URLWithString:[self.menuItem getImageURLWithResolution:@"400x400"]]];
    self.cartManager = [ShoppingCartManager getInstance];
    [self updateCartButton];
}
- (IBAction)cartButtonTaped:(id)sender {
    UIButton *button = (UIButton *)sender;
    
    ShoppingCartItem *cartItem = [self.cartManager findoutShoppingItem:self.menuItem.menuItemId];
    NSLog(@"%d", self.menuItem.menuItemId);
    if(cartItem == nil)
    {
        // not in cart yet, so add to cart
        button.backgroundColor = [UIColor grayColor];
        cartItem = [[ShoppingCartItem alloc] init];
        cartItem.title = self.menuItem.title;
        cartItem.price = self.menuItem.price;
        cartItem.menuItemId = self.menuItem.menuItemId;
        cartItem.quantity = 1;
        cartItem.imageURL = self.menuItem.imageURL;
        [self.cartManager addItem:cartItem];
    }else{
        // in cart already
        [self.cartManager removeItem:cartItem.menuItemId];
    }
    
    [self updateCartButton];
}

- (void) updateCartButton
{
    
    ShoppingCartItem *cartItem = [self.cartManager findoutShoppingItem:self.menuItem.menuItemId];
    if(cartItem == nil)
    {
        // item not in cart yet
        self.cartButton.backgroundColor = [UIColor colorWithRed:0.5 green:0.0 blue:0.25 alpha:1];
        [self.cartButton setTitle:@"点菜" forState:UIControlStateNormal];
    }else{
        // item in cart already
        self.cartButton.backgroundColor = [UIColor grayColor];
        [self.cartButton setTitle:@"已点" forState:UIControlStateNormal];
    }
}

- (void) viewDidLayoutSubviews
{
    [self.titleLabel sizeToFit];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
