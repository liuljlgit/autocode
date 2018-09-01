package com.gen.autocode.enums;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public enum OptType {
    IN_OPT("in","in操作"),
    NOT_IN_OPT("not in","not in操作");

    private String opt;
    private String desc;

    public String getOpt() {
        return opt;
    }

    public String getDesc() {
        return desc;
    }

    private static Map<String, OptType> map = new HashMap<>();
    static {
        if (map == null) {
            map = new HashMap<>();
        }
        Arrays.stream(OptType.values()).forEach(e -> map.put(e.getOpt(), e));
    }


    OptType(String opt, String desc){
        this.opt = opt;
        this.desc = desc;
    }


    public static OptType getOptType(String opt){
        return map.get(opt);
    }
}
