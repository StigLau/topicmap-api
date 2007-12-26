package no.bouvet.topicmap.query;

import net.ontopia.topicmaps.core.TopicIF;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TologQuery is a helperclass for constructing a Tolog Query.
 * It's main goal is for helping with a simpler interface for using sorting, filtering and pageing.
 * @author Stig Lau
 */
public class TologQuery implements ITologQuery {
    private List<FragmentHolder> queryFragments = new ArrayList<FragmentHolder>();
    private List<ITopicParameter> arguments = new ArrayList<ITopicParameter>();

    private SortOrder sortOrder;
    private ITopicParameter[] orderBy;
    private Integer limit;
    private Integer offset;
    private ITopicParameter countParameter;

    public TologQuery()
    {}
    

    /**
     * constructor shorthand
     * @param tologFragment
     * @param arguments
     */
    public TologQuery(ITologFragment tologFragment, ITopicParameter... arguments) {
        this.and(tologFragment, arguments);
    }


    public void and(ITologFragment fragment, ITopicParameter... arguments) {
        this.queryFragments.add(new FragmentHolder(fragment, arguments));
        this.addArguments(arguments);
    }

    /**
     * avoids adding having more than one instance of arguments in the arguments list
     * @param newArguments arguments to add
     */
    private void addArguments(ITopicParameter[] newArguments) {
        for (ITopicParameter argument : newArguments) {
            if(!this.arguments.contains(argument)) {
                this.arguments.add(argument);
            }
        }
    }

    private String selectFragment() {
        String finalQuery = "select ";
        if(countParameter != null) {
            finalQuery += "count ";
            finalQuery += "($" + countParameter.getIdentifyer() + ")";
        } else {
            boolean firstArgument = true;
            for (ITopicParameter argument : arguments) {
                if(argument.getTopicDao() == null) {
                    if(!firstArgument) {
                        finalQuery += ", ";
                    }
                    finalQuery += "$" + argument.getIdentifyer();
                    firstArgument = false;
                }
            }
        }
        finalQuery += " from";
        return finalQuery;
    }

    public String asString() {
        String finalQuery = selectFragment();
        boolean firstQueryFragment = true;
        for (FragmentHolder tologFragment : queryFragments) {
            for (String queryFragment : tologFragment.getFragmentAsString()) {
                if(!firstQueryFragment) {
                    finalQuery += ",";
                } else {
                    firstQueryFragment = false;
                }
                finalQuery += "\n" + queryFragment;
            }
        }
        if (orderBy != null) {
            finalQuery += "\norder by ";
            boolean firstArgument = true;
            for (ITopicParameter argument : orderBy) {
                if(!firstArgument) {
                    finalQuery += ", ";
                } else {
                    firstArgument = false;
                }
                finalQuery += "$" + argument.getIdentifyer();
            }
        }
        if (sortOrder != null) {
            finalQuery += " " + sortOrder;
        }
        if (limit != null) {
            finalQuery += "\nlimit " + limit;
        }
        if (offset != null) {
            finalQuery += "\noffset " + offset;
        }
        finalQuery += "?";
        return finalQuery;
    }

    public Map<String, TopicIF> getArguments() {
        Map<String, TopicIF> argumentMap = new HashMap<String, TopicIF>();
        for (ITopicParameter argument : arguments) {
            if(argument.getTopicDao() != null) {
                argumentMap.put(argument.getIdentifyer(), argument.getTopicDao().getTopicIF());
            }
        }
        return argumentMap;
    }

    public void orderBy(SortOrder sortOrder, ITopicParameter... sortBy) {
        this.orderBy =  sortBy;
        this.addArguments(sortBy);
        this.sortOrder = sortOrder;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    /**
     * Make the query a count query 
     * @param countParameter Set to <code>null</code> to stop counting
     */
    public void setCountParameter(ITopicParameter countParameter) {
        this.countParameter = countParameter;
    }

    public String toString() {
        String output = asString();
        output += "\nArguments:";
        for (ITopicParameter argument : arguments) {
            output += "\n" + argument.getIdentifyer();
            if(argument.getTopicDao() != null) {
                output += " : " + argument.getTopicDao().getName();
            }
        }
        return output;
    }
}
