package com.projekt.forum.repositories;

import com.projekt.forum.entity.GrantedAuthorityEntity;
import com.projekt.forum.entity.UserEntity;
import com.projekt.forum.utility.DateUtility;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection =  EmbeddedDatabaseConnection.H2)
public class CustomUserDetailsRepositoryTest {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;

    @Autowired
    public CustomUserDetailsRepositoryTest(UserRepository userRepository, AuthorityRepository authorityRepository){
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
    }

//    @BeforeEach
//    private void SetUpData(){
//    }
    private GrantedAuthorityEntity userRole = new GrantedAuthorityEntity("user");
    private GrantedAuthorityEntity adminRole = new GrantedAuthorityEntity("admin");

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void saveUserWithRole(){
        Date date = DateUtility.getCurrentDate();
        authorityRepository.save(userRole);
        authorityRepository.save(adminRole);

        UserEntity entity = userRepository.save(new UserEntity(userRole,"123","Krisent","krisent@gmail.com", date));


        Assertions.assertNotNull(entity);
        Assertions.assertEquals(date,entity.getCreationDate());
        GrantedAuthorityEntity entities = entity.getAuthority();
        Assertions.assertNotNull(entity);
        Assertions.assertEquals(entities, userRole);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void JoinUsersWithRole_fetchesDataCorrectly(){
        Date date = DateUtility.getCurrentDate();
        authorityRepository.save(userRole);
        authorityRepository.save(adminRole);
        userRepository.save(new UserEntity(userRole,"123","Krisent","krisent@gmail.com", date ));
        userRepository.save(new UserEntity(adminRole,"23","admin","admin@gmail.com", date ));

        Collection<UserEntity> users = userRepository.joinUsersWithRole();


        Assertions.assertNotNull(users);
        Assertions.assertEquals(2, users.size());
        for (UserEntity user:users){
            Assertions.assertNotNull(user.getUsername());
            Assertions.assertTrue(user.getUsername().equals("Krisent")||user.getUsername().equals("admin"));
            Assumptions.assumingThat(user.getUsername().equals("Krisent"),()->{
                Assertions.assertEquals(userRole,user.getAuthority());
            });

            Assumptions.assumingThat(user.getUsername().equals("admin"),()->{
                Assertions.assertEquals(adminRole,user.getAuthority());
            });
        }
    }


}
