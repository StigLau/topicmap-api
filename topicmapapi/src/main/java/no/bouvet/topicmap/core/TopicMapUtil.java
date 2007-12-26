package no.bouvet.topicmap.core;

import net.ontopia.topicmaps.core.TopicMapIF;
import net.ontopia.topicmaps.core.TopicIF;
import net.ontopia.topicmaps.query.core.DeclarationContextIF;
import net.ontopia.topicmaps.query.core.QueryProcessorIF;
import net.ontopia.topicmaps.query.core.QueryResultIF;
import net.ontopia.topicmaps.query.utils.QueryUtils;
import net.ontopia.utils.URIUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import no.bouvet.topicmap.query.ITologQuery;

/**
 * The Topic Map Adapter Util is concerned with the java-technik part of communicating with and querying the topic map.
 * It handles all the intricate parts of the TM system and returns a nice set of topic lists as a result
 * It is not not to be polluted by tolog querys
 *
 * @author Stig Lau, Bouvet AS
 */
public class TopicMapUtil {
    private static final Log log = LogFactory.getLog(TopicMapUtil.class);



    /**
     * problem if more than one answer is returned
     *
     * @param tologQuery           the tolog query
     * @param fieldName            the field to extract
     * @param declarationContextIF The context of the query. Often import statements of domain or functions
     * @param topicMapIF           the topic map
     * @return the field. Returns null if the query resulted in an exception.
     * @deprecated - consider using the one with rowmapper
     */

    public static Object executeTologQuery(ITologQuery tologQuery, String fieldName, DeclarationContextIF declarationContextIF, TopicMapIF topicMapIF) {
        System.out.println(tologQuery.toString());
        if (log.isDebugEnabled()) {
            log.debug("Executing Query:" + tologQuery);
        }
        QueryResultIF queryResult = null;
        try {
            QueryProcessorIF queryProcessor = QueryUtils.getQueryProcessor(topicMapIF);
            queryResult = queryProcessor.execute(tologQuery.asString(), tologQuery.getArguments(), declarationContextIF);

            boolean queryAnswer = false;
            List resultList = new ArrayList();
            while (queryResult.next()) {
                queryAnswer = true;
                if (StringUtils.isBlank(fieldName)) {
                    return queryAnswer;
                }
                resultList.add(queryResult.getValue(fieldName));
            }
            return queryAnswer ? resultList : false;
        }
        catch (Exception exception) {
            log.error("Query returned exception: \n" + tologQuery, exception);
            // todo consider more specific exception:
            return null;
        }
        finally {
            if (queryResult != null) {
                queryResult.close();
            }
        }
    }

    /**
     * New improved version with SpringHibernateTemplate look'n'feel
     */
    public static Object executeTologQuery(ITologQuery tologQuery, RowMapper rowmapper, DeclarationContextIF declarationContextIF, TopicMapIF topicMapIF) {
        String tologQueryAsString = tologQuery.asString();
        Map arguments = tologQuery.getArguments();

        QueryResultIF queryResult = null;
        try {
            QueryProcessorIF queryProcessor = QueryUtils.getQueryProcessor(topicMapIF);
            queryResult = queryProcessor.execute(tologQueryAsString, arguments, declarationContextIF);

            while (queryResult.next()) {
            	rowmapper.addRow(queryResult);
            }
            return rowmapper.getResult();
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
        finally {
            if (queryResult != null) {
                queryResult.close();
            }
        }
    }


    /**
     * @param original
     * @return
     */
    public static <T> List<T> removeDuplicates(List<T> original) {
        List<T> list = new ArrayList<T>(original.size());
        Set<T> sett = new HashSet<T>(original.size());

        for (T element : original) {
            if (!sett.contains(element)) {
                sett.add(element);
                list.add(element);
            }
        }
        return list;
    }

    /**
     * Finds a topic identified with a given psi
     * @param psi URI
     * @return the topic
     * @param topicMapIF the topicMap
     */
    public static TopicIF getTopicIFByPSI(String psi, TopicMapIF topicMapIF) {
        return topicMapIF.getTopicByIndicator(URIUtils.getURILocator(psi));
    }
}
