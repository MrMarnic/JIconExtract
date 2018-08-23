package me.marnic.jiconextract.jnaextras;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.COM.Unknown;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.ptr.PointerByReference;

/**
 * Created by Marcel Jedig on Aug,2018
 */

public class IImageList extends Unknown {

    public IImageList(Pointer pvInstance) {
        super(pvInstance);
    }

    public WinNT.HRESULT GetIcon(int i, int flags, PointerByReference picon) {
        return (WinNT.HRESULT) _invokeNativeObject(10,
                new Object[]{this.getPointer(), i, flags, picon},WinNT.HRESULT.class);
    }
}
