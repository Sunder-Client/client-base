package wtf.base.client.util.render;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

import static org.lwjgl.opengl.GL11.*;

/**
 * A small utility to help with basic rendering of shapes/lines
 */
public class RenderUtil {

    public static void drawRectangle(double x, double y, double width, double height, int color) {

        glPushMatrix();

        glEnable(GL_BLEND);
        glBlendFunc(770, 771);
        glDisable(GL_TEXTURE_2D);

        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldRenderer = tessellator.getWorldRenderer();

        ColorUtil.setColor(color);

        worldRenderer.begin(GL_QUADS, DefaultVertexFormats.POSITION);
        worldRenderer.pos(x, y, 0.0).endVertex();
        worldRenderer.pos(x, y + height, 0.0).endVertex();
        worldRenderer.pos(x + width, y + height, 0.0).endVertex();
        worldRenderer.pos(x + width, y, 0.0).endVertex();

        tessellator.draw();

        glEnable(GL_TEXTURE_2D);
        glDisable(GL_BLEND);

        glPopMatrix();

    }
}
