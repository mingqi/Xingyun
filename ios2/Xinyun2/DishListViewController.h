//
//  DishListViewController.h
//  Xinyun2
//
//  Created by Mingqi Shao on 3/4/13.
//  Copyright (c) 2013 Mingqi Shao. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "Restfulservice.h"

@interface DishListViewController : UIViewController <UITableViewDataSource, UITableViewDelegate>

@end


@interface DishListTableViewManager : NSObject <UITableViewDataSource, LoadMenuItemDelegate>

@property (nonatomic, weak) UITableView *tableView;
@property (nonatomic, weak) MBProgressHUD *hub;
@property (nonatomic, weak) UILabel *tableFooterLabel;
@property (nonatomic) NSInteger category;
@property (nonatomic) BOOL hasMore;

- (void) reloadTableView;
- (void) loadMoreCellTableView;

@end