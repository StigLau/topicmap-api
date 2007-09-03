package no.bouvet.topicmap;

import net.ontopia.topicmaps.entry.TopicMaps;
import no.bouvet.topicmap.core.TopicMap;
import no.bouvet.topicmap.opera.OperaTopicMap;
import no.bouvet.topicmap.servlet.OperaThreadLocalStoreServletFilter;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 * @author Stig Lau, Bouvet ASA
 * This class creates a static ThreadLocalStoreServletFilter which is used by all tests which depend on topicmap.
 * The ThreadLocalStoreServletFilter class is responsible for handling the transaction aspect of communicating with the topicmap
 */
public abstract class AbstractTopicMapTestFixture {
    protected static OperaTopicMap topicmap;

    @BeforeClass
    public static void setUpEntireClass() {
        topicmap = new OperaTopicMap(TopicMaps.createStore("opera.ltm", true));
        OperaThreadLocalStoreServletFilter.setTopicMap(topicmap);

    }

    /**
     * nrk-grep-test.xtm is a static test topicmap used by all tests instead of using the production database.
     * The TopicMap is Injected into ThreadLocalStoreServletFilter, and thus usd by all DAO's.
     */
    @Before
    public void setUpPerTest() {

    }


    @AfterClass
    public static void tearDownEntireClass() {
        TopicMap topicMap = OperaThreadLocalStoreServletFilter.getTopicMap();
        topicMap.getInterface().getStore().close();
        OperaThreadLocalStoreServletFilter.setTopicMap(null);
    }

    /**
     *  Closes the connection to the topicmap and assures that it is null in ThreadLocalStoreServletFilter
     */
    @After
    public void tearDownPerTest() {

    }
}
