package dev.averse;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class PowerExtended implements ModInitializer {
     
    // items
    public static final Item POWER_WAND_ITEM          = new PowerWand(new FabricItemSettings());

    // blocks
    public static final Block WIRE_BLOCK               = new Block(FabricBlockSettings.create().hardness(0.1f).resistance(0.2f).requiresTool());
    public static final Block STRENGTHENED_WIRE_ITEM   = new Block(FabricBlockSettings.create().hardness(50.0f).resistance(1200.0f).requiresTool());
    public static final Block COMPACTED_OBSIDIAN_BLOCK = new Block(FabricBlockSettings.create().hardness(70.0f).resistance(1500.0f).requiresTool());


    private static final ItemGroup POWER_EXTENDED_GROUP = FabricItemGroup.builder()
    	.icon(() -> new ItemStack(POWER_WAND_ITEM))
    	.displayName(Text.translatable("itemGroup.power_extended.power_extended_group"))
            .entries((context, entries) -> {
    		entries.add(POWER_WAND_ITEM);
            entries.add(WIRE_BLOCK);
            entries.add(STRENGTHENED_WIRE_ITEM);
            entries.add(COMPACTED_OBSIDIAN_BLOCK);
    	})
    	.build();

    @Override
    public void onInitialize() {
        // items
        Registry.register(Registries.ITEM, new Identifier("power_extended", "power_wand"), POWER_WAND_ITEM);

        // blocks
        Registry.register(Registries.BLOCK, new Identifier("power_extended", "wire"), WIRE_BLOCK);
        Registry.register(Registries.ITEM, new Identifier("power_extended", "wire"), new BlockItem(WIRE_BLOCK, new FabricItemSettings()));

        Registry.register(Registries.BLOCK, new Identifier("power_extended", "strengthened_wire"), STRENGTHENED_WIRE_ITEM);
        Registry.register(Registries.ITEM, new Identifier("power_extended", "strengthened_wire"), new BlockItem(STRENGTHENED_WIRE_ITEM, new FabricItemSettings()));

        Registry.register(Registries.BLOCK, new Identifier("power_extended", "compacted_obsidian"), COMPACTED_OBSIDIAN_BLOCK);
        Registry.register(Registries.ITEM, new Identifier("power_extended", "compacted_obsidian"), new BlockItem(COMPACTED_OBSIDIAN_BLOCK, new FabricItemSettings()));

        Registry.register(Registries.ITEM_GROUP, new Identifier("power_extended", "power_extended_group"), POWER_EXTENDED_GROUP);
    }
}