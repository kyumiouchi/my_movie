package zup.omdb.control.listener;

/**
 * Created by Yumi.
 * <p>
 * <p>
 * Interface responsável pelo fluxo do fragmento com a activity
 */
public interface IMovieActivityListener {
    void popBackStack();
    void registerBackPress(final IBackPressListener iBackPressListener);

    void showLoading(final boolean visible);

    void showMainScreen();
    void showSearchMovieScreen();

    void showDetailMovieScreen();

    void showAboutScreen();
}
