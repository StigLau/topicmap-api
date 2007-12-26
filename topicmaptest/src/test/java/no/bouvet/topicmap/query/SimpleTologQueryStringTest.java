package no.bouvet.topicmap.query;

import org.junit.Test;
import static org.junit.Assert.*;
import no.bouvet.topicmap.AbstractTopicMapTestFixture;
import no.bouvet.topicmap.dao.TopicDAO;
import no.bouvet.topicmap.dao.SimpleTopicTypeFactory;

/**
 * Simple test for showing usage of SimpleTologQueryString and extraction of occurrance types
 * @author Stig Lau
 */
public class SimpleTologQueryStringTest extends AbstractTopicMapTestFixture {

    //TestTopics
    private static TopicDAO puccini;

    public static void setUp() {
        puccini = topicmap.getTopicDAOByPSI("http://en.wikipedia.org/wiki/Puccini");
    }
    
    @Test
    public void testComposerBirthDate() {
        ITologQuery tologQuery = new SimpleTologQueryString("date-of-birth(%COMPOSER%, $BIRTHDATE)?", puccini, "COMPOSER");
        assertEquals("1858-12-22", topicmap.queryForSingleValue(
                tologQuery, new StandardTopicParameter(SimpleTopicTypeFactory.create("BIRTHDATE"))));
    }
}
