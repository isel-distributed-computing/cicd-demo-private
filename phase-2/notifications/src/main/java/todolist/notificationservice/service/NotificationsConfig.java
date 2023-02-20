package todolist.notificationservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import todolist.notificationservice.model.EventModel;

@Configuration
public class NotificationsConfig {
    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    /*
      There could be many ways to disseminate the event to consumers,
      using SMS, E-MAIL, queues, or  PUB/SUB solutions.
      This simple implementation uses the console
       */
    @Bean
    public INotificationStrategy getSubscriber1() {
        return new INotificationStrategy() {
            @Override
            public void sendNotification(EventModel evt) {
                logger.info(String.format("Subscriber1: '%s'",evt));
            }
        };

    }

    @Bean
    public INotificationStrategy getSubscriber2() {
        return new INotificationStrategy() {
            @Override
            public void sendNotification(EventModel evt) {
                logger.info(String.format("Subscriber2: '%s'",evt));
            }
        };

    }
}
