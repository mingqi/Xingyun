//
//  OrderConfirmViewController.m
//  Xingyun
//
//  Created by Mingqi Shao on 3/4/13.
//  Copyright (c) 2013 Mingqi Shao. All rights reserved.
//

#import "OrderConfirmViewController.h"
#import "QuartzCore/QuartzCore.h"
#import "Constants.h"


@interface OrderConfirmViewController ()

@end

@implementation OrderConfirmViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}


- (void) drawCell:(UIView *)groupView  title:(NSString *)title width:(CGFloat) width element:(UIView *)element index:(NSInteger)index{
    UILabel *titleLabel = [[UILabel alloc] initWithFrame:CGRectMake(ORDER_CONFIRM_CELL_LEFT_SPACE, index * ORDER_CONFIRM_CELL_HEIGHT - ORDER_CONFIRM_GROUP_BORDER_WIDTH, width, ORDER_CONFIRM_CELL_HEIGHT - ORDER_CONFIRM_GROUP_BORDER_WIDTH*2)];
    titleLabel.textColor = ORDER_CONFIRM_CELL_TEXT_COLOR;
    titleLabel.font = [UIFont systemFontOfSize:ORDER_CONFIRM_CELL_FONT_SIZE];
    titleLabel.text = [NSString stringWithFormat:@"%@:",title];
    titleLabel.backgroundColor = ORDER_CONFIRM_BACKGROUND_COLOR;
    [groupView addSubview:titleLabel];
}

