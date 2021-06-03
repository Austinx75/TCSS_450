package edu.uw.harmony.UI.Request;

import java.io.Serializable;

/**
 * Class to encapsulate a Phish.net Blog Post. Building an Object requires a publish date and title.
 *
 * Optional fields include URL, teaser, and Author.
 *
 *
 * @author Charles Bryan
 * @version 14 September 2018
 */
public class RequestPost implements Serializable{

    private String name;
    private String username;
    private final String id;
    private String status;
    private int avatar;

    /**
     * Nested class builder
     */
    public static class Builder{
        private String name;
        private String username = "";
        private final String id;
        private String status = "";
        private int avatar;

        /**
         * Constructs a new Builder.
         *
         * @param name of the user
         * @param id of the user
         */
        public Builder(String name, String id){
            this.name = name;
            this.id = id;
        }


        /**
         * Method to add username to Builder obj
         * @param val string of username
         * @return builder obj
         */
        public Builder addUsername(String val){
            username = val;
            return this;
        }

        /**
         * Method to add status to Builder obj
         * @param val string of status
         * @return builder obj
         */
        public Builder addStatus(String val){
            status = val;
            return this;
        }

        /**
         * Method to add avatar to Builder obj
         * @param val int of avatar
         * @return builder obj
         */
        public Builder addAvatar(int val){
            avatar = val;
            return this;
        }

        /**
         * Method to return RequestPost obj
         * @return RequestPost
         */
        public RequestPost build() {
            return new RequestPost(this);
        }
    }

    /**
     * Construct a RequestPost using Builder as parameter
     * @param builder Builder
     */
    private RequestPost(final Builder builder){
        this.name = builder.name;
        this.username = builder.username;
        this.id = builder.id;
        this.status = builder.status;
        this.avatar = builder.avatar;
    }

    /**
     * @return The name this class represents.
     */
    public String getName(){ return name; }


    /**
     * @return The username this class represents.
     */
    public String getUsername() {return username;}

    /**
     * @return The id this class represents.
     */
    public String getId(){ return id; }

    /**
     * @return The status this class represents.
     */
    public String getStatus(){ return status;}

    /**
     * @return The avatar this class represents.
     */
    public int getAvatar(){return avatar;}
}















//public class RequestPost implements Serializable {
//
//    private final String mPubDate;
//    private final String mTitle;
//    private final String mUrl;
//    private final String mTeaser;
//    private final String mAuthor;
//
//    /**
//     * Helper class for building Credentials.
//     *
//     * @author Charles Bryan
//     */
//    public static class Builder {
//        private final String mPubDate;
//        private final String mTitle;
//        private  String mUrl = "";
//        private  String mTeaser = "";
//        private  String mAuthor = "";
//
//
//        /**
//         * Constructs a new Builder.
//         *
//         * @param pubDate the published date of the blog post
//         * @param title the title of the blog post
//         */
//        public Builder(String pubDate, String title) {
//            this.mPubDate = pubDate;
//            this.mTitle = title;
//        }
//
//        /**
//         * Add an optional url for the full blog post.
//         * @param val an optional url for the full blog post
//         * @return the Builder of this BlogPost
//         */
//        public Builder addUrl(final String val) {
//            mUrl = val;
//            return this;
//        }
//
//        /**
//         * Add an optional teaser for the full blog post.
//         * @param val an optional url teaser for the full blog post.
//         * @return the Builder of this BlogPost
//         */
//        public Builder addTeaser(final String val) {
//            mTeaser = val;
//            return this;
//        }
//
//        /**
//         * Add an optional author of the blog post.
//         * @param val an optional author of the blog post.
//         * @return the Builder of this BlogPost
//         */
//        public Builder addAuthor(final String val) {
//            mAuthor = val;
//            return this;
//        }
//
//        public RequestPost build() {
//            return new RequestPost(this);
//        }
//
//    }
//
//    private RequestPost(final Builder builder) {
//        this.mPubDate = builder.mPubDate;
//        this.mTitle = builder.mTitle;
//        this.mUrl = builder.mUrl;
//        this.mTeaser = builder.mTeaser;
//        this.mAuthor = builder.mAuthor;
//    }
//
//    public String getPubDate() {
//        return mPubDate;
//    }
//
//    public String getTitle() {
//        return mTitle;
//    }
//
//    public String getUrl() {
//        return mUrl;
//    }
//
//    public String getTeaser() {
//        return mTeaser;
//    }
//
//    public String getAuthor() {
//        return mAuthor;
//    }
//
//
//}
