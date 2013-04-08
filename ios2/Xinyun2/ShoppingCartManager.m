//
//  ShoppingCartManager.m
//  Xingyun2
//
//  Created by Mingqi Shao on 4/7/13.
//  Copyright (c) 2013 Mingqi Shao. All rights reserved.
//

#import "ShoppingCartManager.h"

@implementation ShoppingCartItem

@end

@interface ShoppingCartManager ()

@property  (nonatomic, strong) NSMutableArray *itemArray;

@end

@implementation ShoppingCartManager

+ (ShoppingCartManager *) getInstance
{
    static ShoppingCartManager *instance = nil;
    if(instance == nil)
    {
        instance = [[ShoppingCartManager alloc] init];
    }
    return instance;
}

- (ShoppingCartManager *) init
{
    if(self == [super init])
    {
        self.itemArray = [NSMutableArray arrayWithCapacity:10];
    }
    return self;
}

- (NSInteger) getItemUnits
{
    NSInteger units = 0;
    for(ShoppingCartItem * item in self.itemArray)
    {
        units = units + item.quantity;
    }
    
    return units;
}

- (NSNumber *) getTotalPrice
{
    float totalPrice = 0.0f;
    for(ShoppingCartItem * item in self.itemArray)
    {
        totalPrice = totalPrice + [item.price floatValue] * item.quantity;
    }
    return [NSNumber numberWithFloat:totalPrice];
}

- (void) addItem:(ShoppingCartItem *) item
{
    NSInteger index = [self findDishAtIndex:item.menuItemId];
    if(index >=0)
    {
        ShoppingCartItem *item = [self.itemArray objectAtIndex:index];
        item.quantity = item.quantity +1;
    }else
    {
        [self.itemArray addObject:item];
    }
}

- (void) updateItemQuantity:(NSInteger) menuItemId quantity:(NSInteger) quantity
{
    NSInteger index = [self findDishAtIndex:menuItemId];
    if(index >=0)
    {
        ShoppingCartItem *item = [self.itemArray objectAtIndex:index];
        item.quantity = quantity;
    }
}

- (void) removeItem:(NSInteger)menuItemId
{
    NSInteger index = [self findDishAtIndex:menuItemId];
    if(index >=0)
    {
        [self.itemArray removeObjectAtIndex:index];
    }
}

- (void) cleanup
{
    [self.itemArray removeAllObjects];
}

- (ShoppingCartItem *)  findoutShoppingItem:(NSInteger) menuItemId
{
    ShoppingCartItem *item = Nil;
    NSInteger index = [self findDishAtIndex:menuItemId];
    if(index >=0)
    {
        item = [self.itemArray objectAtIndex:index];
    }
    
    return item;
}

- (NSInteger) findDishAtIndex:(NSInteger) menuItemId
{
    NSInteger index = -1;
    for(int i =0; i<[self.itemArray count]; i++)
    {
        ShoppingCartItem *item = [self.itemArray objectAtIndex:i];
        if(item.menuItemId == menuItemId)
        {
            return i;
        }
    }
    return index;
}

@end
