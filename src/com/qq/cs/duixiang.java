package com.qq.cs;

public class duixiang {
    public static void main(String[] args){
        Object a=new Strings("123");
//        System.out.println(a);
        a aa = new a(1);
//        System.out.println(a);
//
    }
}

class Strings{
    String a;
    public Strings(String args){
             this.a=args;
    }
    public String toString(){
        return a;
    }
    public String toStrings(){
        return super.toString();
    }
}


class  a{


    public a(Object a) {
       // System.out.println(a.hashCode());
//         a ="b";
        System.out.println(a);
       // System.out.println(a.hashCode());
    }

    public void aa(String A){
        A="2";
    }

    //重写finalize方法
    protected void finalize() {

        System.out.println("aa被销毁了");
    }
}