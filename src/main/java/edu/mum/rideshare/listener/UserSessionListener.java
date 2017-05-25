package edu.mum.rideshare.listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import edu.mum.rideshare.model.SysUser;

public class UserSessionListener implements HttpSessionListener{
	private static Map <String,SysUser> userPool=new HashMap<String,SysUser>(); 

	@Override
	public void sessionCreated(HttpSessionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void sessionDestroyed(HttpSessionEvent e) {
		// TODO Auto-generated method stub
		userPool.remove(e.getSession().getId());		
	}
	public static void putUserInPool(HttpSession session,SysUser user){		
		if (!isInPool(user)){
		 userPool.put(session.getId(),user);
		} 
	}
	public static void removeUserFromPool(SysUser user){
		if (null!=user){
			if (null!=userPool&&userPool.size()>0){
				Iterator it = userPool.keySet().iterator();
				while(it.hasNext()) {
					Object key=it.next();				
					SysUser tempUser=(SysUser)userPool.get(key);
					if (user.getUserId()==tempUser.getUserId()){
						it.remove();
					}//if
				}//while
			}//if size>0
		}//null!=user
		
	}
	
	public static void removeUserFromPoolExceptSpecUser(long visacenterId,SysUser specUser){
		
		if (null!=userPool&&userPool.size()>0){
			/**
			Map <String,SysUser> tempPool=new HashMap<String,SysUser>();
			for (String key:userPool.keySet()){
				SysUser tempUser=userPool.get(key);
				if (null!=tempUser.getVisacenterId()&&tempUser.getVisacenterId()==visacenterId&&tempUser.getUserId()!=specUser.getUserId()){
				  //userPool.remove(key);
					tempPool.put(key, tempUser);
				}
			}//for
			***/
			Iterator it = userPool.keySet().iterator();
			while(it.hasNext()) {
				Object key=it.next();				
				SysUser tempUser=(SysUser)userPool.get(key);
				//if (null!=tempUser.getVisacenterId()&&tempUser.getVisacenterId()==visacenterId&&tempUser.getUserId()!=specUser.getUserId()){
					it.remove();
				//}
			}
		}//if
	}
	
	public static List<SysUser> getAllSysUserCurrentVCOnline(long visacenterId){
		List<SysUser> resultList=new ArrayList<SysUser>();
		if (null!=userPool&&userPool.size()>0){
			for (String key:userPool.keySet()){
				SysUser tempUser=userPool.get(key);
				 resultList.add(tempUser);
			}//for		
		}
		return resultList;
	}
	
	public static void removeSessionFromPool(HttpSession session){
		if (null!=session){
			if (null!=userPool&&userPool.size()>0){
				if (userPool.containsKey(session.getId())){
					userPool.remove(session.getId());
				}
			}//if
		}
		
	}
	
	public static boolean isInPool(SysUser user){
		if (null!=user){
			for (String key:userPool.keySet()){
				SysUser tempUser=(SysUser)userPool.get(key);
				if (tempUser.getUserId()==user.getUserId()){
					return true;
				}
			}//for
		}
		return false;		
	}
	public static boolean isInPool(long userId){
		if (userId>0){
			for (String key:userPool.keySet()){
				SysUser tempUser=(SysUser)userPool.get(key);
				if (tempUser.getUserId()==userId){
					return true;
				}
			}//for
		}
		return false;		
	}
	public static boolean isOnline(HttpSession session){
		if (userPool.containsKey(session.getId())){
			return true;
		} else return false;
	}
}
