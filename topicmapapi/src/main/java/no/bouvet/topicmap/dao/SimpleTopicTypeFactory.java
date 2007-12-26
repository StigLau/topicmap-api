package no.bouvet.topicmap.dao;

/**
 * Simple helperclass for creating use-and-throw-away TopicTypes for simple queries
 * @author Stig Lau
 */
public class SimpleTopicTypeFactory {

    public static TopicType create(final String fieldName) {
        return new TopicType() {
            public String tologBinding() {
                return fieldName;
            }

            public String psi() {
                return fieldName;
            }

            public String name() {
                return fieldName;
            }
        };
    }
}
