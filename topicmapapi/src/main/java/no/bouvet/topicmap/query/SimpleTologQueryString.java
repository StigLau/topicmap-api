package no.bouvet.topicmap.query;

import net.ontopia.topicmaps.core.TopicIF;

import java.util.*;

import no.bouvet.topicmap.dao.TopicDAO;

/**
 * TologQuery is a helperclass for constructing a Tolog Query from a single string.
 *
 * @author Stig Lau
 */
public class SimpleTologQueryString implements ITologQuery {

    private String tologQuery;
    private TopicDAO argument;
    private String argumentName;

    public SimpleTologQueryString(String tologQuery, TopicDAO argument, String argumentName) {
        this.tologQuery = tologQuery;
        this.argument = argument;
        this.argumentName = argumentName;
    }

    public String asString() {
        return tologQuery;
    }

    public Map<String, TopicIF> getArguments() {
        Map<String, TopicIF> argumentMap = new HashMap<String, TopicIF>();
        argumentMap.put(argumentName, argument.getTopicIF());
        return argumentMap;
    }

    public String toString() {
        return asString() + "\nArgument: " + argumentName + " : " + argument.getName();
    }
}