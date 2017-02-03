package com.james.tinyurl.controller;

import com.james.tinyurl.handler.TinyURLHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by haozhexu on 2/2/17.
 */
@RestController
public class DebugRestController {

    @Autowired
    private TinyURLHandler tinyURLHandler;

    @RequestMapping(value = "/toshort")
    public String longToShort(@RequestParam(value = "long") String longURL) {
        return tinyURLHandler.getShortHashFromLongURL(longURL);
    }

    @RequestMapping(value = "/tolong")
    public String shortToLong(@RequestParam(value = "hash") String hash) {
        return tinyURLHandler.retrieveLongURLByShortHash(hash);
    }
}
