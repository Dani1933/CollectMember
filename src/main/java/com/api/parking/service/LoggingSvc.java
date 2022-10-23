package com.api.parking.service;

import java.util.HashMap;

public interface LoggingSvc {
    void createLog(HashMap<String,Object>data, String type);
}
