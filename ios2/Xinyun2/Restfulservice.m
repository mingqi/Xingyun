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

@implementation MenuItem

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
    NSURL *url = [NSURL URLWithString:[NSString stringWithFormat:@"%@/api/menus?page=%d", self._remoteHost, pageNum]];
    if(category != 0)
    {
        url = [NSString stringWithFormat:@"%@&category=%d", url, category];
    }
    NSLog(@"Restful URL: %@", url);
    NSURLRequest *request = [NSURLRequest requestWithURL:url];
    
    AFJSONRequestOperation *operation = [AFJSONRequestOperation JSONRequestOperationWithRequest:request success:^(NSURLRequest *request, NSHTTPURLResponse *response, id JSON) {
        NSLog(@"success");
        NSMutableArray *menus = [NSMutableArray arrayWithCapacity:10];
        
        NSNumber *pageCountNum = [JSON valueForKey:@"pages_count"];
        NSInteger pageCount = [pageCountNum intValue];
        
        for( id obj in [JSON valueForKey:@"items"])
        {
            MenuItem *menuItem = [[MenuItem alloc] init];
            menuItem.title =[obj valueForKey:@"title"];
            menuItem.price = [obj valueForKey:@"price"];
            
            NSString *imageUri = [obj valueForKey:@"image_uri"];
            NSArray *imageUriArray = [imageUri componentsSeparatedByString:@"."];
            
            NSString *imageURL = [NSString stringWithFormat:@"%@/static/menu/%@_100x100.%@", self._remoteHost, [imageUriArray objectAtIndex:0], [imageUriArray objectAtIndex:1] ];
            NSLog(@"image url: %@", imageURL);
            menuItem.imageURL = imageURL;
            
            [menus addObject:menuItem];
        }
        
        BOOL hasMore = pageCount>pageNum;
        NSMethodSignature* signature = [ [delegate class] instanceMethodSignatureForSelector: @selector( successLoad: hasMore:)];
        NSInvocation* invocation = [NSInvocation invocationWithMethodSignature: signature];
        [invocation setTarget: delegate];
        [invocation setSelector: @selector( successLoad: hasMore: ) ];
        [invocation setArgument: &menus atIndex: 2];
        [invocation setArgument: &hasMore atIndex: 3];
        [invocation invoke];
    } failure:^(NSURLRequest *request, NSHTTPURLResponse *response, NSError *error, id JSON) {
        NSLog(@"failure");
    }];
                                                                                        
    [operation start];
}


@end
