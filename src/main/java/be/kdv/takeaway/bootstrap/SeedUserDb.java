package be.kdv.takeaway.bootstrap;

import be.kdv.takeaway.model.*;
import be.kdv.takeaway.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class SeedUserDb implements CommandLineRunner {

    private final static Logger LOGGER = LoggerFactory.getLogger(SeedUserDb.class);

    private final UserRepository userRepository;
    private final MongoTemplate mongoTemplate;

    public SeedUserDb(
            UserRepository userRepository,
            MongoTemplate mongoTemplate
    ) {
        this.userRepository = userRepository;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void run(String... args) {
    	
    	//mongoTemplate.dropCollection("users");
    	//LOGGER.info("Cleared old data");
 
        if (userRepository.findAll().isEmpty()) {

            //create users
            User user1 = User.builder()
                             .username("Hu Awai")
                             .password("$2a$10$jXpENQlTEQ5ecn90ugTyxOAotZOox7o3Kyign2NT//RZZCokPN5h2")
                             .email("info@takeaway.be")
                             .enabled(true)
                             .build();
            User user2 = User.builder()
                             .username("So Ni")
                             .password("$2a$10$jXpENQlTEQ5ecn90ugTyxOAotZOox7o3Kyign2NT//RZZCokPN5h2")
                             .enabled(true)
                             .build();

            userRepository.save(user1);
            userRepository.save(user2);
            LOGGER.info("2 users created");
        }
    }
}
