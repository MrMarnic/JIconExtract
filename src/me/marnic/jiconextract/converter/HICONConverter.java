package me.marnic.jiconextract.converter;

import com.sun.jna.Memory;
import com.sun.jna.platform.win32.*;
import me.marnic.jiconextract.extractor.IconSize;

import java.awt.image.BufferedImage;

public class HICONConverter {
    public BufferedImage convertHICONToImage(final int size, final WinNT.HANDLE hicon) {
        final WinGDI.ICONINFO iconinfo = new WinGDI.ICONINFO();

        try {
            // GDI32 g32 = GDI32.INSTANCE;

            // get icon information

            if (!User32.INSTANCE.GetIconInfo(new WinDef.HICON(hicon.getPointer()), iconinfo)) { return null; }
            final WinDef.HWND hwdn = new WinDef.HWND();
            final WinDef.HDC dc = User32.INSTANCE.GetDC(hwdn);

            if (dc == null) {

                return null; }
            try {
                final int nBits = size * size * 4;
                // final BitmapInfo bmi = new BitmapInfo(1);

                final Memory colorBitsMem = new Memory(nBits);
                // // Extract the color bitmap
                final WinGDI.BITMAPINFO bmi = new WinGDI.BITMAPINFO();

                bmi.bmiHeader.biWidth = size;
                bmi.bmiHeader.biHeight = -size;
                bmi.bmiHeader.biPlanes = 1;
                bmi.bmiHeader.biBitCount = 32;
                bmi.bmiHeader.biCompression = WinGDI.BI_RGB;
                GDI32.INSTANCE.GetDIBits(dc, iconinfo.hbmColor, 0, size, colorBitsMem, bmi, WinGDI.DIB_RGB_COLORS);
                // g32.GetDIBits(dc, iconinfo.hbmColor, 0, size, colorBitsMem,
                // bmi,
                // GDI32.DIB_RGB_COLORS);
                final int[] colorBits = colorBitsMem.getIntArray(0, size * size);
                // final Memory maskBitsMem = new Memory(nBits);
                // // // Extract the mask bitmap
                // GDI32.INSTANCE.GetDIBits(dc, iconinfo.hbmMask, 0, height,
                // maskBitsMem, bmi, WinGDI.DIB_PAL_COLORS);
                // // g32.GetDIBits(dc, iconinfo.hbmMask, 0, size, maskBitsMem,
                // bmi,
                // // GDI32.DIB_RGB_COLORS);
                // final int[] maskBits = maskBitsMem.getIntArray(0, width *
                // height);
                // // // Copy the mask alphas into the color bits
                // for (int i = 0; i < colorBits.length; i++) {
                // colorBits[i] = colorBits[i] | (maskBits[i] != 0 ? 0 :
                // 0xFF000000);
                // }
                // // Release DC
                // Main.u32.ReleaseDC(0, dc);

                //

                // // Release bitmap handle in icon info
                // g32.DeleteObject(iconinfo.hbmColor); // add
                // g32.DeleteObject(iconinfo.hbmMask); // add

                final BufferedImage bi = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
                bi.setRGB(0, 0, size, size, colorBits, 0, size);
                return bi;
            } finally {
                com.sun.jna.platform.win32.User32.INSTANCE.ReleaseDC(hwdn, dc);
            }
        } finally {
            User32.INSTANCE.DestroyIcon(new WinDef.HICON(hicon.getPointer()));
            GDI32.INSTANCE.DeleteObject(iconinfo.hbmColor);
            GDI32.INSTANCE.DeleteObject(iconinfo.hbmMask);
        }
    }
}
