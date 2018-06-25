package com.xrw.demo;


/**
 * @author XiaoRenwu
 * e-mail 18629015421@163.com
 * github https://github.com/xiao-ren-wu
 * @version 1
 * @since 2018/6/24 11:17
 */

public class Outer {

    private String outerClassName;
    private Inner inner;

    public Outer() {
        this.outerClassName = "outer";
    }

    public String getOuterClassName() {
        return outerClassName;
    }
    public void outerShow(){
        //外部类无法直接访问内部类成员，需要实例化对象
        //System.out.println(innerName);
        inner = new Inner();
        String innerName = inner.getInnerName();
        System.out.println(innerName);
        inner.show();
    }

    class Inner{
        private String innerName;

        public Inner() {
            innerName = "inner";
        }

        public String getInnerName() {
            return innerName;
        }
        public void show(){
            System.out.println(outerClassName);
        }
    }

    public static void main(String[] args) {
        Outer outer = new Outer();
        outer.outerShow();
    }
}
