//
//  Restfulservice.h
//  Xingyun2
//
//  Created by Mingqi Shao on 4/2/13.
//  Copyright (c) 2013 Mingqi Shao. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "MBProgressHUD.h"

@protocol GetActivitiesDelegate <NSObject>

- (void) successLoadActivities:(NSArray *) images;
- (void) failureLoadActivities:(NSError *) error;

@end

@protocol LoadMenuItemDelegate <NSObject>

- (void) successLoad:(NSArray *) menuItems pageNumber:(NSInteger) pageNum hasMore:(BOOL) hasMore;
- (void) failureLoad:(NSError *) error;
@end

@interface MenuItem : NSObject
@property (nonatomic, strong) NSString *title;
@property (nonatomic, strong) NSNumber *price;
@property (nonatomic, strong) NSString *imageURL;
@end

@interface Restfulservice : NSObject

- (id) initWiteRemoteAddress:(NSString *) remoteAddress;
- (void) getActivities:(id<GetActivitiesDelegate>)delegate withProgressHUB:(MBProgressHUD *)hub;
- (void) loadMenuItems: (id<LoadMenuItemDelegate>) delegate category:(NSInteger) category pageNum:(NSInteger) pageNum;

+ (Restfulservice *) getService;

@end
