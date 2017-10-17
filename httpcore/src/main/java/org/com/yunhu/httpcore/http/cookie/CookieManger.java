package org.com.yunhu.httpcore.http.cookie;

import org.com.yunhu.httpcore.App;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * @author suhu
 * @data 2017/10/17.
 * @description
 */

public class CookieManger implements CookieJar{
    private static PersistentCookieStore cookieStore;

    public CookieManger() {
        if (cookieStore == null) {
            cookieStore = new PersistentCookieStore(App.getContext());
        }
    }

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        if (cookies!=null&&cookies.size()>0){
            for (Cookie cookie : cookies) {
                cookieStore.add(url,cookie);
            }
        }
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        List<Cookie> cookies = cookieStore.get(url);
        return cookies;
    }
}
