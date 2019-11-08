package com.amqp.messaging.adapters;

import com.amqp.messaging.domain.Student;
import com.amqp.messaging.domain.Teacher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static org.springframework.amqp.core.ExchangeTypes.TOPIC;

@Component
@Slf4j
public class TeacherStudentListenerAdapter {

    /**
     * <pre>
     * {@code
     * {
     *   "firstName": "some-first-name",
     *   "id": 123,
     *   "lastName": "some-last-name",
     *   "rollNumber": 345
     * }
     * </pre>
     */
    @RabbitListener(bindings = {
            @QueueBinding(value = @Queue(
                    value = "${rabbitmq.student.queue-name}", durable = "true", ignoreDeclarationExceptions = "true"), //this is to prevent a failure to configure this at startup before the rabbitmq.auth-account-queue.name is declared
                    key = "student.evt.email_verified.#",
                    exchange = @Exchange(value = "${rabbitmq.student.exchange-name}", type = TOPIC, durable = "true")),
            @QueueBinding(value = @Queue(
                    value = "${rabbitmq.student.queue-name}", durable = "true", ignoreDeclarationExceptions = "true"),
                    key = "student.evt.created.#",
                    exchange = @Exchange(value = "${rabbitmq.student.exchange-name}", type = TOPIC, durable = "true")),
            @QueueBinding(value = @Queue(
                    value = "${rabbitmq.student.queue-name}", durable = "true", ignoreDeclarationExceptions = "true"),
                    key = "student.evt.login.#",
                    exchange = @Exchange(value = "${rabbitmq.student.exchange-name}", type = TOPIC, durable = "true"))
        }
            , containerFactory = "rabbitListenerContainerFactory")
    public void receiveStudentActivityMessage(Student message) {
        log.info("Received student activity message {}", message);
    }

    /**
     * <pre>
     * {@code
     * {
     *   "firstName": "some-first-name",
     *   "id": 123,
     *   "lastName": "some-last-name",
     *   "designation": "some-designation"
     * }
     * </pre>
     */
    @RabbitListener(bindings = {
            @QueueBinding(value = @Queue(
                    value = "${rabbitmq.teacher.queue-name}", durable = "true", ignoreDeclarationExceptions = "true"), //this is to prevent a failure to configure this at startup before the rabbitmq.auth-account-queue.name is declared
                    key = "teacher.evt.login.#",
                    exchange = @Exchange(value = "${rabbitmq.student.exchange-name}", type = TOPIC, durable = "true"))
    }
            , containerFactory = "rabbitListenerContainerFactory")
    public void receiveTeacherActivityMessage(Teacher message) {
        log.info("Received teacher activity message {}", message);
    }
}
