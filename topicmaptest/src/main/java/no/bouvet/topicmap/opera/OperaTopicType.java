package no.bouvet.topicmap.opera;

import no.bouvet.topicmap.dao.TopicType;

/**
 * @author Stig Lau, Bouvet AS
 */
public enum OperaTopicType implements TopicType {

    COMPOSER {
        public String tologBinding() {
            return "composer";
        }public String psi() {
            return "http://psi.ontopia.net/music/composer";
        }

    },

    OPERA {
        public String tologBinding() {
            return "work";
        }public String psi() {
            return "http://psi.ontopia.net/music/opera";
        }
    },

    CHARACTER {
        public String tologBinding() {
            return "character";
        }public String psi() {
            return "http://psi.ontopia.net/literature/character";
        }
    },
    WRITER {
        public String tologBinding() {
            return "writer";
        }public String psi() {
            return "http://psi.ontopia.net/literature/writer";
        }
    },
    SOURCE {
        public String tologBinding() {
            return "source";
        }public String psi() {
            return "http://psi.ontopia.net/opera/source";
        }
    },
    PERSON {
        public String tologBinding() {
            return "person";
        }public String psi() {
            return "http://psi.ontopia.net/person";
        }
    },
    PLACE {
        public String tologBinding() {
            return "place";
        }public String psi() {
            return "http://psi.ontopia.net/geography/place";
        }
    },
    /**
     * In case no topicmap was found
     */
    UNDEFINED {
        public String tologBinding() {
            return "undefined";
        }public String psi() {
            return "undefined";
        }
    }
}

