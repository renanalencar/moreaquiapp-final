package br.com.renanalencar.moreaqui;

public class LocationEstate  extends Estate {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /** The size of the estate: small, medium, large. */
    public final Double LATITUDE;

    /** True if the estate is under construction. */
    public final Double LONGITUDE;

//    public LocationEstate(String type, String size, int phone, String inConstruction, double latitude, double longitude) {
//        super(type, size, phone, inConstruction);
//        this.LATITUDE = latitude;
//        this.LONGITUDE = longitude;
//    }

    public LocationEstate(String type, String size, String phone, String inConstruction, double latitude, double longitude) {
        super(type, size, phone, inConstruction);
        this.LATITUDE = latitude;
        this.LONGITUDE = longitude;
    }

}
