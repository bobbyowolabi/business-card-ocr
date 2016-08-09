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
    private static final Set<String> PHONE_SYNONYMS = new HashSet<String>() {{
        Collections.addAll(this, "Phone", "phone", "Telephone", "telephone");
    }};

    private void deleteLastCharacter(final StringBuilder builder) {
        builder.deleteCharAt(builder.length() - 1);
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

    private String extractCharactersAndWhitespace(final String value) {
        final StringBuilder characters = new StringBuilder();
        for (int i = 0; i < value.length(); ++i) {
            final char character = value.charAt(i);
            if (Character.isLetter(character) || Character.isWhitespace(character)) {
                characters.append(character);
            }
        }
        return characters.toString();
    }
    
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
        final Sentence sentence = new Sentence(value);
        final Iterator<String> wordIterator = sentence.words().iterator();
        final Iterator<String> nerTagIterator = sentence.nerTags().iterator();
        final StringBuilder nameBuilder = new StringBuilder();
        while (wordIterator.hasNext()) {
            final String word = wordIterator.next();
            final String nerTags = nerTagIterator.next();
            if (nerTags.equals("PERSON")) {
                nameBuilder.append(word).append(" ");
            }
        }
        if (nameBuilder.length() > 0) {
            deleteLastCharacter(nameBuilder);
            return nameBuilder.toString();
        } else {
            return null;
        }
    }

    /**
     * Parses the given value and extracts and returns a phone number if one
     * exists; otherwise, returns null.
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
        String headings = value.replace(phoneNumber, "");
        headings = extractCharactersAndWhitespace(headings);
        if (headings.isEmpty()) {
            return extractDigits(value);
        }
        for (String heading : headings.split("\\s+")) {
            heading = extractCharactersAndWhitespace(heading);
            for (final String phoneSynonym : PHONE_SYNONYMS) {
                if (heading.isEmpty() == false && phoneSynonym.contains(heading)) {
                    return extractDigits(value);
                }
            }
        }
        return null;
    }

    @Override
    public ContactInfo getContactInfo(final String document) {
        String value;
        final StandardContactInfo contactInfo = new StandardContactInfo();
        for (final String line : document.split("\\v+")) {
            if ((value = extractName(line)) != null) {
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
