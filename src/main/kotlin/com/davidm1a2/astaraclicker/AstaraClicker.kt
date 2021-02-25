package com.davidm1a2.astaraclicker

import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.client.registry.ClientRegistry
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext

@Mod("astaraclicker")
class AstaraClicker {
    init {
        MinecraftForge.EVENT_BUS.register(Clicker())
        FMLJavaModLoadingContext.get().modEventBus.register(this)
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    fun onFMLClientSetupEvent(event: FMLClientSetupEvent) {
        ClientRegistry.registerKeyBinding(ModKeybindings.toggleClickKeybind)
    }
}