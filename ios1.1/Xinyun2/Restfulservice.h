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

@protocol PlaceOrderDelegate <NSObject>

- (void) successPlaceOrder:(NSInteger) orderId;
- (void) failurePlaceOrder:(NSError *) error;

@end

@interface MenuItem : NSObject
@property (nonatomic) NSInteger menuItemId;
@property (nonatomic, strong) NSString *title;
@property (nonatomic, strong) NSNumber *price;
@property (nonatomic, strong) NSString *imageURL;
- (NSString *) getImageURLWithResolution:(NSString *) resolution;
@end

@interface Order : NSObject
@property (nonatomic) NSInteger customerId;
@property (nonatomic, strong) NSString* contactName;
@property (nonatomic, strong) NSString* contactPhone;
@property (nonatomic) NSInteger peopleNumber;
@property (nonatomic) BOOL boxRequired;
@property (nonatomic) float orderPrice;
@property (nonatomic) NSInteger dishesCount;
@property (nonatomic, strong) NSDate* reservedTime;
@property (nonatomic, strong) NSString* otherRequirements;
@property (nonatomic, strong) NSArray* orderDishes;
@end

@interface OrderDish : NSObject
@property (nonatomic) NSInteger menuItemId;
@property (nonatomic) NSInteger quantity;
@end

@interface Restfulservice : NSObject
- (id) initWiteRemoteAddress:(NSString *) remoteAddress;
- (void) getActivities:(id<GetActivitiesDelegate>)delegate withProgressHUB:(MBProgressHUD *)hub;
- (void) loadMenuItems: (id<LoadMenuItemDelegate>) delegate category:(NSInteger) category pageNum:(NSInteger) pageNum;
- (void) placeOrder:(id<PlaceOrderDelegate>) delegate order:(Order *) order;
+ (Restfulservice *) getService;

@end
