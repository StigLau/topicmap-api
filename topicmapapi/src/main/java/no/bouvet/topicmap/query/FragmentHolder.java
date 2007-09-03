package no.bouvet.topicmap.query;

/**
 * For conserving state for tologFragments with their arguments
 * @author Stig Lau, Bouvet ASA
 *         Date: Sep 1, 2007
 *         Time: 6:36:49 PM
 */
public class FragmentHolder {
    private final ITologFragment tologFragment;
    private final ITopicParameter[] arguments;


    public FragmentHolder(ITologFragment tologFragment, ITopicParameter... arguments) {
        this.arguments = arguments;
        this.tologFragment = tologFragment;
    }

    public String[] getFragmentAsString() {
        return tologFragment.tologQuery(arguments);
    }

    public ITopicParameter[] getArguments() {
        return arguments;
    }

    public String fragmentName() {
        return tologFragment.name();
    }
}
