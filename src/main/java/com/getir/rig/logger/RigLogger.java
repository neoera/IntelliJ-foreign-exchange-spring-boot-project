package com.getir.rig.logger;

public interface RigLogger extends org.slf4j.Logger {

    RigLogger with(Object o);
    RigLogger with(String s, Object o);

    void error(Throwable throwable, String s, Object... args);
    void warn(Throwable throwable, String s, Object... args);
    void success(String s, Object... args);
    void dashboard(String s, Object... args);
}
