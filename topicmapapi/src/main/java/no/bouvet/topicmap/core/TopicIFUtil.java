package no.bouvet.topicmap.core;

import net.ontopia.topicmaps.core.TopicIF;
import net.ontopia.infoset.core.LocatorIF;

import java.util.Collection;

import org.apache.commons.lang.StringUtils;

/**
 * @author Stig Lau, Bouvet ASA
 *         Date: Sep 2, 2007
 *         Time: 2:09:52 PM
 */
public class TopicIFUtil {
    /**
     * Finds the correct Topic Type of a topicIF.
     * If the topic already IS a topic type, the function will return the input parameter as the result, as a Topic Types type is "Topic Type"
     */
    public static TopicIF getTopicTypeAsTopicIF(TopicIF topicIF) {

        Collection topicIFsTopicTypes = topicIF.getTypes();
        for (Object topicType : topicIFsTopicTypes) {
            TopicIF topicTypesTopic = (TopicIF) topicType;
            for (Object subjectIndicator : topicTypesTopic.getSubjectIndicators()) {
                LocatorIF locatorIF = (LocatorIF) subjectIndicator;
                if (!StringUtils.equals(locatorIF.getAddress(), "http://psi.ontopia.net/ontology/topic-type")) {
                    return topicTypesTopic;
                }
            }
        }

        return topicIF;
    }
}
