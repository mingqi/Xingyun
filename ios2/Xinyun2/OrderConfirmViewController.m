//
//  OrderConfirmViewController.m
//  Xinyun2
//
//  Created by Mingqi Shao on 3/5/13.
//  Copyright (c) 2013 Mingqi Shao. All rights reserved.
//

#import "OrderConfirmViewController.h"
#import <QuartzCore/QuartzCore.h>

@interface OrderConfirmViewController ()
@property (strong, nonatomic) IBOutlet UITextField *customerNameTextField;
@property (strong, nonatomic) IBOutlet UITextField *telPhoneTextField;
@property (strong, nonatomic) IBOutlet UITextField *peopleNumberTextField;

@end

@implementation OrderConfirmViewController

- (id)initWithStyle:(UITableViewStyle)style
{
    self = [super initWithStyle:style];
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

- (BOOL)textFieldShouldReturn:(UITextField *)theTextField {
    [theTextField resignFirstResponder];
    return YES;
}


- (void)scrollViewWillBeginDragging:(UIScrollView *)scrollView{
    if ([self.customerNameTextField isFirstResponder]) {
        [self.customerNameTextField resignFirstResponder];
    }else if( [self.telPhoneTextField isFirstResponder]){
        [self.telPhoneTextField resignFirstResponder];
    }else if([self.peopleNumberTextField isFirstResponder]){
        [self.peopleNumberTextField resignFirstResponder];
    }
}
#pragma mark - Table view data source
/*
- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return 3;
}
*/
/*
- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *CellIdentifier = @"Cell";
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier forIndexPath:indexPath];
    
    // Configure the cell...
    
    return cell;
}
*/

/*
// Override to support conditional editing of the table view.
- (BOOL)tableView:(UITableView *)tableView canEditRowAtIndexPath:(NSIndexPath *)indexPath
{
    // Return NO if you do not want the specified item to be editable.
    return YES;
}
*/

/*
// Override to support editing the table view.
- (void)tableView:(UITableView *)tableView commitEditingStyle:(UITableViewCellEditingStyle)editingStyle forRowAtIndexPath:(NSIndexPath *)indexPath
{
    if (editingStyle == UITableViewCellEditingStyleDelete) {
        // Delete the row from the data source
        [tableView deleteRowsAtIndexPaths:@[indexPath] withRowAnimation:UITableViewRowAnimationFade];
    }   
    else if (editingStyle == UITableViewCellEditingStyleInsert) {
        // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view
    }   
}
*/

/*
// Override to support rearranging the table view.
- (void)tableView:(UITableView *)tableView moveRowAtIndexPath:(NSIndexPath *)fromIndexPath toIndexPath:(NSIndexPath *)toIndexPath
{
}
*/

/*
// Override to support conditional rearranging of the table view.
- (BOOL)tableView:(UITableView *)tableView canMoveRowAtIndexPath:(NSIndexPath *)indexPath
{
    // Return NO if you do not want the item to be re-orderable.
    return YES;
}
*/

#pragma mark - Table view delegate

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    /*
    if(indexPath.section == 0 && indexPath.row == 4){
        UIDatePicker *datePicker = [[UIDatePicker alloc] initWithFrame:CGRectMake(0, 250, 320, 300)];
        datePicker.datePickerMode = UIDatePickerModeDateAndTime;
        [self.view addSubview:datePicker];
        
    }
    */
}

@end
