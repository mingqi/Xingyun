//
//  MenuViewController.m
//  Xingyun
//
//  Created by Mingqi Shao on 2/27/13.
//  Copyright (c) 2013 Mingqi Shao. All rights reserved.
//

#import "MenuViewController.h"
#import "QuartzCore/QuartzCore.h"


@interface MenuViewController ()

@property (nonatomic, strong) UISegmentedControl *topSegmentedControl;

@end

@implementation MenuViewController

NSInteger  HEAD_HEIGH;
UIColor *HEAD_BACKGROUND_COLOR;

NSInteger HORIZONTAL_SPACE;
NSInteger VIRTICAL_SPACE;
UIFont  *TITLE_FONT;

UIColor *DISH_LIST_BACKGROUND_COLOR ;
NSInteger DISH_CELL_HEIGHT;
NSInteger DISH_IMAGE_HEIGHT;
NSInteger DISH_IMAGE_WIDTH;
NSInteger DISH_IMAGE_HORIZONTAL_SPACE;
NSInteger DISH_IMAGE_VIRTICAL_SPACE;

NSInteger DISH_MAIN_LABEL_X;
NSInteger DISH_MAIN_LABEL_HEIGHT;
NSInteger DISH_MAIN_LABEL_WIDTH;
NSInteger DISH_LABEL_LEFT_SPACE;
NSInteger DISH_MAIN_LABEL_FONT_SIZE;
UIColor *DISH_MAIN_LABEL_FONT_COLOR;

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if(self) {
        HEAD_HEIGH = 44;
        HEAD_BACKGROUND_COLOR = [UIColor darkGrayColor];
        
        HORIZONTAL_SPACE = 7;
        VIRTICAL_SPACE = 7;
        TITLE_FONT = [UIFont systemFontOfSize:14];
        
        DISH_LIST_BACKGROUND_COLOR = [UIColor grayColor];
        DISH_CELL_HEIGHT = 90;
        DISH_IMAGE_HEIGHT = 60;
        DISH_IMAGE_WIDTH = 60;
        DISH_IMAGE_HORIZONTAL_SPACE = 15;
        DISH_IMAGE_VIRTICAL_SPACE = 15;
        
        DISH_MAIN_LABEL_X = 30;
        DISH_MAIN_LABEL_HEIGHT = 20;
        DISH_MAIN_LABEL_WIDTH = 100;
        DISH_LABEL_LEFT_SPACE = 15;
        DISH_MAIN_LABEL_FONT_SIZE = 12;
        DISH_MAIN_LABEL_FONT_COLOR = [UIColor colorWithWhite:1.0f alpha:1.0f];
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    self.title = @"点菜";
    
    /// draw heas view
    UIView *headView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, 320, HEAD_HEIGH)];
    headView.backgroundColor = HEAD_BACKGROUND_COLOR;
    [self.view addSubview:headView];
    
    // draw head section: segmented control
    [self drawTopSegmentedControl:headView];

    // draw table view of dish list
    [self drawDishListTableView];
    
}

- (void) drawDishListTableView{
    UITableView *dishListTableView = [[UITableView alloc] initWithFrame:CGRectMake(0, HEAD_HEIGH, 320, 460 - 44 - HEAD_HEIGH) style:UITableViewStylePlain];
    dishListTableView.backgroundColor = DISH_LIST_BACKGROUND_COLOR;
    dishListTableView.dataSource = self;
    dishListTableView.delegate = self;
    dishListTableView.separatorColor = [UIColor darkGrayColor];
    dishListTableView.rowHeight = DISH_CELL_HEIGHT;
    [self.view addSubview:dishListTableView];
}

- (void) drawTopSegmentedControl:(UIView *) superView{
    self.topSegmentedControl = [[UISegmentedControl alloc] initWithItems:@[@"全部", @"凉菜", @"热菜",@"其他"]];

    
    self.topSegmentedControl.frame =  CGRectMake(HORIZONTAL_SPACE, VIRTICAL_SPACE, superView.bounds.size.width - 2 * HORIZONTAL_SPACE, superView.bounds.size.height - 2 * VIRTICAL_SPACE);
    self.topSegmentedControl.segmentedControlStyle = UISegmentedControlStyleBar;
    
    NSDictionary *attributes = [NSDictionary dictionaryWithObject:TITLE_FONT
                                                           forKey:UITextAttributeFont];
    [self.topSegmentedControl setTitleTextAttributes:attributes forState:UIControlStateNormal];
    
    [self.topSegmentedControl addTarget:self action:@selector(segmentValueChanged:) forControlEvents:UIControlEventValueChanged];
    
    [self.view addSubview:self.topSegmentedControl];
}

- (void) viewDidAppear:(BOOL)animated{
    self.topSegmentedControl.selectedSegmentIndex = 0;
    [self segmentValueChanged:self.topSegmentedControl];
    NSLog(@"Menu root frame 22:%@", NSStringFromCGRect(self.view.frame));
}

- (void) segmentValueChanged:(UISegmentedControl *)segmentedControl{
    UIColor *const SELECTED_COLOR = [UIColor redColor];
    UIColor *const UNSELECTED_COLOR = [UIColor darkGrayColor];
    
    for (int i=0; i<[segmentedControl.subviews count]; i++)
    {
        if ([[segmentedControl.subviews objectAtIndex:i]isSelected] )
        {
           
            UIColor *tintcolor= SELECTED_COLOR;
            [[segmentedControl.subviews objectAtIndex:i] setTintColor:tintcolor];
        }
        if (![[segmentedControl.subviews objectAtIndex:i]isSelected] )
        {
            UIColor *tintcolor= UNSELECTED_COLOR;
            [[segmentedControl.subviews objectAtIndex:i] setTintColor:tintcolor];
        }
    }
}


- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

/**
 * implements of UITableDataSource
 */

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
    }
    
    switch (indexPath.row) {
        case 0:
//            cell.textLabel.text = @"餐厅简介";
            break;
        case 1:
//            cell.textLabel.text = @"经典菜品";
            break;
        case 2:
//            cell.textLabel.text = @"地图位置";
            break;
        case 3:
//            cell.textLabel.text = @"电话：010-66668888";
            break;
        default:
            break;
    }
//    cell.textLabel.font = [UIFont systemFontOfSize:14];
//    cell.selectionStyle = UITableViewCellSelectionStyleBlue;
    return cell;
}


/**********************************************
 * here is the implements of UITableViewDelegate
 **********************************************/

/*
- (float)tableView:(UITableView *)tableView heightForFooterInSection:(NSInteger)section {
    // This will create a "invisible" footer
    NSLog(@"height for footer");
    return 0.0f;
}


- (CGFloat)tableView:(UITableView *)tableView heightForHeaderInSection:(NSInteger)section{
    NSLog(@"height for header");
    return 0;
}
*/

- (UIView *)tableView:(UITableView *)tableView viewForFooterInSection:(NSInteger)section
{
    UIView *footerView = [UIView new];
    NSLog(@"footer section %d", section);
//    footerView.backgroundColor = [UIColor yellowColor];
    return footerView;
    
}
@end
