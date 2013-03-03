//
//  DishConfirmViewController.m
//  Xingyun
//
//  Created by Mingqi Shao on 3/3/13.
//  Copyright (c) 2013 Mingqi Shao. All rights reserved.
//

#import "DishConfirmViewController.h"
#import "Constants.h"
#import "QuartzCore/QuartzCore.h"


@interface DishConfirmViewController ()

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
    self.title = DISH_CONFIRM_VIEW_TITLE;
    self.view.backgroundColor = BODY_BACKGROUND_COLOR;
    
    /// draw heas view
    UIView *headView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, 320, DISH_CONFIRM_HEAD_HEIGH)];
    headView.backgroundColor = DISH_LIST_HEAD_BACKGROUND_COLOR;
    [self.view addSubview:headView];

    // draw head section: segmented control
    [self drawTopHead:headView];

    // draw table view of dish list
    [self drawDishListTableView];
//
//    self.navigationItem.leftBarButtonItem.tintColor = [UIColor redColor];
}

- (void) drawTopHead:(UIView *) headView{
    UILabel *confirmMsgLabel = [[UILabel alloc] initWithFrame:CGRectMake(DISH_CONFIRM_HEAD_LEFT_SPACE, 0, 320 * DISH_CONFIRM_MSG_RATIO, DISH_CONFIRM_HEAD_HEIGH)];
    confirmMsgLabel.backgroundColor = headView.backgroundColor;
    confirmMsgLabel.text = DISH_CONFIRM_MSG;
    confirmMsgLabel.font = [UIFont systemFontOfSize:DISH_CONFIRM_MSG_FONT_SIZE];
    confirmMsgLabel.textColor = DISH_CONFIRM_MSG_FONT_COLOR;
    [headView addSubview:confirmMsgLabel];
    
    UILabel *totalPayLabel = [[UILabel alloc] initWithFrame:CGRectMake(320 * DISH_CONFIRM_MSG_RATIO, 0, 320 * (1-DISH_CONFIRM_MSG_RATIO), DISH_CONFIRM_HEAD_HEIGH)];
    totalPayLabel.backgroundColor = headView.backgroundColor;
    totalPayLabel.text = [NSString stringWithFormat:DISH_CONFIRM_PAY_MSG, 88.0f];
    totalPayLabel.font = [UIFont systemFontOfSize:DISH_CONFIRM_PAY_FONT_SIZE];
    totalPayLabel.textColor = DISH_CONFIRM_PAY_FONT_COLOR;
    [headView addSubview:totalPayLabel];
}

- (void) drawDishListTableView{
    UITableView *dishListTableView = [[UITableView alloc] initWithFrame:CGRectMake(0, DISH_LIST_HEAD_HEIGH, 320, 460 - 44 - DISH_LIST_HEAD_HEIGH) style:UITableViewStylePlain];
    dishListTableView.backgroundColor = DISH_LIST_BACKGROUND_COLOR;
    dishListTableView.dataSource = self;
    dishListTableView.delegate = self;
    dishListTableView.rowHeight = DISH_CELL_HEIGHT;
    dishListTableView.separatorStyle = UITableViewCellSeparatorStyleNone;
    [self.view addSubview:dishListTableView];
}


- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}


