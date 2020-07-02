package optifine;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.IImageBuffer;
import net.minecraft.client.renderer.ImageBufferDownload;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.FilenameUtils;

import com.nakory.cape.CapeImageBuffer;
import com.nakory.cape.CapeThread;

public class CapeUtils
{
    public static void downloadCape(final AbstractClientPlayer p_downloadCape_0_)
    {
        String s = p_downloadCape_0_.getNameClear();

        if (s != null && !s.isEmpty())
        {
        	new CapeThread(s, p_downloadCape_0_);
        }
    }

    public static BufferedImage parseCape(BufferedImage p_parseCape_0_)
    {
        int i = 64;
        int j = 32;
        int k = p_parseCape_0_.getWidth();

        for (int l = p_parseCape_0_.getHeight(); i < k || j < l; j *= 2)
        {
            i *= 2;
        }

        BufferedImage bufferedimage = new BufferedImage(i, j, 2);
        Graphics graphics = bufferedimage.getGraphics();
        graphics.drawImage(p_parseCape_0_, 0, 0, (ImageObserver)null);
        graphics.dispose();
        return bufferedimage;
    }
}
