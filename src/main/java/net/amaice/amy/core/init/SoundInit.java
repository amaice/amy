package net.amaice.amy.core.init;

import net.amaice.amy.AmyMain;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SoundInit {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, AmyMain.MODID);

    public static final RegistryObject<SoundEvent> ILWAG_BEAT = registerSoundEvent("ilwag_beat");
    public static final RegistryObject<SoundEvent> CC_STATIC = registerSoundEvent("cc_static");

    private static RegistryObject<SoundEvent> registerSoundEvent(String name){
        return SOUND_EVENTS.register(name, () -> new SoundEvent(new ResourceLocation(AmyMain.MODID, name)));
    }

    public static void register(IEventBus bus){
        SOUND_EVENTS.register(bus);
    }
}
