package strutsoftheworld.util;

import net.minecraft.util.ColorRGBA;

public record Color(float r, float g, float b, float a) {
    public Color() {
        this(0f, 0f, 0f);
    }

    public Color(float r, float g, float b) {
        this(r, g, b, 1f);
    }

    public Color {
        if (r < 0f || 1f < r) throw new IllegalArgumentException("Red component out of bounds");
        if (g < 0f || 1f < g) throw new IllegalArgumentException("Green component out of bounds");
        if (b < 0f || 1f < b) throw new IllegalArgumentException("Blue component out of bounds");
        if (a < 0f || 1f < a) throw new IllegalArgumentException("Alpha component out of bounds");
    }

    public static Color fromRGB(int colorBits) {
        int r = (colorBits >> 16) & 0xFF;
        int g = (colorBits >> 8) & 0xFF;
        int b = colorBits & 0xFF;

        return fromRGB(r, g, b);
    }

    public static Color fromRGB(int r, int g, int b) {
        return new Color((float) r / 255, (float) g / 255, (float) b / 255);
    }

    public static Color fromRGBA(int colorBits) {
        int a = (colorBits >> 24) & 0xFF;
        int r = (colorBits >> 16) & 0xFF;
        int g = (colorBits >> 8) & 0xFF;
        int b = colorBits & 0xFF;

        return fromRGBA(r, g, b, a);
    }

    public static Color fromRGBA(int r, int g, int b, int a) {
        return new Color((float) r / 255, (float) g / 255, (float) b / 255, (float) a / 255);
    }

    public ColorRGBA toRGBA() {
        return new ColorRGBA(toRGBAInt());
    }

    public int toRGBInt() {
        int r = (int) (this.r * 255);
        int g = (int) (this.g * 255);
        int b = (int) (this.b * 255);

        return (r << 16) | (g << 8) | b;
    }

    public int toRGBAInt() {
        int a = (int) (this.a * 255);

        return (a << 24) | toRGBInt();
    }
}
