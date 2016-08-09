package com.owodigi.model;

/**
 *
 */
public class StandardContactInfo implements ContactInfo {
    private String name;
    private String phoneNumber;
    private String emailAddress;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * 
     * @param name 
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * 
     * @param phoneNumber 
     */
    public void setPhoneNumber(final String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * 
     * @param emailAddress 
     */
    public void setEmailAddress(final String emailAddress) {
        this.emailAddress = emailAddress;
    }
}
