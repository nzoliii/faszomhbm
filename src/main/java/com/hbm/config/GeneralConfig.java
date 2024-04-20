package com.hbm.config;

import java.util.Locale;

import org.apache.logging.log4j.Level;
import org.lwjgl.opengl.GLContext;

import com.hbm.main.MainRegistry;
import com.hbm.render.GLCompat;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

public class GeneralConfig {

	public static double conversionRateHeToRF = 1.0F;
	public static boolean enableDebugMode = false;
	public static boolean enableSkybox = true;
	public static boolean enableWelcomeMessage = true;
	public static boolean enableMycelium = false;
	public static boolean enablePlutoniumOre = false;
	public static boolean enableDungeons = true;
	public static boolean enableMDOres = true;
	public static boolean enableMines = true;
	public static boolean enableRad = true;
	public static boolean enableNITAN = true;
	public static boolean enableAutoCleanup = false;
	public static boolean enableMeteorStrikes = true;
	public static boolean enableMeteorShowers = true;
	public static boolean enableMeteorTails = true;
	public static boolean enableSpecialMeteors = true;
	public static boolean enableBomberShortMode = false;
	public static boolean enableVaults = true;
	public static boolean enableRads = true;
	public static boolean enableCoal = true;
	public static boolean enableAsbestos = true;
	public static boolean advancedRadiation = true;
	public static boolean enableCataclysm = false;
	public static boolean enableExtendedLogging = false;
	public static boolean enableHardcoreTaint = false;
	public static boolean enableGuns = true;
	public static boolean ssgAnim = true;
	public static boolean enableVirus = true;
	public static boolean enableCrosshairs = true;
	public static boolean instancedParticles = true;
	public static boolean callListModels = true;
	public static boolean useShaders = false;
	public static boolean useShaders2 = false;
	public static boolean bloom = true;
	public static boolean heatDistortion = true;
	public static boolean enableBabyMode = false;
	public static boolean recipes = true;
	public static boolean shapeless = true;
	public static boolean oredict = true;
	public static boolean shaped = true;
	public static boolean nonoredict = true;
	public static boolean jei = true;
	public static boolean changelog = true;
	public static boolean registerTanks = true;
	public static boolean duckButton = true;
	public static boolean depthEffects = true;
	public static boolean flashlight = true;
	public static boolean flashlightVolumetric = true;
	public static boolean bulletHoleNormalMapping = true;
	public static int flowingDecalAmountMax = 20;
	public static boolean bloodFX = true;
	public static int crucibleMaxCharges = 16;
	public static boolean enableReEval = true;
	
	public static boolean enable528 = false;
	public static boolean enable528ReasimBoilers = true;
	public static boolean enable528ColtanDeposit = true;
	public static boolean enable528ColtanSpawn = false;
	public static boolean enable528BedrockDeposit = true;
	public static boolean enable528BedrockSpawn = false;
	public static boolean enableReflectorCompat = false;
	public static int coltanRate = 2;
	public static int bedrockRate = 50;
	
