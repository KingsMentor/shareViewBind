package xyz.belvi.sharedview;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zone2 on 9/28/16.
 */

public class Sample implements Parcelable {

    String Details;
    int id;
    float amount;
    long timeStamp;

    public Sample() {
    }


    protected Sample(Parcel in) {
        Details = in.readString();
        id = in.readInt();
        amount = in.readFloat();
        timeStamp = in.readLong();
    }

    public static final Creator<Sample> CREATOR = new Creator<Sample>() {
        @Override
        public Sample createFromParcel(Parcel in) {
            return new Sample(in);
        }

        @Override
        public Sample[] newArray(int size) {
            return new Sample[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(Details);
        parcel.writeInt(id);
        parcel.writeFloat(amount);
        parcel.writeLong(timeStamp);
    }
}
