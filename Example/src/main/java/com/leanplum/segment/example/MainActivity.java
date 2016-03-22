// Copyright 2016, Leanplum, Inc.

package com.leanplum.segment.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.leanplum.Leanplum;
import com.leanplum.LeanplumPushService;
import com.leanplum.segment.LeanplumIntegration;
import com.segment.analytics.Analytics;
import com.segment.analytics.Properties;
import com.segment.analytics.Traits;

public class MainActivity extends AppCompatActivity {

  private static final String SEGMENT_WRITE_KEY =
      "YLaT0mS6h8GdU0KErzTNsPfPvQqDV7sP";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    LeanplumPushService.setGcmSenderId(LeanplumPushService.LEANPLUM_SENDER_ID);
    if (BuildConfig.DEBUG) {
      Leanplum.setApiConnectionSettings(
          "leanplum-staging.appspot.com", "api", true);
      Leanplum.setSocketConnectionSettings("dev-staging.leanplum.com", 443);
      Leanplum.enableVerboseLoggingInDevelopmentMode();
    }

    // Create an analytics client with the given context and Segment write key.
    Analytics analytics = new Analytics
        .Builder(getApplicationContext(), SEGMENT_WRITE_KEY)
        .use(LeanplumIntegration.FACTORY)
        .build();

    // Set the initialized instance as a globally accessible instance.
    Analytics.setSingletonInstance(analytics);

    Traits traits = new Traits()
        .putName("First Last")
        .putEmail("first@last.com");
    Analytics.with(getApplicationContext())
        .identify("f4ca124298", traits, null);

    Properties properties = new Properties()
        .putValue("plan", "Enterprise");
    Analytics.with(getApplicationContext()).track("Signed up", properties);
    Analytics.with(getApplicationContext()).screen("Main", "Start");
  }

  public void trackButtonClicked(View view) {
    Properties properties = new Properties()
        .putPrice(0.99)
        .putCurrency("USD")
        .putTitle("InApp Purchase");
    Analytics.with(getApplicationContext())
        .track("Track Button Clicked!", properties);
  }

}