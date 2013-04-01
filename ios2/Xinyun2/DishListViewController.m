//
//  DishListViewController.m
//  Xinyun2
//
//  Created by Mingqi Shao on 3/4/13.
//  Copyright (c) 2013 Mingqi Shao. All rights reserved.
//

#import "DishListViewController.h"
#import <QuartzCore/QuartzCore.h>

@interface DishListViewController ()

- (IBAction)checkOutAction:(UIBarButtonItem *)sender;

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
//	 NSLog(@"view did load");
//    NSLog(@"%@", self)
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

/**
 * UITableViewDataSource implement
 **/

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView{
    return 1;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:@"cell"];
    UIButton *cartButton = (UIButton *)[cell viewWithTag:4];
    cartButton.layer.cornerRadius = 5;
    return cell;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    return 3;
}



- (void) tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath{
    
    NSLog(@"row selected: %d", indexPath.row);
    [self performSegueWithIdentifier:@"dishDetailSegue" sender:self];
}

@end
