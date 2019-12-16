package org.woehlke.simulation.mandelbrot.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

public class Config implements ConfigProperties {

    private String title;
    private String subtitle;
    private String copyright;
    private int width;
    private int height;

    public Config() {
        String appPropertiesFile = (APP_PROPERTIES_FILENAME);
        Properties prop = new Properties();
        try (
            InputStream input = new FileInputStream(appPropertiesFile)) {
            prop.load(input);
            /*
            for( Object key : prop.keySet()){
                System.out.println(prop.get(key).toString());
            }
            */
            title = prop.getProperty(KEY_TITLE,TITLE);
            subtitle = prop.getProperty(KEY_SUBTITLE,SUBTITLE);
            copyright = prop.getProperty(KEY_COPYRIGHT,COPYRIGHT);
            String widthString = prop.getProperty(KEY_WIDTH,WIDTH);
            String heightString = prop.getProperty(KEY_HEIGHT,HEIGHT);
            width = Integer.parseInt(widthString);
            height = Integer.parseInt(heightString);
        } catch (IOException ex) {
            System.out.println(ex.getLocalizedMessage());
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Config)) return false;
        Config config = (Config) o;
        return getWidth() == config.getWidth() &&
            getHeight() == config.getHeight() &&
            Objects.equals(getTitle(), config.getTitle()) &&
            Objects.equals(getSubtitle(), config.getSubtitle()) &&
            Objects.equals(getCopyright(), config.getCopyright());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), getSubtitle(), getCopyright(), getWidth(), getHeight());
    }

    @Override
    public String toString() {
        return "Config{" +
            "title='" + title + '\'' +
            ", subtitle='" + subtitle + '\'' +
            ", copyright='" + copyright + '\'' +
            ", width=" + width +
            ", height=" + height +
            '}';
    }
}
