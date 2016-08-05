package com.owodigi.parser;

import com.owodigi.model.ContactInfo;

/**
 * BusinessCardParser parses the results of the optical character recognition
 * (OCR) component in order to extract the name, phone number, and email address
 * from processed business card images.
 */
public class OCRBusinessCardParser implements BusinessCardParser {

    @Override
    public ContactInfo getContactInfo(final String document) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
