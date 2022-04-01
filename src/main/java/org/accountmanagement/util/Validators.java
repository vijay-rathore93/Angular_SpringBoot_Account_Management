package org.accountmanagement.util;

import org.accountmanagement.entity.Customer;
import org.accountmanagement.exception.CustomerException;

public class Validators {

    public static void inputValidation(Customer customer){
        if(!customer.getCountry().equalsIgnoreCase("US")
                || customer.getCountry().equalsIgnoreCase("Canada")){
            throw new CustomerException("Country can be only US/Canada");
        }
        if(!customer.getDocumentType().equalsIgnoreCase("DL")
                || customer.getDocumentType().equalsIgnoreCase("Passport")){
            throw new CustomerException("Document Type can be only DL/Passport");
        }
        if(!isNumeric(customer.getZipPin().substring(0,6))){
            throw new CustomerException("Starting Character in Zip/Pin must be number..");
        }
    }


    public static void fileValidation(String fileName){
        if(fileName.contains("..")) {
            throw  new CustomerException("Filename contains invalid path sequence "
                    + fileName);
        }else if(!fileName.split("\\.")[1].equalsIgnoreCase("jpg")){
            throw  new CustomerException("Filename must be in .jpg extension.. "
                    + fileName);
        }
    }

    private static boolean isNumeric(String strNum) {

        try {
            Integer d = Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

}
