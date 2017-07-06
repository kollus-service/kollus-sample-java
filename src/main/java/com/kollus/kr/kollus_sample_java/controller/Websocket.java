package com.kollus.kr.kollus_sample_java.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 콜백의 상황을 Front-End로 전송하기위한 EndPoint 클래스
 * @author Yang Hyeon Deok
 * @since 2017. 7. 6.
 */
@ServerEndpoint("/ws")
public class Websocket {
	private static Set<Session> users = Collections.synchronizedSet(new HashSet<Session>());
	private static final Logger logger = LoggerFactory.getLogger(Websocket.class);
	@OnOpen
	public void handleOpen(Session session){
		logger.debug(String.format("Websocket : Client Session is Open.(%s)",session.getId()));
		users.add(session);
	}
	@OnMessage
	public void handleMessage(String message) throws IOException{
		Iterator<Session> sessions = users.iterator();
		if(users.size() > 0){
			while(sessions.hasNext()){
				Session session = sessions.next();
				session.getBasicRemote().sendText(message);
				logger.debug(String.format("Websocket : To Client(%s) %s",session.getId(), message));
			}
		}
		else{
			logger.debug("Websocket : No Client Session");
		}
	}
	
	@OnClose
	public void handleClose(Session session){
		logger.debug(String.format("Websocket : Client Session remove complete.(%s)",session.getId()));
		users.remove(session);
	}
	@OnError
	public void handleError(Throwable t){
		logger.info(String.format("WS ERR :  %s", t.getMessage()));
	}
}