/************************************************
 ** Here is implements of UITableViewDataSource
 ************************************************/

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
        cell.selectionStyle = UITableViewCellSelectionStyleNone;
        
        // create left  image
        UIImage *image = [UIImage imageNamed:@"images/dish.jpg"];
        UIImageView *imgV = [[UIImageView alloc] initWithFrame:CGRectMake(DISH_IMAGE_HORIZONTAL_SPACE, DISH_IMAGE_VIRTICAL_SPACE, DISH_IMAGE_WIDTH, DISH_IMAGE_HEIGHT)];
        imgV.image = image;
        [cell.contentView addSubview:imgV];
        
        UILabel *mainLabel = [[UILabel alloc] initWithFrame:CGRectMake(DISH_IMAGE_HORIZONTAL_SPACE + DISH_IMAGE_WIDTH + DISH_LABEL_LEFT_SPACE, DISH_MAIN_LABEL_X, DISH_MAIN_LABEL_WIDTH, DISH_MAIN_LABEL_HEIGHT)];
        mainLabel.text = @"这是标题";
        mainLabel.font = [UIFont systemFontOfSize:DISH_MAIN_LABEL_FONT_SIZE];
        mainLabel.textAlignment = UITextAlignmentLeft;
        mainLabel.textColor = DISH_MAIN_LABEL_FONT_COLOR;
        mainLabel.backgroundColor = DISH_LIST_BACKGROUND_COLOR;
        [cell.contentView addSubview:mainLabel];
        
        UILabel *secondLabel = [[UILabel alloc] initWithFrame:CGRectMake(mainLabel.frame.origin.x, mainLabel.frame.origin.y + mainLabel.frame.size.height + DISH_SECOND_LABEL_TOP_SPACE, DISH_SECOND_LABEL_WIDTH, DISH_SECOND_LABEL_HEIGHT)];
        secondLabel.text = @"这是副标题";
        secondLabel.font = [UIFont systemFontOfSize:DISH_SECOND_LABEL_FONT_SIZE];
        secondLabel.textAlignment = UITextAlignmentLeft;
        secondLabel.textColor = DISH_SECOND_LABEL_FONT_COLOR;
        secondLabel.backgroundColor = DISH_LIST_BACKGROUND_COLOR;
        [cell.contentView addSubview:secondLabel];
        
        UIButton *plusButton = [UIButton buttonWithType:UIButtonTypeCustom];
        plusButton.backgroundColor = BODY_BACKGROUND_COLOR;
        plusButton.frame = CGRectMake(320 - DISH_CONFIRM_BUTTON_WIDTH - DISH_BUTTON_RIGHT_SPACE, DISH_IMAGE_VIRTICAL_SPACE, DISH_CONFIRM_BUTTON_WIDTH, DISH_CONFIRM_BUTTON_HEIGHT);
        plusButton.layer.cornerRadius = DISH_CONFIRM_BUTTON_CORNER_RADIUS;
        plusButton.layer.borderWidth = DISH_CONFIRM_BUTTON_BORDER_WIDTH;
        plusButton.layer.borderColor = DISH_CONFIRM_BUTTON_BORDER_COLOR.CGColor;
        [plusButton setTitle:@"+" forState:UIControlStateNormal];
        [plusButton setTitleColor:DISH_CONFIRM_BUTTON_TITLE_COLOR forState:UIControlStateNormal];
        plusButton.titleLabel.font = [UIFont systemFontOfSize:DISH_CONFIRM_BUTTON_TITLE_FONT_SIZE];
        [cell.contentView addSubview:plusButton];

        
        UIButton *minusButton = [UIButton buttonWithType:UIButtonTypeCustom];
        minusButton.backgroundColor = BODY_BACKGROUND_COLOR;
        minusButton.frame = CGRectMake(plusButton.frame.origin.x, DISH_CELL_HEIGHT - DISH_IMAGE_VIRTICAL_SPACE - DISH_CONFIRM_BUTTON_HEIGHT, DISH_CONFIRM_BUTTON_WIDTH, DISH_CONFIRM_BUTTON_HEIGHT);
        minusButton.layer.cornerRadius = DISH_CONFIRM_BUTTON_CORNER_RADIUS;
        minusButton.layer.borderWidth = DISH_CONFIRM_BUTTON_BORDER_WIDTH;
        minusButton.layer.borderColor = DISH_CONFIRM_BUTTON_BORDER_COLOR.CGColor;
        [minusButton setTitle:@"-" forState:UIControlStateNormal];
        [minusButton setTitleColor:DISH_CONFIRM_BUTTON_TITLE_COLOR forState:UIControlStateNormal];
        minusButton.titleLabel.font = [UIFont systemFontOfSize:DISH_CONFIRM_BUTTON_TITLE_FONT_SIZE];
        [cell.contentView addSubview:minusButton];
        
        UILabel *numberLabel = [[UILabel alloc] initWithFrame:CGRectMake(plusButton.frame.origin.x, DISH_CELL_HEIGHT / 2 - DISH_CONFIRM_NUMBER_LABEL_HEIGHT / 2, plusButton.frame.size.width, DISH_CONFIRM_NUMBER_LABEL_HEIGHT)];
        numberLabel.text = @"2";
        numberLabel.font = [UIFont systemFontOfSize:DISH_MAIN_LABEL_FONT_SIZE];
        numberLabel.textAlignment = UITextAlignmentCenter;
        numberLabel.textColor = DISH_MAIN_LABEL_FONT_COLOR;
        numberLabel.layer.cornerRadius = DISH_CONFIRM_BUTTON_CORNER_RADIUS;
        numberLabel.backgroundColor = DISH_CONFIRM_NUMBER_LABEL_BACKGROUND_COLOR;
        [cell.contentView addSubview:numberLabel];

        // separator line
        UIView *separatorLine = [[UIView alloc] initWithFrame:CGRectMake(0, DISH_CELL_HEIGHT, 320, DISH_CELL_SEPARATOR_HEIGHT)];
        separatorLine.backgroundColor = [UIColor darkGrayColor];
        [cell.contentView addSubview:separatorLine];
        
    }
    
    //    cell.textLabel.font = [UIFont systemFontOfSize:14];
    //    cell.selectionStyle = UITableViewCellSelectionStyleBlue;
    return cell;
}

/**********************************************
 * here is the implements of UITableViewDelegate
 **********************************************/


@end
