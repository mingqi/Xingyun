//
//  Restfulservice.m
//  Xingyun2
//
//  Created by Mingqi Shao on 4/2/13.
//  Copyright (c) 2013 Mingqi Shao. All rights reserved.
//

#import "Restfulservice.h"
#import "AFJSONRequestOperation.h"
#import "UIImageView+AFNetworking.h"
#import "DishListViewController.h"
#import "AFHTTPClient.h"

@implementation Order

@end

@implementation OrderDish

@end

@implementation MenuItem

- (NSString *) getImageURLWithResolution:(NSString *)resolution
{
    NSMutableArray *urlSep = [[self.imageURL componentsSeparatedByString:@"/"] mutableCopy];
    NSArray *urlSep2 = [[urlSep objectAtIndex:[urlSep count]-1] componentsSeparatedByString:@"."];
    NSString *newURL = [NSString stringWithFormat:@"%@_%@.%@", [urlSep2 objectAtIndex:0], resolution,[urlSep2 objectAtIndex:1]];
    [urlSep replaceObjectAtIndex:[urlSep count]-1 withObject:newURL];
    return [urlSep componentsJoinedByString:@"/"];
}
@end

@interface  Restfulservice ()
@property (strong, nonatomic) NSString* _remoteHost;
@end

@implementation Restfulservice


+ (Restfulservice *) getService
{
    static Restfulservice *service = nil;
    if(service == nil)
    {
        NSString *path = [[NSBundle mainBundle] pathForResource:@"settings" ofType:@"plist"];
        NSDictionary *settings = [[NSDictionary alloc] initWithContentsOfFile:path];
        service  = [[Restfulservice alloc] initWiteRemoteAddress:[settings objectForKey:@"RestfulServiceRootURL"]];

    }
    return service;
}


- (id) initWiteRemoteAddress:(NSString *) remoteAddress
{
    if(self =[super init])
    {
        self._remoteHost = remoteAddress;
    }
    return self;
}

- (void) getActivities:(id<GetActivitiesDelegate>) delegate withProgressHUB:(MBProgressHUD *)hub
{
    NSURL *url = [NSURL URLWithString:[NSString stringWithFormat:@"%@/api/activities", self._remoteHost]];
    NSURLRequest *request = [NSURLRequest requestWithURL:url];
    
    [hub show:YES];
    AFJSONRequestOperation *operation = [AFJSONRequestOperation JSONRequestOperationWithRequest:request
        success:^(NSURLRequest *request, NSHTTPURLResponse *response, id JSON) {
        
        NSMutableArray *images = [NSMutableArray arrayWithCapacity:5];
        for(NSString * imageUri in [JSON valueForKeyPath:@"image_uri"])
        {
            UIImageView *imageView = [[UIImageView alloc] init];
            NSString *imageUrl = [NSString stringWithFormat:@"%@/static/menu/%@", self._remoteHost, imageUri];
            [imageView setImageWithURL:[NSURL URLWithString:imageUrl]];
            [images addObject:imageView];
        }
        [hub hide:YES];
        [delegate performSelector:@selector(successLoadActivities:) withObject:images];
        
    } failure:^( NSURLRequest *request , NSHTTPURLResponse *response , NSError *error , id JSON ){
        [hub hide:YES];
        NSLog(@"call service error: %@", error);
        [delegate performSelector:@selector(failureLoadActivities:) withObject:error];
    }];
    [operation start];
}

