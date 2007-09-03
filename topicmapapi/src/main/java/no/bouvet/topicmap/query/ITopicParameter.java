package no.bouvet.topicmap.query;

import no.bouvet.topicmap.dao.TopicDAO;
import no.bouvet.topicmap.dao.TopicType;

/**
 * @author Stig Lau, Bouvet ASA
 *         Date: Sep 1, 2007
 *         Time: 12:55:39 PM
 */
public interface ITopicParameter {
    String getIdentifyer();

    TopicDAO getTopicDao();

    TopicType getTopicType();
}
