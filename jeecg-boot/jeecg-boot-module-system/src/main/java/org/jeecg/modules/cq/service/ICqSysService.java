package org.jeecg.modules.cq.service;

import java.util.List;

public interface ICqSysService {
    void syncUser(String username);

    void syncUsers(List<String> usernames);

    void syncAllUsers();

    void forceSyncAllUsers();
}
