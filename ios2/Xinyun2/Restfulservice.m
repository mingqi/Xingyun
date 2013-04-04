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

@interface  Restfulservice ()
@property (strong, nonatomic) NSString* _remoteHost;
@end

@implementation Restfulservice

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
        [delegate performSelector:@selector(failureLoadActivities:) withObject:error];
    }];
    [operation start];
}

@end
