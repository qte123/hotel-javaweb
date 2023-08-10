package com.tafleo.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface Common {
    //功能选择
    public void selectFunction(HttpServletRequest req, HttpServletResponse resp);
}
