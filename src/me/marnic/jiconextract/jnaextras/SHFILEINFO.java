package me.marnic.jiconextract.jnaextras;

import com.sun.jna.Structure;
import com.sun.jna.platform.win32.WinDef;

import java.util.Arrays;
import java.util.List;

public class SHFILEINFO extends Structure {

    public WinDef.HICON hIcon;
    public int iIcon;
    public WinDef.DWORD dwAttributes;
    public char[] szDisplayName = new char[260];
    public char[] szTypeName = new char[80];

    @Override
    protected List<String> getFieldOrder() {
        return Arrays.asList("hIcon", "iIcon", "dwAttributes", "szDisplayName", "szTypeName");
    }
}
