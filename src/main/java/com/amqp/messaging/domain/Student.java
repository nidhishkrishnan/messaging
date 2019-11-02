package com.amqp.messaging.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Student extends User {
    private int rollNumber;
}
