package zup.omdb.control.listener;

/**
 * Created by Yumi.
 * <p>
 * <p>
 * Interface responsável pelo fluxo do backPress
 */
public interface IBackPressListener {
    void onBackPress();
    boolean isFirstBack();
}
