package net.bandle.veigar.rsrv.reqlog;

import org.springframework.lang.Nullable;

import javax.servlet.http.HttpServletRequest;

/**
 * 注意： 此方法的实现尽量简单，确保不依赖外部系统，比如写库，远程访问其它系统等，也不要耗时，最好仅向本地文件系统写日志。
 */
public interface RequestLogger {

    /**
     * controller 执行前打印
     *
     * @param request
     * @param requestBody
     */
    public void preLog(HttpServletRequest request, @Nullable Object requestBody);

    /**
     * controller 执行完毕或报异常后打印
     *
     * @param result 返回的结果，无论正常执行完毕还是异常结束都有值
     * @param e 只有异常才有
     */
    public void postLog(Object result, @Nullable Throwable e, Long cost);

}
