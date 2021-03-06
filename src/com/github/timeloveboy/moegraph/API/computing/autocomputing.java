package com.github.timeloveboy.moegraph.API.computing;

import com.alibaba.fastjson.JSONObject;
import com.github.timeloveboy.moegraph.Computing.computing;
import com.github.timeloveboy.moeserver.DefaultHandle;
import com.github.timeloveboy.moeserver.IHttpRequest;
import com.github.timeloveboy.moeserver.IHttpResponse;
import utils.StreamUtil;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by paidian on 17-2-15.
 */

public class autocomputing extends DefaultHandle {
    @Override
    public void GET(IHttpRequest req, IHttpResponse resp) throws IOException {
        resp.write(computing.Now_vid.toString());
    }


    @Override
    public void POST(IHttpRequest req, IHttpResponse resp) throws Exception {
        final String YMDHMS = "yyyyMMddHHmmss";
        DateFormat df = new SimpleDateFormat(YMDHMS);
        String taskname = df.format(new Date()) + "task.out";
        Map<String, String> param = utils.UrlUtil.splitQuery(req.getUrl().getQuery());

        int fansmax = 1000000, existcount = 10;

        if (param.containsKey("fansmax")) {
            fansmax = Integer.parseInt(param.get("fansmax"));
        }
        if (param.containsKey("existcount")) {
            existcount = Integer.parseInt(param.get("existcount"));
        }
        if (param.containsKey("taskname")) {
            taskname = param.get("taskname");
        }
        List<Integer> ids = JSONObject.parseArray(StreamUtil.getBody(req.getBody()), Integer.class);
        if (computing.Start == false) {
            computing.Mapper(ids.toArray(new Integer[ids.size()]), taskname);
        }
    }

    @Override
    public void DELETE(IHttpRequest req, IHttpResponse resp) throws Exception {

        final String YMDHMS = "yyyyMMddHHmmss";
        DateFormat df = new SimpleDateFormat(YMDHMS);
        String taskname = df.format(new Date()) + "task.out";
        Map<String, String> param = utils.UrlUtil.splitQuery(req.getUrl().getQuery());
        if (param.containsKey("taskname")) {
            taskname = param.get("taskname");
        }
        if (computing.Start) {
            computing.save("");
        }
    }
}


