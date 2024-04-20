package com.hbm.main;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hbm.entity.item.EntityMovingPackage;
import com.hbm.tileentity.network.*;
import org.apache.logging.log4j.Logger;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.BlockCrate;
import com.hbm.blocks.generic.EntityGrenadeTau;
import com.hbm.blocks.network.energy.CableDiode.TileEntityDiode;
import com.hbm.blocks.network.energy.BlockCableGauge.TileEntityCableGauge;
import com.hbm.blocks.generic.BlockBedrockOreTE.TileEntityBedrockOre;
import com.hbm.capability.HbmCapability;
import com.hbm.capability.HbmLivingCapability;
import com.hbm.command.CommandHbm;
import com.hbm.command.CommandRadiation;
import com.hbm.config.BombConfig;
import com.hbm.config.GeneralConfig;
import com.hbm.config.MachineConfig;
import com.hbm.config.MobConfig;
import com.hbm.config.PotionConfig;
import com.hbm.config.RadiationConfig;
import com.hbm.config.ToolConfig;
import com.hbm.config.WeaponConfig;
import com.hbm.config.CompatibilityConfig;
import com.hbm.config.WorldConfig;
import com.hbm.config.BedrockOreJsonConfig;
import com.hbm.creativetabs.ResourceTab;
import com.hbm.creativetabs.BlockTab;
import com.hbm.creativetabs.ConsumableTab;
import com.hbm.creativetabs.ControlTab;
import com.hbm.creativetabs.MachineTab;
import com.hbm.creativetabs.MissileTab;
import com.hbm.creativetabs.NukeTab;
import com.hbm.creativetabs.PartsTab;
import com.hbm.creativetabs.TemplateTab;
import com.hbm.creativetabs.WeaponTab;
import com.hbm.entity.effect.EntityBlackHole;
import com.hbm.entity.effect.EntityCloudFleija;
import com.hbm.entity.effect.EntityCloudFleijaRainbow;
import com.hbm.entity.effect.EntityCloudSolinium;
import com.hbm.entity.effect.EntityCloudTom;
import com.hbm.entity.effect.EntityEMPBlast;
import com.hbm.entity.effect.EntityFalloutRain;
import com.hbm.entity.effect.EntityNukeTorex;
import com.hbm.entity.effect.EntityQuasar;
import com.hbm.entity.effect.EntityRagingVortex;
import com.hbm.entity.effect.EntitySpear;
import com.hbm.entity.effect.EntityVortex;
import com.hbm.entity.grenade.EntityGrenadeASchrab;
import com.hbm.entity.grenade.EntityGrenadeBlackHole;
import com.hbm.entity.grenade.EntityGrenadeBreach;
import com.hbm.entity.grenade.EntityGrenadeBurst;
import com.hbm.entity.grenade.EntityGrenadeCloud;
import com.hbm.entity.grenade.EntityGrenadeCluster;
import com.hbm.entity.grenade.EntityGrenadeElectric;
import com.hbm.entity.grenade.EntityGrenadeFire;
import com.hbm.entity.grenade.EntityGrenadeFlare;
import com.hbm.entity.grenade.EntityGrenadeFrag;
import com.hbm.entity.grenade.EntityGrenadeGas;
import com.hbm.entity.grenade.EntityGrenadeGascan;
import com.hbm.entity.grenade.EntityGrenadeGeneric;
import com.hbm.entity.grenade.EntityGrenadeIFBouncy;
import com.hbm.entity.grenade.EntityGrenadeIFBrimstone;
import com.hbm.entity.grenade.EntityGrenadeIFConcussion;
import com.hbm.entity.grenade.EntityGrenadeIFGeneric;
import com.hbm.entity.grenade.EntityGrenadeIFHE;
import com.hbm.entity.grenade.EntityGrenadeIFHopwire;
import com.hbm.entity.grenade.EntityGrenadeIFImpact;
import com.hbm.entity.grenade.EntityGrenadeIFIncendiary;
import com.hbm.entity.grenade.EntityGrenadeIFMystery;
import com.hbm.entity.grenade.EntityGrenadeIFNull;
import com.hbm.entity.grenade.EntityGrenadeIFSpark;
import com.hbm.entity.grenade.EntityGrenadeIFSticky;
import com.hbm.entity.grenade.EntityGrenadeIFToxic;
import com.hbm.entity.grenade.EntityGrenadeLemon;
import com.hbm.entity.grenade.EntityGrenadeMIRV;
import com.hbm.entity.grenade.EntityGrenadeMk2;
import com.hbm.entity.grenade.EntityGrenadeNuclear;
import com.hbm.entity.grenade.EntityGrenadeNuke;
import com.hbm.entity.grenade.EntityGrenadePC;
import com.hbm.entity.grenade.EntityGrenadePlasma;
import com.hbm.entity.grenade.EntityGrenadePoison;
import com.hbm.entity.grenade.EntityGrenadePulse;
import com.hbm.entity.grenade.EntityGrenadeSchrabidium;
import com.hbm.entity.grenade.EntityGrenadeShrapnel;
import com.hbm.entity.grenade.EntityGrenadeSmart;
import com.hbm.entity.grenade.EntityGrenadeStrong;
import com.hbm.entity.grenade.EntityGrenadeSolinium;
import com.hbm.entity.grenade.EntityGrenadeZOMG;
import com.hbm.entity.item.EntityFireworks;
import com.hbm.entity.item.EntityMovingItem;
import com.hbm.entity.logic.EntityBalefire;
import com.hbm.entity.logic.EntityBomber;
import com.hbm.entity.logic.EntityDeathBlast;
import com.hbm.entity.logic.EntityEMP;
import com.hbm.entity.logic.EntityNukeExplosionMK3;
import com.hbm.entity.logic.EntityNukeExplosionMK5;
import com.hbm.entity.logic.EntityNukeExplosionPlus;
import com.hbm.entity.logic.EntityTomBlast;
import com.hbm.entity.logic.IChunkLoader;
import com.hbm.entity.missile.EntityBobmazon;
import com.hbm.entity.missile.EntityBombletSelena;
import com.hbm.entity.missile.EntityBombletTheta;
import com.hbm.entity.missile.EntityBooster;
import com.hbm.entity.missile.EntityCarrier;
import com.hbm.entity.missile.EntityMIRV;
import com.hbm.entity.missile.EntityMinerRocket;
import com.hbm.entity.missile.EntityMissileAntiBallistic;
import com.hbm.entity.missile.EntityMissileBHole;
import com.hbm.entity.missile.EntityMissileBunkerBuster;
import com.hbm.entity.missile.EntityMissileBurst;
import com.hbm.entity.missile.EntityMissileBusterStrong;
import com.hbm.entity.missile.EntityMissileCluster;
import com.hbm.entity.missile.EntityMissileClusterStrong;
import com.hbm.entity.missile.EntityMissileCustom;
import com.hbm.entity.missile.EntityMissileDoomsday;
import com.hbm.entity.missile.EntityMissileDrill;
import com.hbm.entity.missile.EntityMissileEMP;
import com.hbm.entity.missile.EntityMissileEMPStrong;
import com.hbm.entity.missile.EntityMissileEndo;
import com.hbm.entity.missile.EntityMissileExo;
import com.hbm.entity.missile.EntityMissileGeneric;
import com.hbm.entity.missile.EntityMissileIncendiary;
import com.hbm.entity.missile.EntityMissileIncendiaryStrong;
import com.hbm.entity.missile.EntityMissileInferno;
import com.hbm.entity.missile.EntityMissileMicro;
import com.hbm.entity.missile.EntityMissileMirv;
import com.hbm.entity.missile.EntityMissileNuclear;
import com.hbm.entity.missile.EntityMissileN2;
import com.hbm.entity.missile.EntityMissileRain;
import com.hbm.entity.missile.EntityMissileSchrabidium;
import com.hbm.entity.missile.EntityMissileStrong;
import com.hbm.entity.missile.EntityMissileTaint;
import com.hbm.entity.missile.EntityMissileVolcano;
import com.hbm.entity.missile.EntitySoyuz;
import com.hbm.entity.missile.EntitySoyuzCapsule;
import com.hbm.entity.mob.EntityCyberCrab;
import com.hbm.entity.mob.EntityDuck;
import com.hbm.entity.mob.EntityGlowingOne;
import com.hbm.entity.mob.EntityFBI;
import com.hbm.entity.mob.EntityHunterChopper;
import com.hbm.entity.mob.EntityMaskMan;
import com.hbm.entity.mob.EntityNuclearCreeper;
import com.hbm.entity.mob.EntityQuackos;
import com.hbm.entity.mob.EntityRADBeast;
import com.hbm.entity.mob.EntityTaintCrab;
import com.hbm.entity.mob.EntityTaintedCreeper;
import com.hbm.entity.mob.EntityTeslaCrab;
import com.hbm.entity.mob.EntityUFO;
import com.hbm.entity.mob.botprime.EntityBOTPrimeBody;
import com.hbm.entity.mob.botprime.EntityBOTPrimeHead;
import com.hbm.entity.particle.EntityBSmokeFX;
import com.hbm.entity.particle.EntityChlorineFX;
import com.hbm.entity.particle.EntityCloudFX;
import com.hbm.entity.particle.EntityDSmokeFX;
import com.hbm.entity.particle.EntityGasFX;
import com.hbm.entity.particle.EntityGasFlameFX;
import com.hbm.entity.particle.EntityOilSpillFX;
import com.hbm.entity.particle.EntityOrangeFX;
import com.hbm.entity.particle.EntityPinkCloudFX;
import com.hbm.entity.particle.EntitySSmokeFX;
import com.hbm.entity.particle.EntitySmokeFX;
import com.hbm.entity.particle.EntityTSmokeFX;
import com.hbm.entity.projectile.EntityAAShell;
import com.hbm.entity.projectile.EntityBaleflare;
import com.hbm.entity.projectile.EntityBeamVortex;
import com.hbm.entity.projectile.EntityBombletZeta;
import com.hbm.entity.projectile.EntityBoxcar;
import com.hbm.entity.projectile.EntityBuilding;
import com.hbm.entity.projectile.EntityBullet;
import com.hbm.entity.projectile.EntityBulletBase;
import com.hbm.entity.projectile.EntityBurningFOEQ;
import com.hbm.entity.projectile.EntityChopperMine;
import com.hbm.entity.projectile.EntityCombineBall;
import com.hbm.entity.projectile.EntityDischarge;
import com.hbm.entity.projectile.EntityDuchessGambit;
import com.hbm.entity.projectile.EntityExplosiveBeam;
import com.hbm.entity.projectile.EntityFallingNuke;
import com.hbm.entity.projectile.EntityFire;
import com.hbm.entity.projectile.EntityLN2;
import com.hbm.entity.projectile.EntityLaser;
import com.hbm.entity.projectile.EntityLaserBeam;
import com.hbm.entity.projectile.EntityMeteor;
import com.hbm.entity.projectile.EntityMinerBeam;
import com.hbm.entity.projectile.EntityMiniMIRV;
import com.hbm.entity.projectile.EntityMiniNuke;
import com.hbm.entity.projectile.EntityModBeam;
import com.hbm.entity.projectile.EntityOilSpill;
import com.hbm.entity.projectile.EntityPlasmaBeam;
import com.hbm.entity.projectile.EntityRBMKDebris;
import com.hbm.entity.projectile.EntityRailgunBlast;
import com.hbm.entity.projectile.EntityRainbow;
import com.hbm.entity.projectile.EntityRocket;
import com.hbm.entity.projectile.EntityRocketHoming;
import com.hbm.entity.projectile.EntityRubble;
import com.hbm.entity.projectile.EntitySchrab;
import com.hbm.entity.projectile.EntityShrapnel;
import com.hbm.entity.projectile.EntitySparkBeam;
import com.hbm.entity.projectile.EntityTom;
import com.hbm.entity.projectile.EntityWaterSplash;
import com.hbm.entity.siege.SiegeTier;
import com.hbm.explosion.ExplosionNukeGeneric;
import com.hbm.forgefluid.FFPipeNetwork;
import com.hbm.forgefluid.FluidContainerRegistry;
import com.hbm.forgefluid.FluidTypeHandler;
import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.handler.ArmorUtil;
import com.hbm.handler.BobmazonOfferFactory;
import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.GuiHandler;
import com.hbm.handler.HTTPHandler;
import com.hbm.handler.HazmatRegistry;
import com.hbm.handler.HbmKeybinds;
import com.hbm.handler.MultiblockBBHandler;
import com.hbm.handler.crt.NTMCraftTweaker;
import com.hbm.hazard.HazardRegistry;
import com.hbm.inventory.AnvilRecipes;
import com.hbm.inventory.AssemblerRecipes;
import com.hbm.inventory.ChemplantRecipes;
import com.hbm.inventory.MixerRecipes;
import com.hbm.inventory.BreederRecipes;
import com.hbm.inventory.CrackRecipes;
import com.hbm.inventory.CentrifugeRecipes;
import com.hbm.inventory.CrystallizerRecipes;
import com.hbm.inventory.CyclotronRecipes;
import com.hbm.inventory.HadronRecipes;
import com.hbm.inventory.MagicRecipes;
import com.hbm.inventory.OreDictManager;
import com.hbm.inventory.RefineryRecipes;
import com.hbm.inventory.WasteDrumRecipes;
import com.hbm.inventory.SILEXRecipes;
import com.hbm.inventory.ShredderRecipes;
import com.hbm.inventory.RBMKOutgasserRecipes;
import com.hbm.inventory.RBMKFuelRecipes;
import com.hbm.inventory.DFCRecipes;
import com.hbm.inventory.DiFurnaceRecipes;
import com.hbm.inventory.PotionRecipes;
import com.hbm.inventory.SAFERecipes;
import com.hbm.inventory.StorageDrumRecipes;
import com.hbm.inventory.NuclearTransmutationRecipes;
import com.hbm.inventory.HeatRecipes;
import com.hbm.inventory.EngineRecipes;
import com.hbm.inventory.PressRecipes;
import com.hbm.inventory.FluidCombustionRecipes;
import com.hbm.inventory.BedrockOreRegistry;
import com.hbm.inventory.control_panel.ControlEvent;
import com.hbm.inventory.control_panel.ControlRegistry;
import com.hbm.items.ModItems;
import com.hbm.lib.HBMSoundHandler;
import com.hbm.lib.HbmWorld;
import com.hbm.lib.Library;
import com.hbm.lib.RefStrings;
import com.hbm.packet.PacketDispatcher;
import com.hbm.potion.HbmPotion;
import com.hbm.potion.HbmDetox;
import com.hbm.saveddata.satellites.Satellite;
import com.hbm.tileentity.TileEntityDoorGeneric;
import com.hbm.tileentity.TileEntityKeypadBase;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.TileEntityProxyConductor;
import com.hbm.tileentity.TileEntityProxyEnergy;
import com.hbm.tileentity.TileEntityProxyInventory;
import com.hbm.tileentity.TileEntitySlidingBlastDoorKeypad;
import com.hbm.tileentity.bomb.TileEntityBombMulti;
import com.hbm.tileentity.bomb.TileEntityCompactLauncher;
import com.hbm.tileentity.bomb.TileEntityCrashedBomb;
import com.hbm.tileentity.bomb.TileEntityFireworks;
import com.hbm.tileentity.bomb.TileEntityLandmine;
import com.hbm.tileentity.bomb.TileEntityLaunchPad;
import com.hbm.tileentity.bomb.TileEntityLaunchTable;
import com.hbm.tileentity.bomb.TileEntityNukeBalefire;
import com.hbm.tileentity.bomb.TileEntityNukeBoy;
import com.hbm.tileentity.bomb.TileEntityNukeCustom;
import com.hbm.tileentity.bomb.TileEntityNukeFleija;
import com.hbm.tileentity.bomb.TileEntityNukeGadget;
import com.hbm.tileentity.bomb.TileEntityNukeMan;
import com.hbm.tileentity.bomb.TileEntityNukeMike;
import com.hbm.tileentity.bomb.TileEntityNukeN2;
import com.hbm.tileentity.bomb.TileEntityNukePrototype;
import com.hbm.tileentity.bomb.TileEntityNukeSolinium;
import com.hbm.tileentity.bomb.TileEntityNukeTsar;
import com.hbm.tileentity.bomb.TileEntityRailgun;
import com.hbm.tileentity.network.energy.TileEntityCableBaseNT;
import com.hbm.tileentity.network.energy.TileEntityCableSwitch;
import com.hbm.tileentity.network.energy.TileEntityMachineDetector;
import com.hbm.tileentity.conductor.TileEntityFFFluidDuctMk2;
import com.hbm.tileentity.conductor.TileEntityFFFluidSuccMk2;
import com.hbm.tileentity.conductor.TileEntityFFFluidDuctMk2Solid;
import com.hbm.tileentity.conductor.TileEntityFFFluidSuccMk2Solid;
import com.hbm.tileentity.deco.TileEntityDecoBlock;
import com.hbm.tileentity.deco.TileEntityDecoBlockAlt;
import com.hbm.tileentity.deco.TileEntityDecoPoleSatelliteReceiver;
import com.hbm.tileentity.deco.TileEntityGeysir;
import com.hbm.tileentity.deco.TileEntityObjTester;
import com.hbm.tileentity.deco.TileEntitySpinnyLight;
import com.hbm.tileentity.deco.TileEntityTestRender;
import com.hbm.tileentity.deco.TileEntityTrappedBrick;
import com.hbm.tileentity.deco.TileEntityVent;
import com.hbm.tileentity.machine.*;
import com.hbm.tileentity.machine.oil.*;
import com.hbm.tileentity.network.energy.*;
import com.hbm.tileentity.machine.TileEntityMachineReactorLarge.ReactorFuelType;
import com.hbm.tileentity.machine.pile.TileEntityPileFuel;
import com.hbm.tileentity.machine.pile.TileEntityPileSource;
import com.hbm.tileentity.machine.rbmk.RBMKDials;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKAbsorber;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKBlank;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKBoiler;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKConsole;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKCraneConsole;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKControlAuto;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKControlManual;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKInlet;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKModerator;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKOutgasser;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKOutlet;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKReflector;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKRod;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKRodReaSim;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKStorage;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKCooler;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKHeater;
import com.hbm.tileentity.turret.TileEntityTurretBrandon;
import com.hbm.tileentity.turret.TileEntityTurretCIWS;
import com.hbm.tileentity.turret.TileEntityTurretCheapo;
import com.hbm.tileentity.turret.TileEntityTurretChekhov;
import com.hbm.tileentity.turret.TileEntityTurretFlamer;
import com.hbm.tileentity.turret.TileEntityTurretFriendly;
import com.hbm.tileentity.turret.TileEntityTurretFritz;
import com.hbm.tileentity.turret.TileEntityTurretHeavy;
import com.hbm.tileentity.turret.TileEntityTurretHoward;
import com.hbm.tileentity.turret.TileEntityTurretHowardDamaged;
import com.hbm.tileentity.turret.TileEntityTurretJeremy;
import com.hbm.tileentity.turret.TileEntityTurretLight;
import com.hbm.tileentity.turret.TileEntityTurretMaxwell;
import com.hbm.tileentity.turret.TileEntityTurretRichard;
import com.hbm.tileentity.turret.TileEntityTurretRocket;
import com.hbm.tileentity.turret.TileEntityTurretSpitfire;
import com.hbm.tileentity.turret.TileEntityTurretTau;
import com.hbm.tileentity.turret.TileEntityTurretTauon;
import com.hbm.world.feature.SchistStratum;
import com.hbm.world.generator.CellularDungeonFactory;

