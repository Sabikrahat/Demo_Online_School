package com.sabikrahat.demoonlineschool.MessagingServer;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.sabikrahat.demoonlineschool.Notification.NotificationService;

public class MessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if (remoteMessage.getNotification() != null) {
            String title = remoteMessage.getNotification().getTitle();
            String text = remoteMessage.getNotification().getBody();

            NotificationService notificationService = new NotificationService();
            notificationService.displayNotification(getApplicationContext(), title, text);
        }
    }
}
