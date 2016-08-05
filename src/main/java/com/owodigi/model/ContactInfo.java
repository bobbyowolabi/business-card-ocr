package com.owodigi.model;

/**
 *
 *
 */
public interface ContactInfo {

    /**
     * 
     * @return the full name of the individual (eg. John Smith, Susan Malick)
     */
    public String getName();

    /**
     * 
     * @return the phone number formatted as a sequence of digits
     */
    public String getPhoneNumber();

    /**
     * 
     * @return the email address
     */
    public String getEmailAddress();
}
