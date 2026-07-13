package com.github.misosoupTgit.mwgr.client.tooltip.aprilfool;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import org.joml.Matrix4f;

public class WaveNameClientTooltipComponent implements ClientTooltipComponent {

    private static final float AMPLITUDE = 1.05f;
    private static final float CHAR_PHASE = 0.35f;
    private static final float TIME_SCALE = 0.005f;

    private final Component text;

    public WaveNameClientTooltipComponent(WaveNameTooltipComponent data) {
        this.text = data.text();
    }

    @Override
    public int getHeight() {
        return Minecraft.getInstance().font.lineHeight + (int) (AMPLITUDE * 2);
    }

    @Override
    public int getWidth(Font font) {
        return font.width(text);
    }

    @Override
    public void renderText(Font font, int x, int y, Matrix4f matrix, MultiBufferSource.BufferSource bufferSource) {
        float time = (System.currentTimeMillis() % 1_000_000L) * TIME_SCALE;
        int baseY = y + (int) AMPLITUDE;
        String rawText = text.getString();
        int charX = x;
        int charIndex = 0;

        for (int i = 0; i < rawText.length();) {
            int cp = rawText.codePointAt(i);
            String charStr = new String(Character.toChars(cp));

            int charY = baseY + (int) ((float) Math.sin(time + charIndex * CHAR_PHASE) * AMPLITUDE);

            FormattedCharSequence seq = Component.literal(charStr)
                    .withStyle(text.getStyle())
                    .getVisualOrderText();

            font.drawInBatch(seq, charX, charY, 0xFFFFFF, true, matrix, bufferSource,
                    Font.DisplayMode.NORMAL, 0, 0xF000F0);

            charX += font.width(charStr);
            charIndex++;
            i += Character.charCount(cp);
        }
    }
}
