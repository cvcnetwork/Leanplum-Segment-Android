package com.leanplum.android.segment;

import android.app.Application;
import android.text.TextUtils;

import com.leanplum.Leanplum;
import com.leanplum.LeanplumActivityHelper;
import com.leanplum.LeanplumException;
import com.segment.analytics.Analytics;
import com.segment.analytics.ValueMap;
import com.segment.analytics.integrations.IdentifyPayload;
import com.segment.analytics.integrations.Integration;
import com.segment.analytics.integrations.Logger;
import com.segment.analytics.integrations.ScreenPayload;
import com.segment.analytics.integrations.TrackPayload;

/**
 * Leanplum Segment Integration
 *
 * @author Ben Marten
 */
public class LeanplumIntegration extends Integration {

  private static final String LEANPLUM_KEY = "Leanplum";
  private Logger logger;

  public static final Factory FACTORY = new Factory() {
    @Override
    public Integration<?> create(ValueMap settings, Analytics analytics) {
      Logger logger = analytics.logger(LEANPLUM_KEY);
      String appId = settings.getString("appId");
      String key = settings.getString("clientKey");
      Boolean isDevelopmentMode = settings.getBoolean("devMode", false);

      if (TextUtils.isEmpty(appId)) {
        throw new LeanplumException("Please add Leanplum app id in Segment " +
            "settings.");
      }
      if (TextUtils.isEmpty(key)) {
        throw new LeanplumException("Please add Leanplum client key in " +
            "Segment settings.");
      }

      return new LeanplumIntegration(analytics.getApplication(), appId, key,
          logger, isDevelopmentMode);
    }

    @Override
    public String key() {
      return LEANPLUM_KEY;
    }
  };

  public LeanplumIntegration(Application application, String appId, String key,
                             Logger logger, Boolean isDevelopmentMode
  ) {
    this.logger = logger;
    logger.verbose("Registering Leanplum Integration, appId: %s, key: %s, " +
        "devMode: %b", appId, key, isDevelopmentMode);

    if (isDevelopmentMode) {
      Leanplum.setAppIdForDevelopmentMode(appId, key);
    } else {
      Leanplum.setAppIdForProductionMode(appId, key);
    }

    LeanplumActivityHelper.enableLifecycleCallbacks(application);
    Leanplum.start(application);
    logger.verbose("Leanplum started.");
  }

  @Override
  public void identify(final IdentifyPayload identify) {
    logger.verbose("Identify: %s", identify);
    // Set user ID & map traits to user attributes
    Leanplum.setUserAttributes(identify.userId(), identify.traits());
  }

  @Override
  public void track(TrackPayload track) {
    logger.verbose("Track: %s", track);
    Leanplum.track(track.event(), track.properties());
  }

  @Override
  public void screen(ScreenPayload screen) {
    logger.verbose("Screen: %s", screen);
    Leanplum.advanceTo(screen.event(), screen.properties());
  }

}
