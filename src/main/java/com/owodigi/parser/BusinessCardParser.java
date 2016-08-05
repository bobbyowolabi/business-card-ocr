package com.owodigi.parser;

import com.owodigi.model.ContactInfo;

/**
 * A parser that extracts contact information from the text of a business card.
 */
public interface BusinessCardParser {

    /**
     * Parses the given document and extracts the necessary metadata to create
     * a ContactInfo.
     * 
     * @param document the text of the given business card
     * @return contact information based on extracted information from the given 
     * document
     */
    public ContactInfo getContactInfo(String document);
}
