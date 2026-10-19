package com.yourname.cooldownborder.render;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.player.PlayerEntity;

public class BorderRenderer {
    private static boolean wasCooldownActive = false;
    private static int flashTicks = 0;

    // In 1.21, the float tickDelta is replaced by RenderTickCounter.
    // This signature is for 1.20.x, which is the current standard.
    public static void render(DrawContext context, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();
        PlayerEntity player = client.player;

        // Don't render if we aren't in-game
        if (player == null) return;

        // 0.0F is just starting to swing, 1.0F is fully recharged
        float cooldown = player.getAttackCooldownProgress(0.0F);
        boolean isCooldownActive = cooldown < 1.0F;

        int width = context.getScaledWindowWidth();
        int height = context.getScaledWindowHeight();

        // Hardcoded for Phase 2 - We will move these to config in Phase 3
        int thickness = 6;
        int alpha = 115; // roughly 45% opacity (0-255 scale)

        // Logic to trigger the green flash when cooldown completes
        if (wasCooldownActive && !isCooldownActive) {
            flashTicks = 15; // How many frames the green flash lasts
        }
        wasCooldownActive = isCooldownActive;

        // Render the actual borders
        if (flashTicks > 0) {
            flashTicks--;
            drawBorder(context, width, height, thickness, 0x00FF00, alpha); // Green
        } else if (isCooldownActive) {
            drawBorder(context, width, height, thickness, 0xFF0000, alpha); // Red
        }
    }

    private static void drawBorder(DrawContext context, int width, int height, int thickness, int rgb, int alpha) {
        // Combine our Alpha and RGB values into a single ARGB integer
        int argb = (alpha << 24) | rgb;

        // Top edge
        context.fill(0, 0, width, thickness, argb);
        // Bottom edge
        context.fill(0, height - thickness, width, height, argb);
        // Left edge (adjusted so corners don't overlap and double-render opacity)
        context.fill(0, thickness, thickness, height - thickness, argb);
        // Right edge
        context.fill(width - thickness, thickness, width, height - thickness, argb);
    }
}
