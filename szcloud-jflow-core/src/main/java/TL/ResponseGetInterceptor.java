package TL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class ResponseGetInterceptor extends HandlerInterceptorAdapter
{
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception
	{
		ContextHolderUtils.setResponse(response);
		return true;
	}
}
