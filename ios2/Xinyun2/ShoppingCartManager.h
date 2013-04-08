//
//  ShoppingCartManager.h
//  Xingyun2
//
//  Created by Mingqi Shao on 4/7/13.
//  Copyright (c) 2013 Mingqi Shao. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface ShoppingCartItem : NSObject
@property (nonatomic, strong) NSString *title;
@property (nonatomic, strong) NSNumber *price;
@property (nonatomic, strong) NSString *imageURL;
@property (nonatomic) NSInteger quantity;
@property (nonatomic) NSInteger menuItemId;

@end

@interface ShoppingCartManager : NSObject

- (NSInteger) getItemUnits;

- (NSNumber *) getTotalPrice;

- (void) addItem:(ShoppingCartItem *) item;

- (void) updateItemQuantity:(NSInteger) menuItemId quantity:(NSInteger) quantity;

- (void) removeItem:(NSInteger)menuItemId;

- (void) cleanup;

- (ShoppingCartItem *)  findoutShoppingItem:(NSInteger) menuItemId;

+ (ShoppingCartManager *) getInstance;

@end
