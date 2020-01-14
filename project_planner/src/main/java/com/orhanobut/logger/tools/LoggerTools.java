package com.orhanobut.logger.tools;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.CsvFormatStrategy;
import com.orhanobut.logger.DiskLogAdapter;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

/**
 * @author genialsir@163.com (GenialSir) on 2019/12/12
 */
public class LoggerTools {

    public static void init(boolean isDebug, String appNickName) {
        if (isDebug) {
            PrettyFormatStrategy prettyFormatStrategy = PrettyFormatStrategy.newBuilder()
                    .tag(appNickName)
                    .build();
            Logger.addLogAdapter(new AndroidLogAdapter(prettyFormatStrategy));
        } else {
            CsvFormatStrategy csvFormatStrategy = CsvFormatStrategy.newBuilder()
                    .tag(appNickName)
                    .build();
            Logger.clearLogAdapters();
            Logger.addLogAdapter(new DiskLogAdapter(csvFormatStrategy));
        }
    }
}
