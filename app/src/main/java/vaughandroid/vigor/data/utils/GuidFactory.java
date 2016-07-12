package vaughandroid.vigor.data.utils;

import javax.inject.Inject;

/**
 * TODO: javadoc
 *
 * @author Chris
 */
public class GuidFactory {

    @Inject
    public GuidFactory() {}

    public String newGuid() {
        return "" + Math.random(); // TODO: 15/06/2016 implement proper GUID creation
    }
}
