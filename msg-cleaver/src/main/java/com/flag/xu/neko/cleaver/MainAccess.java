package com.flag.xu.neko.cleaver;

import com.flag.xu.neko.cleaver.amput.AbstractDiscon;
import com.flag.xu.neko.cleaver.amput.DefaultMessageDiacon;
import com.flag.xu.project.system.util.PathUtil;

import java.nio.file.Path;

/**
 * msg cleaver access main class
 * @author xuj
 * @since  2017-02-24-16:52
 * @version 1.0-SNAPSHOT
 */
public class MainAccess {
    public static void main(String[] args) {
        Path path = PathUtil.getPath(MainAccess.class, "file/111.txt");
        AbstractDiscon<Path> discon = new DefaultMessageDiacon<>();
        System.out.println(discon.unpack(path, 17));
    }
}
