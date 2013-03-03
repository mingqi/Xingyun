//
//  RestaurantViewController.m
//  Xingyun
//
//  Created by Mingqi Shao on 2/26/13.
//  Copyright (c) 2013 Mingqi Shao. All rights reserved.
//

#import "RestaurantViewController.h"
#import "QuartzCore/QuartzCore.h"
#import "MenuViewController.h"
#import "MapViewController.h"

@interface RestaurantViewController ()

@property (strong, nonatomic) IBOutlet UITableView *tableView;
@property (strong, nonatomic) MenuViewController *menuViewController;
@property (strong, nonatomic) UIActionSheet *phoneCallSheet;
@property (strong, nonatomic) IBOutlet MapViewController *mapViewController;

@end

@implementation RestaurantViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        self.title = @"餐厅信息";
    }
    
    self.phoneCallSheet = [[UIActionSheet alloc] initWithTitle:@"Title Here"
                                                 delegate:self
                                        cancelButtonTitle:@"取消"
                                   destructiveButtonTitle:nil
                                        otherButtonTitles:@"Button 1", @"Button 2", nil];
    self.phoneCallSheet.actionSheetStyle = UIBarStyleBlackTranslucent;
    NSLog(@"cancel index: %d", self.phoneCallSheet.cancelButtonIndex);
    
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    
    // configuration table view of function list
    self.tableView.layer.borderWidth = 0.4;
    self.tableView.layer.cornerRadius = 10;
    self.tableView.rowHeight = 25;
    self.tableView.scrollEnabled = NO;
    
    self.menuViewController = [[MenuViewController alloc] init];
    NSLog(@"RestaurantView: x=%.1f, y=%.1f, width=%.1f, height=%.1f",
          self.view.bounds.origin.x,
          self.view.bounds.origin.y,
          self.view.bounds.size.width,
          self.view.bounds.size.height);
    
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void)viewDidUnload {
    [self setTableView:nil];
    [self setMenuViewController:nil];
    [self setMapViewController:nil];
    [super viewDidUnload];
}

/**
 * Here is implements of UITableViewDataSource
 */

- (NSInteger) numberOfSectionsInTableView:(UITableView *)tableView{
    return 1;
}

- (NSInteger) tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    return 4;
}

- (UITableViewCell*) tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
    
    NSString *cellIdentifier = @"cell";
    UITableViewCell *cell = [tableView dequeueReusableHeaderFooterViewWithIdentifier:cellIdentifier];
    if(cell == nil) {
        cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:cellIdentifier];
        cell.accessoryType = UITableViewCellAccessoryDisclosureIndicator;
    }
    
    switch (indexPath.row) {
        case 0:
            cell.textLabel.text = @"餐厅简介";
            break;
        case 1:
            cell.textLabel.text = @"经典菜品";
            break;
        case 2:
            cell.textLabel.text = @"地图位置";
            break;
        case 3:
            cell.textLabel.text = @"电话：010-66668888";
            break;
        default:
            break;
    }
    cell.textLabel.font = [UIFont systemFontOfSize:14];
    cell.selectionStyle = UITableViewCellSelectionStyleBlue;
    
    return cell;
   
    return cell;
}

/**
 * here is start implements of UITableViewDelegate
 */

- (void) tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath{
    NSLog(@"RestaurantView click: x=%.1f, y=%.1f, width=%.1f, height=%.1f",
          self.view.bounds.origin.x,
          self.view.bounds.origin.y,
          self.view.bounds.size.width,
          self.view.bounds.size.height);
    
    switch (indexPath.row) {
        case 3:
            self.phoneCallSheet.actionSheetStyle = UIBarStyleBlackTranslucent;
//            [self.phoneCallSheet showFromTabBar: self.tabBarController.tabBar];
            [self.phoneCallSheet showInView:self.tabBarController.view];
            break;
        case 1:
        {
            self.menuViewController.hidesBottomBarWhenPushed = YES;
            /*
            UIBarButtonItem *backButton = [[UIBarButtonItem alloc]
                                           initWithTitle: @"Back Button Text"
                                           style: UIBarButtonItemStyleBordered
                                           target: nil action: nil];
            */
            UIBarButtonItem *checkoutButton = [[UIBarButtonItem alloc] initWithTitle:@"" style:UIBarButtonItemStyleBordered target:nil action:nil];
            self.menuViewController.navigationItem.rightBarButtonItem = checkoutButton;
            [self.menuViewController resetBeforePush];
            [self.navigationController pushViewController:self.menuViewController animated:YES];
            
            break;
        }
        case 2:
            self.mapViewController.hidesBottomBarWhenPushed = YES;
            [self.navigationController pushViewController:self.mapViewController animated:YES];
            break;
            
        default:
            break;
    }
}

/**
 * here is the delegate of UIActionSheetDelegate
 */

- (void) actionSheetCancel:(UIActionSheet *)actionSheet{
    NSLog(@"action sheet cancelled");
}

- (void) actionSheet:(UIActionSheet *)actionSheet clickedButtonAtIndex:(NSInteger)buttonIndex{
    NSLog(@"action sheet at %d", buttonIndex);
    [[UIApplication sharedApplication] openURL:[NSURL URLWithString:@"tel://13811021667"]];
    NSLog(@"call number, %d", buttonIndex);
}

@end
