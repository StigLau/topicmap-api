package no.bouvet.topicmap.query;

import org.junit.Test;
import org.junit.BeforeClass;
import static org.junit.Assert.*;
import no.bouvet.topicmap.AbstractTopicMapTestFixture;
import no.bouvet.topicmap.dao.TopicDAO;
import java.util.List;

/**
 * Simple test for showing usage of SimpleTologQueryString and extraction of occurrance types
 *
 * @author Stig Lau
 */
public class SimpleTologQueryStringTest extends AbstractTopicMapTestFixture {

    //TestTopics
    private static TopicDAO puccini;

    @BeforeClass
    public static void setUp() {
        puccini = topicmap.getTopicDAOByPSI("http://en.wikipedia.org/wiki/Puccini");
    }

    @Test
    public void testComposerBirthDate() {
        ITologQuery tologQuery = new SimpleTologQueryString("date-of-birth(%COMPOSER%, $BIRTHDATE)?", puccini, "COMPOSER");
        assertEquals("1858-12-22",
                topicmap.queryForSingleValue(tologQuery, SimpleTopicParameterFactory.create("BIRTHDATE")));
    }
    // Validation tests

    @Test(expected = IllegalArgumentException.class)
    public void testNullArgumentInputThrowsValidationExceptions() {
        new SimpleTologQueryString("date-of-birth(%COMPOSER%, $BIRTHDATE)?", null, "COMPOSER");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBlankArgumentNameThrowsValidationExceptions() {
        new SimpleTologQueryString("date-of-birth(%COMPOSER%, $BIRTHDATE)?", puccini, "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBlankQueryThrowsValidationExceptions() {
        new SimpleTologQueryString("   ", puccini, "COMPOSER");
    }

    @Test
    public void testSimplerVersion() {
        ITologQuery tologQuery = new SimpleTologQueryString("instance-of($SOMETHING, $SOMETHINGOTHER)?");
        List resutl = topicmap.queryForList(tologQuery, SimpleTopicParameterFactory.create("SOMETHINGOTHER"));
        assertEquals(49, resutl.size());
    }
}
