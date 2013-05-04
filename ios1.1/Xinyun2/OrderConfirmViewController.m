//
//  OrderConfirmViewController.m
//  Xinyun2
//
//  Created by Mingqi Shao on 3/5/13.
//  Copyright (c) 2013 Mingqi Shao. All rights reserved.
//

#import "OrderConfirmViewController.h"
#import <QuartzCore/QuartzCore.h>
#import "Restfulservice.h"
#import "ShoppingCartManager.h"
#import "MBProgressHUD.h"
#import "KeychainItemWrapper.h"


@interface OrderConfirmViewController ()
@property (strong, nonatomic) IBOutlet UITextField *customerNameTextField;
@property (strong, nonatomic) IBOutlet UITextField *telPhoneTextField;
@property (strong, nonatomic) IBOutlet UITextField *peopleNumberTextField;
@property (strong, nonatomic) IBOutlet UISwitch *boxRequiredSwitch;
@property (strong, nonatomic) UIDatePicker *datePicker;
@property (strong, nonatomic) IBOutlet UITextField *reservedTimeTextField;
@property (strong, nonatomic) IBOutlet UITextView *otherRequirementTextView;
@property (strong, nonatomic) ShoppingCartManager *cartManager;
@property (strong, nonatomic) NSDate *reservedTime;
@property (strong, nonatomic) Restfulservice *restService;
@property (strong, nonatomic) MBProgressHUD *hub;
@property (strong, nonatomic) KeychainItemWrapper *keychainItem;
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
    self.datePicker = [[UIDatePicker alloc] initWithFrame:CGRectMake(0, 250, 320, 300)];
    self.datePicker.datePickerMode = UIDatePickerModeDateAndTime;
    self.datePicker.minuteInterval = 15;
    [self.datePicker addTarget:self action:@selector(datePickerValueChanged:) forControlEvents:UIControlEventValueChanged];
    
    self.reservedTimeTextField.inputView = self.datePicker;
    self.cartManager = [ShoppingCartManager getInstance];
    self.restService = [Restfulservice getService];
    
    self.hub = [[MBProgressHUD alloc] initWithView:self.view];
    [self.view addSubview: self.hub];
    [self.hub hide:YES];
    
    self.keychainItem = [[KeychainItemWrapper alloc] initWithIdentifier:@"Xingyun" accessGroup:nil];
}

- (void) viewDidAppear:(BOOL)animated{
    self.customerNameTextField.text = [self.keychainItem objectForKey:(__bridge id)(kSecAttrAccount)];
    self.telPhoneTextField.text = [self.keychainItem objectForKey:(__bridge id)kSecValueData];
}

- (void) datePickerValueChanged:(UIDatePicker *) datePicker
{
    NSDateFormatter *fmt = [[NSDateFormatter alloc] init];
    [fmt setDateFormat:@"yyyy-MM-dd HH:mm"];
    self.reservedTimeTextField.text = [fmt stringFromDate:datePicker.date];
    self.reservedTime = datePicker.date;
}
- (IBAction)placeOrderAction:(id)sender {
    UIAlertView *illegalInputAlertView = [[UIAlertView alloc] initWithTitle:@"输入错误，请重新输入" message:nil delegate:nil cancelButtonTitle:@"知道了" otherButtonTitles:nil];
    if(self.customerNameTextField.text == nil || [self.customerNameTextField.text length] == 0){
        illegalInputAlertView.title = @"请填写联系人";
        [illegalInputAlertView show];
        return;
    }
    if(self.telPhoneTextField.text == nil || [self.telPhoneTextField.text length] == 0){
        illegalInputAlertView.title = @"请填写联系电话";
        [illegalInputAlertView show];
        return;
    }
    if(self.peopleNumberTextField.text == nil || [self.peopleNumberTextField.text length] == 0){
        illegalInputAlertView.title = @"请提供用餐人数";
        [illegalInputAlertView show];
        return;
    }
    if(self.reservedTimeTextField.text == nil || [self.reservedTimeTextField.text length] == 0){
        illegalInputAlertView.title = @"请填写预约就餐时间";
        [illegalInputAlertView show];
        return;
    }

    
    Order *order = [[Order alloc] init];
    order.customerId = 0;
    order.contactName = self.customerNameTextField.text;
    order.contactPhone = self.telPhoneTextField.text;
    order.peopleNumber = [self.peopleNumberTextField.text intValue];
    order.boxRequired = [self.boxRequiredSwitch isOn];
    order.orderPrice = [[self.cartManager getTotalPrice] floatValue];
    order.dishesCount = [self.cartManager getItemUnits];
    order.reservedTime = self.reservedTime;
    order.otherRequirements = self.otherRequirementTextView.text;
    
    NSMutableArray *dishesArray = [NSMutableArray arrayWithCapacity:10];
    for(ShoppingCartItem *cartItem in [self.cartManager getItemArray]){
        OrderDish *dish = [[OrderDish alloc] init];
        dish.menuItemId = cartItem.menuItemId;
        dish.quantity = cartItem.quantity;
        [dishesArray addObject:dish];
    }
    order.orderDishes = dishesArray;
    [self.hub show:YES];
    [self.restService placeOrder:self order:order];
    
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
    }else if( [self.reservedTimeTextField isFirstResponder]){
        [self.reservedTimeTextField resignFirstResponder];
    }else if([self.otherRequirementTextView isFirstResponder]){
        [self.otherRequirementTextView resignFirstResponder];
    }
}

- (void) successPlaceOrder:(NSInteger) orderId{
    [self.keychainItem setObject:self.customerNameTextField.text forKey:(__bridge id)(kSecAttrAccount)];
    [self.keychainItem setObject:self.telPhoneTextField.text forKey:(__bridge id)(kSecValueData)];
    [self.hub hide:YES];
    UIAlertView *successAlterView = [[UIAlertView alloc] initWithTitle:@"预定成功，稍后餐厅会电话和您确认" message:nil delegate:self cancelButtonTitle:@"知道了" otherButtonTitles:nil];
    [self.cartManager cleanup];
    [successAlterView show];
}
- (void) failurePlaceOrder:(NSError *) error{
    [self.hub hide:YES];
    UIAlertView *successAlterView = [[UIAlertView alloc] initWithTitle:@"系统出错，请稍后再试或者电话联系餐厅" message:nil delegate:self cancelButtonTitle:@"知道了" otherButtonTitles:nil];
    [successAlterView show];
}

- (void) alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex{
    [self.navigationController popToRootViewControllerAnimated:YES];
}

@end
