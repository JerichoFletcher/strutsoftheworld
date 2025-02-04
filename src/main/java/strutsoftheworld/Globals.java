package strutsoftheworld;

import strutsoftheworld.util.Color;

public final class Globals {
    private Globals() {
    }

    public static final Color STRUTS_FLOOR_SKY_COLOR = Color.fromRGB(0x19100B);
    public static final Color STRUTS_FLOOR_FOG_COLOR = Color.fromRGB(0x2A1B09);
    public static final Color STRUTS_FLOOR_WATER_COLOR = Color.fromRGB(0xA08231);
    public static final Color STRUTS_FLOOR_WATER_FOG_COLOR = Color.fromRGB(0x4E3A18);

    public static final int STRUTS_RAIN_PARTICLE_DIST_FROM_GROUND = 6;
    public static final float STRUTS_ADD_RAIN_PARTICLE_P_ON_MAX_STRENGTH = 0.26f;

    public static final int ROT_WEED_GROWTH_MAX_SKY_LIGHT = 4;
}
