package com.davidm1a2.astaraclicker

import net.minecraft.client.settings.KeyBinding
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import org.lwjgl.glfw.GLFW

@OnlyIn(Dist.CLIENT)
object ModKeybindings {
    val toggleClickKeybind = KeyBinding("astaraclicker.key.clicktoggle.description", GLFW.GLFW_KEY_O, "key.category.astaraclicker")
}