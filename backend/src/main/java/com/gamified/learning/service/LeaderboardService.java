// LeaderboardService.java
package com.gamified.learning.service;

import com.gamified.learning.model.User;
import com.gamified.learning.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LeaderboardService {
    private final UserRepository userRepository;

    public List<LeaderboardEntry> getGlobalLeaderboard() {
        return userRepository.findAll()
                .stream()
                .sorted((u1, u2) -> u2.getTotalXP().compareTo(u1.getTotalXP()))
                .limit(100)
                .map(user -> new LeaderboardEntry(
                    user.getUsername(),
                    user.getTotalXP(),
                    user.getCurrentStreak(),
                    user.getUnlockedBadges() != null ? user.getUnlockedBadges().size() : 0
                ))
                .collect(Collectors.toList());
    }

    public static class LeaderboardEntry {
        public String username;
        public Integer totalXP;
        public Integer currentStreak;
        public Integer badgeCount;

        public LeaderboardEntry(String username, Integer totalXP, Integer currentStreak, Integer badgeCount) {
            this.username = username;
            this.totalXP = totalXP;
            this.currentStreak = currentStreak;
            this.badgeCount = badgeCount;
        }
    }
}