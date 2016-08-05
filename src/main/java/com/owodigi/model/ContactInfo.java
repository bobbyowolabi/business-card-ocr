package com.owodigi.model;

/**
 * Represents contact information of an individual.
 */
public interface ContactInfo {

    /**
     * The full name of an individual (eg. John Smith, Susan Malick).
     * 
     * @return the full name of the individual
     */
    public String getName();

    /**
     * The phone number of an individual formatted strictly as a sequence of 
     * digits free of non-numerical characters.
     * 
     * @return the phone number formatted as a sequence of digits
     */
    public String getPhoneNumber();

    /**
     * The email address of an individual.
     * 
     * @return the email address of an individual
     */
    public String getEmailAddress();
}
