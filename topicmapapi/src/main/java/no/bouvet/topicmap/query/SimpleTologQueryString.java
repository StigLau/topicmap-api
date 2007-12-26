package no.bouvet.topicmap.query;

import net.ontopia.topicmaps.core.TopicIF;

import java.util.*;

import no.bouvet.topicmap.dao.TopicDAO;
import org.apache.commons.lang.Validate;
import org.apache.commons.lang.StringUtils;

/**
 * TologQuery is a helperclass for constructing a Tolog Query from a single string.
 *
 * @author Stig Lau
 */
public class SimpleTologQueryString implements ITologQuery {

    private String tologQuery;
    private TopicDAO argument;
    private String argumentName;

    public SimpleTologQueryString(String tologQuery) {
        Validate.isTrue(StringUtils.isNotBlank(tologQuery), "QueryString may not be blank");
        this.tologQuery = tologQuery;
    }

    public SimpleTologQueryString(String tologQuery, TopicDAO argument, String argumentName) {
        Validate.isTrue(StringUtils.isNotBlank(tologQuery), "QueryString may not be blank");
        Validate.notNull(argument, "Argument may not be null");
        Validate.isTrue(StringUtils.isNotBlank(argumentName), "ArgumentName may not be blank");

        this.tologQuery = tologQuery;
        this.argument = argument;
        this.argumentName = argumentName;
    }

    public String asString() {
        return tologQuery;
    }

    public Map<String, TopicIF> getArguments() {
        Map<String, TopicIF> argumentMap = new HashMap<String, TopicIF>();
        if(argument != null) {
            argumentMap.put(argumentName, argument.getTopicIF());
        }
        return argumentMap;
    }

    public String toString() {
        String result =  asString();
        if (argument != null) {
            result += "\nArgument: " + argumentName + " : " + argument.getName();
        }
        return result;
    }
}