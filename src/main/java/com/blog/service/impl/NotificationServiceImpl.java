package com.blog.service.impl;

import com.blog.domain.Notification;
import com.blog.mapper.NotificationMapper;
import com.blog.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;

    @Override
    public Notification getNotificationById(Integer notificationId) {
        return notificationMapper.selectByNotificationId(notificationId);
    }

    @Override
    public boolean createNotification(Notification notification) {
        if (notification == null || notification.getUid() == null) {
            return false;
        }

        notification.setStatus(0);
        notification.setCreateTime(new Date());

        int result = notificationMapper.insert(notification);
        return result > 0;
    }

    @Override
    public boolean updateNotification(Notification notification) {
        if (notification == null || notification.getNotificationId() == null) {
            return false;
        }

        int result = notificationMapper.update(notification);
        return result > 0;
    }

    @Override
    public boolean deleteNotification(Integer notificationId) {
        if (notificationId == null) {
            return false;
        }

        int result = notificationMapper.delete(notificationId);
        return result > 0;
    }

    @Override
    public List<Notification> getNotificationsByUid(Integer uid) {
        if (uid == null) {
            return null;
        }
        return notificationMapper.selectByUid(uid);
    }

    @Override
    public List<Notification> getUnreadNotificationsByUid(Integer uid) {
        if (uid == null) {
            return null;
        }
        return notificationMapper.selectUnreadByUid(uid);
    }

    @Override
    public boolean markAsRead(Integer notificationId) {
        if (notificationId == null) {
            return false;
        }

        int result = notificationMapper.updateStatus(notificationId);
        return result > 0;
    }

    @Override
    public int getUnreadCount(Integer uid) {
        if (uid == null) {
            return 0;
        }

        List<Notification> notifications = notificationMapper.selectUnreadByUid(uid);
        return notifications != null ? notifications.size() : 0;
    }
}
