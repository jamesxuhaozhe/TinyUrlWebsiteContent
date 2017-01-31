package com.james.tinyurl.handler;

import com.james.tinyurl.algo.Base62;
import com.james.tinyurl.dao.URLMappingRepository;
import com.james.tinyurl.model.URL;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import java.util.List;

/**
 * Created by haozhexu on 1/30/17.
 */
public class TinyURLHandler {

    private Logger logger = Logger.getLogger(TinyURLHandler.class);

    @Autowired
    private URLMappingRepository urlMappingRepository;

    /**
     * Generate the short url hash for the given long url in question
     * @param longURL longURL in question
     * @param model model object that we can fill with infomation we would like to present to the view
     * @return {@code true} if the operation succeeds, {@code false} otherwise
     */
    public boolean generateShortURLHash(String longURL, Model model) {
        String shortURLHash = null;
        boolean isSuccess = false;
        try {
            shortURLHash = getShortURLHash(longURL);
            model.addAttribute("shortURL", shortURLHash);
            isSuccess = true;
        } catch (Exception e) {
            logger.error("Oh something happened when generating short url hash", e);
            model.addAttribute("error", "Something happened and please try later");
        }
        return isSuccess;
    }

    private String getShortURLHash(String longURL) {
        CheckDBResponse response = checkLongURLinRepo(longURL);
        if (response.isInRepo) {
            return Base62.encode(response.getLongURL().getId());
        } else {
            return Base62.encode(insertLongURLIntoRepo(longURL));
        }
    }

    /**
     * Checks if the given long url exists in our database or not.
     * @param longURL long URL in question
     * @return a wrapper object {@link CheckDBResponse} wraps around the result
     */
    private CheckDBResponse checkLongURLinRepo(String longURL) {
        List<URL> urls = urlMappingRepository.findAll();
        for (URL url : urls) {
            if (url.getLongURL().equals(longURL)) {
                return new CheckDBResponse().setLongURL(url).setInRepo(true);
            }
        }

        return new CheckDBResponse().setLongURL(null).setInRepo(false);
    }

    /**
     * Insert the the given longURL into our database and return the id.
     * @param longURL longURL we need to insert into our database
     * @return the inserted id
     */
    private long insertLongURLIntoRepo(String longURL) {
        URL url = new URL().setLongURL(longURL);
        urlMappingRepository.saveAndFlush(url);
        return url.getId();
    }

    @Data
    @Accessors(chain = true)
    private static class CheckDBResponse {
        @Getter
        @Setter
        private URL longURL;

        @Getter
        @Setter
        private boolean isInRepo;
    }
}
