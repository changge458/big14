package com.pibigstar.qq.utils;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.Cookie;

import java.util.Set;

public class GetCookie {

    public static void main(String[] args) throws Exception {
        String username = "";
        String password = "";

        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        // 等待后台 JS 执行
        webClient.waitForBackgroundJavaScript(10000);

        HtmlPage page = webClient.getPage("https://i.qq.com/");
        DomElement loginFrame = page.getElementById("login_frame");
        page = webClient.getPage(loginFrame.getAttribute("src"));

        page.getElementById("switcher_plogin").click();

        // 获取用户名和密码 Input 并输入
        DomElement usernameInput = null;
        DomElement passwordInput = null;
        for (int i = 0; i < 20; i++) {
            usernameInput = page.getElementById("u");
            if (usernameInput != null) {
                break;
            }
            synchronized (page) {
                page.wait(500);
            }
        }
        passwordInput = page.getElementById("p");
        usernameInput.setAttribute("value", username);
        passwordInput.setAttribute("value", password);

        // 获取并点击登陆按钮
        DomElement loginBtn = page.getElementById("login_button");
        loginBtn.click();

        // 获取 Cookie
        Set<Cookie> cookies = webClient.getCookieManager().getCookies();
        for (Cookie cookie : cookies) {
            System.out.println(cookie.getName() + ": " + cookie.getValue());
        }
    }
}
