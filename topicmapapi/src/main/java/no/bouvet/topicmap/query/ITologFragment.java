package no.bouvet.topicmap.query;

/**
 * @author Stig Lau, Bouvet ASA
 *         Date: Sep 1, 2007
 *         Time: 12:54:29 PM
 */
public interface ITologFragment {
    public String[] tologQuery(ITopicParameter... parameters);

    /**
     * For pretty printing
     * @param parameters
     * @return as String
     */
    public String asString(ITopicParameter... parameters);

    public String name();
}
