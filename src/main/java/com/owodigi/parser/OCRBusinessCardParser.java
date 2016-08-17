package com.owodigi.parser;

import com.owodigi.model.ContactInfo;
import com.owodigi.model.StandardContactInfo;
import edu.stanford.nlp.simple.Sentence;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * BusinessCardParser parses the results of the optical character recognition
 * (OCR) component in order to extract the name, phone number, and email address
 * from processed business card images.
 */
public class OCRBusinessCardParser implements BusinessCardParser {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("(.+[@].+[.].+)");
    private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile(".*(\\(*\\d{3}.*\\d{3}.*\\d{4})");
    private static final Set<String> PHONE_HEADERS = new HashSet<String>() {{
        Collections.addAll(this, "Phone", "phone", "Telephone", "telephone");
    }};

    /**
     * Removes the last character of the given StringBuilder.
     * 
     * @param builder StringBuilder whose last character is to be removed
     */
    private void deleteLastCharacter(final StringBuilder builder) {
        if (builder.length() > 0) {
            builder.deleteCharAt(builder.length() - 1);
        }
    }

    /**
     * If the given Pattern matches the input, the value of the first capturing
     * group is returned; otherwise returns null.
     *
     * This method assumes that the provided Pattern has one and only one capturing
     * group defined.
     *
     * @param value The value to be matched against
     * @param pattern the pattern match against the given input
     * @return
     */
    private String extract(final String value, final Pattern pattern) throws IllegalArgumentException {
        final Matcher matcher = pattern.matcher(value);
        return matcher.matches() ? matcher.group(1) : null;
    }

    /**
     * Returns the letter and whitespace characters found in the given String.
     * 
     * @param value value to extract letters and whitespace from.
     * @return letter and whitespace characters found in the given String
     */
    private String extractLettersAndWhitespace(final String value) {
        final StringBuilder characters = new StringBuilder();
        for (int i = 0; i < value.length(); ++i) {
            final char character = value.charAt(i);
            if (Character.isLetter(character) || Character.isWhitespace(character)) {
                characters.append(character);
            }
        }
        return characters.toString();
    }
    
    /**
     * Returns all of the digit characters found in the given value in order.
     * 
     * @param value value to extract digits from
     * @return all of the digit characters found in the given value
     */
    private String extractDigits(final String value) {
        final StringBuilder digits = new StringBuilder();
        for (int i = 0; i < value.length(); ++i) {
            final char character = value.charAt(i);
            if (Character.isDigit(character)) {
                digits.append(character);
            }
        }
        return digits.toString();
    }

    /**
     * Parses the given value and extracts and returns an email if one exists;
     * otherwise returns null.
     *
     * @param value value to extract email from.
     * @return an email if one exists in the given value; otherwise, null.
     */
    private String extractEmail(final String value) {
        return extract(value, EMAIL_PATTERN);
    }

    /**
     * Parses the given value and extracts and returns a name if one exists;
     * otherwise returns null.
     *
     * @param value value to extract name from
     * @return a name if one exists in the given value; otherwise, null.
     */
    private String extractName(final String value) {
        if (value.isEmpty()) {
            return null;
        }
        final Sentence sentence = new Sentence(value);
        final Iterator<String> wordIterator = sentence.words().iterator();
        final Iterator<String> nerTagIterator = sentence.nerTags().iterator();
        final StringBuilder nameBuilder = new StringBuilder();
        while (wordIterator.hasNext()) {
            if (nerTagIterator.next().equals("PERSON")) {
                do {
                    nameBuilder.append(wordIterator.next()).append(" ");
                } while (wordIterator.hasNext() && nerTagIterator.next().equals("PERSON"));
                break;
            }
            wordIterator.next();
        }
        deleteLastCharacter(nameBuilder);
        return nameBuilder.length() > 0 ? nameBuilder.toString() : null;
    }

    /**
     * Parses the given value and extracts and returns a phone number if one
     * exists; otherwise, returns null.
     * 
     * Only the digits of the phone number will be returned.
     *
     * @param value value to extract phone number from
     * @return returns a phone number if one exists in the given value; otherwise,
     * null.
     */
    private String extractPhoneNumber(final String value) {
        final String phoneNumber = extract(value, PHONE_NUMBER_PATTERN);
        if (phoneNumber == null) {
            return null;
        }
        String headers = value.replace(phoneNumber, "");
        headers = extractLettersAndWhitespace(headers);
        if (headers.isEmpty()) {
            return extractDigits(value);
        }
        for (String heading : headers.split("\\s+")) {
            heading = extractLettersAndWhitespace(heading);
            for (final String phoneSynonym : PHONE_HEADERS) {
                if (heading.isEmpty() == false && phoneSynonym.contains(heading)) {
                    return extractDigits(value);
                }
            }
        }
        return null;
    }

    @Override
    public ContactInfo getContactInfo(final String document) {
        final StandardContactInfo contactInfo = new StandardContactInfo();
        contactInfo.setName(extractName(document));
        String value;
        for (final String line : document.split("\\v+")) {
            if ((contactInfo.getName() == null) && (value = extractName(line)) != null) {
                contactInfo.setName(value);
            } else if ((value = extractPhoneNumber(line)) != null) {
                contactInfo.setPhoneNumber(value);
            } else if ((value = extractEmail(line)) != null) {
                contactInfo.setEmailAddress(value);
            }
        }
        return contactInfo;
    }
}
