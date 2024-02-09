package dev.averse;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registry;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class PowerExtended implements ModInitializer {
     
    // an instance of our new item
    public static final Item POWER_WAND_ITEM = new Item(new FabricItemSettings());

    @Override
    public void onInitialize() {
        Registry.register(Registries.ITEM, new Identifier("power_extended", "power_wand"), POWER_WAND_ITEM);
    }
}