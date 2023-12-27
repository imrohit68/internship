package com.example.internship.Model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@RequiredArgsConstructor
@Getter
@Document
@Setter
public class Employee {
    @Id
    private String id;
    private String EmployeeName;
    private String phoneNumber;
    private String email;
    private String reportsTo;
    private String profileImage;
}
