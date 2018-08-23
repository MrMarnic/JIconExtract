package me.marnic.jiconextract.jnaextras;

import com.sun.jna.Native;
import com.sun.jna.Platform;
import com.sun.jna.platform.win32.BaseTSD;
import com.sun.jna.platform.win32.Guid;
import com.sun.jna.platform.win32.Shell32;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.ptr.PointerByReference;

/**
 * Created by Marcel Jedig on Aug,2018
 */

public interface Shell32Extra extends Shell32 {

    Shell32Extra INSTANCE = (Shell32Extra) Native.loadLibrary((Platform.isWindows() ? "shell32" : "c"),
            Shell32Extra.class);

    BaseTSD.DWORD_PTR SHGetFileInfo(String pszPath, int dwFileAttributes, SHFILEINFO psfi, int cbFileInfo, int uFlags);

    WinNT.HRESULT SHGetImageList(int iImageList, Guid.REFIID riid, PointerByReference ppvObj);

    String IID_IImageList = "46EB5926-582E-4017-9FDF-E8998DAA0950";

    int FILE_ATTRIBUTE_NORMAL = 0x00000080;

    int ILD_TRANSPARENT = 0x00000001;
    int ILD_IMAGE = 0x00000020;
}
