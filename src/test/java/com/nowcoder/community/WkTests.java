package com.nowcoder.community;

import java.io.IOException;

/**
 * @Author: Dai
 * @Date: 2024/05/21 20:14
 * @Description: WkTests
 * @Version: 1.0
 */
public class WkTests {

    public static void main(String[] args) {
        String cmd = "f:/IDEA/wkhtmltopdf/bin/wkhtmltoimage --quality 75 https://www.nowcoder.com f:/IDEA/wk-images/2.png";
        try {
            Runtime.getRuntime().exec(cmd);
            System.out.println("ok.");
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
