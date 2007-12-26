package no.bouvet.topicmap.query;

import no.bouvet.topicmap.dao.TopicType;

/**
 * Simple helperclass for creating use-and-throw-away TopicParameters for simple queries
 *
 * @author Stig Lau, Bouvet ASA
 */
public class SimpleTopicParameterFactory {


    public static ITopicParameter create(final String fieldName) {
        return new StandardTopicParameter(new TopicType() {
            public String tologBinding() {
                return fieldName;
            }

            public String psi() {
                return fieldName;
            }

            public String name() {
                return fieldName;
            }
        }
        );
    }
}