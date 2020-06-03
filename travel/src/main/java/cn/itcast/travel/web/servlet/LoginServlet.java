package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取数据(用户名和密码)
        Map<String, String[]> map = request.getParameterMap();
        //2.封装数据
        User user = new User();
        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //3.调用Service完成登录
        UserService userService = new UserServiceImpl();
        User user2 = userService.login(user);

        ResultInfo info = new ResultInfo();
        //4.判断用户对象是否为null
        if (user2 == null){
            //用户名或密码错误
            info.setFlag(false);
            info.setErrorMsg("用户名户或密码错误！");
        }

        //5.判断用户是否激活
        if (user2 != null && !"Y".equals(user2.getStatus())){
            //用户尚未激活
            info.setFlag(false);
            info.setErrorMsg("用户还未激活，请激活！");
        }

        //6.判断登陆成功
        if (user2 != null && "Y".equals(user2.getStatus())){
            request.getSession().setAttribute("user",user2);//登录成功标记
            //登录成功
            info.setFlag(true);
        }

        //7.响应数据
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(info);

        //将json数据写回客户端
        //设置content—type
        response.setContentType("application/json;charset = utf-8");
        response.getWriter().write(json);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
