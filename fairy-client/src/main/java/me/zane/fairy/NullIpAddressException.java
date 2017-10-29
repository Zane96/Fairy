package me.zane.fairy;

/**
 * Created by Zane on 2017/10/24.
 * Email: zanebot96@gmail.com
 */

public class NullIpAddressException extends Exception{
    public NullIpAddressException() {
        super("The IpAddress can't be null when start a net connect");
    }
}
