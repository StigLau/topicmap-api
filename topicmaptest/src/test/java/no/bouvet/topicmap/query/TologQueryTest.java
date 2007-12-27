package no.bouvet.topicmap.query;

import static junit.framework.Assert.assertEquals;
import net.ontopia.topicmaps.core.TopicIF;
import net.ontopia.topicmaps.utils.TopicStringifiers;
import no.bouvet.topicmap.AbstractTopicMapTestFixture;
import no.bouvet.topicmap.dao.TopicDAO;
import no.bouvet.topicmap.opera.OperaAssociationType;
import no.bouvet.topicmap.opera.OperaTopicType;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * @author Stig Lau, Bouvet ASA
 */
public class TologQueryTest extends AbstractTopicMapTestFixture {

    static TopicDAO shakespeare;
    static TopicDAO puccini;

    @BeforeClass
    public static void setUp() {
        shakespeare = topicmap.getTopicDAOByPSI("http://en.wikipedia.org/wiki/Shakespeare");
        puccini = topicmap.getTopicDAOByPSI("http://en.wikipedia.org/wiki/Puccini");

    }


    @Test
    public void testOperasComposedByPuccini() {
        ITopicParameter composer = new StandardTopicParameter(puccini);
        ITopicParameter opera = new StandardTopicParameter(OperaTopicType.OPERA);
        TologQuery tologQuery = new TologQuery(OperaAssociationType.COMPOSED_BY, opera, composer);
        tologQuery.orderBy(SortOrder.ASC, opera);
        tologQuery.setCountParameter(opera);

        assertEquals("select count ($OPERA) from\n" +
                "composed-by($OPERA : work, %COMPOSER% : composer)\n" +
                "order by $OPERA ASC?\n" +
                "Arguments:\n" +
                "OPERA\n" +
                "COMPOSER : Puccini, Giacomo", tologQuery.toString());
        Integer count = topicmap.queryForSingleValue(tologQuery, opera);
        assertEquals(12, count.intValue());
    }

    @Test
    public void testCounting() {
        ITopicParameter composer = new StandardTopicParameter(OperaTopicType.COMPOSER);
        ITopicParameter opera = new StandardTopicParameter(OperaTopicType.OPERA);
        TologQuery tologQuery = new TologQuery(OperaAssociationType.COMPOSED_BY, opera, composer);
        tologQuery.setCountParameter(opera);
        Integer count = topicmap.queryForSingleValue(tologQuery, opera);
        assertEquals("Resultset was not the size expected", 149, count.intValue());
        assertEquals(
                "select count ($OPERA) from\n" +
                "composed-by($OPERA : work, $COMPOSER : composer)?\n" +
                "Arguments:\n" +
                "OPERA\n" +
                "COMPOSER", tologQuery.toString());
    }

    @Test
    public void testWithInterface() {
        ITopicParameter operaType = new StandardTopicParameter(OperaTopicType.OPERA);
        ITopicParameter composer = new StandardTopicParameter(OperaTopicType.COMPOSER);
        ITologQuery tologQuery = new TologQuery(OperaAssociationType.COMPOSED_BY, operaType, composer);
        assertEquals(
                "select $OPERA, $COMPOSER from\n" +
                "composed-by($OPERA : work, $COMPOSER : composer)?\n" +
                "Arguments:\n" +
                "OPERA\n" +
                "COMPOSER", tologQuery.toString());
        List result = topicmap.queryForList(tologQuery, operaType);
        assertEquals("Expected a larger resultset", 149, result.size());
    }

    @Test
    public void testComposersInspiredByShakespear() {
        ITopicParameter composer = new StandardTopicParameter(OperaTopicType.COMPOSER);
        ITopicParameter opera = new StandardTopicParameter(OperaTopicType.OPERA);
        ITopicParameter source = new StandardTopicParameter(OperaTopicType.SOURCE);
        ITopicParameter writer = new StandardTopicParameter(shakespeare);

        TologQuery tologQuery = new TologQuery();
        tologQuery.and(OperaAssociationType.COMPOSED_BY, opera, composer);
        tologQuery.and(OperaAssociationType.BASED_ON, opera, source);
        tologQuery.and(OperaAssociationType.WRITTEN_BY, source, writer);

        assertEquals(
                "select $OPERA, $COMPOSER, $SOURCE from\n" +
                "composed-by($OPERA : work, $COMPOSER : composer),\n" +
                "based-on($OPERA : result, $SOURCE : source),\n" +
                "written-by($SOURCE : work, %WRITER% : writer)?\n" +
                "Arguments:\n" +
                "OPERA\n" +
                "COMPOSER\n" +
                "SOURCE\n" +
                "WRITER : Shakespeare, William", tologQuery.toString());
        
        List<TopicIF> result = topicmap.queryForList(tologQuery, composer);
        assertEquals("Wrong number of inspirees", 1, result.size());

        for (TopicIF topicIF : result) {
            System.out.println(TopicStringifiers.toString(topicIF));
        }
    }

    @Test
    public void testPersonsBornAndDiedInSamePlace() {
        ITopicParameter person = new StandardTopicParameter(OperaTopicType.PERSON);
        ITopicParameter place = new StandardTopicParameter(OperaTopicType.PLACE);

        TologQuery tologQuery = new TologQuery();
        tologQuery.and(OperaAssociationType.BORN_IN, person, place);
        tologQuery.and(OperaAssociationType.DIED_IN, person, place);
        tologQuery.orderBy(SortOrder.ASC, place, person);

        assertEquals(
            "select $PERSON, $PLACE from\n" +
            "born-in($PERSON : person, $PLACE : place),\n" +
            "died-in($PERSON : person, $PLACE : place)\n" +
            "order by $PLACE, $PERSON ASC?\n" +
            "Arguments:\n" +
            "PERSON\n" +
            "PLACE", tologQuery.toString());

        Map result = topicmap.queryForMap(tologQuery, place, person);
        assertEquals("Wrong number of results", 16, result.size());
    }

    @Test
    public void testSuicides() {
        ITopicParameter person = new StandardTopicParameter(OperaTopicType.PERSON);

        TologQuery tologQuery = new TologQuery();
        tologQuery.and(OperaAssociationType.KILLED_BY, person, person);
        tologQuery.orderBy(SortOrder.ASC, person);

        assertEquals(
            "select $PERSON from\n" + 
            "killed-by($PERSON : victim, $PERSON : perpetrator)\n" +
            "order by $PERSON ASC?\n" +
            "Arguments:\n" +
            "PERSON", tologQuery.toString());

        List<TopicIF> result = topicmap.queryForList(tologQuery, person);
        assertEquals("Wrong number of suicides", 16, result.size());
    }
}

