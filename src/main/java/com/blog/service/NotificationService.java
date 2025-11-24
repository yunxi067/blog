package com.blog.service;

import com.blog.domain.Notification;

import java.util.List;

public interface NotificationService {
    Notification getNotificationById(Integer notificationId);
    
    boolean createNotification(Notification notification);
    
    boolean updateNotification(Notification notification);
    
    boolean deleteNotification(Integer notificationId);
    
    List<Notification> getNotificationsByUid(Integer uid);
    
    List<Notification> getUnreadNotificationsByUid(Integer uid);
    
    boolean markAsRead(Integer notificationId);
    
    int getUnreadCount(Integer uid);
}
