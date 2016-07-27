package vaughandroid.vigor.data.utils;

import javax.inject.Inject;

/**
 * Creates GUIDs used for various IDs
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
