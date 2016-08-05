package com.owodigi.parser;

/**
 * A Factory providing access to BusinessCardParsers implementations.
 */
public class BusinessCardParserFactory {

    /**
     * Return a new BusinessCardParser.
     * 
     * @return a new BusinessCardParser
     */
    public static BusinessCardParser newInstance() {
        return new OCRBusinessCardParser();
    }
}
