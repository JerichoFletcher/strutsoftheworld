package strutsoftheworld.capability;

import net.minecraftforge.common.capabilities.AutoRegisterCapability;

@AutoRegisterCapability
public interface IStrutsWeatherCapability {
    float getMaxRainStrengthDrift();
    float getMaxRainStrengthDriftChange();
    float getRainStrengthDriftChangeProbability();

    float getRainStrength();
    float getRainStrengthDrift();
    void setRainStrength(float value);
    void setRainStrengthDrift(float value);

    void update();
}
