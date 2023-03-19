package net.amaice.amy.core.init;

import net.amaice.amy.common.ModArmorMaterial;
import net.amaice.amy.AmyMain;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ItemInit {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, AmyMain.MODID);

    public static final RegistryObject<Item> AMY_ESSENCE = ITEMS.register("amy_essence",
            () -> new Item(new Item.Properties().tab(AmyMain.ModCreativeTab.instance)));
    public static final RegistryObject<Item> HOODIE_SHREDS = ITEMS.register("hoodie_shreds",
            () -> new Item(new Item.Properties().tab(AmyMain.ModCreativeTab.instance)));

    public static final RegistryObject<Item> ILWAG_BEAT_MUSIC_DISC = ITEMS.register("ilwag_beat_music_disc",
            () -> new RecordItem(4, SoundInit.ILWAG_BEAT,
                    new Item.Properties().tab(AmyMain.ModCreativeTab.instance).stacksTo(1).rarity(Rarity.RARE), 1140));
    public static final RegistryObject<Item> CC_STATIC_MUSIC_DISC = ITEMS.register("cc_static_music_disc",
            () -> new RecordItem(5, SoundInit.CC_STATIC,
                    new Item.Properties().tab(AmyMain.ModCreativeTab.instance).stacksTo(1).rarity(Rarity.RARE), 6280));

    public static final RegistryObject<ArmorItem> LAKEVIEW_HOODIE = ITEMS.register("lakeview_hoodie",
            () -> new ArmorItem(ArmorTiers.AMY_CLOTHES, EquipmentSlot.CHEST, new Item.Properties().tab(AmyMain.ModCreativeTab.instance)));

    public static class ArmorTiers {
        public static final ArmorMaterial AMY_CLOTHES = new ModArmorMaterial(
          "amy_clothes",
                500,
                new int[] {5, 10, 14, 3 },
                300,
                SoundEvents.ARMOR_EQUIP_LEATHER,
                0.0f,
                0.0f,
                () -> Ingredient.of(ItemInit.HOODIE_SHREDS.get())
        );
    }
}