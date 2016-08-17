package com.owodigi.parser;

import com.owodigi.model.ContactInfo;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.Assert;
import org.junit.Test;

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

    @Test
    public void testBlankDocument() {
        final BusinessCardParser parser = BusinessCardParserFactory.newInstance();
        final ContactInfo info = parser.getContactInfo("");
        Assert.assertEquals("Name", null, info.getName());
        Assert.assertEquals("Phone", null, info.getPhoneNumber());
        Assert.assertEquals("Email", null, info.getEmailAddress());
    }

    @Test(expected = NullPointerException.class)
    public void testNullDocument() {
        final BusinessCardParser parser = BusinessCardParserFactory.newInstance();
        parser.getContactInfo(null);
    }

    @Test
    public void testExample1Backwards() throws IOException {
        final String document = new String(Files.readAllBytes(Paths.get("src/test/resources/example1-backwards.txt")), StandardCharsets.UTF_8);
        final BusinessCardParser parser = BusinessCardParserFactory.newInstance();
        final ContactInfo info = parser.getContactInfo(document);
        Assert.assertEquals("Name", "Mike Smith", info.getName());
        Assert.assertEquals("Phone", "4105551234", info.getPhoneNumber());
        Assert.assertEquals("Email", "msmith@asymmetrik.com", info.getEmailAddress());
    }

    /**
     * Source: https://media.digitalprintingireland.ie/media/images/products/slides/2/business-cards-11.jpg
     */
    @Test
    public void testExample4() throws IOException {
        final String document = new String(Files.readAllBytes(Paths.get("src/test/resources/example4.txt")), StandardCharsets.UTF_8);
        final BusinessCardParser parser = BusinessCardParserFactory.newInstance();
        final ContactInfo info = parser.getContactInfo(document);
        Assert.assertEquals("Name", "Wendy Moore", info.getName());
        Assert.assertEquals("Phone", "0123456789011", info.getPhoneNumber());
        Assert.assertEquals("Email", "E.wendy@totalbeauty.co.uk", info.getEmailAddress());
    }

    /**
     * Source: http://fw007721-flywheel.netdna-ssl.com/wp-content/uploads/02-The-Factory-Business-Cards-Ghost-BPO.jpg
     * @throws IOException
     */
    @Test
    public void testExample5() throws IOException {
        final String document = new String(Files.readAllBytes(Paths.get("src/test/resources/example5.txt")), StandardCharsets.UTF_8);
        final BusinessCardParser parser = BusinessCardParserFactory.newInstance();
        final ContactInfo info = parser.getContactInfo(document);
        Assert.assertEquals("Name", "ASHLEY LIDDELL", info.getName());
        Assert.assertEquals("Phone", "4052378146", info.getPhoneNumber());
        Assert.assertEquals("Email", "ashley@shopthefactoryokc.com", info.getEmailAddress());
    }
}
