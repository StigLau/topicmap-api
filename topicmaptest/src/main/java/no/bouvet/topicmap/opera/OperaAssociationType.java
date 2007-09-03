package no.bouvet.topicmap.opera;

import static no.bouvet.topicmap.query.TologFragmentUtil.asParameter;
import no.bouvet.topicmap.query.ITologFragment;
import no.bouvet.topicmap.query.ITopicParameter;
import no.bouvet.topicmap.query.TologFragmentUtil;

/**
 * @author Stig Lau, Bouvet ASA
 *         Date: Sep 1, 2007
 *         Time: 1:12:59 PM
 */
public enum OperaAssociationType implements ITologFragment {
    /**
     * Parameters: COMPOSER, OPERA
     */
    COMPOSED_BY {public String[] tologQuery(ITopicParameter... parameters) {
        return new String[]{"composed-by(" + asParameter(parameters[0]) + ", " + asParameter(parameters[1]) + ")"};
    }
    },
    /**
     * OPERA, SOMETHING
     */
    BASED_ON {public String[] tologQuery(ITopicParameter... parameters) {
        return new String[]{"based-on($"+ parameters[0].getIdentifyer() + " : result, $"+ parameters[1].getIdentifyer()+" : source)"};
    }
    },
    /**
     * WORK, WRITER
     */
    WRITTEN_BY {public String[] tologQuery(ITopicParameter... parameters) {
        return new String[]{"written-by($"+ parameters[0].getIdentifyer() + " : work, $" + parameters[1].getIdentifyer()+" : writer)"};
    }
    },
    BORN_IN {public String[] tologQuery(ITopicParameter... parameters) {
        return new String[]{"born-in("+ asParameter(parameters[0]) + ", "+asParameter(parameters[1])+")"};
    }
    },
    DIED_IN {public String[] tologQuery(ITopicParameter... parameters) {
        return new String[]{"died-in("+ asParameter(parameters[0]) + ", "+asParameter(parameters[1])+")"};
    }
    },
    KILLED_BY {public String[] tologQuery(ITopicParameter... parameters) {
        return new String[]{"killed-by($"+ parameters[0].getIdentifyer() + " : victim, $"+parameters[1].getIdentifyer()+" : perpetrator)"};
    }
    };

    public String asString(ITopicParameter... parameters) {
        return TologFragmentUtil.asString(this, parameters);
    }
}
