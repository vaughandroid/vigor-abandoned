package vaughandroid.vigor.data.utils;

import java.util.Random;

import javax.inject.Inject;

/**
 * Creates GUIDs used for various IDs
 *
 * @author Chris
 */
public class GuidFactory {

    private final Random random = new Random(12345L);

    @Inject
    public GuidFactory() {}

    public String newGuid() {
        // TODO: 15/06/2016 implement proper GUID creation
        return "" + random.nextInt();
    }
}
