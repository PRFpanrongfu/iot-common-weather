package com.iemylife.iot.weather.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.*;

/**
 * Created by prf on 2017/4/1.
 */
public class JsonUtils {
    private static ObjectMapper mapper = new ObjectMapper();

    public static Object getValue(String json, String key, Integer lv) {
        Integer currentLv = 1;
        try {
            Map<String, Object> root = mapper.readValue(json, Map.class);
            if (root.size() == 0) {
                throw new Exception("格式有误");
            }

            for (Map.Entry<String, Object> entry : root.entrySet()) {


            }
            currentLv++;

            if (lv < currentLv) {
                return null;
            }


        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("格式有误");
        } catch (Exception e) {
            e.printStackTrace();
        }


        return "";
    }

    /**
     * 从json中读取tagPath处的值 tagPath用 :分隔
     *
     * @param json
     * @param tagPath
     * @return
     * @throws Exception
     */
    public static List<String> readValueFromJson(String json, String tagPath) throws Exception {
        // 返回值
        List<String> value = new ArrayList<String>();
        if (CommonUtil.isEmpty(json) || (CommonUtil.isEmpty(tagPath))) {
            return value;
        }
        ObjectMapper mapper = CommonUtil.getMapperInstance(false);
        String[] path = tagPath.split(":");
        JsonNode node = mapper.readTree(json);
        getJsonValue(node, path, value, 1);
        return value;
    }

    public static void getJsonValue(JsonNode node, String[] path, List<String> values, int nextIndex) {
        if (CommonUtil.isEmpty(node)) {
            return;
        }
        // 是路径的最后就直接取值
        if (nextIndex == path.length) {
            if (node.isArray()) {
                for (int i = 0; i < node.size(); i++) {
                    JsonNode child = node.get(i).get(path[nextIndex - 1]);
                    if (CommonUtil.isEmpty(child)) {
                        continue;
                    }
                    values.add(child.toString());
                }
            } else {
                JsonNode child = node.get(path[nextIndex - 1]);
                if (!CommonUtil.isEmpty(child)) {
                    values.add(child.toString());
                }
            }
            return;
        }
        // 判断是Node下是集合还是一个节点
        node = node.get(path[nextIndex - 1]);
        if (node.isArray()) {
            for (int i = 0; i < node.size(); i++) {
                getJsonValue(node.get(i), path, values, nextIndex + 1);
            }
        } else {
            getJsonValue(node, path, values, nextIndex + 1);
        }
    }
}
