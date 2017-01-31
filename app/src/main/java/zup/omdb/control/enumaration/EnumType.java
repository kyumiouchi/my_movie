package zup.omdb.control.enumaration;

import zup.omdb.R;
import zup.omdb.util.ResourceUtils;

/**
 * Created by Yumi.
 * <p>
 * <p>
 */
public enum EnumType {
    MOVIE("movie", R.string.type_movie),
    SERIES("series", R.string.type_series),
    EPISODE("episode", R.string.type_episode);

    private final String name;
    private final int idRes;

    EnumType(String name, int idRes) {
        this.name = name;
        this.idRes = idRes;
    }

    public String getName() {
        return name;
    }

    public int getIdRes() {
        return idRes;
    }

    public String getIdResStr() {
        return ResourceUtils.getStringByRes(idRes);
    }
}
