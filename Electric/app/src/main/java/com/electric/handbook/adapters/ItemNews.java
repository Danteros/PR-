package com.electric.handbook.adapters;

/**
 * Created by Anatoliy on 31.10.2015.
 */
public class ItemNews {
    private String title;
    private String description;
    private String photo;
    private String dateString;

    public ItemNews(String title, String description, String photo, String dateString) {
        this.title = title;
        this.description = description;
        this.photo = photo;
        this.dateString = dateString;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPhoto() {
        return photo;
    }

    public String getDateString() {
        return dateString;
    }
}
