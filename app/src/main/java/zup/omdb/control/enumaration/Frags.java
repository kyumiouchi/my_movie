package zup.omdb.control.enumaration;

import zup.omdb.view.fragment.AboutFragment;
import zup.omdb.view.fragment.DetailMovieFragment;
import zup.omdb.view.fragment.FragmentAbstract;
import zup.omdb.view.fragment.MainFragment;
import zup.omdb.view.fragment.SearchMovieFragment;

/**
 * Created by Yumi.
 * <p>
 * <p>
 * Todos os fragmentos do fluxo principal
 */
public enum Frags {
    MAIN("MAIN", MainFragment.class),
    SEARCH("SEARCH", SearchMovieFragment.class),
    DETAIL("DETAIL", DetailMovieFragment.class),
    ABOUT("ABOUT", AboutFragment.class);

    private String name;
    private Class<? extends FragmentAbstract> classFrag;

    private Frags(final String name, Class<? extends FragmentAbstract> classFrag) {
        this.name = name;
        this.classFrag = classFrag;
    }

    public String getName() {
        return name;
    }
    public Class<? extends FragmentAbstract> getClassFrag() {
        return classFrag;
    }
}
