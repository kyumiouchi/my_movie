package zup.omdb.view.fragment;

import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import zup.omdb.R;
import zup.omdb.control.activity.MainActivity;
import zup.omdb.model.bo.MovieBO;
import zup.omdb.model.request.Movie;
import zup.omdb.util.ResourceUtils;
import zup.omdb.component.ExtraAssertion;
import zup.omdb.component.RecyclerViewMatcher;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Yumi.
 * <p>
 * Verifica a tela principal e os fluxos principal
 */
@RunWith(AndroidJUnit4.class)
public class MainFragmentTest {

    @Rule
    public ActivityTestRule<MainActivity> mMainActivity = new ActivityTestRule<MainActivity>(MainActivity.class);

    /**
     * Verifica texto na ActionBar e o id
     **/
    @Test
    public void viewActionBar() throws Exception {
        //Show the actionBar
        onView(ViewMatchers.withId(R.id.toolbar)).check(matches(isDisplayed()));

        //get the text which the fragment shows
        onView(withText(ResourceUtils.getStringByRes(R.string.app_name))).check(matches(isDisplayed()));
    }

    public static RecyclerViewMatcher withRecyclerView(final int recyclerViewId) {
        return new RecyclerViewMatcher(recyclerViewId);
    }

    /**
     * Verifica se não contem itens e esta mostrando o texto correto quando está vazia
     */
    @Test
    public void testMainEmptyList() {

        List<Movie> itemList = null;
        try {
            itemList = MovieBO.getInstance().getAllPersistedMovies();
        } catch (SQLException e) {
        }

        if (itemList == null || itemList.size() == 0) {
            onView(withId(R.id.rl_no_item)).check(ExtraAssertion.isVisible());
            onView(withId(R.id.nested_scroll_main)).check(ExtraAssertion.isGone());
        }

    }


    /**
     * Verifica se recycleView está funcionando, acesso ao link e navegar para a tela seguinte e voltar
     */
    @Test
    public void testMainRecyclerView() {

        List<Movie> itemList;
        try {
            itemList = MovieBO.getInstance().getAllPersistedMovies();
        } catch (SQLException e) {
            itemList = new ArrayList<>();
        }

        final int position = 2;
        if (itemList != null && itemList.size() > position) {
            onView(withId(R.id.menu_recycler_view)).perform(scrollToPosition(position));
            onView(withRecyclerView(R.id.menu_recycler_view).atPosition(position)).check(matches(hasDescendant(withText(itemList.get(position).getName()))));
            onView(withRecyclerView(R.id.menu_recycler_view).atPosition(position)).perform(click());

            ViewInteraction frag = onView(withId(R.id.layout_detail));
            frag.check(matches(isDisplayed()));

            onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click());

            onView(withId(R.id.menu_recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(position, click()));
            frag.check(matches(isDisplayed()));
        }

    }

    /**
     * Verifica se botão flutuante está funcionando, navegar para a tela seguinte e voltar
     */
    @Test
    public void testShowSearchFragment() {

        onView(withId(R.id.float_button)).perform(click());

        ViewInteraction frag = onView(withId(R.id.layout_search));
        frag.check(matches(isDisplayed()));

        onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click());

    }
}
