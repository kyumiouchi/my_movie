package zup.omdb.control.enumaration;

/**
 * Created by Yumi.
 * <p>
 * <p>
 * Enum para os tipos de plot
 */
public enum EnumPlotMovie {
    SHORT("short"),
    FULL("full");

    private final String name;

    EnumPlotMovie(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
