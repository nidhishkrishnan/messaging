package com.amqp.messaging.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Admission {
    private int admissionId;
    private String studentName;
    private String courseName;
    private int admissionYear;
}
