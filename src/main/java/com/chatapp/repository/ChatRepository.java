package com.chatapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.chatapp.model.Chat;
import com.chatapp.model.User;

public interface ChatRepository extends JpaRepository<Chat,Integer>{

	@Query("Select c from Chat c where c.isGroup=false And :user Member of c.users And :reqUser Member of c.users")
	public Chat findSingleChatByUserIds(@Param("user") User user,@Param("reqUser") User reqUser);
	
	
	@Query("Select c from Chat c join c.users u where u.id=:userId")
	public List<Chat> findChatByUserid(@Param("userId") Integer userId);
}
