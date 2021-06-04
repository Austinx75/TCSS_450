package edu.uw.harmony.UI.Avatar;
import android.widget.ImageView;

import java.io.Serializable;

public class Avatar implements Serializable{
    private final int mImageSource;
    public static class Builder{
        private final int mImageSource;

        public Builder(int imageSource){
            this.mImageSource = imageSource;
        }
        public Avatar build(){return new Avatar(this);}
    }
    private Avatar(final Builder builder){
        this.mImageSource = builder.mImageSource;
    }

    public int getImageSource(){
        return mImageSource;
    }

}
