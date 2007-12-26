package no.bouvet.topicmap.query;

import net.ontopia.topicmaps.core.TopicIF;

import java.util.Map;

/**
 * Interface for TologQueries
 * @author Stig Lau
 */
public interface ITologQuery {
    String asString();

    Map<String, TopicIF> getArguments();
}
