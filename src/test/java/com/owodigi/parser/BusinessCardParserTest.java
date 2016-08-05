package com.owodigi.parser;

import com.owodigi.model.ContactInfo;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 */
public class BusinessCardParserTest {

    @Test
    public void testExample1() throws IOException {
        final String document = new String(Files.readAllBytes(Paths.get("src/test/resources/example1.txt")), StandardCharsets.UTF_8);
        final BusinessCardParser parser = BusinessCardParserFactory.newInstance();
        final ContactInfo info = parser.getContactInfo(document);
        Assert.assertEquals("Name", "Mike Smith", info.getName());
        Assert.assertEquals("Phone", "4105551234", info.getPhoneNumber());
        Assert.assertEquals("Email", "msmith@asymmetrik.com", info.getEmailAddress());
    }

    @Test
    public void testExample2() throws IOException {
        final String document = new String(Files.readAllBytes(Paths.get("src/test/resources/example2.txt")), StandardCharsets.UTF_8);
        final BusinessCardParser parser = BusinessCardParserFactory.newInstance();
        final ContactInfo info = parser.getContactInfo(document);
        Assert.assertEquals("Name", "Lisa Haung", info.getName());
        Assert.assertEquals("Phone", "4105551234", info.getPhoneNumber());
        Assert.assertEquals("Email", "lisa.haung@foobartech.com", info.getEmailAddress());
    }

    @Test
    public void testExample3() throws IOException {
        final String document = new String(Files.readAllBytes(Paths.get("src/test/resources/example3.txt")), StandardCharsets.UTF_8);
        final BusinessCardParser parser = BusinessCardParserFactory.newInstance();
        final ContactInfo info = parser.getContactInfo(document);
        Assert.assertEquals("Name", "Arthur Wilson", info.getName());
        Assert.assertEquals("Phone", "17035551259", info.getPhoneNumber());
        Assert.assertEquals("Email", "awilson@abctech.com", info.getEmailAddress());
    }
}