- (UIView *) drawGroupView:(NSString *)title scrollView:(UIScrollView *)scrollView y:(CGFloat) y rowNumber:(NSInteger) rowNumber{
    // draw order information
    UILabel *headerLabel = [[UILabel alloc] initWithFrame:CGRectMake(ORDER_CONFIRM_GROUP_BORDER_SPACE, y, 320 - ORDER_CONFIRM_GROUP_BORDER_SPACE, ORDER_CONFIRM_GROUP_HEADER_HEIGHT)];
    headerLabel.text = title;
    headerLabel.backgroundColor = ORDER_CONFIRM_BACKGROUND_COLOR;
    headerLabel.textColor = ORDER_CONFIRM_GROUP_HEADER_TEXT_COLOR;
    [scrollView addSubview:headerLabel];
    
    UIView *groupView = [[UIView alloc] initWithFrame:CGRectMake(ORDER_CONFIRM_GROUP_BORDER_SPACE, BUTTOM(headerLabel), 320 - ORDER_CONFIRM_GROUP_BORDER_SPACE*2, ORDER_CONFIRM_CELL_HEIGHT * rowNumber)];
    groupView.layer.borderWidth = ORDER_CONFIRM_GROUP_BORDER_WIDTH;
    groupView.layer.cornerRadius = ORDER_CONFIRM_GROUP_CORNER_RADIUS;
    groupView.layer.borderColor = [ORDER_CONFIRM_GROUP_BORDER_COLOR CGColor];
//    [self drawSeparatorLine:orderInfoView rowNumber:3];
    for(int i = 0; i < rowNumber; i++){
        UIView *groupContentView = [[UIView alloc] initWithFrame:CGRectMake(0, ORDER_CONFIRM_CELL_HEIGHT*i, groupView.frame.size.width, ORDER_CONFIRM_CELL_HEIGHT)];
        groupContentView.backgroundColor = ORDER_CONFIRM_BACKGROUND_COLOR;
        groupContentView.tag = 1;
        
        if(i != rowNumber -1){
            UIView *separatorLine = [[UIView alloc] initWithFrame:CGRectMake(0, ORDER_CONFIRM_CELL_HEIGHT * i, 320 - ORDER_CONFIRM_GROUP_BORDER_SPACE*2, 1)];
            separatorLine.backgroundColor = ORDER_CONFIRM_GROUP_BORDER_COLOR;
            [groupView addSubview:separatorLine];
        }
    }
    [scrollView addSubview:groupView];
    return groupView;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    self.title = @"订单确认";
    
    UIScrollView *scrollView = [[UIScrollView alloc] initWithFrame:CGRectMake(0, 0, 320, 460 - 44)];
    scrollView.backgroundColor = ORDER_CONFIRM_BACKGROUND_COLOR;
    [self.view addSubview:scrollView];
    
    // draw order information
    UIView * orderInfoView = [self drawGroupView:@"基本信息" scrollView:scrollView y:0 rowNumber:5];
    UIView * dishInfoView = [self drawGroupView:@"菜品信息" scrollView:scrollView y:BUTTOM(orderInfoView) + ORDER_CONFIRM_GROUP_BETWEEN_SPACE rowNumber:2];
    
    UITextField *customerNameTextField = [[UITextField alloc] init];
//    [self drawCell:orderInfoView title:@"客户姓名" width:55 element:customerNameTextField index:0];
    [self drawCell:orderInfoView title:@"联系方式" width:55 element:customerNameTextField index:1];
//    [self drawCell:orderInfoView title:@"用餐人数" width:55 element:customerNameTextField index:2];
//    [self drawCell:orderInfoView title:@"是否包厢" width:55 element:customerNameTextField index:3];
//    [self drawCell:orderInfoView title:@"到店时间" width:55 element:customerNameTextField index:4];
    
    /*
    UILabel *orderInfoLabel = [[UILabel alloc] initWithFrame:CGRectMake(ORDER_CONFIRM_GROUP_BORDER_SPACE, 0, 320 - ORDER_CONFIRM_GROUP_BORDER_SPACE, ORDER_CONFIRM_GROUP_HEADER_HEIGHT)];
    orderInfoLabel.text = @"订单信息";
    orderInfoLabel.backgroundColor = ORDER_CONFIRM_BACKGROUND_COLOR;
    orderInfoLabel.textColor = ORDER_CONFIRM_GROUP_HEADER_TEXT_COLOR;
    [scrollView addSubview:orderInfoLabel];
    
    UIView *orderInfoView = [[UIView alloc] initWithFrame:CGRectMake(ORDER_CONFIRM_GROUP_BORDER_SPACE, BUTTOM(orderInfoLabel), 320 - ORDER_CONFIRM_GROUP_BORDER_SPACE*2, ORDER_CONFIRM_CELL_HEIGHT * 3)];
    orderInfoView.layer.borderWidth = ORDER_CONFIRM_GROUP_BORDER_WIDTH;
    orderInfoView.layer.cornerRadius = ORDER_CONFIRM_GROUP_CORNER_RADIUS;
    orderInfoView.layer.borderColor = [ORDER_CONFIRM_GROUP_BORDER_COLOR CGColor];
    [self drawSeparatorLine:orderInfoView rowNumber:3];
    [scrollView addSubview:orderInfoView];
    */
    
    
    /*
    UIView *view = [[UIView alloc] initWithFrame:CGRectMake(20, 20, 50,50)];
    view.layer.borderWidth = ORDER_CONFIRM_GROUP_BORDER_WIDTH;
    [self.view addSubview:view];
    */
}

- (CGFloat) buttomOfView:(UIView *) view{
    return view.frame.origin.y + view.frame.size.height;
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}


/******************************
 * implements of UITableViewDataSource
 */

/*
- (NSInteger) numberOfSectionsInTableView:(UITableView *)tableView{
    return 2;
}

- (NSInteger) tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    switch (section) {
        case 0:
            return 3;
            break;
        case 1:
            return 3;
            break;
        default:
            return 3;
            break;
    }
}


- (UITableViewCell *) tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
    NSString *cellIdentifier = @"cell";
    UITableViewCell *cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:cellIdentifier];
    return cell;
}

- (NSString *)tableView:(UITableView *)tableView titleForHeaderInSection:(NSInteger)section{
    return @"abc";
}
*/

@end
