package no.bouvet.topicmap.query;

import org.apache.commons.lang.StringUtils;

/**
 * @author Stig Lau, Bouvet ASA
 *         Date: Sep 1, 2007
 *         Time: 8:21:33 PM
 */
public class TologFragmentUtil {
    public static String asParameter(ITopicParameter topicParameter) {
        String parameterAsString = "";
        if (topicParameter.getTopicDao() != null) {
            parameterAsString += "%" + topicParameter.getIdentifyer() + "%";
        } else {
            parameterAsString += "$" + topicParameter.getIdentifyer();
        }
        if(topicParameter.getTopicType() != null) {
            parameterAsString += " : " + topicParameter.getTopicType().tologBinding();
        }
        return parameterAsString;
    }

    public static String asString(ITologFragment tologFragment, ITopicParameter... parameters) {
        String asString = "";
        for (String fragment : tologFragment.tologQuery(parameters)) {
            if (StringUtils.isNotBlank(asString)) {
                asString += "\n";
            }
            asString += fragment;
        }
        return asString;
    }
}
