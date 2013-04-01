//
//  RestViewController.m
//  Xinyun2
//
//  Created by Mingqi Shao on 3/4/13.
//  Copyright (c) 2013 Mingqi Shao. All rights reserved.
//

#import "RestViewController.h"
#import <QuartzCore/QuartzCore.h>

@interface RestViewController ()
- (IBAction)checkoutButtonTouchUpInside:(id)sender;
@property (strong, nonatomic) IBOutlet UIButton *placeOrderButton;
@property (strong, nonatomic) UIActionSheet *phoneCallSheet;
@property (strong, nonatomic) IBOutlet UITableView *tableView;
@end

@implementation RestViewController

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
	
    self.placeOrderButton.layer.cornerRadius = 7;
    
    self.phoneCallSheet = [[UIActionSheet alloc] initWithTitle:@"拨打电话?"
                                                      delegate:self
                                             cancelButtonTitle:@"取消"
                                        destructiveButtonTitle:nil
                                             otherButtonTitles:@"0996-2218222", nil];
    self.phoneCallSheet.actionSheetStyle = UIBarStyleBlackTranslucent;
}

- (void) viewWillAppear:(BOOL)animated
{
    [self.tableView deselectRowAtIndexPath:[self.tableView indexPathForSelectedRow] animated:YES];
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
    NSString *cellTitle = @"";
    switch (indexPath.row) {
        case 0:
            cellTitle = @"餐厅介绍";
            break;
            
        case 1:
            cellTitle = @"所有菜品";
            break;
        case 2:
            cellTitle = @"地图位置";
            break;
        case 3:
            cellTitle = @"联系电话: 0996-2218222";
            break;
        default:
            break;
    }
    cell.textLabel.text = cellTitle;
     return cell;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    return 4;
}


- (void) tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath{
    switch (indexPath.row) {
        case 0:
            [self performSegueWithIdentifier:@"introductionSegue" sender:self];
            break;
        case 1:
            [self performSegueWithIdentifier:@"dishListSegue" sender:self];
            break;
        case 2:
            [self performSegueWithIdentifier:@"mapSegue" sender:self];
            break;
        case 3:
            [self.phoneCallSheet showInView:self.tabBarController.view];
            break;
        default:
            break;
    }
    
    //[tableView deselectRowAtIndexPath:indexPath animated:YES];
    
}

/***
 * Storyboard methods
 */

- (void) prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender{
    [segue.destinationViewController setHidesBottomBarWhenPushed:YES];
}

/**
 * here is the delegate of UIActionSheetDelegate
 */

- (void) actionSheetCancel:(UIActionSheet *)actionSheet{
    NSLog(@"action sheet cancelled");
}

- (void) actionSheet:(UIActionSheet *)actionSheet clickedButtonAtIndex:(NSInteger)buttonIndex{
    switch (buttonIndex) {
        case 0:
            [[UIApplication sharedApplication] openURL:[NSURL URLWithString:@"tel://0996-2218222"]];
            break;
        case 1:
            [actionSheet dismissWithClickedButtonIndex:buttonIndex animated:YES];
            break;
        default:
            break;
    }
}

- (IBAction)checkoutButtonTouchUpInside:(id)sender {
    [self performSegueWithIdentifier:@"dishListSegue" sender:self];
}
@end
