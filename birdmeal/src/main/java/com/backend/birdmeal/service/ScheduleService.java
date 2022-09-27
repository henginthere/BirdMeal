package com.backend.birdmeal.service;
import com.backend.birdmeal.entity.UserEntity;
import com.backend.birdmeal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ScheduleService {

    private final UserRepository userRepository;

    // 0 0 0 1 * *
    @Scheduled(cron = "0 15 17 27 9 2")
    public void run(){
        List<UserEntity> list = userRepository.findAll();

        for(int i=0; i<list.size(); i++){
            list.get(i).setUserMonthMoney(0);

            userRepository.save(list.get(i));
        }
    }
}