- (void) loadMenuItems: (id<LoadMenuItemDelegate>) delegate category:(NSInteger) category pageNum:(NSInteger) pageNum
{
    NSString *url = [NSString stringWithFormat:@"%@/api/menus?page=%d", self._remoteHost, pageNum];
    if(category != 0)
    {
        url = [NSString stringWithFormat:@"%@&category=%d", url, category];
    }
    NSLog(@"Restful URL: %@", url);
    NSURLRequest *request = [NSURLRequest requestWithURL:[NSURL URLWithString:url]];
    
    AFJSONRequestOperation *operation = [AFJSONRequestOperation JSONRequestOperationWithRequest:request success:^(NSURLRequest *request, NSHTTPURLResponse *response, id JSON) {
        NSLog(@"success");
        NSMutableArray *menus = [NSMutableArray arrayWithCapacity:10];
        
        NSNumber *pageCountNum = [JSON valueForKey:@"pages_count"];
        NSInteger pageCount = [pageCountNum intValue];
        
        for( id obj in [JSON valueForKey:@"items"])
        {
            MenuItem *menuItem = [[MenuItem alloc] init];
            menuItem.menuItemId = [(NSNumber *)[obj valueForKey:@"menu_item_id"] intValue];
            menuItem.title =[obj valueForKey:@"title"];
            menuItem.price = [obj valueForKey:@"price"];
            
            NSString *imageUri = [obj valueForKey:@"image_uri"];            
            NSString *imageURL = [NSString stringWithFormat:@"%@/static/menu/%@", self._remoteHost, imageUri];
            NSLog(@"image url: %@", imageURL);
            menuItem.imageURL = imageURL;
            
            [menus addObject:menuItem];
        }
        BOOL hasMore = pageCount>pageNum;
        NSInteger pn = pageNum;
        NSMethodSignature* signature = [ [delegate class] instanceMethodSignatureForSelector: @selector( successLoad: pageNumber: hasMore:)];
        NSInvocation* invocation = [NSInvocation invocationWithMethodSignature: signature];
        [invocation setTarget: delegate];
        [invocation setSelector: @selector( successLoad: pageNumber:hasMore:) ];
        [invocation setArgument: &menus atIndex: 2];
        [invocation setArgument: &pn atIndex: 3];
        [invocation setArgument: &hasMore atIndex: 4];
        [invocation invoke];
    } failure:^(NSURLRequest *request, NSHTTPURLResponse *response, NSError *error, id JSON) {
        NSLog(@"failure");
    }];
                                                                                        
    [operation start];
}

- (void) placeOrder:(id<PlaceOrderDelegate>) delegate order:(Order *) order{
//    NSString *placeOrderURLString = [NSString stringWithFormat:@"%@/api/orders", self._remoteHost];
    AFHTTPClient * Client = [[AFHTTPClient alloc] initWithBaseURL:[NSURL URLWithString:self._remoteHost]];
    NSMutableDictionary * parameters = [[NSMutableDictionary alloc] init];
    [parameters setObject:[NSNumber numberWithInt:order.customerId] forKey:@"customer_id"];
    [parameters setObject:order.contactName forKey:@"contact_name"];
    [parameters setObject:order.contactPhone forKey:@"contact_phone"];
    [parameters setObject:[NSNumber numberWithInt:order.peopleNumber] forKey:@"people_number"];
    [parameters setObject:[NSNumber numberWithBool:order.boxRequired] forKey:@"box_required"];
    [parameters setObject:[NSNumber numberWithFloat:order.orderPrice] forKey:@"order_price"];
    [parameters setObject:[NSNumber numberWithInt:order.dishesCount] forKey:@"dishes_count"];
    [parameters setObject:order.otherRequirements forKey:@"other_requirements"];

    
    NSDateFormatter *fmt = [[NSDateFormatter alloc] init];
    [fmt setDateFormat:@"yyyy-MM-dd'T'HH:mm:ss"];
    [parameters setObject:[fmt stringFromDate:order.reservedTime] forKey:@"reserved_time"];
    
    NSMutableArray *orderDishesArray = [NSMutableArray arrayWithCapacity:10];
    for( OrderDish *dish in order.orderDishes){
        NSDictionary * dict = [NSDictionary dictionaryWithObjectsAndKeys:[NSNumber numberWithInt:dish.menuItemId], @"menu_item_id", [NSNumber numberWithInt:dish.quantity], @"quantity", nil];
        [orderDishesArray addObject:dict];
    }
    [parameters setObject:orderDishesArray forKey:@"order_dishes"];
    [Client setParameterEncoding:AFJSONParameterEncoding];
    [Client putPath:@"api/orders" parameters:parameters success:^(AFHTTPRequestOperation *operation, id responseObject) {
        NSMethodSignature* signature = [ [delegate class] instanceMethodSignatureForSelector: @selector( successPlaceOrder:)];
        NSInvocation* invocation = [NSInvocation invocationWithMethodSignature: signature];
        [invocation setTarget: delegate];
        [invocation setSelector: @selector( successPlaceOrder: )];
        NSInteger orderId = 0;
        [invocation setArgument: &orderId atIndex: 2];
        [invocation invoke];
        NSLog(@"success place order");
        } failure:^(AFHTTPRequestOperation *operation, NSError *error) {
            NSMethodSignature* signature = [ [delegate class] instanceMethodSignatureForSelector: @selector( failurePlaceOrder:)];
            NSInvocation* invocation = [NSInvocation invocationWithMethodSignature: signature];
            [invocation setTarget: delegate];
            [invocation setSelector: @selector( failurePlaceOrder: )];
            [invocation setArgument: &error atIndex: 2];
            [invocation invoke];
            NSLog(@"failed to place order: %@", error);
        }];
}

@end
