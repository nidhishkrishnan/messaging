package com.amqp.messaging.controller;

import com.amqp.messaging.domain.Admission;
import com.amqp.messaging.domain.Student;
import com.amqp.messaging.domain.Teacher;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final RabbitTemplate rabbitTemplate;

    private final ObjectMapper mapper;

    @PostMapping("/send-queue-for-student")
    public void sendQueueForStudent(@RequestBody Student student) throws JsonProcessingException {
        String exchangeName = "user-amq-exchange";
        String routingKey = "student.evt.created";
        rabbitTemplate.convertAndSend(exchangeName, routingKey, convertToJsonString(student));
    }

    @PostMapping("/send-queue-for-teacher")
    public void sendQueueForTeacher(@RequestBody Teacher teacher) throws JsonProcessingException {
        String exchangeName = "user-amq-exchange";
        String routingKey = "teacher.evt.login";
        rabbitTemplate.convertAndSend(exchangeName, routingKey, convertToJsonString(teacher));
    }

    @PostMapping("/send-queue-for-admission")
    public void sendQueueForAdmission(@RequestBody Admission admission) throws JsonProcessingException {
        String exchangeName = "admission-amq-exchange";
        String routingKey = "admission.evt.create";
        rabbitTemplate.convertAndSend(exchangeName, routingKey, convertToJsonString(admission));
    }

    private String convertToJsonString(Object message) throws JsonProcessingException {
        return mapper.writeValueAsString(message);
    }
}
