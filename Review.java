package com.example.fortouristsbytourists;

/**
 * describing the review object
 * it has been used as a relation between the attractions, the users and the user's review/rating
 */
public class Review {

    private String attrTitle, review, userName;
    private float rating;

    public Review() { }

    public Review(String attrTitle, String review, String userName, float rating) {
        this.attrTitle = attrTitle;  //attraction title
        this.userName = userName; //e-mail address
        this.review = review;
        this.rating = rating;
    }

    public String getAttrTitle() {   return attrTitle;  }
    public void setAttrTitle(String attrTitle) {   this.attrTitle = attrTitle;  }
    public String getReview() { return review; }
    public void setReview(String review) { this.review = review; }
    public String getUserName() { return userName; }
    public void setUserName(String userName) {this.userName = userName; }
    public float getRating() {return rating;}
    public void setRating(float rating) {this.rating = rating;}

}
