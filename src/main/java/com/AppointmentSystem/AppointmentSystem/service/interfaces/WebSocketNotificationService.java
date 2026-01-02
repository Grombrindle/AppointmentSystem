package com.AppointmentSystem.AppointmentSystem.service.interfaces;

import com.AppointmentSystem.AppointmentSystem.enums.AppointmentStatus;
import com.AppointmentSystem.AppointmentSystem.model.NotificationMessage;

public interface WebSocketNotificationService {
    void sendGlobalNotification(NotificationMessage notification);
    
    void sendPrivateNotification(String userId, NotificationMessage notification);
    
    void sendAppointmentNotification(Long appointmentId, NotificationMessage notification);
    
    void notifyAppointmentStatusChange(Long appointmentId, 
                                       AppointmentStatus oldStatus, 
                                       AppointmentStatus newStatus,
                                       Long userId);
}