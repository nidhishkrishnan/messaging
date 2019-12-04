
package com.amqp.messaging.adapters;

import com.amqp.messaging.domain.Admission;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static org.springframework.amqp.core.ExchangeTypes.TOPIC;

@Component
@Slf4j
public class AdmissionListenerAdapter {

/**
     * <pre>
     * {@code
     * {
     *   "admissionId": 123,
     *   "admissionYear": 1988,
     *   "courseName": "some-course-name",
     *   "studentName": "some-student-name"
     * }
     * </pre>
     */

    @RabbitListener(bindings = {
            @QueueBinding(value = @Queue(
                    value = "${rabbitmq.admission.queue-name}", durable = "true", ignoreDeclarationExceptions = "true"), //this is to prevent a failure to configure this at startup before the rabbitmq.auth-account-queue.name is declared
                    key = "admission.evt.create",
                    exchange = @Exchange(value = "${rabbitmq.admission.exchange-name}", type = TOPIC, durable = "true"))},
            containerFactory = "rabbitListenerContainerFactory")
    public void receiveNewAdmissionActivityMessage(Admission message) {
        log.info("Received admission activity message {}", message);
    }
}

