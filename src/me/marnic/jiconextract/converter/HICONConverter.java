package me.marnic.jiconextract.converter;

import com.sun.jna.Memory;
import com.sun.jna.platform.win32.GDI32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinGDI;
import me.marnic.jiconextract.extractor.IconSize;

import java.awt.image.BufferedImage;

public class HICONConverter {
    public BufferedImage convertHICONToImage(WinDef.HICON hIcon, IconSize size) {

        int ICON_SIZE;
        short ICON_DEPTH = 24;
        int ICON_BYTE_SIZE = 8;

        if(size==IconSize.SMALL) {
            ICON_SIZE = 16;
        }if(size==IconSize.LARGE) {
            ICON_SIZE = 32;
        }if(size==IconSize.EXTRALARGE) {
            ICON_SIZE = 48;
        }else{
            ICON_SIZE = 256;
        }


        final int width = ICON_SIZE;
        final int height = ICON_SIZE;
        final short depth = ICON_DEPTH;
        final BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        final Memory lpBitsColor = new Memory(width * height * depth / ICON_BYTE_SIZE);
        final Memory lpBitsMask = new Memory(width * height * depth / ICON_BYTE_SIZE);
        final WinGDI.BITMAPINFO info = new WinGDI.BITMAPINFO();
        final WinGDI.BITMAPINFOHEADER hdr = new WinGDI.BITMAPINFOHEADER();

        info.bmiHeader = hdr;
        hdr.biWidth = width;
        hdr.biHeight = height;
        hdr.biPlanes = 1;
        hdr.biBitCount = depth;
        hdr.biCompression = WinGDI.BI_RGB;

        final WinDef.HDC hDC = User32.INSTANCE.GetDC(null);
        final WinGDI.ICONINFO piconinfo = new WinGDI.ICONINFO();
        User32.INSTANCE.GetIconInfo(hIcon, piconinfo);

        GDI32.INSTANCE.GetDIBits(hDC, piconinfo.hbmColor, 0, height, lpBitsColor, info, WinGDI.DIB_RGB_COLORS);
        GDI32.INSTANCE.GetDIBits(hDC, piconinfo.hbmMask, 0, height, lpBitsMask, info, WinGDI.DIB_RGB_COLORS);

        int r, g, b, a, argb;
        int x = 0, y = height - 1;
        for (int i = 0; i < lpBitsColor.size(); i = i + 3) {
            b = lpBitsColor.getByte(i) & 0xFF;
            g = lpBitsColor.getByte(i + 1) & 0xFF;
            r = lpBitsColor.getByte(i + 2) & 0xFF;
            a = 0xFF - lpBitsMask.getByte(i) & 0xFF;

            argb = a << 24 | r << 16 | g << 8 | b;
            image.setRGB(x, y, argb);
            x = (x + 1) % width;
            if (x == 0) {
                y--;
            }
        }

        User32.INSTANCE.ReleaseDC(null, hDC);
        GDI32.INSTANCE.DeleteObject(piconinfo.hbmColor);
        GDI32.INSTANCE.DeleteObject(piconinfo.hbmMask);

        return image;
    }
}
