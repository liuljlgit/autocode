package com.lijun.autocode;

import com.lijun.autocode.GenProp.GenCommon;

/**
 * 自动代码生成工具,追加代码
 * @author lijun
 */
public class GenMainAppend {
    public static String methodName = "testMyAppendMethod";

    public static String IServicePath =
            "    /**\n" +
            "     * 保存记录\n" +
            "     * @param dailyAmount\n" +
            "     */\n" +
            "     void "+methodName+"(DailyAmount dailyAmount) throws Exception;\n\n}";

    public static void main(String[] args) throws Exception {
        //GenCommon.appendCode("/src/main/java/com/lijun/test/controller/DailyAmountCtrl.java",null);
        //GenCommon.appendCode("/src/main/java/com/lijun/test/service/impl/DailyAmountService.java",null);
        GenCommon.appendCode("/src/main/java/com/lijun/test/service/inft/IDailyAmountService.java",GenMainAppend.IServicePath);
        //GenCommon.appendCode("/src/main/java/com/lijun/test/dao/inft/IDailyAmountDao.java",null);
        //GenCommon.appendCode("/src/main/resources/test/DailyAmountMapper.xml",null);
    }
}
