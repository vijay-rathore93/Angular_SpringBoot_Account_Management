package org.accountmanagement.entity;


import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Set;


@Data
@Entity
@Table(name = "customer")
public class Customer extends Auditable<String> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer customerId;
    @NotEmpty(message = "First Name can not empty..")
    private String firstName;
    @NotEmpty(message = "Last Name can not empty..")
    private String lastName;
    private String dob;
    @NotEmpty(message = "Social Security Number can not empty..")
    private String ssn;
    private String address1;
    private String address2;
    @NotEmpty(message = "City can not empty..")
    private String city;
    @NotEmpty(message = "Country can not empty..")
    private String country;
    @NotEmpty(message = "State can not empty..")
    @Length(min=2,max = 2,message = "State/Province can not be more that 2 Characters")
    private String state;
    @NotEmpty(message = "Zip/Pin can not empty..")
    @Length(min=11, max=11,message = "Zip/Pin must be of length 11.")
    private String zipPin;// validation starting 5 character numver then 6 charatcter string
    @NotEmpty(message = "Zip Ext. can not empty..")
    private String zipExt;
    private String documentType;//DL/passport
    @Lob
    private byte[]  documentToBeVerify;

    private String  fileName;
    private String  fileContentType;
    private boolean  isActive;

    @OneToMany
    @JoinColumn(name = "customerId")
    private Set<Account> accounts;


}
