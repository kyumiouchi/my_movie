package zup.omdb.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

import zup.omdb.control.enumaration.EnumType;

/**
 * Created by Yumi.
 * <p>
 * <p>
 * Classe Filme
 */
@DatabaseTable(tableName = "movie")
public class Movie implements Serializable
{

    @DatabaseField(generatedId=true, columnName="id")
    private Integer id;

    @DatabaseField(columnName = "Name")
    private String name = "";

    @DatabaseField(columnName = "Title")
    @SerializedName("Title")
    @Expose
    private String title;

    @DatabaseField(columnName = "Year")
    @SerializedName("Year")
    @Expose
    private String year;

    @DatabaseField(columnName = "Rated")
    @SerializedName("Rated")
    @Expose
    private String rated;

    @DatabaseField(columnName = "Released")
    @SerializedName("Released")
    @Expose
    private String released;

    @DatabaseField(columnName = "Runtime")
    @SerializedName("Runtime")
    @Expose
    private String runtime;

    @DatabaseField(columnName = "Genre")
    @SerializedName("Genre")
    @Expose
    private String genre;

    @DatabaseField(columnName = "Director")
    @SerializedName("Director")
    @Expose
    private String director;

    @DatabaseField(columnName = "Writer")
    @SerializedName("Writer")
    @Expose
    private String writer;

    @DatabaseField(columnName = "Actors")
    @SerializedName("Actors")
    @Expose
    private String actors;

    @DatabaseField(columnName = "Plot")
    @SerializedName("Plot")
    @Expose
    private String plot;

    @DatabaseField(columnName = "Language")
    @SerializedName("Language")
    @Expose
    private String language;

    @DatabaseField(columnName = "Country")
    @SerializedName("Country")
    @Expose
    private String country;

    @DatabaseField(columnName = "Awards")
    @SerializedName("Awards")
    @Expose
    private String awards;

    @DatabaseField(columnName = "Poster")
    @SerializedName("Poster")
    @Expose
    private String poster;

    @DatabaseField(columnName = "Metascore")
    @SerializedName("Metascore")
    @Expose
    private String metascore;

    @DatabaseField(columnName = "imdbRating")
    @SerializedName("imdbRating")
    @Expose
    private String imdbRating;

    @DatabaseField(columnName = "imdbVotes")
    @SerializedName("imdbVotes")
    @Expose
    private String imdbVotes;

    @DatabaseField(columnName = "imdbID")
    @SerializedName("imdbID")
    @Expose
    private String imdbID;

    @DatabaseField(columnName = "Type")
    @SerializedName("Type")
    @Expose
    private String type;

    @SerializedName("Response")
    @Expose
    private String response;

    @DatabaseField(columnName = "Path")
    private String path;

    private final static long serialVersionUID = -8434834551572166932L;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getRated() {
        return rated;
    }

    public void setRated(String rated) {
        this.rated = rated;
    }

    public String getReleased() {
        return released;
    }

    public void setReleased(String released) {
        this.released = released;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAwards() {
        return awards;
    }

    public void setAwards(String awards) {
        this.awards = awards;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getMetascore() {
        return metascore;
    }

    public void setMetascore(String metascore) {
        this.metascore = metascore;
    }

    public String getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(String imdbRating) {
        this.imdbRating = imdbRating;
    }

    public String getImdbVotes() {
        return imdbVotes;
    }

    public void setImdbVotes(String imdbVotes) {
        this.imdbVotes = imdbVotes;
    }

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public String getType() {
        return type;
    }

    public String getTypeStr() {
        for (EnumType enumType : EnumType.values()) {
            if (enumType.getName().equals(type)) {
                return enumType.getIdResStr();
            }
        }
        return "";
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}