import net.minecraft.block.BlockDispenser;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootEntry;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.LoadingCallback;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = RefStrings.MODID, version = RefStrings.VERSION, name = RefStrings.NAME)
public class MainRegistry {

	static {
		HBMSoundHandler.init();
		FluidRegistry.enableUniversalBucket();
	}

	@SidedProxy(clientSide = RefStrings.CLIENTSIDE, serverSide = RefStrings.SERVERSIDE)
	public static ServerProxy proxy;

	@Mod.Instance(RefStrings.MODID)
	public static MainRegistry instance;

	public static Logger logger;

	public static List<FFPipeNetwork> allPipeNetworks = new ArrayList<FFPipeNetwork>();

	// Creative Tabs
	// ingots, nuggets, wires, machine parts
	public static CreativeTabs partsTab = new PartsTab(CreativeTabs.getNextID(), "tabParts");
	// items that belong in machines, fuels, etc
	public static CreativeTabs controlTab = new ControlTab(CreativeTabs.getNextID(), "tabControl");
	// templates, siren tracks
	public static CreativeTabs templateTab = new TemplateTab(CreativeTabs.getNextID(), "tabTemplate");
	// ore and mineral blocks
	public static CreativeTabs resourceTab = new ResourceTab(CreativeTabs.getNextID(), "tabResource");
	// construction blocks
	public static CreativeTabs blockTab = new BlockTab(CreativeTabs.getNextID(), "tabBlocks");
	// machines, structure parts
	public static CreativeTabs machineTab = new MachineTab(CreativeTabs.getNextID(), "tabMachine");
	// bombs
	public static CreativeTabs nukeTab = new NukeTab(CreativeTabs.getNextID(), "tabNuke");
	// missiles, satellites
	public static CreativeTabs missileTab = new MissileTab(CreativeTabs.getNextID(), "tabMissile");
	// turrets, weapons, ammo
	public static CreativeTabs weaponTab = new WeaponTab(CreativeTabs.getNextID(), "tabWeapon");
	// drinks, kits, tools
	public static CreativeTabs consumableTab = new ConsumableTab(CreativeTabs.getNextID(), "tabConsumable");

	public static int generalOverride = 0;
	public static int polaroidID = 1;
	
	public static int x;
	public static int y;
	public static int z;
	public static long time;

	// Armor Materials
	// Drillgon200: I have no idea what the two strings and the number at the
	// end are.
	public static ArmorMaterial enumArmorMaterialT45 = EnumHelper.addArmorMaterial(RefStrings.MODID + ":T45", RefStrings.MODID + ":T45", 150, new int[] { 3, 6, 8, 3 }, 0, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 2.0F);
	public static ArmorMaterial aMatBJ = EnumHelper.addArmorMaterial(RefStrings.MODID + ":BLACKJACK", RefStrings.MODID + ":HBM_BLACKJACK", 150, new int[] { 3, 6, 8, 3 }, 100, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 2.0F);
	public static ArmorMaterial aMatAJR = EnumHelper.addArmorMaterial(RefStrings.MODID + ":T45AJR", RefStrings.MODID + ":T45AJR", 150, new int[] { 3, 6, 8, 3 }, 100, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 2.0F);

	public static ArmorMaterial aMatRPA = EnumHelper.addArmorMaterial(RefStrings.MODID + ":RPA", RefStrings.MODID + ":RPA", 150, new int[] { 3, 6, 8, 3 }, 100, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 2.0F);
	public static ArmorMaterial aMatHEV = EnumHelper.addArmorMaterial(RefStrings.MODID + ":HEV", RefStrings.MODID + ":HEV", 150, new int[] { 3, 6, 8, 3 }, 100, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 2.0F);
	public static ArmorMaterial enumArmorMaterialHazmat = EnumHelper.addArmorMaterial(RefStrings.MODID + ":HAZMAT", RefStrings.MODID + ":HAZMAT", 60, new int[] { 1, 4, 5, 2 }, 5, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0.0F);
	public static ArmorMaterial enumArmorMaterialHazmat2 = EnumHelper.addArmorMaterial(RefStrings.MODID + ":HAZMAT2", RefStrings.MODID + ":HAZMAT2", 60, new int[] { 1, 4, 5, 2 }, 5, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0.0F);
	public static ArmorMaterial enumArmorMaterialHazmat3 = EnumHelper.addArmorMaterial(RefStrings.MODID + ":HAZMAT3", RefStrings.MODID + ":HAZMAT3", 60, new int[] { 1, 4, 5, 2 }, 5, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0.0F);
	public static ArmorMaterial enumArmorMaterialPaa = EnumHelper.addArmorMaterial(RefStrings.MODID + ":PAA", RefStrings.MODID + ":PAA", 75, new int[] { 3, 6, 8, 3 }, 25, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 2.0F);
	public static ArmorMaterial enumArmorMaterialSchrabidium = EnumHelper.addArmorMaterial(RefStrings.MODID + ":SCHRABIDIUM", RefStrings.MODID + ":SCHRABIDIUM", 100, new int[] { 3, 6, 8, 3 }, 50, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 2.0F);
	public static ArmorMaterial enumArmorMaterialEuphemium = EnumHelper.addArmorMaterial(RefStrings.MODID + ":EUPHEMIUM", RefStrings.MODID + ":EUPHEMIUM", 15000000, new int[] { 3, 6, 8, 3 }, 100, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 2.0F);
	public static ArmorMaterial enumArmorMaterialSteel = EnumHelper.addArmorMaterial(RefStrings.MODID + ":STEEL", RefStrings.MODID + ":STEEL", 20, new int[] { 2, 5, 6, 2 }, 5, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0.0F);
	public static ArmorMaterial enumArmorMaterialAlloy = EnumHelper.addArmorMaterial(RefStrings.MODID + ":ALLOY", RefStrings.MODID + ":ALLOY", 40, new int[] { 3, 6, 8, 3 }, 12, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0.0F);
	public static ArmorMaterial enumArmorMaterialAusIII = EnumHelper.addArmorMaterial(RefStrings.MODID + ":AUSIII", RefStrings.MODID + ":AUSIII", 375, new int[] {2, 5, 6, 2}, 0, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0.0F);
	public static ArmorMaterial enumArmorMaterialTitanium = EnumHelper.addArmorMaterial(RefStrings.MODID + ":TITANIUM", RefStrings.MODID + ":TITANIUM", 25, new int[] {3, 6, 8, 3}, 9, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 2.0F);
	public static ArmorMaterial enumArmorMaterialCmb = EnumHelper.addArmorMaterial(RefStrings.MODID + ":CMB", RefStrings.MODID + ":CMB", 60, new int[] {3, 6, 8, 3}, 50, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 2.0F);
	public static ArmorMaterial enumArmorMaterialSecurity = EnumHelper.addArmorMaterial(RefStrings.MODID + ":SECURITY", RefStrings.MODID + ":SECURITY", 100, new int[] {3, 6, 8, 3}, 15, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 2.0F);
	public static ArmorMaterial enumArmorMaterialAsbestos = EnumHelper.addArmorMaterial(RefStrings.MODID + ":ASBESTOS", RefStrings.MODID + ":ASBESTOS", 20, new int[] {1, 3, 4, 1}, 5, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0.0F);
	public static ArmorMaterial aMatCobalt = EnumHelper.addArmorMaterial(RefStrings.MODID + ":COBALT", RefStrings.MODID + ":COBALT", 70, new int[] {3, 6, 8, 3}, 25, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 2.0F);
	public static ArmorMaterial aMatStarmetal = EnumHelper.addArmorMaterial(RefStrings.MODID + ":STARMETAL", RefStrings.MODID + ":STARMETAL", 150, new int[] {3, 6, 8, 3}, 100, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 2.0F);
	public static ArmorMaterial aMatLiquidator = EnumHelper.addArmorMaterial(RefStrings.MODID + ":LIQUIDATOR", RefStrings.MODID + ":LIQUIDATOR", 750, new int[] { 3, 6, 8, 3 }, 10, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 2.0F);
	public static ArmorMaterial aMatFau = EnumHelper.addArmorMaterial(RefStrings.MODID + ":DIGAMMA", RefStrings.MODID + ":DIGAMMA", 150, new int[] { 3, 8, 6, 3 }, 100, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 2.0F);
	public static ArmorMaterial aMatDNS = EnumHelper.addArmorMaterial(RefStrings.MODID + ":DNT_NANO", RefStrings.MODID + ":DNT_NANO", 150, new int[] { 3, 8, 6, 3 }, 100, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 2.0F);
	
	
	// Tool Materials
	public static ToolMaterial enumToolMaterialSchrabidium = EnumHelper.addToolMaterial(RefStrings.MODID + ":SCHRABIDIUM", 4, 10000, 50.0F, 100.0F, 200);
	public static ToolMaterial enumToolMaterialHammer = EnumHelper.addToolMaterial(RefStrings.MODID + ":SCHRABIDIUMHAMMER", 3, 0, 50.0F, 999999996F, 200);
	public static ToolMaterial enumToolMaterialChainsaw = EnumHelper.addToolMaterial(RefStrings.MODID + ":CHAINSAW", 3, 1500, 50.0F, 22.0F, 0);
	public static ToolMaterial enumToolMaterialSteel = EnumHelper.addToolMaterial(RefStrings.MODID + ":STEEL", 2, 500, 7.5F, 2.0F, 10);
	public static ToolMaterial enumToolMaterialTitanium = EnumHelper.addToolMaterial(RefStrings.MODID + ":TITANIUM", 2, 750, 9.0F, 2.5F, 15);
	public static ToolMaterial enumToolMaterialAlloy = EnumHelper.addToolMaterial(RefStrings.MODID + ":ALLOY", 3, 2000, 15.0F, 5.0F, 5);
	public static ToolMaterial enumToolMaterialCmb = EnumHelper.addToolMaterial(RefStrings.MODID + ":CMB", 4, 8500, 40.0F, 55F, 100);
	public static ToolMaterial enumToolMaterialElec = EnumHelper.addToolMaterial(RefStrings.MODID + ":ELEC", 2, 0, 30.0F, 12.0F, 2);
	public static ToolMaterial enumToolMaterialDesh = EnumHelper.addToolMaterial(RefStrings.MODID + ":DESH", 2, 0, 7.5F, 2.0F, 10);
	public static ToolMaterial enumToolMaterialCobalt = EnumHelper.addToolMaterial(RefStrings.MODID + ":COBALT", 4, 750, 9.0F, 2.5F, 15);

