package edu.uw.harmony.UI.Avatar;
import android.widget.ImageView;

import java.io.Serializable;

/**
 * This class represents a avatar object.
 *
 * @author  Jack Lin
 * @version 1.0
 */

//This class builds an Avatar object with the image id.
public class Avatar implements Serializable{
    private final int mImageSource;


    /**
     * Nested class builder
     */
    public static class Builder{
        private final int mImageSource;

        /**
         * Constructs a new Builder.
         *
         * @param imageSource integer(location) of the image
         */
        public Builder(int imageSource){
            this.mImageSource = imageSource;
        }
        public Avatar build(){return new Avatar(this);}
    }
    private Avatar(final Builder builder){
        this.mImageSource = builder.mImageSource;
    }

    /**
     * @return The image location.
     */
    public int getImageSource(){
        return mImageSource;
    }

}
