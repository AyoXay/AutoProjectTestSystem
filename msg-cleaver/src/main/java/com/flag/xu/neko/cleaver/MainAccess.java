package com.flag.xu.neko.cleaver;

import com.flag.xu.neko.cleaver.amput.AbstractDiscon;
import com.flag.xu.neko.cleaver.amput.DefaultMessageDiacon;

/**
 * msg cleaver access main class
 * @author xuj
 * @since  2017-02-24-16:52
 * @version 1.0-SNAPSHOT
 */
public class MainAccess {
    public static void main(String[] args) {
        String input = "串2\t12:13:09 Ywtb\t232302FE4C413958-4C3434423747454C-47363833380101BB-11020C0C0D020101\r\n"
                +"串2\t12:13:09 Ywtb\t03010000000001B8-15F5271D64020028-1A00000201010328-4E204E2028000027";
        AbstractDiscon discon = new DefaultMessageDiacon();
        System.out.println(discon.unpack(input, 17));
    }
}
