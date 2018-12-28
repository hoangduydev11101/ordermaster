package com.master.ordercoffee.root;

import com.uber.rib.core.ViewRouter;

public class RootRouter extends ViewRouter<RootView, RootInteractor, RootBuilder.Component> {

    RootRouter(RootView view, RootInteractor interactor, RootBuilder.Component component) {
        super(view, interactor, component);
    }
}
