# 异常log收集辅助App

    此框架使用了uncaughtException与ContentProvider的配合

使用方法:

 步骤一:
    将testhelplib项目复制并且添加到你自己的项目中,并且调用:
    TestHelp.getInstance().initErrorCollect(application); //初始化收集异常

    或者在需要日志的地方调用:
    TestHelp.getInstance().insertLog(CommonAppLife.app, message); //插入日志

 步骤二:
    将此项目的app打包并且安装到手机上与被测试的app配合

