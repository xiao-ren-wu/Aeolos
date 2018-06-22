package com.xrw.common.utils;

import java.io.*;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author xiaorenwu
 */
public class UrlDemo {
    public static void main(String[] args)throws Exception {
        getHtmlMsg();
        worm();
        downLoadPic();
    }
    /**通过图片链接下载图片*/

    private static void downLoadPic() throws Exception{
        //通过FileReader关联本地文件
        FileReader fr = new FileReader("E://picture.html");
        //添加缓冲区
        BufferedReader br=new BufferedReader(fr);
        //用来记录网址
        String msg=null;
        //用来更改图片名称
        int count=0;
        while(null!=(msg=br.readLine())){
            //创建URL对象，并关联指定的网址
            URL url=new URL(msg);
            //打开指定的网址，并关联流对象
            InputStream is=url.openStream();
            // 上网中
            Thread.sleep(1000);
            //加缓冲区
            BufferedInputStream bis=new BufferedInputStream(is);
            //指定要存放的地址
            count++;
            String path="E:\\picture\\pic" +count+".jpg";
            System.out.println(path);
            FileOutputStream fos = new FileOutputStream(path);
            //加缓冲区
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            byte[] buf=new byte[1024];
            int len;
            while(-1!=(len=bis.read(buf))){
                bos.write(buf,0,len);
            }
            is.close();
            bis.close();
            bos.close();
            System.out.println("=============================================已经下载"+count+"个=============================================");
        }
        br.close();
    }

    /**通过网页源码和正则表达式，获取图片链接*/
    private static void worm() throws Exception {
        FileReader fr = new FileReader("E:\\girl.html");
        BufferedReader br = new BufferedReader(fr);
        FileWriter fw=new FileWriter("E://picture.html");
        BufferedWriter bw=new BufferedWriter(fw);
        int len=-1;
        String buf=null;
        StringBuilder sb=new StringBuilder();
        while(null!=(buf=br.readLine())){
            sb.append(buf);
        }
        String AllTxt=sb.toString();
        //String reges="src=\"(//club2.autoimg.cn)(.*?)(\\.jpg)\"";
        String reges="<img class=\"floor-img\" src=\"(.*?)\" alt";
        Pattern patt=Pattern.compile(reges);
        Matcher mat=patt.matcher(AllTxt);
        int index=0;
        while(mat.find(index)){
            String msg=mat.group(1);
            System.out.println(msg);
            bw.write(msg);
            bw.newLine();
            index=mat.end();
        }
        bw.close();
        br.close();
    }
    /**通过给定的网页连接将HTML源码保存到本地*/
    private static void getHtmlMsg() throws Exception {
        //关联一个URL
        URL url = new URL("http://www.happymmall.com/index.html");
        //打开URL连接并关联InputStream
        InputStream is=url.openStream();
        BufferedReader br=new BufferedReader(new InputStreamReader(is,"utf-8"));
        Thread.sleep(1000);
        FileWriter fw = new FileWriter("E:\\girl.html");
        BufferedWriter bw = new BufferedWriter(fw);
        String str=null;
        StringBuilder sb = new StringBuilder();
        byte[] buf=new byte[1024];
        int len=-1;
        while(null!=(str=br.readLine())){
            sb.append(str);
            bw.write(str);
            bw.newLine();
        }
        bw.close();
        br.close();
        String allTxt=sb.toString();
        System.out.println("hello");
        System.out.println(allTxt);
    }
}

