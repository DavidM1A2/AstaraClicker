package com.davidm1a2.astaraclicker

import net.minecraft.client.Minecraft
import net.minecraft.client.entity.player.ClientPlayerEntity
import net.minecraft.util.text.StringTextComponent
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import net.minecraftforge.client.event.ClientPlayerNetworkEvent
import net.minecraftforge.client.event.InputEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.ObfuscationReflectionHelper
import org.apache.commons.logging.LogFactory
import org.lwjgl.glfw.GLFW
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.random.Random

@OnlyIn(Dist.CLIENT)
class Clicker {
    private var isAutoClicking = AtomicBoolean(false)
    private val clickThread = Executors.newSingleThreadExecutor()

    @SubscribeEvent
    fun onKeyInputEvent(event: InputEvent.KeyInputEvent) {
        try {
            if (ModKeybindings.toggleClickKeybind.isPressed) {
                if (event.action == GLFW.GLFW_PRESS) {
                    val player = Minecraft.getInstance().player as ClientPlayerEntity
                    isAutoClicking.set(!isAutoClicking.get())
                    if (isAutoClicking.get()) {
                        player.sendStatusMessage(StringTextComponent("Started clicking"), false)
                        start()
                    } else {
                        player.sendStatusMessage(StringTextComponent("Stopped clicking"), false)
                    }
                }
            }
        } catch (e: Exception) {
            LOG.error("Failed to process key input event!", e)
        }
    }

    @SubscribeEvent
    fun onPlayerLogoutEvent(event: ClientPlayerNetworkEvent.LoggedOutEvent) {
        isAutoClicking.set(false)
    }

    private fun start() {
        clickThread.submit {
            while (isAutoClicking.get()) {
                val mc = Minecraft.getInstance()
                mc.execute {
                    LEFT_CLICK_COUNTER.set(mc, 0)
                    CLICK_MOUSE.invoke(mc)
                }
                // Wait 1.5 to 2 seconds
                Thread.sleep(Random.nextLong(600, 800))
            }
        }
    }

    companion object {
        private val LOG = LogFactory.getLog(Clicker::class.java)
        private val CLICK_MOUSE = ObfuscationReflectionHelper.findMethod(Minecraft::class.java, "func_147116_af")
        private val LEFT_CLICK_COUNTER = ObfuscationReflectionHelper.findField(Minecraft::class.java, "field_71429_W")
    }
}