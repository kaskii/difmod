package fi.kask.difmod;

import com.mojang.logging.LogUtils;
import fi.kask.difmod.config.ConfigReader;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLConfig;
import net.minecraftforge.fml.loading.FMLPaths;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(DifMod.MODID)
public class DifMod
{
    public static final String MODID = "difmod";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final String MOD_NAME = "DifMod";
    public static final String MOD_VERSION = "0.1.0";



    private final ConfigReader configReader;

    public DifMod()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Create config object
        var basePath = FMLPaths.GAMEDIR.get().resolve(FMLConfig.defaultConfigPath());
        configReader = new ConfigReader(basePath.toString());
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        LOGGER.info("Initializing DifMod");

    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        LOGGER.info("Loading DifMod for the server");
    }

    @SubscribeEvent
    public void onDamage(LivingHurtEvent livingHurtEvent) {
        if(!(livingHurtEvent.getEntity() instanceof Player)) {
            return;
        }


        var source = livingHurtEvent.getSource();
        var originalDamageAmount = livingHurtEvent.getAmount();

        var config = configReader.getModConfig();
        var player = livingHurtEvent.getEntity();
        var playerName = player.getDisplayName().getString();

        LOGGER.debug("Player " + playerName + " took original damage: " + originalDamageAmount);

        if(!config.PlayerDamages.containsKey(playerName)) {
            LOGGER.trace("Player {} not found from the config file", playerName);
            return;
        }

        var playerModifiers = config.PlayerDamages.get(playerName);
        LOGGER.trace("Player {} found from config file. Values: {} {}", playerName, System.lineSeparator(), playerModifiers.logValues());

        // Can die
        if(!playerModifiers.CanDie && player.getHealth() - originalDamageAmount <= 0) {
            LOGGER.trace("Player would die, reducing health to 1");
            livingHurtEvent.setAmount(player.getHealth() - 1);
            return;
        }

        if(source.isFire()){
            if(player.getHealth() - originalDamageAmount <= 0) {
                LOGGER.trace("Player would die to fire, reducing health to 1");
                livingHurtEvent.setAmount(player.getHealth() - 1);
                return;
            }

            LOGGER.trace("Set damage modifier to {}", playerModifiers.ProjectileDamageModifier);
            livingHurtEvent.setAmount(livingHurtEvent.getAmount() * playerModifiers.FireDamageModifier);
        } else if(source.isProjectile()) {
            LOGGER.trace("Set damage modifier to {}", playerModifiers.ProjectileDamageModifier);
            livingHurtEvent.setAmount(livingHurtEvent.getAmount() * playerModifiers.ProjectileDamageModifier);
        } else if(source.isMagic()) {
            LOGGER.trace("Set damage modifier to {}", playerModifiers.MagicDamageModifier);
            livingHurtEvent.setAmount(livingHurtEvent.getAmount() * playerModifiers.MagicDamageModifier);
        } else if(source.isExplosion()) {
            LOGGER.trace("Set damage modifier to {}", playerModifiers.ExplosionDamageModifier);
            livingHurtEvent.setAmount(livingHurtEvent.getAmount() * playerModifiers.ExplosionDamageModifier);
        } else if(source.isFall()) {
            LOGGER.trace("Set damage modifier to {}", playerModifiers.FallDamageModifier);
            livingHurtEvent.setAmount(livingHurtEvent.getAmount() * playerModifiers.FallDamageModifier);
        } else {
            LOGGER.trace("Set damage modifier to {}", playerModifiers.OtherDamageModifier);
            livingHurtEvent.setAmount(livingHurtEvent.getAmount() * playerModifiers.OtherDamageModifier);
        }

        LOGGER.info("Reduced player {} damage from {} to {}", playerName, originalDamageAmount, livingHurtEvent.getAmount());
    }
}
