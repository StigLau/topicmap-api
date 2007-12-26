package no.bouvet.topicmap.query;

import org.apache.commons.lang.StringUtils;

/**
 * Used for creating AssociationTypeStrings 
 * @author Stig Lau, Bouvet ASA
 */
public class AssociationTypeFactory {
    public static String[] createAssociationString(String association, String[] associationRoles, ITopicParameter[] parameters) {
        if(associationRoles.length != parameters.length) {
            throw new IllegalArgumentException("Inconsistent number of parameters: associationroles " + associationRoles.length + ", parameters " + parameters.length);
        }
        String result = association + "(";
        boolean firstParameter = true;
        for (int i = 0; i < associationRoles.length; i++) {
            ITopicParameter parameter = parameters[i];
            if(!firstParameter) {
                result += ", ";
            } else {
                firstParameter = false;
            }
            if(StringUtils.isNotBlank(associationRoles[i])) {
                result += "$" + parameter.getIdentifyer();
                result += " : " + associationRoles[i];
            } else {
                result += createSingleParameter(parameter);
            }
        }
        result += ")";
        return new String[] {result};
    }


    private static String createSingleParameter(ITopicParameter topicParameter) {
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
