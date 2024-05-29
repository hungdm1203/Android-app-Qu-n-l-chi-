package com.example.quanlychitieu;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class NotificationReceiver  {
    public void onReceive(Context context, int n, String s) {
        createNotificationChannel(context);
        Bitmap bitmap= BitmapFactory.decodeResource(context.getResources(),R.drawable.baseline_circle_notifications_24);
        if(n==0){
            Notification notification=new NotificationCompat.Builder(context.getApplicationContext(), "my_channel_id")
                    .setContentTitle("Quản lý chi tiêu")
                    .setContentText("Đã đến giờ, bạn hãy nhập thu chi trong ngày hôm nay!!")
                    .setSmallIcon(R.drawable.baseline_circle_notifications_24)
                    .setLargeIcon(bitmap)
                    .setColor(context.getResources().getColor(R.color.green))
                    .build();

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            if(notificationManager!=null) {
                notificationManager.notify(1, notification);
            }
        } else if(n==1){
            Notification notification=new NotificationCompat.Builder(context.getApplicationContext(), "my_channel_id")
                    .setContentTitle("Quản lý chi tiêu")
                    .setContentText("Khoản chi cho "+s+" đã vượt quá dự định, bạn hãy cân đối lại thu chi!!")
                    .setSmallIcon(R.drawable.baseline_circle_notifications_24)
                    .setLargeIcon(bitmap)
                    .setColor(context.getResources().getColor(R.color.green))
                    .build();

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            if(notificationManager!=null) {
                notificationManager.notify(1, notification);
            }
        } else if(n==2){
            Notification notification=new NotificationCompat.Builder(context.getApplicationContext(), "my_channel_id")
                    .setContentTitle("Quản lý chi tiêu")
                    .setContentText("Số dư trong tháng này của bạn đã hết!!")
                    .setSmallIcon(R.drawable.baseline_circle_notifications_24)
                    .setLargeIcon(bitmap)
                    .setColor(context.getResources().getColor(R.color.green))
                    .build();

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            if(notificationManager!=null) {
                notificationManager.notify(1, notification);
            }
        } else if (n==3) {
            Notification notification=new NotificationCompat.Builder(context.getApplicationContext(), "my_channel_id")
                    .setContentTitle("Quản lý chi tiêu")
                    .setContentText("Số dư trong tháng này của bạn còn "+s+" , bạn hãy cân đối lại chi tiêu!!")
                    .setSmallIcon(R.drawable.baseline_circle_notifications_24)
                    .setLargeIcon(bitmap)
                    .setColor(context.getResources().getColor(R.color.green))
                    .build();

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            if(notificationManager!=null) {
                notificationManager.notify(1, notification);
            }
        }
    }


    private void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence channelName = "My Channel";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("my_channel_id", channelName, importance);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }
}

