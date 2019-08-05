package com.example.fortouristsbytourists;

/**
 * a class being used for attractions, museums and parks
 */
public class Attractions
{

    private String title, description, imageURL, bookingURL;
    private String location;
    private boolean canBook; //the field has to be filled by the System Administrator
                             //the field indicates if there is a bookingURL

    public Attractions(){}

    public Attractions(String bookingURL, String description, String imageURL, String title, String location, boolean canBook) {
        this.title = title;
        this.description = description;
        this.imageURL = imageURL;
        this.bookingURL = bookingURL;
        this.canBook = canBook;
        this.location = location;
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getImageURL() { return imageURL; }
    public void setImageURL(String imageURL) { this.imageURL = imageURL; }

    public String getBookingURL() { return bookingURL; }
    public void setBookingURL(String bookingURL) { this.bookingURL = bookingURL; }

    public boolean isCanBook() { return canBook; }
    public void setCanBook(boolean canBook) { this.canBook = canBook;  }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

}