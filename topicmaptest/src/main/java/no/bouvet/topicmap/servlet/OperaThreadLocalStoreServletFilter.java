// $Id: TopicMaps.java,v 1.1 2007/07/13 11:36:07 geir.gronmo Exp $

package no.bouvet.topicmap.servlet;

import net.ontopia.topicmaps.core.TopicMapStoreIF;
import net.ontopia.topicmaps.entry.TopicMaps;
import net.ontopia.topicmaps.nav2.utils.ThreadLocalStoreServletFilter;
import org.apache.log4j.Logger;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

import no.bouvet.topicmap.core.TopicMap;
import no.bouvet.topicmap.opera.OperaTopicMap;

/**
 * EXPERIMENTAL: Servlet filter that creates a new topic map store for
 * each request thread. The topic map store is stored in a thread
 * local. The filter can be configured using the following
 * configuration parameters:<p>
 * <p/>
 * <em>topicMapId</em> - The id of the topic map to create a store for. Mandatory.<br>
 * <em>repositoryId</em> - The id of the topic map repository. The default is to use the default repository.<br>
 * <em>readOnly</em> - A boolean specifying if the store should be readonly. The default is true.<br>
 *
 * @since %NEXT%
 */

public class OperaThreadLocalStoreServletFilter extends ThreadLocalStoreServletFilter {

    static Logger log = Logger.getLogger(ThreadLocalStoreServletFilter.class.getName());

    private static ThreadLocal<TopicMap> data = new ThreadLocal<TopicMap>();


    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String repositoryId = getRepositoryId(request);
        String topicMapId = getTopicMapId(request);
        boolean readOnly = getReadOnly(request);

        TopicMapStoreIF store;
        if (repositoryId == null)
            store = TopicMaps.createStore(topicMapId, readOnly);
        else
            store = TopicMaps.createStore(topicMapId, readOnly, repositoryId);

        try {
            log.debug("ThreadLocal setting up topicmap");
            data.set(new OperaTopicMap(store));
            chain.doFilter(request, response);
            if (!readOnly) store.commit();
        } catch (Exception e) {
            if (!readOnly) store.abort();
            log.error("Exception thrown from doFilter.", e);
        } finally {
            log.debug("ThreadLocal closing topicmap");
            data.set(null);
            store.close();
        }
    }

    // --- public access

    public static TopicMap getTopicMap() {
        return data.get();
    }

    public static void setTopicMap(TopicMap topicMap) {
        data.set(topicMap);
    }
}
