//
//  MenuViewController.m
//  Xingyun
//
//  Created by Mingqi Shao on 2/27/13.
//  Copyright (c) 2013 Mingqi Shao. All rights reserved.
//

#import "MenuViewController.h"
#import "QuartzCore/QuartzCore.h"
#import "Constants.h"


@interface MenuViewController ()

@property (nonatomic, strong) UISegmentedControl *topSegmentedControl;

@end

@implementation MenuViewController


- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if(self) {

    }
    return self;
}

- (void) resetBeforePush{
    self.navigationItem.rightBarButtonItem.title = @"跳过";
    self.navigationItem.rightBarButtonItem.tintColor =  DISH_LIST_NAV_BAR_RIGHT_BUTTON_COLOR;
    self.navigationItem.rightBarButtonItem.target = self;
    self.navigationItem.rightBarButtonItem.action = @selector(checkout);
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    self.title = @"点菜";
    
    /// draw heas view
    UIView *headView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, 320, DISH_LIST_HEAD_HEIGH)];
    headView.backgroundColor = DISH_LIST_HEAD_BACKGROUND_COLOR;
    [self.view addSubview:headView];
    
    // draw head section: segmented control
    [self drawTopSegmentedControl:headView];

    // draw table view of dish list
    [self drawDishListTableView];
    
    self.navigationItem.leftBarButtonItem.tintColor = [UIColor redColor];
}

- (void) drawDishListTableView{
    UITableView *dishListTableView = [[UITableView alloc] initWithFrame:CGRectMake(0, DISH_LIST_HEAD_HEIGH, 320, 460 - 44 - DISH_LIST_HEAD_HEIGH) style:UITableViewStylePlain];
    dishListTableView.backgroundColor = DISH_LIST_BACKGROUND_COLOR;
    dishListTableView.dataSource = self;
    dishListTableView.delegate = self;
    dishListTableView.separatorColor = [UIColor darkGrayColor];
    dishListTableView.rowHeight = DISH_CELL_HEIGHT;
    [self.view addSubview:dishListTableView];
}

- (void) drawTopSegmentedControl:(UIView *) superView{
    self.topSegmentedControl = [[UISegmentedControl alloc] initWithItems:@[@"全部", @"凉菜", @"热菜",@"其他"]];

    self.topSegmentedControl.frame =  CGRectMake(DISH_LIST_SEGMENTED_HORIZONTAL_SPACE, DISH_LIST_SEGMENTED_VIRTICAL_SPACE, superView.bounds.size.width - 2 * DISH_LIST_SEGMENTED_HORIZONTAL_SPACE, superView.bounds.size.height - 2 * DISH_LIST_SEGMENTED_VIRTICAL_SPACE);
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
        
        UIButton *addCartButton = [UIButton buttonWithType:UIButtonTypeCustom];
        addCartButton.backgroundColor = DISH_BUTTON_BACKGROUND_COLOR;
        addCartButton.frame = CGRectMake(320 - DISH_BUTTON_WIDTH - DISH_BUTTON_RIGHT_SPACE, DISH_CELL_HEIGHT/2 - DISH_BUTTON_HEIGHT/2, DISH_BUTTON_WIDTH, DISH_BUTTON_HEIGHT);
        addCartButton.layer.cornerRadius = DISH_BUTTON_CORNER_RADIUS;
        addCartButton.layer.borderWidth = DISH_BUTTON_BORDER_WIDTH;
        addCartButton.layer.borderColor = DISH_BUTTON_BORDER_COLOR.CGColor;
        UIImage *highlightedImage = [UIImage imageNamed:DISH_BUTTON_HIGHLIGHTED_IMAGE];
        [addCartButton setBackgroundImage:highlightedImage forState:UIControlStateHighlighted];
        [addCartButton setTitle:@"点菜" forState:UIControlStateNormal];
        addCartButton.titleLabel.textColor = DISH_BUTTON_TITLE_COLOR;
        addCartButton.titleLabel.font = [UIFont systemFontOfSize:DISH_BUTTON_TITLE_FONT_SIZE];
        [addCartButton addTarget:self action:@selector(addCartButtonTouchupInside:) forControlEvents:UIControlEventTouchUpInside];
        [cell.contentView addSubview:addCartButton];
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


- (UIView *)tableView:(UITableView *)tableView viewForFooterInSection:(NSInteger)section
{
    UIView *footerView = [UIView new];
//    footerView.backgroundColor = [UIColor yellowColor];
    return footerView;
    
}

- (void) tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath{
    NSLog(@"table row was selected: %d", indexPath.row);
    NSLog(@"navigation item %@", self.navigationItem.rightBarButtonItem.title);
    switch (indexPath.row) {
        case 3:
            break;
        case 1:
            break;
        case 2:
            break;
        default:
            break;
    }
}


/**********************************************
 * here is to handle cart button event
 **********************************************/
- (void) addCartButtonTouchupInside:(UIButton*)sender{
    NSLog(@"add cart button touch up inside");
    sender.backgroundColor = [UIColor darkGrayColor];
    [sender setTitle:@"已点" forState:UIControlStateNormal];
}

/*******************************************
 * the event when click checkout
 *******************************************/
- (void) checkout{
    NSLog(@"this is check out");
}
@end

