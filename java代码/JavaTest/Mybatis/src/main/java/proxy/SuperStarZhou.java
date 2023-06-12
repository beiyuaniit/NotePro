package proxy;

import proxy.Service;

/**
 * @author: beiyuan
 * @className: SuperStarZhou
 * @date: 2022/6/6  20:28
 */
public class SuperStarZhou implements Service {
    @Override
    public void sing() {
        System.out.println("Zhou sing..");
    }

    @Override
    public String show(){
        return "Zhou is showing";
    }

    public void drunk(){
        System.out.println("Zhou drunk..");
    }
}
