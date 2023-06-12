package proxy;

import proxy.Service;

/**
 * @author: beiyuan
 * @className: SuperStarLi
 * @date: 2022/6/6  20:29
 */
public class SuperStarLi implements Service {
    @Override
    public void sing() {
        System.out.println("Li sing..");
    }

    @Override
    public String show() {
        return "Li is showing";
    }
}
