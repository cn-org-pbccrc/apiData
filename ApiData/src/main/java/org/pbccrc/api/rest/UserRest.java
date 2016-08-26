package org.pbccrc.api.rest;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.pbccrc.api.bean.User;
import org.pbccrc.api.biz.UserBiz;
import org.pbccrc.api.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;

@Path("/user")
public class UserRest {
	
	@Autowired
	private UserBiz userBiz;
	
	@GET
	@Path("/register")
	@Produces(MediaType.TEXT_HTML)
	public Response register(
			@QueryParam("userName") String userName, 
			@QueryParam("password") String password,
			@QueryParam("compName") String compName,
			@QueryParam("compTel") String compTel,
			@QueryParam("contactName") String contactName,
			@QueryParam("contactTel") String contactTel){
		
		User user = new User();
		user.setUserName(userName);
		user.setPassword(password);
		user.setCompName(compName);
		user.setCompTel(compTel);
		user.setContactName(contactName);
		user.setCompTel(compTel);
		
		userBiz.addUser(user);
		
		return Response.ok("").build();
	}
	
	
	@GET
	@Path("/isExist")
	@Produces(MediaType.TEXT_HTML)
	public Response isExist(@QueryParam("userName") String userName){
		
		String retData = "N";
		
		if(userBiz.isExist(userName)) {
			
			retData = "Y";
		}
		
		return Response.ok(retData).build();
	}
	
	@GET
	@Path("/login")
	@Produces(MediaType.TEXT_HTML)
	public Response login(@QueryParam("userName") String userName, @QueryParam("password") String password, @Context HttpServletRequest request){
		
		String retData = Constants.RET_STAT_ERROR;
		
		User user = userBiz.login(userName, password);
		
		if (null != user && null != user.getID()) {
			retData = Constants.RET_STAT_SUCCESS;
			request.getSession().setAttribute(Constants.CURRENT_USER, user);
		}
		
		return Response.ok(retData).build();
	}
}
