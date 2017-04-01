package com.iemylife.iot.weather.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by prf on 2017/4/1.
 */
public class JsonUtils {

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
