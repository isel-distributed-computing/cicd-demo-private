package todolist.notificationservice.service;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.AnonymousQueue;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService //{
    implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);
    private final static String QUEUE_NAME = "myQueue";
/*
    List<INotificationStrategy> notificationStrategyList = new ArrayList<>();

    public void addNotificationStrategy(INotificationStrategy strategy) {
        notificationStrategyList.add(strategy);
    }

    public void sendItemCreatedNotification(ToDoListItem item) {
        logger.info("Item created: " + item);
        for(INotificationStrategy s : notificationStrategyList) {
            s.sendCreateNotification(item);
        }
    }

    public void sendItemDeletedNotification(ToDoListItem item) {
        logger.info("Item deleted: " + item);
        for(INotificationStrategy s : notificationStrategyList) {
            s.sendDeleteNotification(item);
        }
    }
*/

    public void init() {
        logger.info("Service up.");
    }

    public void startListening()
    {
        logger.info("Start listening up.");

        logger.info("Closing service.");

    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("Start listening up.");

        //Producer

        /*  ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            String message = "Hello World!";
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            logger.info(" [x] Sent '" + message + "'");

        }
*/
        //Consumer
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        logger.info(" [*] Waiting for messages. To exit press CTRL+C");


        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            logger.info("[x] Received '" + message + "'");
        };
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
    }

}
