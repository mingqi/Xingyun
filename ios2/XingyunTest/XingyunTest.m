//
//  XingyunTest.m
//  XingyunTest
//
//  Created by Mingqi Shao on 4/4/13.
//  Copyright (c) 2013 Mingqi Shao. All rights reserved.
//

#import "XingyunTest.h"
#import "Restfulservice.h"
#import "AppDelegate.h"

@implementation XingyunTest

- (void)setUp
{
    [super setUp];
    
    // Set-up code here.
}

- (void)tearDown
{
    // Tear-down code here.
    
    [super tearDown];
}

- (void)testExample
{
    //STFail(@"Unit tests are not implemented yet in XingyunTest");
}

- (void) testAFNetwork
{
    
    Restfulservice *service = [[Restfulservice alloc] init];
    NSLog(@"this is aaaa: %@",[service getActivities]);
    
}

@end
