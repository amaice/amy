package net.amaice.amy.core.init;

import net.amaice.amy.AmyMain;
import net.amaice.amy.common.block.*;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class BlockInit {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, AmyMain.MODID);


    // RegisterEvent so it fires when things are supposed to be registered
    @SubscribeEvent
    public static void onRegisterItems(final RegisterEvent event) {
        if (event.getRegistryKey().equals(ForgeRegistries.Keys.ITEMS)) {
            BLOCKS.getEntries().forEach((blockRegistryObject) -> {
                Block block = blockRegistryObject.get();
                Item.Properties properties = new Item.Properties().tab(AmyMain.ModCreativeTab.instance);

                Supplier<Item> blockItemFactory;
                if (block instanceof BlockOfAmy) {
                    // Create a BlockOfAmy with a custom tooltip
                    blockItemFactory = () -> new BlockItem(block, properties) {
                        @Override
                        public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
                            super.appendHoverText(stack, level, tooltip, flag);
                            tooltip.add(Component.literal("Amy's spawn here!"));
                        }
                    };
                }
                else {
                    // make a BlockItem with those properties to place our block
                    blockItemFactory = () -> new BlockItem(block, properties);
                }
                event.register(ForgeRegistries.Keys.ITEMS, blockRegistryObject.getId(), blockItemFactory);
            });
        }
    }

    public static final RegistryObject<Block> BLOCK_OF_AMY = BLOCKS.register("block_of_amy",
            () -> new BlockOfAmy(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(4f, 800f)
                    .sound(SoundType.METAL)
                    .requiresCorrectToolForDrops()
            )
    );
    public static final RegistryObject<Block> BLOCK_OF_AMY_II = BLOCKS.register("block_of_amy_ii",
            () -> new BlockOfAmyII(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(4f, 800f)
                    .sound(SoundType.METAL)
                    .requiresCorrectToolForDrops()
            )
    );
    public static final RegistryObject<Block> BLOCK_OF_AMY_III = BLOCKS.register("block_of_amy_iii",
            () -> new BlockOfAmyIII(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(4f, 800f)
                    .sound(SoundType.METAL)
                    .requiresCorrectToolForDrops()
            )
    );
    public static final RegistryObject<Block> BLOCK_OF_AMY_IV = BLOCKS.register("block_of_amy_iv",
            () -> new BlockOfAmyIV(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(4f, 800f)
                    .sound(SoundType.METAL)
                    .requiresCorrectToolForDrops()
            )
    );

    public static final RegistryObject<Block> HEAD_OF_MITO = BLOCKS.register("head_of_mito",
            () -> new MitoPartBlock(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(6f, 1200f)
                    .requiresCorrectToolForDrops()
            )
    );
    public static final RegistryObject<Block> BACK_OF_MITO = BLOCKS.register("back_of_mito",
            () -> new MitoPartBlock(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(6f, 1200f)
                    .requiresCorrectToolForDrops()
            )
    );
    public static final RegistryObject<Block> EARS_OF_MITO = BLOCKS.register("ears_of_mito",
            () -> new MitoPartBlock(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(6f, 1200f)
                    .requiresCorrectToolForDrops()
            )
    );
}