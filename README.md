# Leanplum Segment Integration for Android

A Segment integration for the Leanplum Android SDK.

## Installation
To install the Leanplum Segment integration, simply add these lines to your
build.gradle file:

```java
  compile 'com.segment.analytics.android:analytics:4.0.4'
  compile 'com.leanplum.android.segment:1.0.0'
```

## Usage

Add the following lines to your AppDelegate or Controller:

```java
  private static final String SEGMENT_WRITE_KEY = " ... ";

  Analytics analytics = new Analytics
      .Builder(getApplicationContext(), SEGMENT_WRITE_KEY)
      .use(LeanplumIntegration.FACTORY)
      .build();
```

Now you can use Segment as you are used to, e.g.:

```java
  Analytics.with(getApplicationContext()).track(" ... ", ... );
```

In addition to that you can also use the advanced features of Leanplum, e.g.:

```java
  Leanplum.addVariablesChangedHandler( ... );
```

## Example
We have included a sample application.

1. To run the sample app, open this folder in Android Studio.
2. Choose & run target `Example`

## Tests
We have included unit tests for the integration.

1. To run the unit tests, open this folder in Android Studio.
2. Choose & run target `LeanplumIntegrationTests`

## License

See LICENSE file.
