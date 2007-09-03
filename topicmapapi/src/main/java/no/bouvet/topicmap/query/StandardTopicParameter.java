package no.bouvet.topicmap.query;

import no.bouvet.topicmap.dao.TopicDAO;
import no.bouvet.topicmap.dao.TopicType;

/**
 * Standard utensil for creating a topic parameter
 * A topic parameter can either be a TopicType (you define what kind you want)
 * Or a topic (which inherantly knows its topicType)
 * @author Stig Lau, Bouvet ASA
 */
public class StandardTopicParameter implements ITopicParameter {
    private final String identifyer;
    private final TopicDAO topicDao;
    private final TopicType topicType;


    public StandardTopicParameter(TopicType topicType) {
        this.identifyer = topicType.name();
        this.topicType = topicType;
        this.topicDao = null;
    }


    public StandardTopicParameter(TopicDAO topicDao) {
        this.identifyer = topicDao.getTopicType().name();
        this.topicDao = topicDao;
        this.topicType = topicDao.getTopicType();
    }

    public String getIdentifyer() {
        return identifyer;
    }

    public TopicDAO getTopicDao() {
        return topicDao;
    }

    public TopicType getTopicType() {
        return topicType;
    }
}
