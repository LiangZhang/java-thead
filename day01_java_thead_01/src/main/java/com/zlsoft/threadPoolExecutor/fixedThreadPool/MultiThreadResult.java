package com.zlsoft.threadPoolExecutor.fixedThreadPool;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @version 1.0.0
 * @RESTful：Create-post Read-get update-put/path delete-delete
 * @package: com.glsoft.report.base.thread
 * @ClassName: MultiThreadResult.java
 * @author: L.Z QQ.191288065@qq.com
 * @Description 广力年报-年报基层框架API
 * @createTime 2020年06月11日 17:38:00
 */
@Data
@NoArgsConstructor
public class MultiThreadResult {
    private List resultList;
    private Map resultMap;
}
