package vaughandroid.vigor.app.rx;

import dagger.Module;
import dagger.Provides;
import vaughandroid.vigor.app.di.ApplicationScope;
import vaughandroid.vigor.data.utils.GuidFactory;

/**
 * TODO: javadoc
 *
 * @author chris.vaughan@laterooms.com
 */
@Module
public class UtilsModule {

    @Provides
    @ApplicationScope
    public GuidFactory provideGuidFactory() {
        return new GuidFactory();
    }
}
