# Leanplum Segment Integration for Android

A Segment integration for the Leanplum Android SDK.

## Installation
To install the Leanplum Segment integration, simply add this line to your
build.gradle file:

```java
    compile 'com.segment.analytics.android:analytics:4.0.4'
    compile 'com.leanplum.android.segment:1.0.0'
```

## Usage

Add the following lines to your AppDelegate or Controller:

```java
    NSString *const SEGMENT_WRITE_KEY = @" ... ";

    SEGAnalyticsConfiguration *config =
        [SEGAnalyticsConfiguration configurationWithWriteKey:SEGMENT_WRITE_KEY];
    [config use:[SEGLeanplumIntegrationFactory instance]];
    [SEGAnalytics setupWithConfiguration:config];
```

Now you can use Segment as you are used to, e.g.:

```java
    [[SEGAnalytics sharedAnalytics] track:@" ... " properties:@{ ... }];
```

In addition to that you can also use the advanced features of Leanplum, e.g.:

```java
    [Leanplum onVariablesChanged: ... ]
```

## Example
We have included a sample application.

1. To run the sample app `open LeanplumSegment.xcworkspace`
2. Choose & run target `LeanplumSegment_Example`

## Tests
We have included unit tests for the integration.

1. To run the unit tests `open LeanplumSegment.xcworkspace`
2. Choose & test target `LeanplumSegment_Tests`

## License

See LICENSE file.
