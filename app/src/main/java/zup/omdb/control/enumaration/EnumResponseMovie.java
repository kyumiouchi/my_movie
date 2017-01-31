package zup.omdb.control.enumaration;

/**
 * Created by Yumi.
 * <p>
 * <p>
 * Enum para os tipos de serviço de retorno
 */
public enum EnumResponseMovie {
    JSON("json"),
    XML("xml");

    private final String name;

    EnumResponseMovie(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
