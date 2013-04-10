//
//  HistoryOrderHomeViewController.m
//  Xingyun2
//
//  Created by Mingqi Shao on 4/9/13.
//  Copyright (c) 2013 Mingqi Shao. All rights reserved.
//

#import "HistoryOrderHomeViewController.h"

@interface HistoryOrderHomeViewController ()

@end

@implementation HistoryOrderHomeViewController

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
	// Do any additional setup after loading the view.
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}
- (IBAction)nextStepAction:(id)sender {
    [self performSegueWithIdentifier:@"orderListSegue" sender:self];
}

@end
