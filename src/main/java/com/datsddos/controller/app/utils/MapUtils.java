package com.datsddos.controller.app.utils;

import com.datsddos.controller.app.model.participant.OperableParticipant;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
public class MapUtils {

    public Map<String, OperableParticipant> concatenateTwoMapsOverIntersections(Map<String, String> map1, Map<String, String> map2) {
        HashMap<String, OperableParticipant> map3 = new HashMap<>();
        for (Map.Entry<String, String> entry : map2.entrySet()) {
            String map2Key = entry.getKey();
            String map2Value = entry.getValue();
            if (map1.containsKey(map2Key)) {
                OperableParticipant operableParticipant = OperableParticipant.builder().details(map1.get(map2Key)).ipAddress(map2Value).build();
                map3.put(map2Key, operableParticipant);
            }
        }
        return map3;
    }
}
