package vaughandroid.vigor.data.utils;

import java.util.UUID;

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
        return UUID.randomUUID().toString();
    }
}
