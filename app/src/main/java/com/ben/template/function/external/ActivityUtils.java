package com.ben.template.function.external;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.ben.template.R;

/**
 * Activity工具类
 *
 * @author Benhero
 * @date 2021/1/19
 */
public class ActivityUtils {

    private static class MyHandler extends Handler {
        public MyHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message message) {
            super.handleMessage(message);
            if (sContext == null) {
                return;
            }
            if (message.what == MSG_WHAT) {
                try {
                    ((NotificationManager) sContext.getSystemService(Context.NOTIFICATION_SERVICE))
                            .cancel(NOTIFICATION_TAG, NOTIFICATION_ID);
                } catch (Exception ignored) {
                }
            }
        }
    }

    public static final Handler sHandler = new MyHandler(Looper.getMainLooper());
    public static final int MSG_WHAT = 101;
    public static final String NOTIFICATION_CHANNEL_ID = "channel_ext";

    public static final int NOTIFICATION_ID = 10101;
    public static final String NOTIFICATION_TAG = "AA_TAG1";

    @SuppressLint("StaticFieldLeak")
    private static Context sContext;

    private static void initContext(Context context) {
        if (sContext == null) {
            sContext = context.getApplicationContext();
        }
    }

    public static void openExt(Context context, Intent intent) {
        initContext(context);
        intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);

        PendingIntent activity = PendingIntent.getActivity(sContext, 10102, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        try {
            activity.send();
        } catch (PendingIntent.CanceledException unused) {
            intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
            sContext.startActivity(intent);
        }
        try {
            NotificationManager notificationManager = (NotificationManager) sContext.getSystemService(Context.NOTIFICATION_SERVICE);
            String channel = createNotificationChannel(notificationManager);
            notificationManager.cancel(NOTIFICATION_TAG, NOTIFICATION_ID);
            notificationManager.notify(NOTIFICATION_TAG, NOTIFICATION_ID,
                    new NotificationCompat.Builder(sContext, channel)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setFullScreenIntent(activity, true)
                            .setPriority(NotificationCompat.PRIORITY_MIN)
                            .build());
            sHandler.removeMessages(MSG_WHAT);
            sHandler.sendEmptyMessageDelayed(MSG_WHAT, 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            openExt29(intent);
        }
    }

    /**
     * Android 10打开应用
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    private static void openExt29(Intent intent) {
        PendingIntent pendingIntent = PendingIntent.getActivity(sContext, 10102, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) sContext.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 200, pendingIntent);
        }
        intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
        intent.addFlags(Intent.FLAG_RECEIVER_REGISTERED_ONLY | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        try {
            sContext.startActivity(intent);
        } catch (Exception ignored) {
        }
    }

    private static String createNotificationChannel(NotificationManager notificationManager) {
        if (Build.VERSION.SDK_INT < 26 || notificationManager.getNotificationChannel(NOTIFICATION_CHANNEL_ID) != null) {
            return NOTIFICATION_CHANNEL_ID;
        }
        NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "Notification", NotificationManager.IMPORTANCE_HIGH);
        notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_SECRET);
        notificationChannel.enableLights(false);
        notificationChannel.enableVibration(false);
        notificationChannel.setShowBadge(false);
        notificationChannel.setSound(null, null);
        notificationChannel.setBypassDnd(true);
        notificationManager.createNotificationChannel(notificationChannel);
        return NOTIFICATION_CHANNEL_ID;
    }
}