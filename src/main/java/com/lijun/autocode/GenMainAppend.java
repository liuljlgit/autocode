package com.lijun.autocode;

import com.lijun.autocode.GenProp.GenCommon;

/**
 * 自动代码生成工具,追加代码
 * @author lijun
 */
public class GenMainAppend {

    public static void main(String[] args) throws Exception {
        GenCommon.appendCode("/src/main/java/com/lijun/test/controller/DailyAmountCtrl.java","\t123");
    }
}