	public static ToolMaterial enumToolMaterialSaw = EnumHelper.addToolMaterial(RefStrings.MODID + ":SAW", 2, 750, 2.0F, 3.5F, 25);
	public static ToolMaterial enumToolMaterialBat = EnumHelper.addToolMaterial(RefStrings.MODID + ":BAT", 0, 500, 1.5F, 3F, 25);
	public static ToolMaterial enumToolMaterialBatNail = EnumHelper.addToolMaterial(RefStrings.MODID + ":BATNAIL", 0, 450, 1.0F, 4F, 25);
	public static ToolMaterial enumToolMaterialGolfClub = EnumHelper.addToolMaterial(RefStrings.MODID + ":GOLFCLUB", 1, 1000, 2.0F, 5F, 25);
	public static ToolMaterial enumToolMaterialPipeRusty = EnumHelper.addToolMaterial(RefStrings.MODID + ":PIPERUSTY", 1, 350, 1.5F, 4.5F, 25);
	public static ToolMaterial enumToolMaterialPipeLead = EnumHelper.addToolMaterial(RefStrings.MODID + ":PIPELEAD", 1, 250, 1.5F, 5.5F, 25);

	public static ToolMaterial enumToolMaterialBottleOpener = EnumHelper.addToolMaterial(RefStrings.MODID + ":OPENER", 1, 250, 1.5F, 0.5F, 200);
	public static ToolMaterial enumToolMaterialSledge = EnumHelper.addToolMaterial(RefStrings.MODID + ":SHIMMERSLEDGE", 1, 0, 25.0F, 26F, 200);

	public static ToolMaterial enumToolMaterialMultitool = EnumHelper.addToolMaterial(RefStrings.MODID + ":MULTITOOL", 3, 5000, 25F, 5.5F, 25);
	
	public static ToolMaterial matMeteorite = EnumHelper.addToolMaterial("HBM_METEORITE", 4, 0, 50F, 0.0F, 200);
	
	public static ToolMaterial matCrucible = EnumHelper.addToolMaterial("CRUCIBLE", 3, 10000, 50.0F, 100.0F, 200);
	public static ToolMaterial matHS = EnumHelper.addToolMaterial("CRUCIBLE", 3, 10000, 50.0F, 100.0F, 200);
	public static ToolMaterial matHF = EnumHelper.addToolMaterial("CRUCIBLE", 3, 10000, 50.0F, 100.0F, 200);

	Random rand = new Random();

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		if(logger == null)
			logger = event.getModLog();

		if(generalOverride > 0 && generalOverride < 19) {
			polaroidID = generalOverride;
		} else {
			polaroidID = rand.nextInt(18) + 1;
			while(polaroidID == 4 || polaroidID == 9)
				polaroidID = rand.nextInt(18) + 1;
		}
		
		if(SharedMonsterAttributes.MAX_HEALTH.clampValue(Integer.MAX_VALUE) <= 2000){
			try{
				@SuppressWarnings("deprecation")
				Field f = ReflectionHelper.findField(RangedAttribute.class, "maximumValue", "field_111118_b");
				Field modifiersField = Field.class.getDeclaredField("modifiers");
				modifiersField.setAccessible(true);
				modifiersField.setInt(f, f.getModifiers() & ~Modifier.FINAL);
				f.set(SharedMonsterAttributes.MAX_HEALTH, Integer.MAX_VALUE);
			} catch(Throwable e){}
		}
		proxy.checkGLCaps();
		reloadConfig();
		
		OreDictManager.registerGroups();
		OreDictManager oreMan = new OreDictManager();

		MinecraftForge.EVENT_BUS.register(oreMan); //OreRegisterEvent

		MinecraftForge.EVENT_BUS.register(new ModEventHandler());
		MinecraftForge.TERRAIN_GEN_BUS.register(new ModEventHandler());
		MinecraftForge.ORE_GEN_BUS.register(new ModEventHandler());
		
		if(event.getSide() == Side.CLIENT) {
			HbmKeybinds keyHandler = new HbmKeybinds();
			MinecraftForge.EVENT_BUS.register(keyHandler);
		}
		
		PacketDispatcher.registerPackets();

		HbmPotion.init();

		CapabilityManager.INSTANCE.register(HbmLivingCapability.IEntityHbmProps.class, new HbmLivingCapability.EntityHbmPropsStorage(), HbmLivingCapability.EntityHbmProps.FACTORY);
		CapabilityManager.INSTANCE.register(HbmCapability.IHBMData.class, new HbmCapability.HBMDataStorage(), HbmCapability.HBMData.FACTORY);
		ModForgeFluids.init();
		ModItems.preInit();
		ModBlocks.preInit();
		BulletConfigSyncingUtil.loadConfigsForSync();
		CellularDungeonFactory.init();
		Satellite.register();
		HTTPHandler.loadStats();
		AssemblerRecipes.preInit(event.getModConfigurationDirectory());
		MultiblockBBHandler.init();
		ControlEvent.init();
		SiegeTier.registerTiers();
		HazardRegistry.registerItems();
		PotionRecipes.registerPotionRecipes();

		proxy.registerRenderInfo();
		HbmWorld.mainRegistry();
		proxy.preInit(event);
		Library.initSuperusers();
		
