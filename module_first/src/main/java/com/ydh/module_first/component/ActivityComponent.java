package com.ydh.module_first.component;


import com.ydh.module_first.activity.FirstActivity;
import com.ydh.module_first.bean.ActivityModule;
import com.ydh.module_first.bean.ApiModule;

import dagger.Component;
@Component(modules ={ApiModule.class, ActivityModule.class} )
public interface ActivityComponent {
    void inject(FirstActivity firstActivity);
}
