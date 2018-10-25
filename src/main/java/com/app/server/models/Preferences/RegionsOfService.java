package com.app.server.models.Preferences;

public enum RegionsOfService {

    //WEST_COAST
    BAY_AREA("BayArea", 1),
    GREATER_SEATTLE("Greater Seattle",2),
    SOUTHERN_CALIFONIA("LA and Southern California",3),
    NEVADA("Nevada and Arizona",4),
    OREGON("Oregon and Dakotas", 5),

    //CENTRAL and SOUTHERN
    TEXAS("Southern United States", 6),
    COLORADO("Central United States", 7),
    FLORIDA("Florida", 12),

    //EAST_COAST
    NEW_YORK("New York, New Jersey", 13),
    CAROLINA("Carolinas and Georgia", 14),
    CENTRAL_DISTRICT("DC and Virgina", 15),
    NORTH_EAST("Other New England",16);

    private final String regionOfService;
    private final Integer id;

    RegionsOfService(String regionOfService, Integer id) {
        this.regionOfService = regionOfService;
        this.id = id;
    }

    public String getRegionOfService() {
        return regionOfService;
    }
    public Integer getId() {
        return id;
    }

    public static RegionsOfService getRegionOfService(int value) {
        for(RegionsOfService e: RegionsOfService.values()) {
            if(e.id == value) {
                return e;
            }
        }
        return null;// not found
    }
}
