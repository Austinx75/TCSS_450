package edu.uw.harmony.UI.Avatar;
import java.io.Serializable;

public class Avatar implements Serializable{
    private int mImageSource;
    public static class Builder{
        private int mImageSource;
        public Builder(int imageSource){
            this.mImageSource = imageSource;
        }
        public Avatar build(){return new Avatar(this);}
    }
    public Avatar(final Builder builder){
        this.mImageSource = builder.mImageSource;
    }

    public int getImageSource(){
        return mImageSource;
    }

    public void setImageSource(int imageSource){this.mImageSource = imageSource;}
}
