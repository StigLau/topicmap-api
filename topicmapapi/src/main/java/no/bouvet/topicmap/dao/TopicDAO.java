package no.bouvet.topicmap.dao;

import net.ontopia.topicmaps.core.TopicIF;
import net.ontopia.topicmaps.utils.TopicStringifiers;
import no.bouvet.topicmap.core.TopicIFUtil;
import no.bouvet.topicmap.core.TopicMap;

/**
 * Wrapper class round the topicIF with heplerfunctions for accessing its fields
 * @author Stig Lau, Bouvet ASA
 */
public class TopicDAO {
    private final TopicIF topicIF;

    public TopicDAO(TopicIF topicIF) {
        this.topicIF = topicIF;
    }

    public TopicType getTopicType() {
        TopicIF topicTypeIF = TopicIFUtil.getTopicTypeAsTopicIF(this.topicIF);
        return TopicMap.locateTopicType(topicTypeIF);
    }

    public TopicIF getTopicIF() {
        return topicIF;
    }

    public String getName() {
        return TopicStringifiers.toString(topicIF);
    }
}