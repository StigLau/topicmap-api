package no.bouvet.topicmap.core;

import no.bouvet.topicmap.dao.TopicDAO;
import no.bouvet.topicmap.dao.TopicType;
import no.bouvet.topicmap.opera.OperaTopicType;
import no.bouvet.topicmap.AbstractTopicMapTestFixture;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import net.ontopia.topicmaps.core.TopicIF;

/**
 * @author Stig Lau, Bouvet ASA
 *         Date: Sep 2, 2007
 *         Time: 1:54:17 PM
 */
public class TopicMapTest extends AbstractTopicMapTestFixture {

    @Test
    public void testFindTopicDAOByPSI() {
        TopicDAO puccini = topicmap.getTopicDAOByPSI("http://en.wikipedia.org/wiki/Puccini");
        assertEquals(OperaTopicType.COMPOSER, puccini.getTopicType());
    }

    @Test
    public void testSearchForComposerTopicType() {
        TopicIF topicTypeIF = TopicMapUtil.getTopicIFByPSI(OperaTopicType.COMPOSER.psi(), topicmap.getInterface());
        TopicType topicType = TopicMap.locateTopicType(topicTypeIF);
        assertEquals(OperaTopicType.COMPOSER, topicType);
    }

}