		enumArmorMaterialSchrabidium.setRepairItem(new ItemStack(ModItems.ingot_schrabidium));
		enumArmorMaterialHazmat.setRepairItem(new ItemStack(ModItems.hazmat_cloth));
		enumArmorMaterialHazmat2.setRepairItem(new ItemStack(ModItems.hazmat_cloth_red));
		enumArmorMaterialHazmat3.setRepairItem(new ItemStack(ModItems.hazmat_cloth_grey));
		enumArmorMaterialT45.setRepairItem(new ItemStack(ModItems.plate_titanium));
		aMatBJ.setRepairItem(new ItemStack(ModItems.plate_armor_lunar));
		aMatAJR.setRepairItem(new ItemStack(ModItems.plate_armor_ajr));
		aMatHEV.setRepairItem(new ItemStack(ModItems.plate_armor_hev));
		enumArmorMaterialTitanium.setRepairItem(new ItemStack(ModItems.ingot_titanium));
		enumArmorMaterialSteel.setRepairItem(new ItemStack(ModItems.ingot_steel));
		enumArmorMaterialAlloy.setRepairItem(new ItemStack(ModItems.ingot_advanced_alloy));
		enumArmorMaterialPaa.setRepairItem(new ItemStack(ModItems.plate_paa));
		enumArmorMaterialCmb.setRepairItem(new ItemStack(ModItems.ingot_combine_steel));
		enumArmorMaterialAusIII.setRepairItem(new ItemStack(ModItems.ingot_australium));
		enumArmorMaterialSecurity.setRepairItem(new ItemStack(ModItems.plate_kevlar));
		enumToolMaterialSchrabidium.setRepairItem(new ItemStack(ModItems.ingot_schrabidium));
		enumToolMaterialHammer.setRepairItem(new ItemStack(Item.getItemFromBlock(ModBlocks.block_schrabidium)));
		enumToolMaterialChainsaw.setRepairItem(new ItemStack(ModItems.ingot_steel));
		enumToolMaterialTitanium.setRepairItem(new ItemStack(ModItems.ingot_titanium));
		enumToolMaterialSteel.setRepairItem(new ItemStack(ModItems.ingot_steel));
		enumToolMaterialAlloy.setRepairItem(new ItemStack(ModItems.ingot_advanced_alloy));
		enumToolMaterialCmb.setRepairItem(new ItemStack(ModItems.ingot_combine_steel));
		enumToolMaterialBottleOpener.setRepairItem(new ItemStack(ModItems.plate_steel));
		enumToolMaterialDesh.setRepairItem(new ItemStack(ModItems.ingot_desh));
		enumArmorMaterialAsbestos.setRepairItem(new ItemStack(ModItems.asbestos_cloth));
		matMeteorite.setRepairItem(new ItemStack(ModItems.plate_paa));
		aMatLiquidator.setRepairItem(new ItemStack(ModItems.plate_lead));
		aMatFau.setRepairItem(new ItemStack(ModItems.plate_armor_fau));
		aMatDNS.setRepairItem(new ItemStack(ModItems.plate_armor_dnt));
		
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
		GameRegistry.registerTileEntity(TileEntityDummy.class, new ResourceLocation(RefStrings.MODID, "tileentity_dummy"));
		GameRegistry.registerTileEntity(TileEntityMachineAssembler.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_assembler"));
		GameRegistry.registerTileEntity(TileEntityDiFurnace.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_difurnace"));
		GameRegistry.registerTileEntity(TileEntityDiFurnaceRTG.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_difurnace_rtg"));
		GameRegistry.registerTileEntity(TileEntityMachinePress.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_press"));
		GameRegistry.registerTileEntity(TileEntityTestRender.class, new ResourceLocation(RefStrings.MODID, "tileentity_testrenderer"));
		GameRegistry.registerTileEntity(TileEntityMachineChemplant.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_chemplant"));
		GameRegistry.registerTileEntity(TileEntityMachineChemfac.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_chemfac"));
		GameRegistry.registerTileEntity(TileEntityMachineMixer.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_mixer"));
		GameRegistry.registerTileEntity(TileEntityDummyPort.class, new ResourceLocation(RefStrings.MODID, "tileentity_dummy_port"));
		GameRegistry.registerTileEntity(TileEntityNukeMan.class, new ResourceLocation(RefStrings.MODID, "tileentity_nuke_man"));
		GameRegistry.registerTileEntity(TileEntityNukeFleija.class, new ResourceLocation(RefStrings.MODID, "tileentity_nuke_fleija"));
		GameRegistry.registerTileEntity(TileEntityMachineCoal.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_coal"));
		GameRegistry.registerTileEntity(TileEntityMachineGenerator.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_generator"));
		GameRegistry.registerTileEntity(TileEntityMachineReactorSmall.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_reactor_small"));
		GameRegistry.registerTileEntity(TileEntityMachineRTG.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_rtg_grey"));
		GameRegistry.registerTileEntity(TileEntityCableBaseNT.class, new ResourceLocation(RefStrings.MODID, "tileentity_cable"));
		GameRegistry.registerTileEntity(TileEntityDiode.class, new ResourceLocation(RefStrings.MODID, "tileentity_cable_diode"));
		GameRegistry.registerTileEntity(TileEntityCableGauge.class, new ResourceLocation(RefStrings.MODID, "tileentity_cable_gauge"));
		GameRegistry.registerTileEntity(TileEntityBedrockOre.class, new ResourceLocation(RefStrings.MODID, "tileentity_ore_bedrock"));
		GameRegistry.registerTileEntity(TileEntityMachineBattery.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_battery"));
		GameRegistry.registerTileEntity(TileEntityMachineTransformer.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_transformer"));
		GameRegistry.registerTileEntity(TileEntityConverterHeRf.class, new ResourceLocation(RefStrings.MODID, "tileentity_converter_he_rf"));
		GameRegistry.registerTileEntity(TileEntityConverterRfHe.class, new ResourceLocation(RefStrings.MODID, "tileentity_converter_rf_he"));
		GameRegistry.registerTileEntity(TileEntityMachineTurbine.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_turbine"));
		GameRegistry.registerTileEntity(TileEntityTurretSpitfire.class, new ResourceLocation(RefStrings.MODID, "tileentity_turret_spitfire"));
		GameRegistry.registerTileEntity(TileEntityTurretCIWS.class, new ResourceLocation(RefStrings.MODID, "tileentity_turret_ciws"));
		GameRegistry.registerTileEntity(TileEntityTurretCheapo.class, new ResourceLocation(RefStrings.MODID, "tileentity_turret_cheapo"));
		GameRegistry.registerTileEntity(TileEntityTurretRocket.class, new ResourceLocation(RefStrings.MODID, "tileentity_turret_rocket"));
		GameRegistry.registerTileEntity(TileEntityTurretLight.class, new ResourceLocation(RefStrings.MODID, "tileentity_turret_light"));
		GameRegistry.registerTileEntity(TileEntityTurretHeavy.class, new ResourceLocation(RefStrings.MODID, "tileentity_turret_heavy"));
		GameRegistry.registerTileEntity(TileEntityTurretFlamer.class, new ResourceLocation(RefStrings.MODID, "tileentity_turret_flamer"));
		GameRegistry.registerTileEntity(TileEntityDecoBlock.class, new ResourceLocation(RefStrings.MODID, "tileentity_deco_block"));
		GameRegistry.registerTileEntity(TileEntityLaunchPad.class, new ResourceLocation(RefStrings.MODID, "tileentity_launch_pad"));
		GameRegistry.registerTileEntity(TileEntityMachineBoiler.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_boiler"));
		GameRegistry.registerTileEntity(TileEntityHeatBoiler.class, new ResourceLocation(RefStrings.MODID, "tileentity_heat_boiler"));
		GameRegistry.registerTileEntity(TileEntityMachineBoilerElectric.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_boiler_electric"));
		GameRegistry.registerTileEntity(TileEntityMachineBoilerRTG.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_boiler_rtg"));
		GameRegistry.registerTileEntity(TileEntityMachineEPress.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_epress"));
		GameRegistry.registerTileEntity(TileEntityPylon.class, new ResourceLocation(RefStrings.MODID, "tileentity_pylon_red_wire"));
		GameRegistry.registerTileEntity(TileEntityPylonLarge.class, new ResourceLocation(RefStrings.MODID, "tileentity_pylon_large"));
		GameRegistry.registerTileEntity(TileEntitySubstation.class, new ResourceLocation(RefStrings.MODID, "tileentity_pylon_substation"));
		GameRegistry.registerTileEntity(TileEntityMachineCentrifuge.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_centrifuge"));
		GameRegistry.registerTileEntity(TileEntityMachineGasCent.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_gascent"));
		GameRegistry.registerTileEntity(TileEntityMachineUF6Tank.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_uf6_tank"));
		GameRegistry.registerTileEntity(TileEntityMachinePuF6Tank.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_puf6_tank"));
		GameRegistry.registerTileEntity(TileEntityRailgun.class, new ResourceLocation(RefStrings.MODID, "tileentity_railgun"));
		GameRegistry.registerTileEntity(TileEntityMachineReactor.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_reactor"));
		GameRegistry.registerTileEntity(TileEntityMachineShredder.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_shredder"));
		GameRegistry.registerTileEntity(TileEntityDummyFluidPort.class, new ResourceLocation(RefStrings.MODID, "tileentity_dummy_fluid_port"));
		GameRegistry.registerTileEntity(TileEntityMachineFluidTank.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_fluidtank"));
		GameRegistry.registerTileEntity(TileEntityCableSwitch.class, new ResourceLocation(RefStrings.MODID, "tileentity_cable_switch"));
		GameRegistry.registerTileEntity(TileEntityMachineRefinery.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_refinery"));
		GameRegistry.registerTileEntity(TileEntityMachineCyclotron.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_cyclotron"));
		GameRegistry.registerTileEntity(TileEntityMachineSchrabidiumTransmutator.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_schrabidium_transmutator"));
		GameRegistry.registerTileEntity(TileEntityMachineSiren.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_siren"));
		GameRegistry.registerTileEntity(TileEntityBroadcaster.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_broadcaster"));
		GameRegistry.registerTileEntity(TileEntityGeiger.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_geiger"));
		GameRegistry.registerTileEntity(TileEntityHatch.class, new ResourceLocation(RefStrings.MODID, "tileentity_hatch"));
		GameRegistry.registerTileEntity(TileEntityVaultDoor.class, new ResourceLocation(RefStrings.MODID, "tileentity_vault_door"));
		GameRegistry.registerTileEntity(TileEntityBlastDoor.class, new ResourceLocation(RefStrings.MODID, "tileentity_blastdoor"));
		GameRegistry.registerTileEntity(TileEntityCrateIron.class, new ResourceLocation(RefStrings.MODID, "tileentity_crate_iron"));
		GameRegistry.registerTileEntity(TileEntityCrateSteel.class, new ResourceLocation(RefStrings.MODID, "tileentity_crate_steel"));
		GameRegistry.registerTileEntity(TileEntityCrateDesh.class, new ResourceLocation(RefStrings.MODID, "tileentity_crate_desh"));
		GameRegistry.registerTileEntity(TileEntitySafe.class, new ResourceLocation(RefStrings.MODID, "tileentity_safe"));
		GameRegistry.registerTileEntity(TileEntityMachineKeyForge.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_key_forge"));
		GameRegistry.registerTileEntity(TileEntityNukeFurnace.class, new ResourceLocation(RefStrings.MODID, "tileentity_nuke_furnace"));
		GameRegistry.registerTileEntity(TileEntityRtgFurnace.class, new ResourceLocation(RefStrings.MODID, "tileentity_rtg_furnace"));
		GameRegistry.registerTileEntity(TileEntityMachineSeleniumEngine.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_selenium"));
		GameRegistry.registerTileEntity(TileEntityReactorControl.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_reactor_control"));
		GameRegistry.registerTileEntity(TileEntityMachineRadGen.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_radgen"));
		GameRegistry.registerTileEntity(TileEntityRadSensor.class, new ResourceLocation(RefStrings.MODID, "tileentity_radsensor"));
		GameRegistry.registerTileEntity(TileEntityMachineAmgen.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_amgen"));
		GameRegistry.registerTileEntity(TileEntityMachineSPP.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_spp"));
		GameRegistry.registerTileEntity(TileEntityMachineArcFurnace.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_arc_furnace"));
		GameRegistry.registerTileEntity(TileEntityMachineElectricFurnace.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_electric_furnace"));
		GameRegistry.registerTileEntity(TileEntityWasteDrum.class, new ResourceLocation(RefStrings.MODID, "tileentity_waste_drum"));
		GameRegistry.registerTileEntity(TileEntityMachineOilWell.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_oil_well"));
		GameRegistry.registerTileEntity(TileEntityMachinePumpjack.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_pumpjack"));
		GameRegistry.registerTileEntity(TileEntityMachineFrackingTower.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_fracking_tower"));
		GameRegistry.registerTileEntity(TileEntityMachineCatalyticCracker.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_catalytic_cracker"));
		GameRegistry.registerTileEntity(TileEntityMachineGasFlare.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_gas_flare"));
		GameRegistry.registerTileEntity(TileEntityMachineExcavator.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_excavator"));
		GameRegistry.registerTileEntity(TileEntityMachineTurbofan.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_turbofan"));
		GameRegistry.registerTileEntity(TileEntityMachineCMBFactory.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_cmb_factory"));
		GameRegistry.registerTileEntity(TileEntityMachineTeleporter.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_teleporter"));
		GameRegistry.registerTileEntity(TileEntityRadiobox.class, new ResourceLocation(RefStrings.MODID, "tileentity_radiobox"));
		GameRegistry.registerTileEntity(TileEntityRadioRec.class, new ResourceLocation(RefStrings.MODID, "tileentity_radiorec"));
		GameRegistry.registerTileEntity(TileEntityDeconRad.class, new ResourceLocation(RefStrings.MODID, "tileentity_deconrad"));
		GameRegistry.registerTileEntity(TileEntityDeconDi.class, new ResourceLocation(RefStrings.MODID, "tileentity_decondi"));
		GameRegistry.registerTileEntity(TileEntityCharger.class, new ResourceLocation(RefStrings.MODID, "tileentity_charger"));
		GameRegistry.registerTileEntity(TileEntityVent.class, new ResourceLocation(RefStrings.MODID, "tileentity_vent"));
		GameRegistry.registerTileEntity(TileEntityChlorineSeal.class, new ResourceLocation(RefStrings.MODID, "tileentity_chlorine_seal"));
		GameRegistry.registerTileEntity(TileEntityStructureMarker.class, new ResourceLocation(RefStrings.MODID, "tileentity_structure_marker"));
		GameRegistry.registerTileEntity(TileEntityCoreTitanium.class, new ResourceLocation(RefStrings.MODID, "tileentity_core_titanium"));
		GameRegistry.registerTileEntity(TileEntityCoreAdvanced.class, new ResourceLocation(RefStrings.MODID, "tileentity_core_advanced"));
		GameRegistry.registerTileEntity(TileEntityReactorHatch.class, new ResourceLocation(RefStrings.MODID, "tileentity_reactor_hatch"));
		GameRegistry.registerTileEntity(TileEntityFactoryHatch.class, new ResourceLocation(RefStrings.MODID, "tileentity_factory_hatch"));
		GameRegistry.registerTileEntity(TileEntityMachineReactorLarge.class, new ResourceLocation(RefStrings.MODID, "tileentity_reactor_large"));
		GameRegistry.registerTileEntity(TileEntityWatzHatch.class, new ResourceLocation(RefStrings.MODID, "tileentity_watz_hatch"));
		GameRegistry.registerTileEntity(TileEntityWatzCore.class, new ResourceLocation(RefStrings.MODID, "tileentity_watz_core"));
		GameRegistry.registerTileEntity(TileEntityFWatzHatch.class, new ResourceLocation(RefStrings.MODID, "tileentity_fwatz_hatch"));
		GameRegistry.registerTileEntity(TileEntityFWatzCore.class, new ResourceLocation(RefStrings.MODID, "tileentity_fwatz_core"));
		GameRegistry.registerTileEntity(TileEntityNukeGadget.class, new ResourceLocation(RefStrings.MODID, "tileentity_nuke_gadget"));
		GameRegistry.registerTileEntity(TileEntityNukeBoy.class, new ResourceLocation(RefStrings.MODID, "tileentity_nuke_boy"));
		GameRegistry.registerTileEntity(TileEntityNukeMike.class, new ResourceLocation(RefStrings.MODID, "tileentity_nuke_mike"));
		GameRegistry.registerTileEntity(TileEntityNukeTsar.class, new ResourceLocation(RefStrings.MODID, "tileentity_nuke_tsar"));
		GameRegistry.registerTileEntity(TileEntityNukePrototype.class, new ResourceLocation(RefStrings.MODID, "tileentity_nuke_prototype"));
		GameRegistry.registerTileEntity(TileEntityNukeSolinium.class, new ResourceLocation(RefStrings.MODID, "tileentity_nuke_solinium"));
		GameRegistry.registerTileEntity(TileEntityNukeN2.class, new ResourceLocation(RefStrings.MODID, "tileentity_nuke_n2"));
		GameRegistry.registerTileEntity(TileEntityNukeCustom.class, new ResourceLocation(RefStrings.MODID, "tileentity_nuke_custom"));
		GameRegistry.registerTileEntity(TileEntityBombMulti.class, new ResourceLocation(RefStrings.MODID, "tileentity_bomb_multi"));
		GameRegistry.registerTileEntity(TileEntityCrashedBomb.class, new ResourceLocation(RefStrings.MODID, "tileentity_crashed_bomb"));
		GameRegistry.registerTileEntity(TileEntityLandmine.class, new ResourceLocation(RefStrings.MODID, "tileentity_landmine"));
		GameRegistry.registerTileEntity(TileEntityMachineTeleLinker.class, new ResourceLocation(RefStrings.MODID, "tileentity_telelinker"));
		GameRegistry.registerTileEntity(TileEntityMachineMissileAssembly.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_missile_assembly"));
		GameRegistry.registerTileEntity(TileEntityCompactLauncher.class, new ResourceLocation(RefStrings.MODID, "tileentity_compact_launcher"));
		GameRegistry.registerTileEntity(TileEntityMultiblock.class, new ResourceLocation(RefStrings.MODID, "tileentity_launcher_multiblock"));
		GameRegistry.registerTileEntity(TileEntityLaunchTable.class, new ResourceLocation(RefStrings.MODID, "tileentity_launch_table"));
		GameRegistry.registerTileEntity(TileEntityAMSEmitter.class, new ResourceLocation(RefStrings.MODID, "tileentity_ams_emitter"));
		GameRegistry.registerTileEntity(TileEntityAMSLimiter.class, new ResourceLocation(RefStrings.MODID, "tileentity_ams_limiter"));
		GameRegistry.registerTileEntity(TileEntityAMSBase.class, new ResourceLocation(RefStrings.MODID, "tileentity_ams_base"));
		GameRegistry.registerTileEntity(TileEntityMachineSatLinker.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_sat_linker"));
		GameRegistry.registerTileEntity(TileEntityMachineSatDock.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_sat_dock"));
		GameRegistry.registerTileEntity(TileEntityMachineDiesel.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_diesel"));
		GameRegistry.registerTileEntity(TileEntityForceField.class, new ResourceLocation(RefStrings.MODID, "tileentity_force_field"));
		GameRegistry.registerTileEntity(TileEntityMachineRadar.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_radar"));
		GameRegistry.registerTileEntity(TileEntityDecoPoleSatelliteReceiver.class, new ResourceLocation(RefStrings.MODID, "tileentity_deco_pole_satellite_receiver"));
		GameRegistry.registerTileEntity(TileEntityDeuteriumExtractor.class, new ResourceLocation(RefStrings.MODID, "tileentity_deuterium_extractor"));
		GameRegistry.registerTileEntity(TileEntityDeuteriumTower.class, new ResourceLocation(RefStrings.MODID, "tileentity_deuterium_tower"));
		GameRegistry.registerTileEntity(TileEntityGeysir.class, new ResourceLocation(RefStrings.MODID, "tileentity_geyser"));
		GameRegistry.registerTileEntity(TileEntityObjTester.class, new ResourceLocation(RefStrings.MODID, "tileentity_obj_tester"));
		GameRegistry.registerTileEntity(TileEntityDecoBlockAlt.class, new ResourceLocation(RefStrings.MODID, "tileentity_deco_block_alt"));
		GameRegistry.registerTileEntity(TileEntityFFFluidDuctMk2.class, new ResourceLocation(RefStrings.MODID, "tileentity_ff_fludi_duct_mk2"));
		GameRegistry.registerTileEntity(TileEntityFFFluidDuctMk2Solid.class, new ResourceLocation(RefStrings.MODID, "tileentity_ff_fludi_duct_mk2_solid"));
		GameRegistry.registerTileEntity(TileEntityBarrel.class, new ResourceLocation(RefStrings.MODID, "tileentity_barrel"));
		GameRegistry.registerTileEntity(TileEntityTesla.class, new ResourceLocation(RefStrings.MODID, "tileentity_tesla"));
		GameRegistry.registerTileEntity(TileEntityCyberCrab.class, new ResourceLocation(RefStrings.MODID, "tileentity_cybercrab"));
		GameRegistry.registerTileEntity(TileEntityCoreEmitter.class, new ResourceLocation(RefStrings.MODID, "tileentity_core_emitter"));
		GameRegistry.registerTileEntity(TileEntityCoreReceiver.class, new ResourceLocation(RefStrings.MODID, "tileentity_core_receiver"));
		GameRegistry.registerTileEntity(TileEntityCoreInjector.class, new ResourceLocation(RefStrings.MODID, "tileentity_core_injector"));
		GameRegistry.registerTileEntity(TileEntityCoreStabilizer.class, new ResourceLocation(RefStrings.MODID, "tileentity_core_stabilizer"));
		GameRegistry.registerTileEntity(TileEntityCore.class, new ResourceLocation(RefStrings.MODID, "tileentity_core_core"));
		GameRegistry.registerTileEntity(TileEntitySoyuzCapsule.class, new ResourceLocation(RefStrings.MODID, "tileentity_soyuz_capsule"));
		GameRegistry.registerTileEntity(TileEntitySoyuzLauncher.class, new ResourceLocation(RefStrings.MODID, "tileentity_soyuz_launcher"));
		GameRegistry.registerTileEntity(TileEntityFFFluidSuccMk2.class, new ResourceLocation(RefStrings.MODID, "tileentity_ff_succ_mk2"));
		GameRegistry.registerTileEntity(TileEntityFFFluidSuccMk2Solid.class, new ResourceLocation(RefStrings.MODID, "tileentity_ff_succ_mk2_solid"));
		GameRegistry.registerTileEntity(TileEntityMachineCrystallizer.class, new ResourceLocation(RefStrings.MODID, "tileentity_acidomatic"));
		GameRegistry.registerTileEntity(TileEntitySoyuzStruct.class, new ResourceLocation(RefStrings.MODID, "tileentity_soyuz_struct"));
		GameRegistry.registerTileEntity(TileEntityITERStruct.class, new ResourceLocation(RefStrings.MODID, "tileentity_iter_struct"));
		GameRegistry.registerTileEntity(TileEntityMachineMiningLaser.class, new ResourceLocation(RefStrings.MODID, "tileentity_mining_laser"));
		GameRegistry.registerTileEntity(TileEntityProxyInventory.class, new ResourceLocation(RefStrings.MODID, "tileentity_proxy_inventory"));
		GameRegistry.registerTileEntity(TileEntityProxyEnergy.class, new ResourceLocation(RefStrings.MODID, "tileentity_proxy_power"));
		GameRegistry.registerTileEntity(TileEntityNukeBalefire.class, new ResourceLocation(RefStrings.MODID, "tileentity_nuke_fstbmb"));
		GameRegistry.registerTileEntity(TileEntityProxyCombo.class, new ResourceLocation(RefStrings.MODID, "tileentity_proxy_combo"));
		GameRegistry.registerTileEntity(TileEntityProxyConductor.class, new ResourceLocation(RefStrings.MODID, "tileentity_proxy_conductor"));
		GameRegistry.registerTileEntity(TileEntityMicrowave.class, new ResourceLocation(RefStrings.MODID, "tileentity_microwave"));
		GameRegistry.registerTileEntity(TileEntityMachineUUCreator.class, new ResourceLocation(RefStrings.MODID, "tileentity_uu_creator"));
		GameRegistry.registerTileEntity(TileEntityMachineMiniRTG.class, new ResourceLocation(RefStrings.MODID, "tileentity_mini_rtg"));
		GameRegistry.registerTileEntity(TileEntityITER.class, new ResourceLocation(RefStrings.MODID, "tileentity_iter"));
		GameRegistry.registerTileEntity(TileEntityMachinePlasmaHeater.class, new ResourceLocation(RefStrings.MODID, "tileentity_plasma_heater"));
		GameRegistry.registerTileEntity(TileEntityMachineFENSU.class, new ResourceLocation(RefStrings.MODID, "tileentity_fensu"));
		GameRegistry.registerTileEntity(TileEntityTrappedBrick.class, new ResourceLocation(RefStrings.MODID, "tileentity_trapped_brick"));
		GameRegistry.registerTileEntity(TileEntityPlasmaStruct.class, new ResourceLocation(RefStrings.MODID, "tileentity_plasma_struct"));
		GameRegistry.registerTileEntity(TileEntityMachineLargeTurbine.class, new ResourceLocation(RefStrings.MODID, "tileentity_industrial_turbine"));
		GameRegistry.registerTileEntity(TileEntitySlidingBlastDoor.class, new ResourceLocation(RefStrings.MODID, "tileentity_sliding_blast_door"));
		GameRegistry.registerTileEntity(TileEntityKeypadBase.class, new ResourceLocation(RefStrings.MODID, "tileentity_keypad_base"));
		GameRegistry.registerTileEntity(TileEntitySlidingBlastDoorKeypad.class, new ResourceLocation(RefStrings.MODID, "tileentity_keypad_door"));
		GameRegistry.registerTileEntity(TileEntityBlackBook.class, new ResourceLocation(RefStrings.MODID, "tileentity_book_crafting"));
		GameRegistry.registerTileEntity(TileEntityHadronDiode.class, new ResourceLocation(RefStrings.MODID, "tileentity_hadron_diode"));
		GameRegistry.registerTileEntity(TileEntityHadronPower.class, new ResourceLocation(RefStrings.MODID, "tileentity_hadron_power"));
		GameRegistry.registerTileEntity(TileEntityHadron.class, new ResourceLocation(RefStrings.MODID, "tileentity_hadron"));
		GameRegistry.registerTileEntity(TileEntitySolarBoiler.class, new ResourceLocation(RefStrings.MODID, "tileentity_solarboiler"));
		GameRegistry.registerTileEntity(TileEntitySolarMirror.class, new ResourceLocation(RefStrings.MODID, "tileentity_solarmirror"));
		GameRegistry.registerTileEntity(TileEntityMachineDetector.class, new ResourceLocation(RefStrings.MODID, "tileentity_he_detector"));
		GameRegistry.registerTileEntity(TileEntityFireworks.class, new ResourceLocation(RefStrings.MODID, "tileentity_firework_box"));
		GameRegistry.registerTileEntity(TileEntityMachineIGenerator.class, new ResourceLocation(RefStrings.MODID, "tileentity_igenerator"));
		GameRegistry.registerTileEntity(TileEntitySiloHatch.class, new ResourceLocation(RefStrings.MODID, "tileentity_silo_hatch"));
		GameRegistry.registerTileEntity(TileEntitySpinnyLight.class, new ResourceLocation(RefStrings.MODID, "tileentity_spinny_light"));
		GameRegistry.registerTileEntity(TileEntityControlPanel.class, new ResourceLocation(RefStrings.MODID, "tileentity_control_panel"));
		GameRegistry.registerTileEntity(TileEntityCrateTungsten.class, new ResourceLocation(RefStrings.MODID, "tileentity_crate_tungsten"));
		GameRegistry.registerTileEntity(TileEntityDemonLamp.class, new ResourceLocation(RefStrings.MODID, "tileentity_demon_lamp"));
		GameRegistry.registerTileEntity(TileEntityTurretChekhov.class, new ResourceLocation(RefStrings.MODID, "tileentity_turret_chekhov"));
		GameRegistry.registerTileEntity(TileEntityTurretJeremy.class, new ResourceLocation(RefStrings.MODID, "tileentity_turret_jeremy"));
		GameRegistry.registerTileEntity(TileEntityTurretTau.class, new ResourceLocation(RefStrings.MODID, "tileentity_turret_tau"));
		GameRegistry.registerTileEntity(TileEntityTurretTauon.class, new ResourceLocation(RefStrings.MODID, "tileentity_turret_tauon"));
		GameRegistry.registerTileEntity(TileEntityTurretFriendly.class, new ResourceLocation(RefStrings.MODID, "tileentity_turret_friendly"));
		GameRegistry.registerTileEntity(TileEntityTurretRichard.class, new ResourceLocation(RefStrings.MODID, "tileentity_turret_richard"));
		GameRegistry.registerTileEntity(TileEntityTurretHoward.class, new ResourceLocation(RefStrings.MODID, "tileentity_turret_howard"));
		GameRegistry.registerTileEntity(TileEntityTurretHowardDamaged.class, new ResourceLocation(RefStrings.MODID, "tileentity_turret_howard_damaged"));
		GameRegistry.registerTileEntity(TileEntityTurretMaxwell.class, new ResourceLocation(RefStrings.MODID, "tileentity_turret_maxwell"));
		GameRegistry.registerTileEntity(TileEntityTurretFritz.class, new ResourceLocation(RefStrings.MODID, "tileentity_turret_fritz"));
		GameRegistry.registerTileEntity(TileEntityTurretBrandon.class, new ResourceLocation(RefStrings.MODID, "tileentity_turret_brandon"));
		GameRegistry.registerTileEntity(TileEntityRBMKRod.class, new ResourceLocation(RefStrings.MODID, "tileentity_rbmk_rod"));
		GameRegistry.registerTileEntity(TileEntityRBMKRodReaSim.class, new ResourceLocation(RefStrings.MODID, "tileentity_rbmk_rod_reasim"));
		GameRegistry.registerTileEntity(TileEntityRBMKControlManual.class, new ResourceLocation(RefStrings.MODID, "tileentity_rbmk_control"));
		GameRegistry.registerTileEntity(TileEntityRBMKControlAuto.class, new ResourceLocation(RefStrings.MODID, "tileentity_rbmk_control_auto"));
		GameRegistry.registerTileEntity(TileEntityRBMKBlank.class, new ResourceLocation(RefStrings.MODID, "tileentity_rbmk_blank"));
		GameRegistry.registerTileEntity(TileEntityRBMKBoiler.class, new ResourceLocation(RefStrings.MODID, "tileentity_rbmk_boiler"));
		GameRegistry.registerTileEntity(TileEntityRBMKReflector.class, new ResourceLocation(RefStrings.MODID, "tileentity_rbmk_reflector"));
		GameRegistry.registerTileEntity(TileEntityRBMKAbsorber.class, new ResourceLocation(RefStrings.MODID, "tileentity_rbmk_absorber"));
		GameRegistry.registerTileEntity(TileEntityRBMKModerator.class, new ResourceLocation(RefStrings.MODID, "tileentity_rbmk_moderator"));
		GameRegistry.registerTileEntity(TileEntityRBMKOutgasser.class, new ResourceLocation(RefStrings.MODID, "tileentity_rbmk_outgasser"));
		GameRegistry.registerTileEntity(TileEntityRBMKStorage.class, new ResourceLocation(RefStrings.MODID, "tileentity_rbmk_storage"));
		GameRegistry.registerTileEntity(TileEntityRBMKConsole.class, new ResourceLocation(RefStrings.MODID, "tileentity_rbmk_console"));
		GameRegistry.registerTileEntity(TileEntityRBMKCooler.class, new ResourceLocation(RefStrings.MODID, "tileentity_rbmk_cooler"));
		GameRegistry.registerTileEntity(TileEntityRBMKHeater.class, new ResourceLocation(RefStrings.MODID, "tileentity_rbmk_heater"));
		GameRegistry.registerTileEntity(TileEntityRBMKCraneConsole.class, new ResourceLocation(RefStrings.MODID, "tileentity_rbmk_crane_console"));
		GameRegistry.registerTileEntity(TileEntityRBMKInlet.class, new ResourceLocation(RefStrings.MODID, "tileentity_rbmk_inlet"));
		GameRegistry.registerTileEntity(TileEntityRBMKOutlet.class, new ResourceLocation(RefStrings.MODID, "tileentity_rbmk_outlet"));
		GameRegistry.registerTileEntity(TileEntityStorageDrum.class, new ResourceLocation(RefStrings.MODID, "tileentity_storage_drum"));
		GameRegistry.registerTileEntity(TileEntityPileFuel.class, new ResourceLocation(RefStrings.MODID, "tileentity_pile_fuel"));
		GameRegistry.registerTileEntity(TileEntityPileSource.class, new ResourceLocation(RefStrings.MODID, "tileentity_pile_source"));
		GameRegistry.registerTileEntity(TileEntityMachineBAT9000.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_bat9000"));
		GameRegistry.registerTileEntity(TileEntityMachineOrbus.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_orbus"));
		GameRegistry.registerTileEntity(TileEntityCondenser.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_condenser"));
		GameRegistry.registerTileEntity(TileEntityChungus.class, new ResourceLocation(RefStrings.MODID, "tileentity_chungus"));
		GameRegistry.registerTileEntity(TileEntitySpacer.class, new ResourceLocation(RefStrings.MODID, "tileentity_spacer"));
		GameRegistry.registerTileEntity(TileEntityMachineFractionTower.class, new ResourceLocation(RefStrings.MODID, "tileentity_machine_frac_tower"));
		GameRegistry.registerTileEntity(TileEntityTowerSmall.class, new ResourceLocation(RefStrings.MODID, "tileentity_tower_small"));
		GameRegistry.registerTileEntity(TileEntityTowerLarge.class, new ResourceLocation(RefStrings.MODID, "tileentity_tower_large"));
		GameRegistry.registerTileEntity(TileEntitySILEX.class, new ResourceLocation(RefStrings.MODID, "tileentity_silex"));
		GameRegistry.registerTileEntity(TileEntityFEL.class, new ResourceLocation(RefStrings.MODID, "tileentity_fel"));
		GameRegistry.registerTileEntity(TileEntityHeaterFirebox.class, new ResourceLocation(RefStrings.MODID, "tileentity_heater_firebox"));
		GameRegistry.registerTileEntity(TileEntityHeaterOven.class, new ResourceLocation(RefStrings.MODID, "tileentity_heater_oven"));
		GameRegistry.registerTileEntity(TileEntityHeaterOilburner.class, new ResourceLocation(RefStrings.MODID, "tileentity_heater_oilburner"));
		GameRegistry.registerTileEntity(TileEntityHeaterElectric.class, new ResourceLocation(RefStrings.MODID, "tileentity_heater_electric"));
		GameRegistry.registerTileEntity(TileEntityHeaterHeatex.class, new ResourceLocation(RefStrings.MODID, "tileentity_heater_heatex"));
		GameRegistry.registerTileEntity(TileEntityHeaterRadioThermal.class, new ResourceLocation(RefStrings.MODID, "tileentity_heater_rt"));
		GameRegistry.registerTileEntity(TileEntityFurnaceIron.class, new ResourceLocation(RefStrings.MODID, "tileentity_furnace_iron"));
		GameRegistry.registerTileEntity(TileEntityFurnaceSteel.class, new ResourceLocation(RefStrings.MODID, "tileentity_furnace_steel"));
		GameRegistry.registerTileEntity(TileEntityDoorGeneric.class, new ResourceLocation(RefStrings.MODID, "tileentity_door_generic"));
		GameRegistry.registerTileEntity(TileEntityBMPowerBox.class, new ResourceLocation(RefStrings.MODID, "tileentity_bm_power_box"));
		GameRegistry.registerTileEntity(TileEntityRadioTorchSender.class, new ResourceLocation(RefStrings.MODID, "tileentity_radio_torch_sender"));
		GameRegistry.registerTileEntity(TileEntityRadioTorchReceiver.class, new ResourceLocation(RefStrings.MODID, "tileentity_radio_torch_receiver"));
		GameRegistry.registerTileEntity(TileEntityCraneExtractor.class, new ResourceLocation(RefStrings.MODID, "tileentity_craneejector"));
		GameRegistry.registerTileEntity(TileEntityCraneInserter.class, new ResourceLocation(RefStrings.MODID, "tileentity_craneinserter"));
		GameRegistry.registerTileEntity(TileEntityCraneSplitter.class, new ResourceLocation(RefStrings.MODID, "tileentity_cranesplitter"));
		GameRegistry.registerTileEntity(TileEntityCraneBoxer.class, new ResourceLocation(RefStrings.MODID, "tileentity_craneboxer"));
		GameRegistry.registerTileEntity(TileEntityCraneUnboxer.class, new ResourceLocation(RefStrings.MODID, "tileentity_craneunboxer"));
		GameRegistry.registerTileEntity(TileEntityCraneRouter.class, new ResourceLocation(RefStrings.MODID, "tileentity_cranerouter"));
		GameRegistry.registerTileEntity(TileEntityCraneGrabber.class, new ResourceLocation(RefStrings.MODID, "tileentity_cranegrabber"));
		
		int i = 0;
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_nuke_mk5"), EntityNukeExplosionMK5.class, "entity_nuke_mk5", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_d_smoke_fx"), EntityDSmokeFX.class, "entity_d_smoke_fx", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_fallout_rain"), EntityFalloutRain.class, "entity_fallout_rain", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_effect_torex"), EntityNukeTorex.class, "entity_effect_torex", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_smoke_fx"), EntitySmokeFX.class, "entity_smoke_fx", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_b_smoke_fx"), EntityBSmokeFX.class, "entity_b_smoke_fx", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_shrapnel"), EntityShrapnel.class, "enity_shrapnel", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_s_smoke_fx"), EntitySSmokeFX.class, "entity_s_smoke_fx", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_rubble"), EntityRubble.class, "entity_rubble", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_burning_foeq"), EntityBurningFOEQ.class, "entity_burning_foeq", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_nuke_mk3"), EntityNukeExplosionMK3.class, "entity_nuke_mk3", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_fleija_rainbow"), EntityCloudFleijaRainbow.class, "entity_fleija_rainbow", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_explosive_beam"), EntityExplosiveBeam.class, "entity_explosive_beam", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_tainted_creeper"), EntityTaintedCreeper.class, "entity_tainted_creeper", i++, MainRegistry.instance, 80, 3, true, 0x009CCA, 0x00F761);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_nuclear_creeper"), EntityNuclearCreeper.class, "entity_nuclear_creeper", i++, MainRegistry.instance, 80, 3, true, 0x3D3D3D, 0xCECECE);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_glowing_one"), EntityGlowingOne.class, "entity_glowing_one", i++, MainRegistry.instance, 1000, 1, true, 0x357C2E, 0x4CFF00);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_ntm_radiation_blaze"), EntityRADBeast.class, "entity_ntm_radiation_blaze", i++, MainRegistry.instance, 1000, 1, true, 0x303030, 0x27F000);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_cloud_fleija"), EntityCloudFleija.class, "entity_cloud_fleija", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_bullet"), EntityBullet.class, "entity_bullet", i++, MainRegistry.instance, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_gasflame_fx"), EntityGasFlameFX.class, "entity_gasflame_fx", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_rocket"), EntityRocket.class, "entity_rocket", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_fire"), EntityFire.class, "entity_fire", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_aa_shell"), EntityAAShell.class, "entity_aa_shell", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_bomber"), EntityBomber.class, "entity_bomber", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_agent_orange"), EntityOrangeFX.class, "entity_agent_orange", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_pink_cloud_fx"), EntityPinkCloudFX.class, "entity_pink_cloud_fx", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_cloud_fx"), EntityCloudFX.class, "entity_cloud_fx", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_chlorine_fx"), EntityChlorineFX.class, "entity_chlorine_fx", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_stinger"), EntityRocketHoming.class, "entity_stinger", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_t_smoke_fx"), EntityTSmokeFX.class, "entity_t_smoke_fx", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_boxcar"), EntityBoxcar.class, "entity_boxcar", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_zeta"), EntityBombletZeta.class, "entity_zeta", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_emp"), EntityEMP.class, "entity_emp", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_theta"), EntityBombletTheta.class, "entity_theta", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_selena"), EntityBombletSelena.class, "entity_selena", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_black_hole"), EntityBlackHole.class, "entity_black_hole", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_emp_blast"), EntityEMPBlast.class, "entity_emp_blast", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_bullet_mk2"), EntityBulletBase.class, "entity_bullet_mk2", i++, MainRegistry.instance, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_duchessgambit"), EntityDuchessGambit.class, "entity_duchessgambit", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_spark_beam"), EntitySparkBeam.class, "entity_spark_beam", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_mod_beam"), EntityModBeam.class, "entity_mod_beam", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_vortex"), EntityVortex.class, "entity_vortex", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_raging_vortex"), EntityRagingVortex.class, "entity_raging_vortex", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_mini_nuke"), EntityMiniNuke.class, "entity_mini_nuke", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_mini_mirv"), EntityMiniMIRV.class, "entity_mini_mirv", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_baleflare"), EntityBaleflare.class, "entity_baleflare", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_balefire"), EntityBalefire.class, "entity_balefire", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_rainbow"), EntityRainbow.class, "entity_rainbow", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_plasma_beam"), EntityPlasmaBeam.class, "entity_plasma_beam", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_ln2"), EntityLN2.class, "entity_ln2", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_combine_ball"), EntityCombineBall.class, "entity_combine_ball", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_discharge"), EntityDischarge.class, "entity_discharge", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_schrab"), EntitySchrab.class, "entity_schrab", i++, MainRegistry.instance, 1000, 1, true);

		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_grenade_generic"), EntityGrenadeGeneric.class, "entity_grenade_generic", i++, MainRegistry.instance, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_grenade_strong"), EntityGrenadeStrong.class, "entity_grenade_strong", i++, MainRegistry.instance, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_grenade_frag"), EntityGrenadeFrag.class, "entity_grenade_frag", i++, MainRegistry.instance, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_grenade_fire"), EntityGrenadeFire.class, "entity_grenade_fire", i++, MainRegistry.instance, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_grenade_cluster"), EntityGrenadeCluster.class, "entity_grenade_cluster", i++, MainRegistry.instance, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_grenade_flare"), EntityGrenadeFlare.class, "entity_grenade_flare", i++, MainRegistry.instance, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_grenade_electric"), EntityGrenadeElectric.class, "entity_grenade_electric", i++, MainRegistry.instance, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_grenade_poison"), EntityGrenadePoison.class, "entity_grenade_poison", i++, MainRegistry.instance, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_grenade_gas"), EntityGrenadeGas.class, "entity_grenade_gas", i++, MainRegistry.instance, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_grenade_schrabidium"), EntityGrenadeSchrabidium.class, "entity_grenade_schrabidium", i++, MainRegistry.instance, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_grenade_pulse"), EntityGrenadePulse.class, "entity_grenade_pulse", i++, MainRegistry.instance, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_grenade_plasma"), EntityGrenadePlasma.class, "entity_grenade_plasma", i++, MainRegistry.instance, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_grenade_tau"), EntityGrenadeTau.class, "entity_grenade_tau", i++, MainRegistry.instance, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_grenade_cloud"), EntityGrenadeCloud.class, "entity_grenade_cloud", i++, MainRegistry.instance, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_grenade_pc"), EntityGrenadePC.class, "entity_grenade_pc", i++, MainRegistry.instance, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_grenade_smart"), EntityGrenadeSmart.class, "entity_grenade_smart", i++, MainRegistry.instance, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_grenade_mirv"), EntityGrenadeMIRV.class, "entity_grenade_mirv", i++, MainRegistry.instance, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_grenade_breach"), EntityGrenadeBreach.class, "entity_grenade_breach", i++, MainRegistry.instance, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_grenade_burst"), EntityGrenadeBurst.class, "entity_grenade_burst", i++, MainRegistry.instance, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_grenade_lemon"), EntityGrenadeLemon.class, "entity_grenade_lemon", i++, MainRegistry.instance, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_grenade_mk2"), EntityGrenadeMk2.class, "entity_grenade_mk2", i++, MainRegistry.instance, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_grenade_aschrab"), EntityGrenadeASchrab.class, "entity_grenade_aschrab", i++, MainRegistry.instance, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_grenade_zomg"), EntityGrenadeZOMG.class, "entity_grenade_zomg", i++, MainRegistry.instance, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_grenade_solinium"), EntityGrenadeSolinium.class, "entity_grenade_solinium", i++, MainRegistry.instance, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_grenade_shrapnel"), EntityGrenadeShrapnel.class, "entity_grenade_shrapnel", i++, MainRegistry.instance, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_grenade_black_hole"), EntityGrenadeBlackHole.class, "entity_grenade_black_hole", i++, MainRegistry.instance, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_grenade_gascan"), EntityGrenadeGascan.class, "entity_grenade_gascan", i++, MainRegistry.instance, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_grenade_nuke"), EntityGrenadeNuke.class, "entity_grenade_nuke", i++, MainRegistry.instance, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_grenade_nuclear"), EntityGrenadeNuclear.class, "entity_grenade_nuclear", i++, MainRegistry.instance, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_grenade_if_generic"), EntityGrenadeIFGeneric.class, "entity_grenade_if_generic", i++, MainRegistry.instance, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_grenade_if_he"), EntityGrenadeIFHE.class, "entity_grenade_if_he", i++, MainRegistry.instance, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_grenade_if_bouncy"), EntityGrenadeIFBouncy.class, "entity_grenade_if_bouncy", i++, MainRegistry.instance, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_grenade_if_sticky"), EntityGrenadeIFSticky.class, "entity_grenade_if_sticky", i++, MainRegistry.instance, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_grenade_if_impact"), EntityGrenadeIFImpact.class, "entity_grenade_if_impact", i++, MainRegistry.instance, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_grenade_if_incendiary"), EntityGrenadeIFIncendiary.class, "entity_grenade_if_incendiary", i++, MainRegistry.instance, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_grenade_if_toxic"), EntityGrenadeIFToxic.class, "entity_grenade_if_toxic", i++, MainRegistry.instance, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_grenade_if_concussion"), EntityGrenadeIFConcussion.class, "entity_grenade_if_concussion", i++, MainRegistry.instance, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_grenade_if_brimstone"), EntityGrenadeIFBrimstone.class, "entity_grenade_if_brimstone", i++, MainRegistry.instance, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_grenade_if_mystery"), EntityGrenadeIFMystery.class, "entity_grenade_if_mystery", i++, MainRegistry.instance, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_grenade_if_spark"), EntityGrenadeIFSpark.class, "entity_grenade_if_spark", i++, MainRegistry.instance, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_grenade_if_hopwire"), EntityGrenadeIFHopwire.class, "entity_grenade_if_hopwire", i++, MainRegistry.instance, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_grenade_if_null"), EntityGrenadeIFNull.class, "entity_grenade_if_null", i++, MainRegistry.instance, 250, 1, true);

		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_missile_generic"), EntityMissileGeneric.class, "entity_missile_generic", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_missile_incendiary"), EntityMissileIncendiary.class, "entity_missile_incendiary", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_missile_cluster"), EntityMissileCluster.class, "entity_missile_cluster", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_missile_bunker_buster"), EntityMissileBunkerBuster.class, "entity_missile_bunker_buster", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_missile_strong"), EntityMissileStrong.class, "entity_missile_strong", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_missile_incendiary_strong"), EntityMissileIncendiaryStrong.class, "entity_missile_incendiary_strong", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_missile_cluster_strong"), EntityMissileClusterStrong.class, "entity_missile_cluster_strong", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_missile_buster_strong"), EntityMissileBusterStrong.class, "entity_missile_buster_strong", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_missile_emp_strong"), EntityMissileEMPStrong.class, "entity_missile_emp_strong", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_missile_burst"), EntityMissileBurst.class, "entity_missile_burst", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_missile_inferno"), EntityMissileInferno.class, "entity_missile_inferno", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_missile_rain"), EntityMissileRain.class, "entity_missile_rain", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_missile_drill"), EntityMissileDrill.class, "entity_missile_drill", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_missile_n2"), EntityMissileN2.class, "entity_missile_n2", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_missile_nuclear"), EntityMissileNuclear.class, "entity_missile_nuclear", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_missile_mirv"), EntityMissileMirv.class, "entity_missile_mirv", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_missile_endo"), EntityMissileEndo.class, "entity_missile_endo", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_missile_exo"), EntityMissileExo.class, "entity_missile_exo", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_missile_doomsday"), EntityMissileDoomsday.class, "entity_missile_doomsday", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_missile_taint"), EntityMissileTaint.class, "entity_missile_taint", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_missile_micro"), EntityMissileMicro.class, "entity_missile_micro", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_missile_bhole"), EntityMissileBHole.class, "entity_missile_bhole", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_missile_schrab"), EntityMissileSchrabidium.class, "entity_missile_schrab", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_missile_emp"), EntityMissileEMP.class, "entity_missile_emp", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_missile_ab"), EntityMissileAntiBallistic.class, "entity_missile_ab", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_carrier"), EntityCarrier.class, "entity_carrier", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_booster"), EntityBooster.class, "entity_booster", i++, MainRegistry.instance, 1000, 1, true);

		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_railgun_pellet"), EntityRailgunBlast.class, "entity_railgun_pellet", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_gas_fx"), EntityGasFX.class, "entity_gas_fx", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_oil_spill"), EntityOilSpill.class, "entity_oil_spill", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_oil_spill_fx"), EntityOilSpillFX.class, "entity_oil_spill_fx", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_clound_solinium"), EntityCloudSolinium.class, "entity_clound_solinium", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_nuke_explosion_advanced"), EntityNukeExplosionPlus.class, "entity_nuke_explosion_advanced", i++, MainRegistry.instance, 250, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_falling_bomb"), EntityFallingNuke.class, "entity_falling_bomb", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_custom_missile"), EntityMissileCustom.class, "entity_custom_missile", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_laser_blast"), EntityDeathBlast.class, "entity_laser_blast", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_miner_rocket"), EntityMinerRocket.class, "entity_miner_rocket", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_meteor"), EntityMeteor.class, "entity_meteor", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_bobmazon"), EntityBobmazon.class, "entity_bobmazon", i++, MainRegistry.instance, 1000, 1, true);
		//Drillgon200: The hunter chopper is messed up and janky and I don't know what do about it. I'd probably have to recode the whole thing, and I don't have time for that.
		//Alcater: I feel that, sigh...
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_hunter_chopper"), EntityHunterChopper.class, "entity_hunter_chopper", i++, MainRegistry.instance, 1000, 1, true, 0x000020, 0x2D2D72);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_chopper_mine"), EntityChopperMine.class, "entity_chopper_mine", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_water_splash"), EntityWaterSplash.class, "entity_water_splash", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_miner_beam"), EntityMinerBeam.class, "entity_miner_beam", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_laser_beam"), EntityLaserBeam.class, "entity_laser_beam", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_mirvlet"), EntityMIRV.class, "entity_mirvlet", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_building"), EntityBuilding.class, "entity_building", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_cyber_crab"), EntityCyberCrab.class, "entity_cyber_crab", i++, MainRegistry.instance, 250, 1, true, 0xAAAAAA, 0x444444);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_taint_crab"), EntityTaintCrab.class, "entity_taint_crab", i++, MainRegistry.instance, 250, 1, true, 0x252324, 0x0082FF);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_tesla_crab"), EntityTeslaCrab.class, "entity_tesla_crab", i++, MainRegistry.instance, 250, 1, true, 0x252324, 0xCF1718);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_tom_the_moonstone"), EntityTom.class, "entity_tom_the_moonstone", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_tom_bust"), EntityTomBlast.class, "entity_tom_bust", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_soyuz_capsule"), EntitySoyuzCapsule.class, "entity_soyuz_capsule", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_soyuz"), EntitySoyuz.class, "entity_soyuz", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_laser"), EntityLaser.class, "entity_laser", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_c_item"), EntityMovingItem.class, "entity_c_item", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_c_package"), EntityMovingPackage.class, "entity_c_package", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_moonstone_blast"), EntityCloudTom.class, "entity_moonstone_blast", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_mask_man"), EntityMaskMan.class, "entity_mask_man", i++, MainRegistry.instance, 1000, 1, true, 0x78786F, 0x3E3E32);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_balls_o_tron"), EntityBOTPrimeHead.class, "entity_balls_o_tron", i++, MainRegistry.instance, 1000, 1, true, 0x434343, 0xA0A0A0);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_balls_o_tron_seg"), EntityBOTPrimeBody.class, "entity_balls_o_tron_seg", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_fucc_a_ducc"), EntityDuck.class, "entity_fucc_a_ducc", i++, MainRegistry.instance, 1000, 1, true, 0xd0d0d0, 0xEED900);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_vortex_beam"), EntityBeamVortex.class, "entity_vortex_beam", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_elder_one"), EntityQuackos.class, "entity_elder_one", i++, MainRegistry.instance, 1000, 1, true, 0xFFFFFF, 0xFFBF00);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_ntm_fbi"), EntityFBI.class, "entity_ntm_fbi", i++, MainRegistry.instance, 1000, 1, true, 0xE79255, 0x1F3849);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_firework_ball"), EntityFireworks.class, "entity_firework_ball", i++, MainRegistry.instance, 1000, 1, true);
		
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_rbmk_debris"), EntityRBMKDebris.class, "entity_rbmk_debris", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_spear"), EntitySpear.class, "entity_spear", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_missile_volcano"), EntityMissileVolcano.class, "entity_missile_volcano", i++, MainRegistry.instance, 1000, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_ntm_ufo"), EntityUFO.class, "entity_ntm_ufo", i++, MainRegistry.instance, 1000, 1, true, 0x00FFFF, 0x606060);
		EntityRegistry.registerModEntity(new ResourceLocation(RefStrings.MODID, "entity_digamma_quasar"), EntityQuasar.class, "entity_digamma_quasar", i++, MainRegistry.instance, 1000, 1, true);
		
		ForgeChunkManager.setForcedChunkLoadingCallback(this, new LoadingCallback() {

			@Override
			public void ticketsLoaded(List<Ticket> tickets, World world) {
				for(Ticket ticket : tickets) {

					if(ticket.getEntity() instanceof IChunkLoader) {
						((IChunkLoader) ticket.getEntity()).init(ticket);
					}
				}
			}
		});
		
		registerDispenserBehaviors();
	}

	public static void reloadConfig() {
		Configuration config = new Configuration(new File(proxy.getDataDir().getPath() + "/config/hbm/hbm.cfg"));
		config.load();
		GeneralConfig.loadFromConfig(config);
		MachineConfig.loadFromConfig(config);
		BombConfig.loadFromConfig(config);
		RadiationConfig.loadFromConfig(config);
		PotionConfig.loadFromConfig(config);
		ToolConfig.loadFromConfig(config);
		WeaponConfig.loadFromConfig(config);
		MobConfig.loadFromConfig(config);
		config.save();
		reloadCompatConfig();
		WorldConfig.loadFromCompatibilityConfig();
		BedrockOreJsonConfig.init();
	}

	public static void reloadCompatConfig() {
		Configuration config = new Configuration(new File(proxy.getDataDir().getPath() + "/config/hbm/hbm_dimensions.cfg"));
		config.load();
		CompatibilityConfig.loadFromConfig(config);
		config.save();
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		ModItems.init();
		ModBlocks.init();
		HazmatRegistry.registerHazmats();
		registerReactorFuels();
		ControlRegistry.init();
		OreDictManager.registerOres();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		ModItems.postInit();
		ModBlocks.postInit();
		BlockCrate.setDrops();
		BedrockOreRegistry.registerBedrockOres();
		FluidTypeHandler.registerFluidProperties();
		CraftingManager.addBedrockOreSmelting();
		ShredderRecipes.registerShredder();
		ShredderRecipes.registerOverrides();
		DiFurnaceRecipes.registerRecipes();
		DiFurnaceRecipes.registerFuels();
		CrystallizerRecipes.register();
		CentrifugeRecipes.register();
		PressRecipes.registerOverrides();
		BreederRecipes.registerFuels();
		BreederRecipes.registerRecipes();
		AssemblerRecipes.loadRecipes();
		ChemplantRecipes.registerRecipes();
		MixerRecipes.copyChemplantRecipes();
		MixerRecipes.registerRecipes();
		CrackRecipes.registerRecipes();
		ExplosionNukeGeneric.loadSoliniumFromFile();
		CyclotronRecipes.register();
		HadronRecipes.register();
		MagicRecipes.register();
		SILEXRecipes.register();
		AnvilRecipes.register();
		WasteDrumRecipes.registerRecipes();
		RefineryRecipes.registerFractions();
		RefineryRecipes.registerRefineryRecipes();
		TileEntityNukeCustom.registerBombItems();
		ArmorUtil.register();
		RBMKOutgasserRecipes.registerOverrides();
		RBMKFuelRecipes.registerRecipes();
		DFCRecipes.register();
		SAFERecipes.registerRecipes();
		StorageDrumRecipes.registerRecipes();
		NuclearTransmutationRecipes.registerRecipes();
		HeatRecipes.registerHeatRecipes();
		EngineRecipes.registerEngineRecipes();
		FluidCombustionRecipes.registerFluidCombustionRecipes();
		HbmDetox.init();

		FluidContainerRegistry.registerContainer(Item.getItemFromBlock(ModBlocks.lox_barrel), ModItems.tank_steel, new FluidStack(ModForgeFluids.oxygen, 10000));
		FluidContainerRegistry.registerContainer(Item.getItemFromBlock(ModBlocks.pink_barrel), ModItems.tank_steel, new FluidStack(ModForgeFluids.kerosene, 10000));
		FluidContainerRegistry.registerContainer(Item.getItemFromBlock(ModBlocks.red_barrel), ModItems.tank_steel, new FluidStack(ModForgeFluids.diesel, 10000));
		FluidContainerRegistry.registerContainer(ModItems.iv_xp, ModItems.iv_xp_empty, new FluidStack(ModForgeFluids.experience, 100));
		FluidContainerRegistry.registerContainer(ModItems.nugget_mercury, null, new FluidStack(ModForgeFluids.mercury, 125));
		FluidContainerRegistry.registerContainer(ModItems.bottle_mercury, Items.GLASS_BOTTLE, new FluidStack(ModForgeFluids.mercury, 1000));
		FluidContainerRegistry.registerContainer(ModItems.particle_hydrogen, ModItems.particle_empty, new FluidStack(ModForgeFluids.hydrogen, 1000));
		FluidContainerRegistry.registerContainer(ModItems.particle_amat, ModItems.particle_empty, new FluidStack(ModForgeFluids.amat, 1000));
		FluidContainerRegistry.registerContainer(ModItems.particle_aschrab, ModItems.particle_empty, new FluidStack(ModForgeFluids.aschrab, 1000));
		FluidContainerRegistry.registerContainer(Item.getItemFromBlock(ModBlocks.ore_gneiss_gas), Item.getItemFromBlock(ModBlocks.stone_gneiss), new FluidStack(ModForgeFluids.petroleum, 250));
		
		//Drillgon200: expand the max entity radius for the hunter chopper
		if(World.MAX_ENTITY_RADIUS < 5)
			World.MAX_ENTITY_RADIUS = 5;
		MinecraftForge.EVENT_BUS.register(new SchistStratum()); //DecorateBiomeEvent.Pre
		
		NTMCraftTweaker.applyPostInitActions();
		AssemblerRecipes.generateList();
		HeatRecipes.setFluidsForRBMKLoader();
		if(event.getSide() == Side.CLIENT) {
			BedrockOreRegistry.registerOreColors();
			ModForgeFluids.registerFluidColors();
		}
		proxy.postInit(event);
	}

	@EventHandler
	public void serverStarting(FMLServerStartingEvent evt) {
		RBMKDials.createDials(evt.getServer().getEntityWorld());
		evt.registerServerCommand(new CommandRadiation());
		evt.registerServerCommand(new CommandHbm());
		AdvancementManager.init(evt.getServer());
		//MUST be initialized AFTER achievements!!
		BobmazonOfferFactory.reset();
		BobmazonOfferFactory.init();
	}

	private void registerReactorFuels(){
		TileEntityMachineReactorLarge.registerFuelEntry(1, ReactorFuelType.URANIUM, ModItems.nugget_uranium_fuel);
		TileEntityMachineReactorLarge.registerFuelEntry(9, ReactorFuelType.URANIUM, ModItems.ingot_uranium_fuel);
		TileEntityMachineReactorLarge.registerFuelEntry(81, ReactorFuelType.URANIUM, Item.getItemFromBlock(ModBlocks.block_uranium_fuel));
		TileEntityMachineReactorLarge.registerFuelEntry(6, ReactorFuelType.URANIUM, ModItems.billet_uranium_fuel);
		TileEntityMachineReactorLarge.registerFuelEntry(6, ReactorFuelType.URANIUM, ModItems.rod_uranium_fuel);
		TileEntityMachineReactorLarge.registerFuelEntry(12, ReactorFuelType.URANIUM, ModItems.rod_dual_uranium_fuel);
		TileEntityMachineReactorLarge.registerFuelEntry(24, ReactorFuelType.URANIUM, ModItems.rod_quad_uranium_fuel);
		TileEntityMachineReactorLarge.registerWasteEntry(6, ReactorFuelType.URANIUM, ModItems.rod_empty, ModItems.rod_uranium_fuel_depleted);
		TileEntityMachineReactorLarge.registerWasteEntry(12, ReactorFuelType.URANIUM, ModItems.rod_dual_empty, ModItems.rod_dual_uranium_fuel_depleted);
		TileEntityMachineReactorLarge.registerWasteEntry(24, ReactorFuelType.URANIUM, ModItems.rod_quad_empty, ModItems.rod_quad_uranium_fuel_depleted);

		TileEntityMachineReactorLarge.registerFuelEntry(1, ReactorFuelType.PLUTONIUM, ModItems.nugget_plutonium_fuel);
		TileEntityMachineReactorLarge.registerFuelEntry(9, ReactorFuelType.PLUTONIUM, ModItems.ingot_plutonium_fuel);
		TileEntityMachineReactorLarge.registerFuelEntry(81, ReactorFuelType.PLUTONIUM, Item.getItemFromBlock(ModBlocks.block_plutonium_fuel));
		TileEntityMachineReactorLarge.registerFuelEntry(6, ReactorFuelType.PLUTONIUM, ModItems.billet_plutonium_fuel);
		TileEntityMachineReactorLarge.registerFuelEntry(6, ReactorFuelType.PLUTONIUM, ModItems.rod_plutonium_fuel);
		TileEntityMachineReactorLarge.registerFuelEntry(12, ReactorFuelType.PLUTONIUM, ModItems.rod_dual_plutonium_fuel);
		TileEntityMachineReactorLarge.registerFuelEntry(24, ReactorFuelType.PLUTONIUM, ModItems.rod_quad_plutonium_fuel);
		TileEntityMachineReactorLarge.registerWasteEntry(6, ReactorFuelType.PLUTONIUM, ModItems.rod_empty, ModItems.rod_plutonium_fuel_depleted);
		TileEntityMachineReactorLarge.registerWasteEntry(12, ReactorFuelType.PLUTONIUM, ModItems.rod_dual_empty, ModItems.rod_dual_plutonium_fuel_depleted);
		TileEntityMachineReactorLarge.registerWasteEntry(24, ReactorFuelType.PLUTONIUM, ModItems.rod_quad_empty, ModItems.rod_quad_plutonium_fuel_depleted);

		TileEntityMachineReactorLarge.registerFuelEntry(1, ReactorFuelType.MOX, ModItems.nugget_mox_fuel);
		TileEntityMachineReactorLarge.registerFuelEntry(9, ReactorFuelType.MOX, ModItems.ingot_mox_fuel);
		TileEntityMachineReactorLarge.registerFuelEntry(6, ReactorFuelType.MOX, ModItems.billet_mox_fuel);
		TileEntityMachineReactorLarge.registerFuelEntry(6, ReactorFuelType.MOX, ModItems.rod_mox_fuel);
		TileEntityMachineReactorLarge.registerFuelEntry(12, ReactorFuelType.MOX, ModItems.rod_dual_mox_fuel);
		TileEntityMachineReactorLarge.registerFuelEntry(24, ReactorFuelType.MOX, ModItems.rod_quad_mox_fuel);
		TileEntityMachineReactorLarge.registerWasteEntry(6, ReactorFuelType.MOX, ModItems.rod_empty, ModItems.rod_mox_fuel_depleted);
		TileEntityMachineReactorLarge.registerWasteEntry(12, ReactorFuelType.MOX, ModItems.rod_dual_empty, ModItems.rod_dual_mox_fuel_depleted);
		TileEntityMachineReactorLarge.registerWasteEntry(24, ReactorFuelType.MOX, ModItems.rod_quad_empty, ModItems.rod_quad_mox_fuel_depleted);

		TileEntityMachineReactorLarge.registerFuelEntry(10, ReactorFuelType.SCHRABIDIUM, ModItems.nugget_schrabidium_fuel);
		TileEntityMachineReactorLarge.registerFuelEntry(90, ReactorFuelType.SCHRABIDIUM, ModItems.ingot_schrabidium_fuel);
		TileEntityMachineReactorLarge.registerFuelEntry(810, ReactorFuelType.SCHRABIDIUM, Item.getItemFromBlock(ModBlocks.block_schrabidium_fuel));
		TileEntityMachineReactorLarge.registerFuelEntry(60, ReactorFuelType.SCHRABIDIUM, ModItems.billet_schrabidium_fuel);
		TileEntityMachineReactorLarge.registerFuelEntry(60, ReactorFuelType.SCHRABIDIUM, ModItems.rod_schrabidium_fuel);
		TileEntityMachineReactorLarge.registerFuelEntry(120, ReactorFuelType.SCHRABIDIUM, ModItems.rod_dual_schrabidium_fuel);
		TileEntityMachineReactorLarge.registerFuelEntry(240, ReactorFuelType.SCHRABIDIUM, ModItems.rod_quad_schrabidium_fuel);
		TileEntityMachineReactorLarge.registerWasteEntry(60, ReactorFuelType.SCHRABIDIUM, ModItems.rod_empty, ModItems.rod_schrabidium_fuel_depleted);
		TileEntityMachineReactorLarge.registerWasteEntry(120, ReactorFuelType.SCHRABIDIUM, ModItems.rod_dual_empty, ModItems.rod_dual_schrabidium_fuel_depleted);
		TileEntityMachineReactorLarge.registerWasteEntry(240, ReactorFuelType.SCHRABIDIUM, ModItems.rod_quad_empty, ModItems.rod_quad_schrabidium_fuel_depleted);

		TileEntityMachineReactorLarge.registerFuelEntry(1, ReactorFuelType.THORIUM, ModItems.nugget_thorium_fuel);
		TileEntityMachineReactorLarge.registerFuelEntry(9, ReactorFuelType.THORIUM, ModItems.ingot_thorium_fuel);
		TileEntityMachineReactorLarge.registerFuelEntry(81, ReactorFuelType.THORIUM, Item.getItemFromBlock(ModBlocks.block_thorium_fuel));
		TileEntityMachineReactorLarge.registerFuelEntry(6, ReactorFuelType.THORIUM, ModItems.billet_thorium_fuel);
		TileEntityMachineReactorLarge.registerFuelEntry(6, ReactorFuelType.THORIUM, ModItems.rod_thorium_fuel);
		TileEntityMachineReactorLarge.registerFuelEntry(12, ReactorFuelType.THORIUM, ModItems.rod_dual_thorium_fuel);
		TileEntityMachineReactorLarge.registerFuelEntry(24, ReactorFuelType.THORIUM, ModItems.rod_quad_thorium_fuel);
		TileEntityMachineReactorLarge.registerWasteEntry(6, ReactorFuelType.THORIUM, ModItems.rod_empty, ModItems.rod_thorium_fuel_depleted);
		TileEntityMachineReactorLarge.registerWasteEntry(12, ReactorFuelType.THORIUM, ModItems.rod_dual_empty, ModItems.rod_dual_thorium_fuel_depleted);
		TileEntityMachineReactorLarge.registerWasteEntry(24, ReactorFuelType.THORIUM, ModItems.rod_quad_empty, ModItems.rod_quad_thorium_fuel_depleted);
	}
	
	private void registerDispenserBehaviors(){
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.grenade_generic, new BehaviorProjectileDispense() {
			@Override
            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_, ItemStack stack)
            {
                return new EntityGrenadeGeneric(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.grenade_strong, new BehaviorProjectileDispense() {
			@Override
            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_, ItemStack stack)
            {
                return new EntityGrenadeStrong(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.grenade_frag, new BehaviorProjectileDispense() {
			@Override
            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_, ItemStack stack)
            {
                return new EntityGrenadeFrag(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.grenade_fire, new BehaviorProjectileDispense() {
			@Override
            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_, ItemStack stack)
            {
                return new EntityGrenadeFire(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.grenade_cluster, new BehaviorProjectileDispense() {
			@Override
            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_, ItemStack stack)
            {
                return new EntityGrenadeCluster(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.grenade_flare, new BehaviorProjectileDispense() {
			@Override
            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_, ItemStack stack)
            {
                return new EntityGrenadeFlare(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.grenade_electric, new BehaviorProjectileDispense() {
			@Override
            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_, ItemStack stack)
            {
                return new EntityGrenadeElectric(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.grenade_poison, new BehaviorProjectileDispense() {
			@Override
            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_, ItemStack stack)
            {
                return new EntityGrenadePoison(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.grenade_gas, new BehaviorProjectileDispense() {
			@Override
            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_, ItemStack stack)
            {
                return new EntityGrenadeGas(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.grenade_schrabidium, new BehaviorProjectileDispense() {
			@Override
            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_, ItemStack stack)
            {
                return new EntityGrenadeSchrabidium(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.grenade_nuke, new BehaviorProjectileDispense() {
			@Override
            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_, ItemStack stack)
            {
                return new EntityGrenadeNuke(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.grenade_nuclear, new BehaviorProjectileDispense() {
			@Override
            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_, ItemStack stack)
            {
                return new EntityGrenadeNuclear(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.grenade_pulse, new BehaviorProjectileDispense() {
			@Override
            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_, ItemStack stack)
            {
                return new EntityGrenadePulse(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.grenade_plasma, new BehaviorProjectileDispense() {
			@Override
            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_, ItemStack stack)
            {
                return new EntityGrenadePlasma(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.grenade_tau, new BehaviorProjectileDispense() {
			@Override
            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_, ItemStack stack)
            {
                return new EntityGrenadeTau(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.grenade_lemon, new BehaviorProjectileDispense() {
			@Override
            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_, ItemStack stack)
            {
                return new EntityGrenadeLemon(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.grenade_mk2, new BehaviorProjectileDispense() {
			@Override
            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_, ItemStack stack)
            {
                return new EntityGrenadeMk2(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.grenade_aschrab, new BehaviorProjectileDispense() {
			@Override
            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_, ItemStack stack)
            {
                return new EntityGrenadeASchrab(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.grenade_zomg, new BehaviorProjectileDispense() {
			@Override
            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_, ItemStack stack)
            {
                return new EntityGrenadeZOMG(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
        BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.grenade_solinium, new BehaviorProjectileDispense() {
			@Override
            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_, ItemStack stack)
            {
                return new EntityGrenadeSolinium(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.grenade_shrapnel, new BehaviorProjectileDispense() {
			@Override
            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_, ItemStack stack)
            {
                return new EntityGrenadeShrapnel(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.grenade_black_hole, new BehaviorProjectileDispense() {
			@Override
            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_, ItemStack stack)
            {
                return new EntityGrenadeBlackHole(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.grenade_gascan, new BehaviorProjectileDispense() {
			@Override
            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_, ItemStack stack)
            {
                return new EntityGrenadeGascan(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.grenade_cloud, new BehaviorProjectileDispense() {
			@Override
            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_, ItemStack stack)
            {
                return new EntityGrenadeCloud(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.grenade_pink_cloud, new BehaviorProjectileDispense() {
			@Override
            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_, ItemStack stack)
            {
                return new EntityGrenadePC(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.grenade_smart, new BehaviorProjectileDispense() {
			@Override
            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_, ItemStack stack)
            {
                return new EntityGrenadeSmart(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.grenade_mirv, new BehaviorProjectileDispense() {
			@Override
            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_, ItemStack stack)
            {
                return new EntityGrenadeMIRV(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.grenade_breach, new BehaviorProjectileDispense() {
			@Override
            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_, ItemStack stack)
            {
                return new EntityGrenadeBreach(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.grenade_burst, new BehaviorProjectileDispense() {
			@Override
            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_, ItemStack stack)
            {
                return new EntityGrenadeBurst(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.grenade_if_generic, new BehaviorProjectileDispense() {
			@Override
            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_, ItemStack stack)
            {
                return new EntityGrenadeIFGeneric(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.grenade_if_he, new BehaviorProjectileDispense() {
			@Override
            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_, ItemStack stack)
            {
                return new EntityGrenadeIFHE(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.grenade_if_bouncy, new BehaviorProjectileDispense() {
			@Override
            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_, ItemStack stack)
            {
                return new EntityGrenadeIFBouncy(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.grenade_if_sticky, new BehaviorProjectileDispense() {
			@Override
            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_, ItemStack stack)
            {
                return new EntityGrenadeIFSticky(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.grenade_if_impact, new BehaviorProjectileDispense() {
			@Override
            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_, ItemStack stack)
            {
                return new EntityGrenadeIFImpact(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.grenade_if_incendiary, new BehaviorProjectileDispense() {
			@Override
            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_, ItemStack stack)
            {
                return new EntityGrenadeIFIncendiary(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.grenade_if_toxic, new BehaviorProjectileDispense() {
			@Override
            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_, ItemStack stack)
            {
                return new EntityGrenadeIFToxic(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.grenade_if_concussion, new BehaviorProjectileDispense() {
			@Override
            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_, ItemStack stack)
            {
                return new EntityGrenadeIFConcussion(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.grenade_if_brimstone, new BehaviorProjectileDispense() {
			@Override
            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_, ItemStack stack)
            {
                return new EntityGrenadeIFBrimstone(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.grenade_if_mystery, new BehaviorProjectileDispense() {
			@Override
            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_, ItemStack stack)
            {
                return new EntityGrenadeIFMystery(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.grenade_if_spark, new BehaviorProjectileDispense() {
			@Override
            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_, ItemStack stack)
            {
                return new EntityGrenadeIFSpark(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.grenade_if_hopwire, new BehaviorProjectileDispense() {
			@Override
            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_, ItemStack stack)
            {
                return new EntityGrenadeIFHopwire(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.grenade_if_null, new BehaviorProjectileDispense() {
			@Override
            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_, ItemStack stack)
            {
                return new EntityGrenadeIFNull(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
	}

}
