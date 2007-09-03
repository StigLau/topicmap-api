package no.bouvet.topicmap.query;

/**
 * For conserving state for tologFragments with their arguments
 * @author Stig Lau, Bouvet ASA
 */
class FragmentHolder {
    private final ITologFragment tologFragment;
    private final ITopicParameter[] arguments;


    FragmentHolder(ITologFragment tologFragment, ITopicParameter... arguments) {
        this.arguments = arguments;
        this.tologFragment = tologFragment;
    }

    String[] getFragmentAsString() {
        return tologFragment.tologQuery(arguments);
    }

    ITopicParameter[] getArguments() {
        return arguments;
    }

    String fragmentName() {
        return tologFragment.name();
    }
}
