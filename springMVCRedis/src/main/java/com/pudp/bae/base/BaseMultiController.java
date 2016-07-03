package com.pudp.bae.base;
import java.util.Map;

import org.springframework.web.servlet.ModelAndView;
 
public class BaseMultiController {
    protected ModelAndView toView(final String url,final Map<String,Object> map)
    {  
        ModelAndView  view = new ModelAndView(url,map);
        return view;
    }
}