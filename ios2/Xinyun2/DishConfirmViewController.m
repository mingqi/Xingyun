//
//  DishConfirmViewController.m
//  Xinyun2
//
//  Created by Mingqi Shao on 3/5/13.
//  Copyright (c) 2013 Mingqi Shao. All rights reserved.
//

#import "DishConfirmViewController.h"
#import <QuartzCore/QuartzCore.h>

@interface DishConfirmViewController ()
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
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}


/**
 * UITableViewDataSource implement
 **/

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView{
    return 1;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:@"cell"];
    UIView *plusButton = [cell viewWithTag:3];
    plusButton.layer.cornerRadius = 3;
    
    UIView *minusButton = [cell viewWithTag:4];
    minusButton.layer.cornerRadius = 3;
    return cell;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    return 4;
}

- (IBAction)nextStepAction:(UIBarButtonItem *)sender {
    [self performSegueWithIdentifier:@"orderConfirmSegue" sender:self];
}
@end
