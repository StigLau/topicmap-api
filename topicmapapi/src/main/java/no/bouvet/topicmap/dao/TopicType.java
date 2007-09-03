package no.bouvet.topicmap.dao;

import no.bouvet.topicmap.query.ITopicParameter;

/**
 * @author Ove H. Scheel, Bouvet AS
 * @author Stig Lau, Bouvet AS
 */
public interface TopicType {
     public static final TopicType NULL = new TopicType() {
         public String tologBinding() { return "NULL";}
         public String psi() { return "NULL"; }
         public String name() { return "NULL"; }
     };

    /**
     * @return the tolog binding that is used
     */
    String tologBinding();

    /**
     * @return the tolog fragment to be used when filtering on this type
     */
    String psi();

    String name();
}
