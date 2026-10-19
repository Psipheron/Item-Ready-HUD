package com.psipheron.cooldownborder;

import com.psipheron.cooldownborder.render.BorderRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CooldownBorderClient implements ClientModInitializer {
    public static final String MOD_ID = "cooldownborder";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitializeClient() {
        LOGGER.info("Initializing Cooldown Border...");

        // Register our custom HUD renderer to draw every frame
        HudRenderCallback.EVENT.register(BorderRenderer::render);
    }
}
