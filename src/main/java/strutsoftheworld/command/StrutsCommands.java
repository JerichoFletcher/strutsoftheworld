package strutsoftheworld.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraftforge.network.PacketDistributor;
import strutsoftheworld.capability.SOTWCapabilities;
import strutsoftheworld.capability.StrutsWeatherCapability;
import strutsoftheworld.dimension.SOTWDimensions;
import strutsoftheworld.network.SOTWNetworkHandler;

public class StrutsCommands {
    public static final String WEATHER_GET_RAIN_STRENGTH_KEY = "strutsoftheworld.commands.struts.weather.rain_strength.get.success";
    public static final String WEATHER_SET_RAIN_STRENGTH_KEY = "strutsoftheworld.commands.struts.weather.rain_strength.set.success";
    public static final String WEATHER_GET_RAIN_STRENGTH_DRIFT_KEY = "strutsoftheworld.commands.struts.weather.rain_strength_drift.get.success";
    public static final String WEATHER_SET_RAIN_STRENGTH_DRIFT_KEY = "strutsoftheworld.commands.struts.weather.rain_strength_drift.set.success";
    public static final String DIMENSION_NOT_FOUND_KEY = "strutsoftheworld.commands.struts.dim_not_found";

    private static final SimpleCommandExceptionType DIMENSION_NOT_FOUND = new SimpleCommandExceptionType(Component.translatable(DIMENSION_NOT_FOUND_KEY));

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext buildContext) {
        dispatcher.register(Commands.literal("struts")
            .requires(s -> s.hasPermission(2))
            .then(
                Commands.literal("weather")
                    .then(
                        Commands.literal("rainStrength")
                            .executes(context -> getWeatherRainStrength(context.getSource()))
                            .then(
                                Commands.argument("strength", FloatArgumentType.floatArg(0f, 1f))
                                    .executes(context -> setWeatherRainStrength(
                                        context.getSource(),
                                        FloatArgumentType.getFloat(context, "strength")
                                    ))
                            )
                    )
                    .then(
                        Commands.literal("rainStrengthDrift")
                            .executes(context -> getWeatherRainStrengthDrift(context.getSource()))
                            .then(
                                Commands.argument("strengthDrift", FloatArgumentType.floatArg(
                                    -StrutsWeatherCapability.MAX_RAIN_STRENGTH_DRIFT,
                                    StrutsWeatherCapability.MAX_RAIN_STRENGTH_DRIFT
                                ))
                                    .executes(context -> setWeatherRainStrengthDrift(
                                        context.getSource(),
                                        FloatArgumentType.getFloat(context, "strengthDrift")
                                    ))
                            )
                    )
            )
        );
    }

    private static int getWeatherRainStrength(CommandSourceStack source) throws CommandSyntaxException {
        var dimKey = ResourceKey.create(Registries.DIMENSION, SOTWDimensions.STRUTS_OF_THE_WORLD.location());
        var level = source.getServer().getLevel(dimKey);

        if (level != null) {
            level.getCapability(SOTWCapabilities.STRUTS_WEATHER).ifPresent(weather -> {
                source.sendSuccess(() -> Component.translatable(WEATHER_GET_RAIN_STRENGTH_KEY, weather.getRainStrength()), true);
            });
        } else throw DIMENSION_NOT_FOUND.create();

        return Command.SINGLE_SUCCESS;
    }

    private static int setWeatherRainStrength(CommandSourceStack source, float targetStrength) throws CommandSyntaxException {
        var dimKey = ResourceKey.create(Registries.DIMENSION, SOTWDimensions.STRUTS_OF_THE_WORLD.location());
        var level = source.getServer().getLevel(dimKey);

        if (level != null) {
            level.getCapability(SOTWCapabilities.STRUTS_WEATHER).ifPresent(weather -> {
                weather.setRainStrength(targetStrength);
                source.sendSuccess(() -> Component.translatable(WEATHER_SET_RAIN_STRENGTH_KEY, weather.getRainStrength()), true);

                SOTWNetworkHandler.instance().send(
                    StrutsWeatherCapability.Packet.of(weather),
                    PacketDistributor.DIMENSION.with(level.dimension())
                );
            });
        } else throw DIMENSION_NOT_FOUND.create();

        return Command.SINGLE_SUCCESS;
    }

    private static int getWeatherRainStrengthDrift(CommandSourceStack source) throws CommandSyntaxException {
        var dimKey = ResourceKey.create(Registries.DIMENSION, SOTWDimensions.STRUTS_OF_THE_WORLD.location());
        var level = source.getServer().getLevel(dimKey);

        if (level != null) {
            level.getCapability(SOTWCapabilities.STRUTS_WEATHER).ifPresent(weather -> {
                source.sendSuccess(() -> Component.translatable(WEATHER_GET_RAIN_STRENGTH_DRIFT_KEY, weather.getRainStrengthDrift()), true);
            });
        } else throw DIMENSION_NOT_FOUND.create();

        return Command.SINGLE_SUCCESS;
    }

    private static int setWeatherRainStrengthDrift(CommandSourceStack source, float targetStrengthDrift) throws CommandSyntaxException {
        var dimKey = ResourceKey.create(Registries.DIMENSION, SOTWDimensions.STRUTS_OF_THE_WORLD.location());
        var level = source.getServer().getLevel(dimKey);

        if (level != null) {
            level.getCapability(SOTWCapabilities.STRUTS_WEATHER).ifPresent(weather -> {
                weather.setRainStrengthDrift(targetStrengthDrift);
                source.sendSuccess(() -> Component.translatable(WEATHER_SET_RAIN_STRENGTH_DRIFT_KEY, weather.getRainStrengthDrift()), true);

                SOTWNetworkHandler.instance().send(
                    StrutsWeatherCapability.Packet.of(weather),
                    PacketDistributor.DIMENSION.with(level.dimension())
                );
            });
        } else throw DIMENSION_NOT_FOUND.create();

        return Command.SINGLE_SUCCESS;
    }
}
