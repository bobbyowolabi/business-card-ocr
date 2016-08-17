package com.owodigi.model;

/**
 * Basic implementation of ContactInfo
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
     * Sets the name of this ContactInfo to the given name.
     * 
     * @param name name to set to this ContactInfo
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Sets the phone number of this ContactInfo.
     * 
     * @param phoneNumber phone number to set to this ContactInfo
     */
    public void setPhoneNumber(final String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Sets the email address of this ContactInfo
     * 
     * @param emailAddress  email address to set to this ContactInfo
     */
    public void setEmailAddress(final String emailAddress) {
        this.emailAddress = emailAddress;
    }
}
