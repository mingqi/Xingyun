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


@interface Restfulservice : NSObject

- (id) initWiteRemoteAddress:(NSString *) remoteAddress;
- (void) getActivities:(id<GetActivitiesDelegate>)delegate withProgressHUB:(MBProgressHUD *)hub;

@end
