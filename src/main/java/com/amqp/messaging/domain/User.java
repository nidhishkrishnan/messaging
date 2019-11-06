package com.amqp.messaging.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class User {
    private int id;
    private String firstName;
    private String lastName;
}