	public static void loadFromConfig(Configuration config){
		final String CATEGORY_GENERAL = "01_general";
		enableDebugMode = config.get(CATEGORY_GENERAL, "1.00_enableDebugMode", false).getBoolean(false);
		enableSkybox = config.get(CATEGORY_GENERAL, "1.00_enableSkybox", true).getBoolean(true);
		enableMycelium = config.get(CATEGORY_GENERAL, "1.01_enableMyceliumSpread", false).getBoolean(false);
		enablePlutoniumOre = config.get(CATEGORY_GENERAL, "1.02_enablePlutoniumNetherOre", false).getBoolean(false);
		enableDungeons = config.get(CATEGORY_GENERAL, "1.03_enableDungeonSpawn", true).getBoolean(true);
		enableMDOres = config.get(CATEGORY_GENERAL, "1.04_enableOresInModdedDimensions", true).getBoolean(true);
		enableMines = config.get(CATEGORY_GENERAL, "1.05_enableLandmineSpawn", true).getBoolean(true);
		enableRad = config.get(CATEGORY_GENERAL, "1.06_enableRadHotspotSpawn", true).getBoolean(true);
		enableNITAN = config.get(CATEGORY_GENERAL, "1.07_enableNITANChestSpawn", true).getBoolean(true);
		enableAutoCleanup = config.get(CATEGORY_GENERAL, "1.09_enableAutomaticRadCleanup", false).getBoolean(false);
		enableMeteorStrikes = config.get(CATEGORY_GENERAL, "1.10_enableMeteorStrikes", true).getBoolean(true);
		enableMeteorShowers = config.get(CATEGORY_GENERAL, "1.11_enableMeteorShowers", true).getBoolean(true);
		enableMeteorTails = config.get(CATEGORY_GENERAL, "1.12_enableMeteorTails", true).getBoolean(true);
		enableSpecialMeteors = config.get(CATEGORY_GENERAL, "1.13_enableSpecialMeteors", false).getBoolean(false);
		enableBomberShortMode = config.get(CATEGORY_GENERAL, "1.14_enableBomberShortMode", false).getBoolean(false);
		enableVaults = config.get(CATEGORY_GENERAL, "1.15_enableVaultSpawn", true).getBoolean(true);
		enableRads = config.get(CATEGORY_GENERAL, "1.16_enableNewRadiation", true).getBoolean(true);
		enableCataclysm = config.get(CATEGORY_GENERAL, "1.17_enableCataclysm", false).getBoolean(false);
		enableExtendedLogging = config.get(CATEGORY_GENERAL, "1.18_enableExtendedLogging", false).getBoolean(false);
		enableHardcoreTaint = config.get(CATEGORY_GENERAL, "1.19_enableHardcoreTaint", false).getBoolean(false);
		enableGuns = config.get(CATEGORY_GENERAL, "1.20_enableGuns", true).getBoolean(true);
		enableVirus = config.get(CATEGORY_GENERAL, "1.21_enableVirus", false).getBoolean(false);
        enableCrosshairs = config.get(CATEGORY_GENERAL, "1.22_enableCrosshairs", true).getBoolean(true);
		Property shaders = config.get(CATEGORY_GENERAL, "1.23_enableShaders", false);
		shaders.setComment("Experimental, don't use");
		useShaders = shaders.getBoolean(false);
		if(FMLCommonHandler.instance().getSide() == Side.CLIENT)
			if(!OpenGlHelper.shadersSupported) {
				MainRegistry.logger.log(Level.WARN, "GLSL shaders are not supported; not using shaders");
				useShaders = false;
			} else if(!GLContext.getCapabilities().OpenGL30) {
				MainRegistry.logger.log(Level.WARN, "OpenGL 3.0 is not supported; not using shaders");
				useShaders = false;
			}
		useShaders = false;
		useShaders2 = config.get(CATEGORY_GENERAL, "1.23_enableShaders2", false).getBoolean(false);
		Property ssg_anim = config.get(CATEGORY_GENERAL, "1.24_ssgAnimType", true);
		ssg_anim.setComment("Which supershotgun reload animation to use. True is Drillgon's animation, false is Bob's animation");
		ssgAnim = ssg_anim.getBoolean();
		instancedParticles = CommonConfig.createConfigBool(config, CATEGORY_GENERAL, "1.25_instancedParticles", "Enables instanced particle rendering for some particles, which makes them render several times faster. May not work on all computers, and will break with shaders.", true);
		depthEffects = CommonConfig.createConfigBool(config, CATEGORY_GENERAL, "1.25_depthBufferEffects", "Enables effects that make use of reading from the depth buffer", true);
		flashlight = CommonConfig.createConfigBool(config, CATEGORY_GENERAL, "1.25_flashlights", "Enables dynamic directional lights", true);
		flashlightVolumetric = CommonConfig.createConfigBool(config, CATEGORY_GENERAL, "1.25_flashlight_volumetrics", "Enables volumetric lighting for directional lights", true);
		bulletHoleNormalMapping = CommonConfig.createConfigBool(config, CATEGORY_GENERAL, "1.25_bullet_hole_normal_mapping", "Enables normal mapping on bullet holes, which can improve visuals", true);
		flowingDecalAmountMax = CommonConfig.createConfigInt(config, CATEGORY_GENERAL, "1.25_flowing_decal_max", "The maximum number of 'flowing' decals that can exist at once (eg blood that can flow down walls)", 20);
		
		callListModels = CommonConfig.createConfigBool(config, CATEGORY_GENERAL, "1.26_callListModels", "Enables call lists for a few models, making them render extremely fast", true);
		enableBabyMode = config.get(CATEGORY_GENERAL, "1.27_enableBabyMode", false).getBoolean(false);
		enableReflectorCompat = config.get(CATEGORY_GENERAL, "1.24_enableReflectorCompat", false).getBoolean(false);
		
		enableCoal = config.get(CATEGORY_GENERAL, "1.26_enableCoalDust", true).getBoolean(true);
		enableAsbestos = config.get(CATEGORY_GENERAL, "1.26_enableAsbestosDust", true).getBoolean(true);
		
		enableReEval = config.get(CATEGORY_GENERAL, "1.27_enableReEval", true, "Allows re-evaluating power networks on link remove instead of destroying and recreating").getBoolean(true);
		
		recipes = config.get(CATEGORY_GENERAL, "1.28_enableRecipes", true).getBoolean(true);
		shapeless = config.get(CATEGORY_GENERAL, "1.28_enableShapeless", true).getBoolean(true);
		oredict = config.get(CATEGORY_GENERAL, "1.28_enableOreDict", true).getBoolean(true);
		shaped = config.get(CATEGORY_GENERAL, "1.28_enableShaped", true).getBoolean(true);
		nonoredict = config.get(CATEGORY_GENERAL, "1.28_enableNonOreDict", true).getBoolean(true);
		registerTanks = config.get(CATEGORY_GENERAL, "1.28_registerTanks", true).getBoolean(true);
		
		jei = config.get(CATEGORY_GENERAL, "1.28_enableJei", true).getBoolean(true);
		changelog = config.get(CATEGORY_GENERAL, "1.28_enableChangelog", true).getBoolean(true);
		duckButton = config.get(CATEGORY_GENERAL, "1.28_enableDuckButton", true).getBoolean(true);
		bloom = config.get(CATEGORY_GENERAL, "1.30_enableBloom", true).getBoolean(true);
		heatDistortion = config.get(CATEGORY_GENERAL, "1.30_enableHeatDistortion", true).getBoolean(true);
		
		Property adv_rads = config.get(CATEGORY_GENERAL, "1.31_enableAdvancedRadiation", true);
		adv_rads.setComment("Enables a 3 dimensional version of the radiation system that also allows some blocks (like concrete bricks) to stop it from spreading");
		advancedRadiation = adv_rads.getBoolean(true);
		
		bloodFX = CommonConfig.createConfigBool(config, CATEGORY_GENERAL, "1.32_enable_blood_effects", "Enables the over-the-top blood visual effects for some weapons", true);
	
		if((instancedParticles || depthEffects || flowingDecalAmountMax > 0 || bloodFX || bloom || heatDistortion) && (!GLCompat.error.isEmpty() || !useShaders2)){
			MainRegistry.logger.error("Warning - Open GL 3.3 not supported! Disabling 3.3 effects...");
			if(!useShaders2){
				MainRegistry.logger.error("Shader effects manually disabled");
			}
			instancedParticles = false;
			depthEffects = false;
			flowingDecalAmountMax = 0;
			bloodFX = false;
			useShaders2 = false;
			bloom = false;
			heatDistortion = false;
		}
		if(!depthEffects){
			flashlight = false;
			bulletHoleNormalMapping = false;
		}
		if(!flashlight){
			flashlightVolumetric = false;
		}
		
		crucibleMaxCharges = CommonConfig.createConfigInt(config, CATEGORY_GENERAL, "1.33_crucible_max_charges", "How many times you can use the crucible before recharge", 16);
		
		if(crucibleMaxCharges <= 0){
			crucibleMaxCharges = 16;
		}

		enableWelcomeMessage = CommonConfig.createConfigBool(config, CATEGORY_GENERAL, "1.34_enableWelcomeMessage", "Enables the welcome message which appears in the chat when you load into the game", true);

		conversionRateHeToRF = CommonConfig.createConfigDouble(config, CATEGORY_GENERAL, "1.35_conversionRateHeToRF", "One HE is (insert number) RF - <number> (double)", 1.0D);

		final String CATEGORY_528 = "528";

		config.addCustomCategoryComment(CATEGORY_528, "CAUTION\n"
				+ "528 Mode: Please proceed with caution!\n"
				+ "528-Modus: Lassen Sie Vorsicht walten!\n"
				+ "Ñ�Ð¿Ð¾Ñ�Ð¾Ð±-528: Ð´ÐµÐ¹Ñ�Ñ‚Ð²Ð¾Ð²Ð°Ñ‚ÑŒ Ñ� Ð¾Ñ�Ñ‚Ð¾Ñ€Ð¾Ð¶Ð½Ð¾Ñ�Ñ‚ÑŒÑŽ!");
		
		enable528 = CommonConfig.createConfigBool(config, CATEGORY_528, "enable528Mode", "The central toggle for 528 mode.", false);
		enable528ReasimBoilers = CommonConfig.createConfigBool(config, CATEGORY_528, "X528_forceReasimBoilers", "Keeps the RBMK dial for ReaSim boilers on, preventing use of non-ReaSim boiler columns and forcing the use of steam in-/outlets", true);
		enable528ColtanDeposit = CommonConfig.createConfigBool(config, CATEGORY_528, "X528_enableColtanDepsoit", "Enables the coltan deposit. A large amount of coltan will spawn around a single random location in the world.", true);
		enable528ColtanSpawn = CommonConfig.createConfigBool(config, CATEGORY_528, "X528_enableColtanSpawning", "Enables coltan ore as a random spawn in the world. Unlike the deposit option, coltan will not just spawn in one central location.", false);
		enable528BedrockDeposit = CommonConfig.createConfigBool(config, CATEGORY_528, "X528_enableBedrockDepsoit", "Enables bedrock coltan ores in the coltan deposit. These ores can be drilled to extract infinite coltan, albeit slowly.", true);
		enable528BedrockSpawn = CommonConfig.createConfigBool(config, CATEGORY_528, "X528_enableBedrockSpawning", "Enables the bedrock coltan ores as a rare spawn. These will be rarely found anywhere in the world.", false);
		coltanRate = CommonConfig.createConfigInt(config, CATEGORY_528, "X528_oreColtanFrequency", "Determines how many coltan ore veins are to be expected in a chunk. These values do not affect the frequency in deposits, and only apply if random coltan spanwing is enabled.", 2);
		bedrockRate = CommonConfig.createConfigInt(config, CATEGORY_528, "X528_bedrockColtanFrequency", "Determines how often (1 in X) bedrock coltan ores spawn. Applies for both the bedrock ores in the coltan deposit (if applicable) and the random bedrock ores (if applicable)", 50);
		
		if(enable528){
			enableBabyMode = false;
		}
	}
}
