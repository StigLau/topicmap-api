package no.bouvet.topicmap.opera;

import static no.bouvet.topicmap.query.AssociationTypeFactory.createAssociationString;
import no.bouvet.topicmap.query.ITologFragment;
import no.bouvet.topicmap.query.ITopicParameter;
import no.bouvet.topicmap.query.AssociationTypeFactory;

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
        return createAssociationString("composed-by", new String[]{"", ""}, parameters);
    }
    },
    /**
     * OPERA, SOMETHING
     */
    BASED_ON {public String[] tologQuery(ITopicParameter... parameters) {
        return createAssociationString("based-on", new String[]{"result", "source"}, parameters);
    }
    },
    /**
     * WORK, WRITER
     */
    WRITTEN_BY {public String[] tologQuery(ITopicParameter... parameters) {
        return createAssociationString("written-by", new String[]{"work", "writer"}, parameters);
    }
    },
    BORN_IN {public String[] tologQuery(ITopicParameter... parameters) {
        return createAssociationString("born-in", new String[]{"", ""}, parameters);
    }
    },
    DIED_IN {public String[] tologQuery(ITopicParameter... parameters) {
        return createAssociationString("died-in", new String[]{"", ""}, parameters);
    }
    },
    KILLED_BY {public String[] tologQuery(ITopicParameter... parameters) {
        return createAssociationString("killed-by", new String[]{"victim", "perpetrator"}, parameters);
    }
    };

    public String asString(ITopicParameter... parameters) {
        return AssociationTypeFactory.asString(this, parameters);
    }
}