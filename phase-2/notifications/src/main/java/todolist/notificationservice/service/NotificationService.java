package todolist.notificationservice.service;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import dev.failsafe.Failsafe;
import dev.failsafe.RetryPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import todolist.notificationservice.repository.EventRepository;

import java.time.Duration;

@Service
public class NotificationService //{
    implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);
    @Value("${spring.rabbitmq.queue}")
    private String QUEUE_NAME;
    @Value("${spring.rabbitmq.host}")
    private String HOST;
    @Value("${spring.rabbitmq.username}")
    private String USERNAME;
    @Value("${spring.rabbitmq.password}")
    private String PASSWORD;

    @Autowired
    private EventRepository eventRepository;
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
    @Override
    public void run(String... args) throws Exception {
        logger.info(String.format("Trying to connect to %s",HOST));

        //Setting resilient connection on missing resources
        RetryPolicy<Object> retryPolicy = RetryPolicy.builder()
                .handle(Exception.class)
                .withDelay(Duration.ofSeconds(2))
                .withMaxRetries(10)
                .build();


        //creating connection to RabbitMQ
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(HOST);

        //connect with failsafe policy
        Connection connection = Failsafe.with(retryPolicy).get(() -> factory.newConnection());

        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        logger.info(String.format("Waiting for messages on queue %s",QUEUE_NAME));

        //Callback to handle messages
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            logger.info("Received '" + message + "'");

        };

        //Set up handler
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
    }


}
