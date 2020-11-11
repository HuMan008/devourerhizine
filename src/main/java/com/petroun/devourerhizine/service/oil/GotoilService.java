package com.petroun.devourerhizine.service.oil;

/**
 * @author think <syj247@qq.com>、
 * @date 2020/11/9、11:29
 */
public interface GotoilService {
    boolean appendGotoilRefuelQueue(String id, int redo);

    boolean appendGotoilQueryQueue(String id);
}
