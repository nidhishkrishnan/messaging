package com.amqp.messaging.config;

import com.amqp.messaging.domain.Admission;
import com.amqp.messaging.domain.Student;
import com.amqp.messaging.domain.Teacher;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.io.IOException;

@Slf4j
@Configuration
@Import(RabbitAutoConfiguration.class)
public class RabbitMQConfig {

    @Bean
    public ApplicationRunner createQueues(ConnectionFactory connectionFactory,
                                          @Value("${rabbitmq.student.queue-name:}") String studentQueueName,
                                          @Value("${rabbitmq.teacher.queue-name:}") String teacherQueueName,
                                          @Value("${rabbitmq.admission.queue-name:}") String admissionQueueName) {
        return args -> {
            RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);

            Queue studentQueue = QueueBuilder.durable(studentQueueName).build();
            Queue teacherQueue = QueueBuilder.durable(teacherQueueName).build();
            Queue admissionQueue = QueueBuilder.durable(admissionQueueName).build();

            rabbitAdmin.declareQueue(studentQueue); //declaring queue for student
            rabbitAdmin.declareQueue(teacherQueue); //declaring queue for teacher
            rabbitAdmin.declareQueue(admissionQueue); //declaring queue for admission
        };
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new MessageConverter() {
            private ObjectMapper objectMapper = new ObjectMapper()
                    .enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);

            @Override
            public Message toMessage(Object object, MessageProperties messageProperties) {
                return null;
            }

            @Override
            public Object fromMessage(Message message) {
                String messageRoutingKey = message.getMessageProperties().getReceivedRoutingKey();
                String body = new String(message.getBody());
                try {
                    log.debug("Message properties: {}", message.getMessageProperties());
                    if (messageRoutingKey != null && messageRoutingKey.startsWith("student.evt")) {
                        return objectMapper.readValue(body, Student.class);
                    } else if (messageRoutingKey != null && messageRoutingKey.startsWith("teacher.evt")) {
                        return objectMapper.readValue(body, Teacher.class);
                    } else {
                        return objectMapper.readValue(body, Admission.class);
                    }
                } catch (IOException e) {
                    throw new MessageConversionException(e.getMessage());
                }
            }
        };
    }
}
