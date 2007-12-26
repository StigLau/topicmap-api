package no.bouvet.topicmap.core;

import net.ontopia.infoset.impl.basic.URILocator;
import net.ontopia.topicmaps.core.TopicIF;
import net.ontopia.topicmaps.core.TopicMapIF;
import net.ontopia.topicmaps.core.TopicMapStoreIF;
import net.ontopia.topicmaps.query.core.DeclarationContextIF;
import net.ontopia.topicmaps.query.core.InvalidQueryException;
import net.ontopia.topicmaps.query.core.QueryResultIF;
import net.ontopia.topicmaps.query.utils.QueryUtils;
import no.bouvet.topicmap.query.ITologQuery;
import no.bouvet.topicmap.query.ITopicParameter;
import no.bouvet.topicmap.dao.TopicDAO;
import no.bouvet.topicmap.dao.TopicType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.lang.StringUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * Encapsulation of the Topic Map engine.
 *
 * @author n05609 Stig Lau, Bouvet AS
 * @version 1.0 19.jun.2007
 */
public abstract class TopicMap {

    private static final Log log = LogFactory.getLog(TopicMap.class);


    private final TopicMapIF topicMapIF;
    public final DeclarationContextIF declarationContext;

    protected static TopicType[] topicTypes;

    /**
     * Constructor, mostly used by ThreadLocalStoreServletFilter
     *
     * @param storeIF The store where the topicmap can be fetched from
     */
    public TopicMap(TopicMapStoreIF storeIF) {
        topicMapIF = storeIF.getTopicMap();
        declarationContext = getDeclarationContext(topicMapIF);
    }

    public TopicMapIF getInterface() {
        return topicMapIF;
    }

    public abstract TopicMap getInstance();

    /**
     * Safe way of querying the topicMap if a topicID exists in the topicmap
     *
     * @param objectId topic ID on the form T252324
     * @return the topicIF
     */
    public TopicIF getTopicIFProgrammatically(String objectId) {
        return (TopicIF) topicMapIF.getObjectById(objectId);
    }

    public TopicDAO getTopicDAOByPSI(String psi) {
        TopicIF topicIf = TopicMapUtil.getTopicIFByPSI(psi, topicMapIF);
        return new TopicDAO(topicIf);
    }

    // Returns the specified object or null
    public <T> T queryForSingleValue(ITologQuery tologQuery, ITopicParameter resultParameterField) {
        List<T> resultAsList = queryForList(tologQuery, resultParameterField);
        if(resultAsList.size() == 0) {
            return null;
        } else if(resultAsList.size() == 1){
            return resultAsList.get(0);
        } else {
            throw new ArithmeticException("Resultset contained " + resultAsList.size() + " entries. Expected 0 or one");
        }
    }

    public Map queryForMap(ITologQuery tologQuery, ITopicParameter keyName, ITopicParameter valueName) {
        final String localKeyName = keyName.getIdentifyer();
        final String localValueName = valueName.getIdentifyer();

        RowMapper rowmapper = new RowMapper() {
            private Map result = new HashMap();

            public void addRow(QueryResultIF queryResult) {
                Object key = queryResult.getValue(localKeyName);
                Object value = queryResult.getValue(localValueName);
                result.put(key, value);
            }

            public Object getResult() {
                return result;
            }
        };

        Map result = (Map) TopicMapUtil.executeTologQuery(tologQuery, rowmapper, declarationContext, topicMapIF);

        if (result == null) {
            log.error("Query returned object of unexpected type: " + result);
            return Collections.EMPTY_MAP;
        } else {
            return result;
        }
    }

    public<T> List<T> queryForList(ITologQuery tologQuery, ITopicParameter fieldValue) {
        final String localKeyName = fieldValue.getIdentifyer();

        System.out.println("tologQuery:\n" + tologQuery.toString() + ". Get Field: " + localKeyName);

        RowMapper rowmapper = new RowMapper() {
            private List<T> result = new ArrayList<T>();

            public void addRow(QueryResultIF queryResult) {
                Object key = queryResult.getValue(localKeyName);
                result.add((T) key);
            }

            public Object getResult() {
                return result;
            }
        };
        List<T> result = (List<T>) TopicMapUtil.executeTologQuery(tologQuery, rowmapper, declarationContext, topicMapIF);
        if (result == null) {
            log.error("Query returned object of unexpected type: " + result);
            return Collections.EMPTY_LIST;
        } else {
            return TopicMapUtil.removeDuplicates(result);
        }
    }

    /**
     * Search for the correct Topic Type
     * @param topicTypeIF
     * @return
     */
    public static TopicType locateTopicType(TopicIF topicTypeIF) {
        Collection psi = topicTypeIF.getSubjectIndicators();
        for (Object indicatorAsObject : psi) {
            if (indicatorAsObject instanceof URILocator) {
                URILocator uriLocator = (URILocator) indicatorAsObject;
                for (TopicType topicType : topicTypes) {
                    if (StringUtils.equals(topicType.psi(), uriLocator.getAddress())) {
                        return topicType;
                    }
                }
            }
        }
        return TopicType.NULL;
    }

    protected static String declarations;


    /**
     * Returns DeclarationContext for use with tolog query methods.
     * Note that the order in which the inference rules (used as part of the
     * import statement) appear is of importance.
     *
     * @param tmIF the topicMap to parse declaration with
     * @return the context
     */
    private DeclarationContextIF getDeclarationContext(TopicMapIF tmIF) {
        try {
            return QueryUtils.parseDeclarations(tmIF, declarations);
        } catch (InvalidQueryException e) {
            throw new RuntimeException(e);
        }
    }
}


