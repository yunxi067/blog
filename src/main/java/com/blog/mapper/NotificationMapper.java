package com.blog.mapper;

import com.blog.domain.Notification;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationMapper {
    Notification selectByNotificationId(Integer notificationId);
    
    int insert(Notification notification);
    
    int update(Notification notification);
    
    int delete(Integer notificationId);
    
    List<Notification> selectByUid(Integer uid);
    
    List<Notification> selectUnreadByUid(Integer uid);
    
    int updateStatus(Integer notificationId);
}
