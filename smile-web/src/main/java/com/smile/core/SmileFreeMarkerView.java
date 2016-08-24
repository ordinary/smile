package com.smile.core;

import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 扩展spring的FreemarkerView，加上base属性。
 * <p/>
 * 支持jsp标签，Application、Session、Request、RequestParameters属性
 */
public class SmileFreeMarkerView extends FreeMarkerView {

    /**
     * 部署路径属性名称
     */
    public static final String CONTEXT_PATH = "base";


    /**
     * 在model中增加部署路径ctx，方便处理部署路径问题。
     *
     * @throws Exception
     */
    @Override
    protected void exposeHelpers(Map<String, Object> model, HttpServletRequest request) {
        try {
            super.exposeHelpers(model, request);
            model.put(CONTEXT_PATH, request.getContextPath());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
