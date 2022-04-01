package org.accountmanagement.entity;


import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer customerId;
    @Column(nullable = false)
    private String firstName;
    private String lastName;
    private String dob;
    private String ssn;
    private String address1;
    private String address2;
    @Column(nullable = false)
    private String city;
    private String country;
    @Column(length = 2)
    private String state;
    private String zipPin;// validation starting 5 character numver then 6 charatcter string
    private String zipExt;
    private String documentType;//DL/passport
    @Lob
    private byte[]  documentToBeVerify;

    private String  fileName;
    private String  fileContentType;
    private boolean  isActive;


}
