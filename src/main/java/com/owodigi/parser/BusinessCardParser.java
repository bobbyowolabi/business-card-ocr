package com.owodigi.parser;

import com.owodigi.model.ContactInfo;

/**
 *
 */
public interface BusinessCardParser {

    /**
     * 
     * @param document
     * @return 
     */
    public ContactInfo getContactInfo(String document);
}
