package edu.vuum.mocca;

import java.lang.ref.WeakReference;
import java.util.concurrent.CountDownLatch;

import android.app.Activity;
import android.util.Log;
import android.widget.TextView;

/**
 * @class AndroidPlatformStrategy
 * 
 * @brief Provides methods that define a platform-independent API for
 *        output data to Android UI thread and synchronizing on thread
 *        completion in the ping/pong game.  It plays the role of the
 *        "Concrete Strategy" in the Strategy pattern.
 */
public class AndroidPlatformStrategy extends PlatformStrategy {
  /** TextViewVariable. */
  private final TextView mTextViewOutput;

  /** Activity variable finds gui widgets by view. */
  private final WeakReference<Activity> mActivity;

  public AndroidPlatformStrategy(final Object output, final Object activityParam) {
    /**
     * A textview output which displays calculations and
     * expression trees.
     */
    mTextViewOutput = (TextView)output;

    /** The current activity window (succinct or verbose). */
    mActivity = new WeakReference<Activity>((Activity)activityParam);
  }

  /**
   * Latch to decrement each time a thread exits to control when the
   * play() method returns.
   */
  private static CountDownLatch mLatch = null;

  /** Do any initialization needed to start a new game. */
  @Override
  public void begin() {
    /** (Re)initialize the CountDownLatch. */
    // TODO - You fill in here.
    mLatch = new CountDownLatch(NUMBER_OF_THREADS);
  }

  /** Print the outputString to the display. */
  @Override
  public void print(final String outputString) {
    /** 
     * Create a Runnable that's posted to the UI looper thread
     * and appends the outputString to a TextView. 
     */
    // TODO - You fill in here.
    Activity activity = mActivity.get();

    if (null != activity) {
      activity.runOnUiThread(new Runnable() {

        @Override
        public void run() {
          mTextViewOutput.append(outputString);
          mTextViewOutput.append("\n");
        }
      });
    } else {
      Log.e("AndroidPlatformStrategy", "Activity was gc'ed");
      mLatch.countDown();
    }
  }

  /** Indicate that a game thread has finished running. */
  @Override
  public void done() {
    // TODO - You fill in here.
    Activity activity = mActivity.get();

    if (null != activity) {
      activity.runOnUiThread(new Runnable() {

        @Override
        public void run() {
          mLatch.countDown();
        }

      });
    } else {
      Log.e("AndroidPlatformStrategy", "Activity was gc'ed");
      mLatch.countDown();
    }
  }

  /** Barrier that waits for all the game threads to finish. */
  @Override
  public void awaitDone() {
    // TODO - You fill in here.
    try {
      mLatch.await();
    } catch (java.lang.InterruptedException e) {
    }
  }

  /** 
   * Error log formats the message and displays it for the
   * debugging purposes.
   */
  @Override
  public void errorLog(final String javaFile, final String errorMessage) {
    Log.e(javaFile, errorMessage);
  }
}
