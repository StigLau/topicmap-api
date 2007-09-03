package no.bouvet.topicmap.opera;

import net.ontopia.topicmaps.core.TopicMapStoreIF;
import no.bouvet.topicmap.core.TopicMap;
import no.bouvet.topicmap.dao.TopicType;
import no.bouvet.topicmap.servlet.OperaThreadLocalStoreServletFilter;

/**
 * @author Stig Lau, Bouvet AS
 */
public class OperaTopicMap extends TopicMap {

    static String prefixes =
            "  using ont   for i\"http://psi.ontopia.net/#\"" +
                    "  using onto  for i\"http://psi.ontopia.net/ontology/\"" +
                    "  using purl  for i\"http://purl.org/dc/elements/1.1/\"" +
                    "  using xtm   for i\"http://www.topicmaps.org/xtm/1.0/core.xtm#\"" +
                    "  using tech  for i\"http://www.techquila.com/psi/thesaurus/#\"" +
                    "  using lkt   for i\"http://psi.udir.no/ontologi/lkt/\"" +
                    "  using udir  for i\"http://psi.udir.no/ontologi/\"" +
                    "  import \"http://psi.ontopia.net/tolog/string/\" as str ";

    // Inference rules - the order in which these appear is of importance!
    /*
    static String descendantOf = " descendant-of($ANCESTOR, $DESCENDANT) :- {\n" +
            "      tech:broader-narrower($ANCESTOR : tech:broader, $DESCENDANT : tech:narrower) |\n" +
            "      tech:broader-narrower($ANCESTOR : tech:broader, $MID : tech:narrower),\n" +
            "      descendant-of($MID, $DESCENDANT) }. ";
      */
    static {
        declarations = prefixes;
    }

    /**
     * Constructor, mostly used by ThreadLocalStoreServletFilter
     *
     * @param storeIF The store where the topicmap can be fetched from
     */
    public OperaTopicMap(TopicMapStoreIF storeIF) {
        super(storeIF);
        TopicMap.topicTypes = OperaTopicType.values();
    }

    public TopicMap getInstance() {
        return OperaThreadLocalStoreServletFilter.getTopicMap();
    }
}